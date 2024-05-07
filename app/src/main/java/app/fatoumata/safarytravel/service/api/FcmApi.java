package app.fatoumata.safarytravel.service.api;


import java.util.concurrent.CompletableFuture;

import app.fatoumata.safarytravel.models.fcm.FCMBody;
import app.fatoumata.safarytravel.models.fcm.RequestNotification;
import app.fatoumata.safarytravel.models.fcm.SendNotificationModel;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;




public interface FcmApi {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Authorization: Bearer {fcm_access_token}"
    })
    @RequestLine("POST")
     CompletableFuture<String> sendNotification(FCMBody requestBody, @Param("fcm_access_token") String fcmAccessToken );

}

