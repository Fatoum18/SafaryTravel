package app.fatoumata.safarytravel.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DBUtils {

    public static  class Collection{
        public static  final String COUNTRIES = "Countries";
        public static  final String CHALLENGES = "challenges";
    }
   public static FirebaseUser getCurrentUser(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser();
    }

}
