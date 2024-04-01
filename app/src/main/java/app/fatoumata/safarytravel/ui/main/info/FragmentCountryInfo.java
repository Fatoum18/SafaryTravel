package app.fatoumata.safarytravel.ui.main.info;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.util.List;

import app.fatoumata.safarytravel.databinding.FragmentCountryBinding;
import app.fatoumata.safarytravel.service.CountryService;
import app.fatoumata.safarytravel.service.dto.CountryOfRegionDto;
import app.fatoumata.safarytravel.ui.main.PageViewModel;
import app.fatoumata.safarytravel.ui.main.info.component.CountryInfoView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentCountryInfo extends Fragment {

    private static final String COUNTRY_NAME = "COUNTRY_NAME";

    private PageViewModel pageViewModel;
    private FragmentCountryBinding binding;
    private String countryName;

    public static FragmentCountryInfo newInstance(String name) {
        FragmentCountryInfo fragment = new FragmentCountryInfo();
        Bundle bundle = new Bundle();
        bundle.putString(COUNTRY_NAME, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

        if (getArguments() != null) {
            countryName = getArguments().getString(COUNTRY_NAME);
        }
//        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentCountryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final CountryInfoView countryInfoView =  binding.countryInfoView;

        CountryService.api.getCountryByName(countryName).enqueue(new Callback<List<CountryOfRegionDto>>() {
            @Override
            public void onResponse(Call<List<CountryOfRegionDto>> call, Response<List<CountryOfRegionDto>> response) {

                if(response.body()!=null && response.body().size()==1){
                    CountryOfRegionDto countryOfRegionDto =  response.body().get(0);
                    countryInfoView.setOfficialName(countryOfRegionDto.name.official);
                    countryInfoView.setCapital(String.valueOf(countryOfRegionDto.capital));
                    countryInfoView.setPopulation(String.valueOf(countryOfRegionDto.population));
                    countryInfoView.setLanguages(String.valueOf(countryOfRegionDto.languages.values()));
                    countryInfoView.setMoney(String.valueOf(countryOfRegionDto.currencies.keySet()));
                    Glide.with(root).load(countryOfRegionDto.flags.png).into(countryInfoView.getFlagView());
                    Log.i("countryOfRegionDto", countryOfRegionDto.toString());
                }
            }

            @Override
            public void onFailure(Call<List<CountryOfRegionDto>> call, Throwable throwable) {

            }
        });

//        final TextView textView = binding.sectionLabel;
//        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}