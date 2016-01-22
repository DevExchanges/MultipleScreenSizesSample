package info.devexchanges.multiplescreen;

public class Country {
    private String name;
    private int flagId;

    public Country(String name, int flagId) {
        this.name = name;
        this.flagId = flagId;
    }

    public String getName() {
        return name;
    }

    public int getFlagId() {
        return flagId;
    }
}
