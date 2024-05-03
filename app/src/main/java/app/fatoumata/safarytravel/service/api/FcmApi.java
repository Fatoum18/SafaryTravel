package app.fatoumata.safarytravel.service.api;


import java.util.concurrent.CompletableFuture;

import app.fatoumata.safarytravel.models.fcm.RequestNotification;
import app.fatoumata.safarytravel.models.fcm.SendNotificationModel;
import feign.Body;
import feign.Headers;
import feign.RequestLine;




public interface FcmApi {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "Authorization: Bearer ya29.a0AXooCgvt99jfzZE-2V523XVoyp9gFt4PBiBbYHByo_soLlb4TBRl8oMzwlO99R0rdeYHFddVDpE8Qv7u3NMQdolPP-Te31xHsOY-R2uHSu77c2BKRWojKqflM4Xit3o8pg-zCpXeOm_dQ8mxT2OhTtGX8pXiNzKZ3_DdaCgYKAT4SARISFQHGX2MiHyuEWGpDU1lQUl7bnyL0KQ0171"
    })
    @RequestLine("POST")


     CompletableFuture<String> sendNotification( FCMBody requestBody);
 class FCMBody{
     private RequestNotification message;

     public FCMBody(RequestNotification message){
         this.message = message ;
     }
 }
}

