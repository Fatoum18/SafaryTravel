package app.fatoumata.safarytravel.models;

public class CountryModel {

    private String id;
    private String name;
    private String flagUrl;

    public CountryModel() {
    }

    public CountryModel(String name, String flagUrl) {
        this.name = name;
        this.flagUrl = flagUrl;
    }

    public CountryModel(String id, String name, String flagUrl) {
        this.id = id;
        this.name = name;
        this.flagUrl = flagUrl;
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

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }
}
