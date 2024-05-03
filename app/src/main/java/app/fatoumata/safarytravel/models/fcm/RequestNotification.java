package app.fatoumata.safarytravel.models.fcm;


public class RequestNotification {


    private String token;


    private SendNotificationModel notification;

    public SendNotificationModel getNotification() {
        return notification;
    }

    public void setNotification(SendNotificationModel notification) {
        this.notification = notification;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RequestNotification{" +
                "token='" + token + '\'' +
                ", notification=" + notification +
                '}';
    }
}