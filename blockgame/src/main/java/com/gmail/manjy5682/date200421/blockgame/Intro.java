package com.gmail.manjy5682.date200421.blockgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
// 현재 화면이 가로/세로 변경되지 않도록 지정하기
// AndroidManifest.xml 에 screenOrientation="portrait" 지정

// 액션바 없애기 -> styles.xml 에서 NoActionBar 지정


public class Intro extends AppCompatActivity {
    //초기화면
    //3초동안 보이고, 다음 화면 (Main)으로 넘어가기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Intent intent= new Intent(getApplicationContext(),Main.class);
                Toast.makeText(getApplicationContext(), "START", Toast.LENGTH_SHORT).show();
                startActivity(intent);//화면전환
                finish();//Intro 화면은 종료
            }
        };

        mHandler.sendEmptyMessageDelayed(1,3000);

    }//end onCreate()
}
