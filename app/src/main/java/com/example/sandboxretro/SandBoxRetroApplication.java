package com.example.sandboxretro;

import android.app.Application;

public class SandBoxRetroApplication extends Application {

    private String currentScreen;
    private Boolean isCarouselViewed = false;
    private static SandBoxRetroApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static SandBoxRetroApplication getInstance() {
        return INSTANCE;
    }

    public static void initialize(Application app) {
        INSTANCE = new SandBoxRetroApplication();
    }

    public SandBoxRetroApplication() {
        currentScreen = null;
        INSTANCE = this;
    }

    public void setcurrentScreen(String screen) {
        currentScreen = screen;
    }

    public String getcurrentScreen() {
        return currentScreen;
    }

    public boolean isCarouselViewed() {
        return isCarouselViewed;
    }

    public void  setCarouselViewed(boolean bool) {
        isCarouselViewed = bool;
    }

}
