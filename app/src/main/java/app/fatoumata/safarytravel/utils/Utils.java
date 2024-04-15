package app.fatoumata.safarytravel.utils;

import android.os.Build;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Utils {

    public static  String generateId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


}
