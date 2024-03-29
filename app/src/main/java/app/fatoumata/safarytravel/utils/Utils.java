package app.fatoumata.safarytravel.utils;

import java.util.UUID;

public class Utils {

    public static  String generateId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
