package uco_396575.movio2.pv256.fi.muni.cz.movio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david on 10-Jan-18.
 */

public class Crew extends Cast {
    @SerializedName("job")
    String job;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}