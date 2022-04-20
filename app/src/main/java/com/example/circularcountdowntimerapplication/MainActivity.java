package com.example.circularcountdowntimerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int i=-1;
    ProgressBar mProgressBar;

    private Button buttonStartTime, buttonStopTime;
    private EditText edtTimerValue;
    private TextView textViewShowTime; // will show the time
    private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartTime = (Button) findViewById(R.id.btnStartTime);
        buttonStopTime = (Button) findViewById(R.id.btnStopTime);
        textViewShowTime = (TextView) findViewById(R.id.tvTimeCount);
        edtTimerValue = (EditText) findViewById(R.id.edtTimerValue);

        buttonStartTime.setOnClickListener(this);
        buttonStopTime.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStartTime) {
//            textViewShowTime.setTextAppearance(getApplicationContext(),
//                    R.style.normalText);
            setTimer();

            //Hides the Keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtTimerValue.getWindowToken(), 0);

            buttonStopTime.setVisibility(View.VISIBLE);
            buttonStartTime.setVisibility(View.GONE);
            edtTimerValue.setVisibility(View.GONE);
            edtTimerValue.setText("");
            //textViewShowTime.setTextColor(color.white);
            //textViewShowTime.getContext();
            startTimer();

        } else if (v.getId() == R.id.btnStopTime) {
            countDownTimer.cancel();
            buttonStartTime.setVisibility(View.VISIBLE);
            buttonStopTime.setVisibility(View.GONE);
            edtTimerValue.setVisibility(View.VISIBLE);
        }
    }

    private void setTimer() {
        int time = 0;
        if (!edtTimerValue.getText().toString().equals("")) {
            time = Integer.parseInt(edtTimerValue.getText().toString());
        } else
            Toast.makeText(MainActivity.this, "Please Enter Minutes...",
                    Toast.LENGTH_LONG).show();

        totalTimeCountInMilliseconds = 60 * time * 1000;

        timeBlinkInMilliseconds = 30 * 1000;
        mProgressBar.setMax(60*time);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                //i++;
                //Setting the Progress Bar to decrease wih the timer
                mProgressBar.setProgress((int) (leftTimeInMilliseconds / 1000));
//                textViewShowTime.setTextAppearance(getApplicationContext(),
//                        R.style.normalColor);


                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
//                    textViewShowTime.setTextAppearance(getApplicationContext(),
//                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime.setVisibility(View.INVISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

                textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }
            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                textViewShowTime.setText("Time up!");
                textViewShowTime.setVisibility(View.VISIBLE);
                buttonStartTime.setVisibility(View.VISIBLE);
                buttonStopTime.setVisibility(View.GONE);
                edtTimerValue.setVisibility(View.VISIBLE);
            }

        }.start();

    }


}