package no.cantara.dwsample.api;

public class Planet {
    private String planetName;
    private String yourName;

    public Planet() {
    }

    public Planet(String planetName, String yourName) {
        this.planetName = planetName;
        this.yourName = yourName;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public String getYourName() {
        return yourName;
    }

    public void setYourName(String yourName) {
        this.yourName = yourName;
    }
}
