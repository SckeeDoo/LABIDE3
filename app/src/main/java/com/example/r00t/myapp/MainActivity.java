package com.example.r00t.myapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText etLaps;
    private Button buttonStart;
    private Button buttonLap;
    private Button buttonStop;
    private int lapCounter = 1;
    private ScrollView scrollView;
    private EditText lastStopTime;

    private Context mContext;
    private Chronometer chronometer;
    private Thread threadChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        textView = (TextView)findViewById(R.id.time);
        etLaps = (EditText)findViewById(R.id.et_laps);
        buttonStart = (Button)findViewById(R.id.start);
        buttonLap = (Button)findViewById(R.id.lapb);
        buttonStop = (Button)findViewById(R.id.stop);
        scrollView = (ScrollView)findViewById(R.id.sv_laps);

        etLaps.setEnabled(false);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer == null) {
                    chronometer = new Chronometer(mContext);
                    //run the chronometer on a separate thread
                    threadChronometer = new Thread(chronometer);
                    threadChronometer.start();

                    chronometer.start();

                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the chronometer had been instantiated before...
                if(chronometer != null) {
                    //stop the chronometer
                    chronometer.stop();
                    //stop the thread
                    threadChronometer.interrupt();
                    threadChronometer = null;
                    //kill the chrono class
                    chronometer = null;
                }
            }



        });

        buttonLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if chrono is not running we shouldn't capture the lap!
                if(chronometer == null) {

                    return; //do nothing!
                }

                //we just simply copy the current text of tv_timer and append it to et_laps
                etLaps.append("LAP " + String.valueOf(lapCounter++)
                        + "   " + textView.getText() + "\n");

                //scroll to the bottom of et_laps
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, etLaps.getBottom());
                    }
                });
            }
        });

    }


    public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(timeAsText);
            }
        });
    }
}
