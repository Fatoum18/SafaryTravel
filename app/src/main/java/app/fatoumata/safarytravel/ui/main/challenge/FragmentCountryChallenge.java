package app.fatoumata.safarytravel.ui.main.challenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;

import app.fatoumata.safarytravel.ChallengeActivity;
import app.fatoumata.safarytravel.databinding.FragmentCountryChallengeBinding;
import app.fatoumata.safarytravel.models.Challenge;
import app.fatoumata.safarytravel.ui.main.BaseFragment;
import app.fatoumata.safarytravel.ui.main.PageViewModel;
import app.fatoumata.safarytravel.ui.main.SectionsPagerAdapter;
import app.fatoumata.safarytravel.utils.DBUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentCountryChallenge extends BaseFragment{


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

        Calendar calendar = Calendar.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentRef = db.document(DBUtils.Collection.CHALLENGES+"/"+calendar.get(Calendar.DAY_OF_MONTH));


        documentRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            if (documentSnapshot.exists()) {
                                // Document exists, cast it into a Challenge object
                                Challenge challenge = documentSnapshot.toObject(Challenge.class);

                                if(challenge!=null){
                                    // Now you can use the Challenge object
                                    String title = challenge.getTitle();
                                    String description = challenge.getDescription();

                                    binding.titleTextView.setText(title);
                                    binding.descriptionTextView.setText(description);
                                    binding.goToChallengeButton.setOnClickListener((e)->onChallengeClick(challenge));
                                }

                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle errors
                    }
                });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onChallengeClick(Challenge challenge) {

        ChallengeActivity.startActivity((AppCompatActivity) getActivity(),countryName,challenge.getTitle(),String.valueOf(challenge.getDay()));
    }
}