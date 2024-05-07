package app.fatoumata.safarytravel.ui.challenge.allphotos;

import static app.fatoumata.safarytravel.utils.DBUtils.getCurrentUser;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.fatoumata.safarytravel.adapters.ChallengePhotoAdapter;
import app.fatoumata.safarytravel.databinding.FragmentChallengePhotosBinding;
import app.fatoumata.safarytravel.models.PhotoModel;
import app.fatoumata.safarytravel.models.fcm.RequestNotification;
import app.fatoumata.safarytravel.models.fcm.SendNotificationModel;
import app.fatoumata.safarytravel.service.FMService;
import app.fatoumata.safarytravel.ui.challenge.BaseFragment;
import app.fatoumata.safarytravel.ui.challenge.SectionsChallengePagerAdapter;
import app.fatoumata.safarytravel.utils.DBUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentChallengePhotos extends BaseFragment implements ChallengePhotoAdapter.Listener {

    ChallengePhotoAdapter challengePhotoAdapter;
    private FragmentChallengePhotosBinding binding;

    public static FragmentChallengePhotos newInstance(String countryName, String challengeKey) {
        FragmentChallengePhotos fragment = new FragmentChallengePhotos();
        Bundle bundle = new Bundle();
        bundle.putString(SectionsChallengePagerAdapter.COUNTRY_NAME_KEY, countryName);
        bundle.putString(SectionsChallengePagerAdapter.CHALLENGE_KEY, challengeKey);
        fragment.setArguments(bundle);
        return fragment;
    }
    FirebaseFirestore db ;
    FirebaseUser user ;

    FMService fmService = new FMService();

    private final FirebaseMessaging messaging = FirebaseMessaging.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        user = getCurrentUser();

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


        CollectionReference collectionChallenges = db.collection(DBUtils.Collection.CHALLENGES+"/"+challengeKey+"/"+DBUtils.Collection.COUNTRIES + "/" + countryName +"/photos");
        collectionChallenges.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                fetchPhoto(collectionChallenges);

            }
        });
        fetchPhoto(collectionChallenges);




        return root;
    }

    private void fetchPhoto(CollectionReference collectionChallenges){

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

            challengePhotoAdapter =  new ChallengePhotoAdapter(requireActivity(),list,this,user.getUid());
            binding.gridPhotoChallenge.setAdapter(challengePhotoAdapter);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onThumbClick(PhotoModel photoModel) {

        FirebaseUser user = getCurrentUser();
        String userId =  user.getUid();


        CollectionReference collectionChallenges = db.collection(DBUtils.Collection.COUNTRIES + "/" + countryName + "/" + DBUtils.Collection.CHALLENGES+"/"+challengeKey+"/photos");

        DocumentReference photoRef = collectionChallenges.document(photoModel.getId());
        photoRef.collection("likes").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                //User has already liked the photo
                if(snapshot.exists()){

                    photoRef.update("countLike", FieldValue.increment(-1));
                    photoRef.collection("likes").document(userId).delete();
                    photoModel.increment(-1);
                    challengePhotoAdapter.notifyDataSetChanged();
                }else{
                    photoRef.update("countLike", FieldValue.increment(1));
                    Map<String, Object> likeDoc =  new HashMap<>();
                    likeDoc.put("createdAt",new Date());
                    photoRef.collection("likes").document(userId).set(likeDoc);
                    photoModel.increment(1);

                    sendNotification(photoModel.getUserId(),"");

                    challengePhotoAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void sendNotification(String targetUserId, String messageText){


        fmService.fetcUserToken(targetUserId, token -> {

            Log.d("FCM", "sendNotification: "+token);
            SendNotificationModel sendNotificationModel = new SendNotificationModel("Someone like your photo", "Challenge: Maison");
            RequestNotification requestNotificaton = new RequestNotification();
            requestNotificaton.setNotification(sendNotificationModel);

            requestNotificaton.setToken(token);

            Log.d("FCM-", "sendNotification: "+requestNotificaton);
            fmService.sendNotification(requestNotificaton);
        });

    }
}