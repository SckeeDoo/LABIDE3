package com.example.r00t.myapp;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by r00t on 5/12/16.
 */
public class Chronometer implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;

    private Context mContext;
    private long startTime;
    private boolean isRunning;

    public Chronometer(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void run() {
        while(isRunning) {
            long since = System.currentTimeMillis() - startTime;

            int seconds = (int)((since / 1000) % 60);
            int minutes  = (int)((since / MILLIS_TO_MINUTES) % 60);
            int hours = (int)(((since) / MILLIS_TO_HOURS) % 24);
            int millis = (int)since % 1000;

            ((MainActivity)mContext).updateTimerText(String.format(
                    "%02d:%02d:%02d:%03d", hours, minutes, seconds,millis
            ));
        }
    }

    public void start(){
        startTime = System.currentTimeMillis();
        isRunning = true;
    }

    public void stop(){
        isRunning = false;
    }
}
