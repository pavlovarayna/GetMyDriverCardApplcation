package com.mystique.rt.getmydrivercardapplcation.models;

import java.io.Serializable;

public class Picture implements Serializable {
    private int pictureId;
    private byte[] picture;

    public Picture() {
        // empty constructor
    }

    public Picture(byte[] picture){
        this.picture = picture;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
