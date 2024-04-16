package app.fatoumata.safarytravel.ui.challenge.allphotos;

import static app.fatoumata.safarytravel.utils.DBUtils.getCurrentUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import app.fatoumata.safarytravel.adapters.ChallengePhotoAdapter;
import app.fatoumata.safarytravel.databinding.FragmentChallengePhotosBinding;
import app.fatoumata.safarytravel.models.PhotoModel;
import app.fatoumata.safarytravel.ui.challenge.BaseFragment;
import app.fatoumata.safarytravel.ui.challenge.PageViewModel;
import app.fatoumata.safarytravel.ui.challenge.SectionsChallengePagerAdapter;
import app.fatoumata.safarytravel.ui.main.SectionsPagerAdapter;
import app.fatoumata.safarytravel.ui.main.challenge.FragmentCountryChallenge;
import app.fatoumata.safarytravel.ui.main.challenge.adapter.ChallengeAdapter;
import app.fatoumata.safarytravel.utils.DBUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentChallengePhotos extends BaseFragment implements ChallengePhotoAdapter.Listener {

    private FragmentChallengePhotosBinding binding;

    public static FragmentChallengePhotos newInstance(String countryName, String challengeKey) {
        FragmentChallengePhotos fragment = new FragmentChallengePhotos();
        Bundle bundle = new Bundle();
        bundle.putString(SectionsChallengePagerAdapter.COUNTRY_NAME_KEY, countryName);
        bundle.putString(SectionsChallengePagerAdapter.CHALLENGE_KEY, challengeKey);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            countryName = getArguments().getString(SectionsChallengePagerAdapter.COUNTRY_NAME_KEY);
            challengeKey = getArguments().getString(SectionsChallengePagerAdapter.CHALLENGE_KEY);
        }

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentChallengePhotosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseUser user = getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionChallenges = db.collection(DBUtils.Collection.COUNTRIES + "/" + countryName + "/" + DBUtils.Collection.CHALLENGES+"/"+challengeKey+"/photos");

        collectionChallenges.orderBy("createdAt", Query.Direction.DESCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {

            List<PhotoModel> list =  new ArrayList<>();
            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
            for(DocumentSnapshot snapshot  : list1){
                PhotoModel photoModel = snapshot.toObject(PhotoModel.class);
                if(photoModel!=null){
                    photoModel.setId(snapshot.getId());
                    list.add(photoModel);
                }

            }

            ChallengePhotoAdapter challengePhotoAdapter =  new ChallengePhotoAdapter(requireActivity(),list,this,user.getUid());
            binding.gridPhotoChallenge.setAdapter(challengePhotoAdapter);
        });


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