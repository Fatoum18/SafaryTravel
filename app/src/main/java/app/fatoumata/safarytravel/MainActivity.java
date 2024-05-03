package app.fatoumata.safarytravel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import app.fatoumata.safarytravel.adapters.CountryAdapter;
import app.fatoumata.safarytravel.databinding.ActivityMainBinding;
import app.fatoumata.safarytravel.models.CountryModel;
import app.fatoumata.safarytravel.service.Converter;
import app.fatoumata.safarytravel.service.CountryService;
import app.fatoumata.safarytravel.service.FMService;
import app.fatoumata.safarytravel.service.dto.CountryOfRegionDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  CountryAdapter.CountrAdapterListener{

    FirebaseAuth auth;

    FirebaseUser user;

    GridView countryGridView;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();
        countryGridView = findViewById(R.id.gridCountry);
        user = auth.getCurrentUser();
        if(user == null)
        {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
            return;
        }

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        FMService.registerDevice(token);

                        Log.i("FCM", token);
                    }
                });

        CountryService.api.getCountryOfRegion("europe").enqueue(new Callback<List<CountryOfRegionDto>>() {
            @Override
            public void onResponse(Call<List<CountryOfRegionDto>> call, Response<List<CountryOfRegionDto>> response) {

                if(response.body()!=null){
                    List<CountryModel> list =    Converter.countryDtosToModels(response.body());
                    CountryAdapter countryAdapter =  new CountryAdapter(MainActivity.this,list,MainActivity.this);
                    countryGridView.setAdapter(countryAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<CountryOfRegionDto>> call, Throwable throwable) {

            }
        });

        binding.toolbar.inflateMenu(R.menu.main_activity_menu);

    }




    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.m_logout ){
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCountryClick(CountryModel countryModel) {

        CountryActivity.startActivity(this,countryModel);
    }
}