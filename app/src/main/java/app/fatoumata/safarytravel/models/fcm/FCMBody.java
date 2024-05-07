package app.fatoumata.safarytravel.models.fcm;

public class FCMBody {

    private RequestNotification message;

    public FCMBody(RequestNotification message){
        this.message = message ;
    }

    public RequestNotification getMessage() {
        return message;
    }

    public void setMessage(RequestNotification message) {
        this.message = message;
    }
}
