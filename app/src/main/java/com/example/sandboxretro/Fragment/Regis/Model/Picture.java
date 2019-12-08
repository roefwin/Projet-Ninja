package com.example.sandboxretro.Fragment.Regis.Model;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

public class Picture {
    private String element;
    private int nbr_elem;
    private int resId;

    public Picture(String element, int nbr_elem, int resId) {
        this.element = element;
        this.nbr_elem = nbr_elem;
        this.resId = resId;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public int getNbr_elem() {
        return nbr_elem;
    }

    public void setNbr_elem(int nbr_elem) {
        this.nbr_elem = nbr_elem;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
