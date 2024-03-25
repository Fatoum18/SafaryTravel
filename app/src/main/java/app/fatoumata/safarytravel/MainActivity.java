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

import app.fatoumata.safarytravel.adapters.CountryAdapter;
import app.fatoumata.safarytravel.models.CountryModel;

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
        textView = findViewById(R.id.user_details);
        countryGridView = findViewById(R.id.gridCountry);
        user = auth.getCurrentUser();
        if(user == null)
        {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else
        {
            textView.setText(user.getEmail());

        }


        ArrayList<CountryModel> countryModels = new ArrayList<>();
        countryModels.add(new CountryModel("1","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("2","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("3","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("4","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("5","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("6","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("7","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("7","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("7","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("7","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        countryModels.add(new CountryModel("7","Cameroon","https://mainfacts.com/media/images/coats_of_arms/cy.png"));
        CountryAdapter countryAdapter =  new CountryAdapter(this,countryModels);
        countryGridView.setAdapter(countryAdapter);

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