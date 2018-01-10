package uco_396575.movio2.pv256.fi.muni.cz.movio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david on 10-Jan-18.
 */

public class Cast {
    @SerializedName("name")
    String name;
    @SerializedName("profile_path")
    String profilePic;

    public Cast() {
    }

    public Cast(String name, String profilePic) {
        this.name = name;
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
