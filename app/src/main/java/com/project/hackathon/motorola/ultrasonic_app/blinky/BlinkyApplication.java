package com.project.hackathon.motorola.ultrasonic_app.blinky;

import android.app.Application;

/**
 * Created by wla015 on 21/03/2017.
 */

public class BlinkyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        ModAssistant.getInstance(this);
        ModRawProtocol.getInstance(this);
    }
}
