package app.fatoumata.safarytravel;

import static app.fatoumata.safarytravel.utils.DBUtils.getCurrentUser;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.osmdroid.config.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import app.fatoumata.safarytravel.databinding.ActivityChallengeBinding;
import app.fatoumata.safarytravel.databinding.ActivityCountryBinding;
import app.fatoumata.safarytravel.models.CountryModel;
import app.fatoumata.safarytravel.ui.challenge.SectionsChallengePagerAdapter;
import app.fatoumata.safarytravel.ui.main.SectionsPagerAdapter;
import app.fatoumata.safarytravel.ui.main.challenge.adapter.ChallengeAdapter;
import app.fatoumata.safarytravel.utils.DBUtils;


public class ChallengeActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    static final  String COUNTRY_NAME = "COUNTRY_NAME";
    static final  String CHALLENGE_KEY = "CHALLENGE_KEY";
    static final  String CHALLENGE_NAME = "CHALLENGE_NAME";

    private ActivityChallengeBinding binding;


    private ActivityResultLauncher<Intent> launcher;
    Uri  imageUri;

    private  String countryName;
    private  String challengeKey ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Configuration.getInstance().load(getApplication(), PreferenceManager.getDefaultSharedPreferences(getApplication()));
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        uploadImageToFirebase();
                    }
                }
        );



        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(COUNTRY_NAME) && bundle.containsKey(CHALLENGE_NAME)&& bundle.containsKey(CHALLENGE_KEY)){
            challengeKey = bundle.getString(CHALLENGE_KEY);
            countryName = bundle.getString(COUNTRY_NAME);
            binding.title.setText(countryName);

            final String challengeName = bundle.getString(CHALLENGE_NAME);
            binding.challengeName.setText(challengeName);

            SectionsChallengePagerAdapter sectionsPagerAdapter = new SectionsChallengePagerAdapter(this, getSupportFragmentManager(),countryName);
            ViewPager viewPager = binding.viewPager;
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = binding.tabs;
            tabs.setupWithViewPager(viewPager);
            FloatingActionButton fab = binding.fab;

            fab.setOnClickListener(view -> openCamera());
        }

    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image from camera");
        imageUri =  getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if(intent.resolveActivity(getPackageManager()) !=null){
            launcher.launch(intent);
        }
    }

    private void uploadImageToFirebase()  {
        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference countryImagesRef = storageRef.child("images/"+countryName+"/"+imageUri.getLastPathSegment()+".jpg");

        try{

            UploadTask uploadTask = countryImagesRef.putFile(imageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.d("UPLOAD_IMAGE", "onFailure: "+exception.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    countryImagesRef.getDownloadUrl().addOnSuccessListener(uri->{

                        createMedia(uri.toString());

                    });
                }
            });
        }catch(Exception e){

        }

    }


    private void createMedia(String url){

    if(challengeKey!=null){
        FirebaseUser user = getCurrentUser();
        if(user!=null){

            Map<String, Object> challengePhoto =  new HashMap<>();
            challengePhoto.put("url",url);
            challengePhoto.put("userId",user.getUid());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(DBUtils.Collection.COUNTRIES+"/"+countryName+"/"+DBUtils.Collection.CHALLENGES+"/"+challengeKey+"/photos").add(challengePhoto);

        }
    }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        permissionsToRequest.addAll(Arrays.asList(permissions).subList(0, grantResults.length));
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    public static void startActivity(AppCompatActivity compatActivity, String countryName, String challengeName,String challengeKey){
        Intent intent =  new Intent(compatActivity, ChallengeActivity.class);
        Bundle bundle =  new Bundle();
        bundle.putString(COUNTRY_NAME, countryName);
        bundle.putString(CHALLENGE_KEY, challengeKey);
        bundle.putString(CHALLENGE_NAME, challengeName);
        intent.putExtras(bundle);
        compatActivity.startActivity(intent);


    }
}