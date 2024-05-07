package app.fatoumata.safarytravel.ui.challenge.myphoto;

import static app.fatoumata.safarytravel.utils.DBUtils.getCurrentUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import app.fatoumata.safarytravel.adapters.ChallengePhotoAdapter;
import app.fatoumata.safarytravel.databinding.FragmentChallengeMyPhotosBinding;
import app.fatoumata.safarytravel.models.PhotoModel;
import app.fatoumata.safarytravel.ui.challenge.BaseFragment;
import app.fatoumata.safarytravel.ui.challenge.PageViewModel;
import app.fatoumata.safarytravel.ui.challenge.SectionsChallengePagerAdapter;
import app.fatoumata.safarytravel.utils.DBUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentChallengeMyPhotos extends BaseFragment  implements ChallengePhotoAdapter.Listener {


    private FragmentChallengeMyPhotosBinding binding;



    public static Fragment newInstance(String countryName, String challengeKey) {
        FragmentChallengeMyPhotos fragment = new FragmentChallengeMyPhotos();
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

        binding = FragmentChallengeMyPhotosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = getCurrentUser();
        CollectionReference collectionChallenges = db.collection(DBUtils.Collection.CHALLENGES+"/"+challengeKey+"/"+DBUtils.Collection.COUNTRIES + "/" + countryName +"/photos");

        collectionChallenges.whereEqualTo("userId", user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {

            List<PhotoModel> list =  new ArrayList<>();
            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
            for(DocumentSnapshot snapshot  : list1){
                PhotoModel photoModel = snapshot.toObject(PhotoModel.class);
                if(photoModel!=null){
                    photoModel.setId(snapshot.getId());
                    list.add(photoModel);
                }

            }

            ChallengePhotoAdapter challengePhotoAdapter =  new ChallengePhotoAdapter(requireActivity(),list,this, user.getUid());
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
    public void onThumbClick(PhotoModel countryModel) {

    }

}