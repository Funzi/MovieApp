package uco_396575.movio2.pv256.fi.muni.cz.movio.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity
public class Cast {
    @PrimaryKey
    @NonNull
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
