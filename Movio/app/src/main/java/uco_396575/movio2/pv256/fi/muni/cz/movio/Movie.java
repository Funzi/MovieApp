package uco_396575.movio2.pv256.fi.muni.cz.movio;

import android.widget.ImageView;

/**
 * Created by david on 16-Dec-17.
 */

public class Movie {
    String name;
    ImageView image;

    public Movie() {
    }

    public Movie(String name, ImageView image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
