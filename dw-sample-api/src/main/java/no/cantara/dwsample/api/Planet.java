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

    @Override
    public String toString() {
        return "Planet{" +
                "planetName='" + planetName + '\'' +
                ", yourName='" + yourName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Planet planet = (Planet) o;

        if (planetName != null ? !planetName.equals(planet.planetName) : planet.planetName != null) return false;
        return !(yourName != null ? !yourName.equals(planet.yourName) : planet.yourName != null);

    }

    @Override
    public int hashCode() {
        int result = planetName != null ? planetName.hashCode() : 0;
        result = 31 * result + (yourName != null ? yourName.hashCode() : 0);
        return result;
    }
}
