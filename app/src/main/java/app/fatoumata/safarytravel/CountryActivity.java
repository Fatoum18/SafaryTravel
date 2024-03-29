package app.fatoumata.safarytravel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import app.fatoumata.safarytravel.models.CountryModel;
import app.fatoumata.safarytravel.ui.main.SectionsPagerAdapter;
import app.fatoumata.safarytravel.databinding.ActivityCountryBinding;



public class CountryActivity extends AppCompatActivity {


    static final  String COUNTRY_NAME = "COUNTRY_NAME";

    private ActivityCountryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCountryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView title =  findViewById(R.id.title);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(COUNTRY_NAME)){
            title.setText(bundle.getString(COUNTRY_NAME));
        }


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
    }

    public static void startActivity(AppCompatActivity compatActivity, CountryModel countryModel){
        Intent intent =  new Intent(compatActivity,CountryActivity.class);
        Bundle bundle =  new Bundle();
        bundle.putString(COUNTRY_NAME, countryModel.getName());
        intent.putExtras(bundle);
        compatActivity.startActivity(intent);
        compatActivity.finish();

    }
}