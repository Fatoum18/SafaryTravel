package app.fatoumata.safarytravel.models;

import java.util.Date;

public class SafaryModel extends  PhotoModel{

    private String challengeName;

    public SafaryModel() {
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }
}
