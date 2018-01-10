package uco_396575.movio2.pv256.fi.muni.cz.movio.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviePersonnel {
    @SerializedName("cast")
    private List<Cast> cast;
    @SerializedName("crew")
    private List<Crew> crew;

    private Cast director = null;

    private final String DIRECTOR_TAG = "Director";

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public Cast getDirector() {
        setDirector();
        return director;
    }

    private void setDirector() {
        if (director == null) {
            for (Crew crew : crew) {
                crew.getJob().equals(DIRECTOR_TAG);
                director = crew;
                return;
            }
            director = new Cast("", "");
        }
    }
}
