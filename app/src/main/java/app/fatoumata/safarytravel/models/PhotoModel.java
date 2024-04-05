package app.fatoumata.safarytravel.models;

public class PhotoModel {

    private String id;
    private String name;
    private String url;

    private int countLike;

    public PhotoModel() {
    }

    public PhotoModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public PhotoModel(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }


}
