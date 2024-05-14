package app.fatoumata.safarytravel.ui.main.safary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.fatoumata.safarytravel.adapters.ChallengePhotoAdapter;
import app.fatoumata.safarytravel.adapters.SafaryPhotoAdapter;
import app.fatoumata.safarytravel.databinding.FragmentCountrySafaryBinding;
import app.fatoumata.safarytravel.models.PhotoModel;
import app.fatoumata.safarytravel.models.SafaryModel;
import app.fatoumata.safarytravel.ui.challenge.BaseFragment;
import app.fatoumata.safarytravel.ui.challenge.SectionsChallengePagerAdapter;
import app.fatoumata.safarytravel.ui.main.PageViewModel;
import app.fatoumata.safarytravel.utils.DBUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentCountrySafaryPhoto extends BaseFragment {



    SafaryPhotoAdapter challengePhotoAdapter;
    private FragmentCountrySafaryBinding binding;
    FirebaseFirestore db ;
    public static FragmentCountrySafaryPhoto newInstance(String countryName) {
        FragmentCountrySafaryPhoto fragment = new FragmentCountrySafaryPhoto();
        Bundle bundle = new Bundle();
        bundle.putString(SectionsChallengePagerAdapter.COUNTRY_NAME_KEY, countryName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        if (getArguments() != null) {
            countryName = getArguments().getString(SectionsChallengePagerAdapter.COUNTRY_NAME_KEY);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentCountrySafaryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        CollectionReference collectionChallenges = db.collection(DBUtils.Collection.COUNTRIES+"/"+countryName+"/"+DBUtils.Collection.ALBUMS );
        collectionChallenges.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                fetchPhoto(collectionChallenges);

            }
        });
        fetchPhoto(collectionChallenges);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void fetchPhoto(CollectionReference collectionChallenges){

        collectionChallenges.orderBy("createdAt", Query.Direction.DESCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {

            List<SafaryModel> list =  new ArrayList<>();
            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
            for(DocumentSnapshot snapshot  : list1){
                SafaryModel safaryModel = snapshot.toObject(SafaryModel.class);
                if(safaryModel!=null){
                    safaryModel.setId(snapshot.getId());
                    list.add(safaryModel);
                }

            }

            challengePhotoAdapter =  new SafaryPhotoAdapter(requireActivity(),list);
            binding.gridPhotoChallenge.setAdapter(challengePhotoAdapter);
        });
    }
}