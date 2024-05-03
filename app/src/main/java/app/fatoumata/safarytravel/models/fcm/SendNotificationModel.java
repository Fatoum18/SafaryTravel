package app.fatoumata.safarytravel.models.fcm;

public class SendNotificationModel {


    private String body,title;

    public SendNotificationModel(String body, String title) {
        this.body = body;
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SendNotificationModel{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}