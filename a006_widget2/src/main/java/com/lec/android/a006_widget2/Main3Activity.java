package com.lec.android.a006_widget2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    TextView tvResult;
    SeekBar seekBar;

    int value = 0;
    int add = 10;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvResult = findViewById(R.id.tvResult);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           //값의 변화가 생겼을 때
            //progress : 진행값 0~200
            //fromUser : 사용자에 의한 진행값 변화인 경우 true
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvResult.setText("onProgressChanged" + progress+"("+fromUser+")");
            }
            //트레킹 시작할 때
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "트래킹시작",Toast.LENGTH_SHORT).show();
            }
            //tracking 끝날 때 (손 뗄때)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "트래킹끝",Toast.LENGTH_SHORT).show();

            }
        });

        //앱시작시 Thread.. Seekbar 증가
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                    int max = seekBar.getMax();//200
                    value = seekBar.getProgress()+add;
                    if (value>max||value<0){
                        add = -add;
                    }
                    value = seekBar.getProgress()+add;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(value);

                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
