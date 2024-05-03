package app.fatoumata.safarytravel;

import static app.fatoumata.safarytravel.utils.DBUtils.getCurrentUser;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.osmdroid.config.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import app.fatoumata.safarytravel.databinding.ActivityCountryBinding;
import app.fatoumata.safarytravel.models.CountryModel;
import app.fatoumata.safarytravel.ui.main.SectionsPagerAdapter;
import app.fatoumata.safarytravel.utils.DBUtils;


public class CountryActivity extends AppCompatActivity {

    static final  String COUNTRY_NAME = "COUNTRY_NAME";

    private ActivityCountryBinding binding;


    private  String countryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCountryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Configuration.getInstance().load(getApplication(), PreferenceManager.getDefaultSharedPreferences(getApplication()));

        binding.toolbar.setNavigationOnClickListener(view -> finish());

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(COUNTRY_NAME)){
            countryName = bundle.getString(COUNTRY_NAME);
            binding.toolbar.setTitle(countryName);

            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),countryName);
            ViewPager viewPager = binding.viewPager;
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = binding.tabs;
            tabs.setupWithViewPager(viewPager);
            FloatingActionButton fab = binding.fab;
            FirebaseUser user = getCurrentUser();
            if(user!=null){
                if(Objects.equals(user.getEmail(), "admin@safarytravel.com")){
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(view ->  createChallengeDialog());
                }
            }

        }

    }


    private  void createChallengeDialog(){


        EditText editText = new EditText(this);
        editText.setHint("Challenge name");
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Challenge")
                .setMessage("Create challenge in order to allow people to participate")
                .setView(editText)
                .setPositiveButton("Create", (dialogInterface, i) -> {

                    String name =  editText.getText().toString();
                    if(!TextUtils.isEmpty(name)){
                        createChallenge(name);
                    }else{
                        Toast.makeText(this,"Challenge name must not be empty",Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .setNegativeButton("Cancel",(dialogInterface, i) -> {

                }).show();
    }

    private  void createChallenge(String challengeName){

        Map<String, Object> challenge =  new HashMap<>();
        challenge.put("name",challengeName);
        challenge.put("createAt", new Date());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(DBUtils.Collection.COUNTRIES)
                .document(countryName)
                .collection(DBUtils.Collection.CHALLENGES)
                .add(challenge);
    }


    public static void startActivity(AppCompatActivity compatActivity, CountryModel countryModel){
        Intent intent =  new Intent(compatActivity,CountryActivity.class);
        Bundle bundle =  new Bundle();
        bundle.putString(COUNTRY_NAME, countryModel.getName());
        intent.putExtras(bundle);
        compatActivity.startActivity(intent);

    }
}