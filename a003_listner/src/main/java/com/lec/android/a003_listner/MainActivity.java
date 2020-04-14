package com.lec.android.a003_listner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.R.color.holo_red_dark;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvResult;
    EditText et;

    //Oncreate()
    //액티비티가 생성될때 호출되는 메소드
    //엑티비티 초기화 하는 코드 작성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn1);//보라색 btn1은 정수값
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);

        tvResult = findViewById(R.id.tvResult);
        et = findViewById(R.id.et);
        final LinearLayout ll = findViewById(R.id.ll);

        //방법2 : 익명클래스 (anonymous class) 사용
       btn2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) { // 클릭되었을 때 호출되는 메소드  (콜백 메소드 callback method)
               Log.d("myapp","버트2 가 클릭 되었습니다.");
               tvResult.setText("버튼2가 클릭 되었습니다.");
               tvResult.setBackgroundColor(Color.YELLOW);

           }
       });

       //다양한 이벤트, 다양한 리스너 등록 가능

        btn2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { //롱클릭시 발생 // 함수위에서 ctrl + Q 함수 assist
                Log.d("myapp", "버튼2가 롱클릭 되었습니다.");
                tvResult.setText("버튼 2가 롱클릭 되었습니다.");
                tvResult.setTextSize(40);
                tvResult.setBackgroundColor(Color.RED);


//                return false; // false 리턴하면 이벤트가click 까지 간다.
                return true; //true  리턴하면 이벤트가 Long click으로 소멸(consume)된다.

            }
        });

        //방법 3 : Lambada - expression 사용하기
        //안드로이드스튜디오의 세팅 필요 / PPT 참조
        btn3.setOnClickListener((v)->{  //onClick(View v)
            Log.d("myapp","버튼3 이 클릭 되었다. ");
            tvResult.setText("버튼3 클릭됨");
            ll.setBackgroundColor(Color.rgb(165,33,22));

        });

        //방법 4 : implementn 한 클래스 사용


        Button btnA = findViewById(R.id.btnA);
        Button btnB = findViewById(R.id.btnB);
        Button btnC = findViewById(R.id.btnC);
        Button btnD = findViewById(R.id.btnD);
        Button btnE = findViewById(R.id.btnE);
        Button btnF = findViewById(R.id.btnF);
        //단축기 ctrl + D -> 밑으로 복사해서 내리기
        class MyListener implements View.OnClickListener{
            String name;
            public MyListener(String name){this.name = name;}

            @Override
            public void onClick(View v) {
                String tag = (String)v.getTag();
                String text = (String)((Button)v).getText(); //getText는 String을 리턴하지 않고 CharSequence 객체를 리턴한다.
                String msg = String.format("%s버튼%s 이 클릭 [%s]",name,text,tag);
                Log.d("myapp",msg);
                tvResult.setText(msg);
                et.setText(et.getText().append(name));

            }
        }
        btnA.setOnClickListener(new MyListener("안녕1"));
        btnB.setOnClickListener(new MyListener("안녕2"));
        btnC.setOnClickListener(new MyListener("안녕3"));
        btnD.setOnClickListener(new MyListener("안녕4"));
        btnE.setOnClickListener(new MyListener("안녕5"));
        btnF.setOnClickListener(new MyListener("안녕6"));

        // 방법 5
        Button btnClear = findViewById(R.id.btnclear);
        btnClear.setOnClickListener(this);

        //연습
        //+, - 버튼 누르면 tvresult의 글씨가 점점 커지고 .작아지게 하기
        // getTextSize() : float 값 리턴
        Button btnInc = findViewById(R.id.btnInc);
        Button btnDec = findViewById(R.id.btnDec);

        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float textSize = tvResult.getTextSize()*(float)1.2;
                Log.d("myapp", "확~대");
                tvResult.setText(tvResult.getText());
                tvResult.setTextSize(0,textSize);
            }
        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float textSize = tvResult.getTextSize()*(float)0.8;
                Log.d("myapp", "축~소");
                tvResult.setText(tvResult.getText());
                tvResult.setTextSize(0,textSize);
            }
        });

    }

    //방법1 : 레이아웃 xml의 onclick에 지정
    public void changeText(View v){
        //Log.d(tag,message)
        //Log 창의 Debug 메세지로 출력
        Log.d("myapp","버튼1이 클릭되었습니다.");
        tvResult.setText("버튼 1이 클릭되었습니다.");
    }

    //방법 5 : 엑티비티가 implement한것을 사용
    @Override
    public void onClick(View v) {
        Log.d("myapp","Clear 버튼이 클릭되었습니다.");
        tvResult.setText("clear 버튼이 클릭되었습니다.");
        et.setText("");
    }
}
