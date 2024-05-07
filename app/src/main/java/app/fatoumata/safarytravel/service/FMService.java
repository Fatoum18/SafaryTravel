package app.fatoumata.safarytravel.service;

import static app.fatoumata.safarytravel.utils.DBUtils.getCurrentUser;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import app.fatoumata.safarytravel.models.fcm.FCMBody;
import app.fatoumata.safarytravel.models.fcm.RequestNotification;
import app.fatoumata.safarytravel.service.api.FcmApi;
import feign.AsyncClient;
import feign.AsyncFeign;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;


public class FMService extends FirebaseMessagingService {


    public static interface  FecthToken{
        public void onSuccess(String token);
    }


    final String TAG = "FCM";



    FcmApi fcmApi =   AsyncFeign.builder().client(new OkHttpClient())
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .logger(new Slf4jLogger())
            .logLevel(Logger.Level.FULL)
            .target(FcmApi.class, "https://fcm.googleapis.com/v1/projects/safarytravel/messages:send");

    public void sendNotification(RequestNotification requestNotification ){

             sendNoticationWithToken(requestNotification);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("TAG", "Refreshed token: " + token);

    }

    public static void registerDevice (String token){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = getCurrentUser();
        DocumentReference userRef = db.collection("users").document(user.getUid());

        Map map = new HashMap();
        map.put("fcmToken", token);
        userRef.set(map, SetOptions.merge()).addOnSuccessListener(unused -> Log.i("FCM", "FCM Token added")).addOnFailureListener(e -> Log.i("FCM","FCM Token failed to add"));
    }


    private  void sendNoticationWithToken (RequestNotification requestNotification){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference configRef = db.collection("Config").document("v1");
        configRef.get().addOnSuccessListener(documentSnapshot -> {

            String token = documentSnapshot.getString("fcmAccessToken");
            if(token!=null){
                fcmApi.sendNotification(new FCMBody(requestNotification),token).whenComplete((s, throwable) -> Log.d(TAG, "FCM notification sent "+s));
            }
        });

    }

    public  void fetcUserToken (String userId, FecthToken listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnSuccessListener(documentSnapshot -> {

            String token = documentSnapshot.getString("fcmToken");
            if(token!=null){
                if(listener !=null){
                    listener.onSuccess(token);
                }
            }
        });

    }
}
