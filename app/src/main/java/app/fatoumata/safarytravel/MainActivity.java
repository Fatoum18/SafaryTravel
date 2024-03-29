package app.fatoumata.safarytravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import app.fatoumata.safarytravel.adapters.CountryAdapter;
import app.fatoumata.safarytravel.models.CountryModel;
import app.fatoumata.safarytravel.service.Converter;
import app.fatoumata.safarytravel.service.CountryService;
import app.fatoumata.safarytravel.service.dto.CountryOfRegionDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView textView;
    FirebaseUser user;

    GridView countryGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        countryGridView = findViewById(R.id.gridCountry);
        user = auth.getCurrentUser();
        if(user == null)
        {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }



        CountryService.api.getCountryOfRegion("europe").enqueue(new Callback<List<CountryOfRegionDto>>() {
            @Override
            public void onResponse(Call<List<CountryOfRegionDto>> call, Response<List<CountryOfRegionDto>> response) {

                if(response.body()!=null){
                    List<CountryModel> list =    Converter.countryDtosToModels(response.body());
                    CountryAdapter countryAdapter =  new CountryAdapter(MainActivity.this,list);
                    countryGridView.setAdapter(countryAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<CountryOfRegionDto>> call, Throwable throwable) {

            }
        });






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.m_logout ){
            logout();
        }

        return super.onOptionsItemSelected(item);
    }
}