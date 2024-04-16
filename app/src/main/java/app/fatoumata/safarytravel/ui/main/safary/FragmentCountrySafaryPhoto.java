package app.fatoumata.safarytravel.ui.main.safary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import app.fatoumata.safarytravel.adapters.ChallengePhotoAdapter;
import app.fatoumata.safarytravel.databinding.FragmentCountrySafaryBinding;
import app.fatoumata.safarytravel.models.PhotoModel;
import app.fatoumata.safarytravel.ui.main.PageViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentCountrySafaryPhoto extends Fragment  implements ChallengePhotoAdapter.Listener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentCountrySafaryBinding binding;

    public static FragmentCountrySafaryPhoto newInstance(int index) {
        FragmentCountrySafaryPhoto fragment = new FragmentCountrySafaryPhoto();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentCountrySafaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        List<PhotoModel> list =    new ArrayList<>();
        list.add(new PhotoModel("","https://loremflickr.com/640/480/animals"));
        list.add(new PhotoModel("","https://loremflickr.com/1234/2345/animals"));
        list.add(new PhotoModel("","https://loremflickr.com/1234/2345/cats"));
        list.add(new PhotoModel("","https://loremflickr.com/1234/2345/city"));
        list.add(new PhotoModel("","https://loremflickr.com/1234/2345/nature"));
        ChallengePhotoAdapter challengePhotoAdapter =  new ChallengePhotoAdapter(requireActivity(),list,this,null);
        binding.gridPhotoChallenge.setAdapter(challengePhotoAdapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCountryClick(PhotoModel countryModel) {

    }
}