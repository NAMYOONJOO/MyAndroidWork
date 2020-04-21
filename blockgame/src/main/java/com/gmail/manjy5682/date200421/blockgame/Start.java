package com.gmail.manjy5682.date200421.blockgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Start extends AppCompatActivity implements View.OnClickListener {

    TextView tvTime;//시간표시
    TextView tvPoint;//점수표시

    int time = 30;//시간
    int point=0;//점수

    //블럭이미지 리소스 배열
    int []img = {R.drawable.block_red,R.drawable.block_green,R.drawable.block_blue};

    //떨어지는 블럭의 ImageView 배열 객체체
    ImageView[] iv = new ImageView[8]; //iv[0]<---? null

    private Vibrator vibrator; //진동
    private SoundPool soundPool; //음향

    private int soundID_OK; //음향 id : 블럭맞추었을 때
    private int soundID_Error; //음향 id : 블럭 못 맞추었을 때

    private MediaPlayer mp;

    final int DIALOG_TIMEOVER = 1; //다이얼로그 ID

    Handler handler = new Handler();// 시간


    // 게임진행 쓰레드
    class GameThread extends Thread{
        @Override
        public void run() {
            //시간을 1초마다 다시 표시(업데이트)
            //Handler 사용하여 화면 UI 업데이트
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //TODO
                    if (time >= 0) {
                        tvTime.setText("Time : "+time);

                        if(time>0){
                            time--; //1초감소, 1초후 다시 run 수행
                            handler.postDelayed(this,1000);
                        } else{
                            //time -> 0이 된경우.
                            AlertDialog.Builder builder
                                    = new AlertDialog.Builder(Start.this);
                            builder.setTitle("TIME OUT")
                                    .setMessage("Score : "+point)
                                    .setNegativeButton("STOP", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .setPositiveButton("ONE MORE", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //게임 리셋하고, 새게임 시작
                                            time = 30;
                                            point = 0;
                                            tvTime.setText("Time : "+time);
                                            tvPoint.setText(("Score : "+point));
                                            new GameThread().start();
                                        }
                                    })
                            .setCancelable(false);
                        builder.show();

                        }

                    }

                }
            }, 1000); //1초후에 시간 표시시


        }//end rn
    }//end GameThread


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 상태바(status bar) 없애기, 반드시 setContentView() 앞에서 처리
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.start);

        //레이아웃 객체(뷰) 초기화
        tvTime = findViewById(R.id.tvTime);
        tvPoint = findViewById(R.id.tvPoint);

        ImageView ivRed =findViewById(R.id.ivRed);
        ImageView ivBlue =findViewById(R.id.ivBlue);
        ImageView ivGreen =findViewById(R.id.ivGreen);

        iv[0] = findViewById(R.id.ivBlock1);
        iv[1] = findViewById(R.id.ivBlock2);
        iv[2] = findViewById(R.id.ivBlock3);
        iv[3] = findViewById(R.id.ivBlock4);
        iv[4] = findViewById(R.id.ivBlock5);
        iv[5] = findViewById(R.id.ivBlock6);
        iv[6] = findViewById(R.id.ivBlock7);
        iv[7] = findViewById(R.id.ivBlock8);

        //게임이 시작되면 초기화
        //렌덤으로 블럭의 색상 지정
        for(int i = 0;i<iv.length;i++){
            ///0,1,2 <-red green blue
            int num = new Random().nextInt(3);//0,1,2중의 랜덤 정수
            iv[i].setImageResource(img[num]);
            iv[i].setTag(num+""); //태그를 버턴의 색상 판단하기 위한 값으로 활용
        }

        //점수초기화
        point = 0;
        tvPoint.setText("Score : "+point);

        //r,g,b 버튼의 리스너 등록
        ivRed.setOnClickListener(this);
        ivGreen.setOnClickListener(this);
        ivBlue.setOnClickListener(this);

        //시간표시, 게임진행 쓰레드 시작하기
        new GameThread().start();
    }

    @Override
    public void onClick(View v) {
        //버튼을 눌렀을 때 호출되는 콜백
        //블럭과 같은 색깔의 버튼이 눌렸는지 판별, 같은 블럭이면 이미지 블럭 한칸씩 내려오기
        //맨위에는 새로운 블럭 생성

        boolean isOk = false ;// 맞추었는지 판별

        ImageView imageView = (ImageView) v;
        switch (imageView.getId()){
            //맨 아래 블럭 ImageView의 색상과 일치하는지 판정
            case R.id.ivRed: //빨강버튼 클릭시
                if("0".equals(iv[7].getTag().toString())) isOk= true;//빨강블럭의 tag 값 "0"
                break;
            case R.id.ivGreen: //빨강버튼 클릭시
                if("1".equals(iv[7].getTag().toString())) isOk= true;//초록블럭의 tag 값 "1"
                break;
            case R.id.ivBlue: //빨강버튼 클릭시
                if("2".equals(iv[7].getTag().toString())) isOk= true;//파랑블럭의 tag 값 "2"
                break;

        }
        if(isOk){ //버튼색깔을 맞추었다면
            //진동 음향

            // 위의 7개 블럭을 한칸 아래로 이동
            for(int i = iv.length-2;i>=0;i--){
                int num = Integer.parseInt(iv[i].getTag().toString());//"0","1","2"
                iv[i+1].setImageResource(img[num]);
                iv[i+1].setTag(num+""); //아래쪽 블럭 tag값 업데이트

            }
            //가장 위쪽의 블럭 ImageView는 랜덤으로 생성
            int num = new Random().nextInt(3);
            iv[0].setImageResource(img[0]);
            iv[0].setTag(num+"");

            vibrator.vibrate(200);
            soundPool.play(soundID_OK,1,1,0,0,1);
            //점수 올리기
            point++;
            tvPoint.setText("Score : "+point);
        }else{ //버튼색깔이 틀리다면면
            vibrator.vibrate(new long[]{20,80,20,80,20,80},-1);
            soundPool.play(soundID_Error,1,1,0,0,1);
           //점수 깍기
            point--;
            tvPoint.setText("Score : "+point);
        }    }

    @Override
    protected void onResume() {
        super.onResume();
        //자원획득

        //Vibrator 객체 얻어오기
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        //SoundPool 객체
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        soundID_OK = soundPool.load(Start.this,R.raw.gun3,1);
        soundID_Error = soundPool.load(Start.this,R.raw.error,1);

        // MediaPlayer 객체, 배경음악 연주시작
        mp = MediaPlayer.create(getApplicationContext(),R.raw.bgm);
        mp.setLooping(false);//반복재생 안함
        mp.start(); //배경음악 제생(Resume 부터)
    }

    @Override
    protected void onPause() {
        super.onPause();
        //자원해제
        if(mp!=null){
            mp.stop();
            mp.release();
        }
    }
}
