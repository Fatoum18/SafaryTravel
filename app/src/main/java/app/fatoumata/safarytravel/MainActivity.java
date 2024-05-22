package app.fatoumata.safarytravel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import android.Manifest;
import android.widget.Toast;

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

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements  CountryAdapter.CountrAdapterListener{

    FirebaseAuth auth;

    FirebaseUser user;

    ActivityMainBinding binding;


    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(
                                    binding.getRoot(),
                                    "The user denied the notifications ):",
                                    Snackbar.LENGTH_INDEFINITE
                            )
                            .setAction("Settings", view -> {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("package:"+getString(R.string.app_package)));
//                                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getString(R.string.app_package));

                                startActivity(intent);
                            }) .show();


                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public void onStart() {
        super.onStart();



        if(user != null && TextUtils.isEmpty(user.getDisplayName())){
            Intent intent = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                intent = new Intent(getApplicationContext(), FirstRunActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermission();
        }else{
            createNotificationChannel();
        }


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();



        binding.homeTitle.setText(getString(R.string.home_title, user.getDisplayName()));

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    FMService.registerDevice(token);

                    Log.i("FCM", token);
                });

        CountryService.api.getCountryOfRegion("europe").enqueue(new Callback<List<CountryOfRegionDto>>() {
            @Override
            public void onResponse(Call<List<CountryOfRegionDto>> call, Response<List<CountryOfRegionDto>> response) {

                if(response.body()!=null){
                    List<CountryModel> list =    Converter.countryDtosToModels(response.body());
                    CountryAdapter countryAdapter =  new CountryAdapter(MainActivity.this,list,MainActivity.this);
                    binding.gridCountry.setAdapter(countryAdapter);
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<CountryOfRegionDto>> call, Throwable throwable) {

                Log.d("TAG", "onFailure: "+throwable.getMessage());
            }
        });

        binding.toolbar.inflateMenu(R.menu.main_activity_menu);
        binding.toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);

    }




    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String CHANNEL_ID = getString(R.string.default_notification_channel_id);
            CharSequence name = getString(R.string.default_notification_channel_name);

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG-SAFARY", "checkPermission:  isGranted");
            createNotificationChannel();
        }
        else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.POST_NOTIFICATIONS)) {
            requestPermissionLauncher.launch( Manifest.permission.POST_NOTIFICATIONS);
        } else{
            Log.d("TAG-SAFARY", "checkPermission:  NOT Granted");
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch( Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}