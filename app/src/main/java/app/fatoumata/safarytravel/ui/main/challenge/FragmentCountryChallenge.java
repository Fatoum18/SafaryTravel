package app.fatoumata.safarytravel.ui.main.challenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.fatoumata.safarytravel.ChallengeActivity;
import app.fatoumata.safarytravel.adapters.ChallengePhotoAdapter;
import app.fatoumata.safarytravel.databinding.FragmentCountryChallengeBinding;
import app.fatoumata.safarytravel.models.CountryModel;
import app.fatoumata.safarytravel.models.PhotoModel;
import app.fatoumata.safarytravel.ui.main.BaseFragment;
import app.fatoumata.safarytravel.ui.main.PageViewModel;
import app.fatoumata.safarytravel.ui.main.SectionsPagerAdapter;
import app.fatoumata.safarytravel.ui.main.challenge.adapter.ChallengeAdapter;
import app.fatoumata.safarytravel.ui.main.info.FragmentCountryInfo;
import app.fatoumata.safarytravel.utils.DBUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentCountryChallenge extends BaseFragment implements ChallengeAdapter.Listener {


    private PageViewModel pageViewModel;
    private FragmentCountryChallengeBinding binding;


    public static FragmentCountryChallenge newInstance(String name) {
        FragmentCountryChallenge fragment = new FragmentCountryChallenge();
        Bundle bundle = new Bundle();
        bundle.putString(SectionsPagerAdapter.COUNTRY_NAME_KEY, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        if (getArguments() != null) {
            countryName = getArguments().getString(SectionsPagerAdapter.COUNTRY_NAME_KEY);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentCountryChallengeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionChallenges = db.collection(DBUtils.Collection.COUNTRIES + "/" + countryName + "/" + DBUtils.Collection.CHALLENGES);
        collectionChallenges.get().addOnSuccessListener(queryDocumentSnapshots -> {

            List<ChallengeAdapter.ChallengeModel> challengeModels =  new ArrayList<>();
            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
            for(DocumentSnapshot snapshot  : list1){
                ChallengeAdapter.ChallengeModel challengeModel = snapshot.toObject(ChallengeAdapter.ChallengeModel.class);
                if(challengeModel!=null){
                    challengeModel.setId(snapshot.getId());
                    challengeModels.add(challengeModel);
                }

            }

            ChallengeAdapter challengeAdapter =  new ChallengeAdapter(requireActivity(),challengeModels,this);
            binding.gridChallenge.setAdapter(challengeAdapter);
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onChallengeClick(ChallengeAdapter.ChallengeModel challengeModel) {

        ChallengeActivity.startActivity((AppCompatActivity) getActivity(),countryName,challengeModel.getName(),challengeModel.getId());
    }
}