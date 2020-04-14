package com.lec.android.a003_listner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    //계산기 앱 만들기
    //연산기호가 하나 들어갈때만 계산가능해요

    EditText et;
    int num1=0;
    int num2=0;
    int result = 0;
    boolean flag = true;

    int calType =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        et = findViewById(R.id.result);



        Button btn0 = findViewById(R.id.btn0);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);

        Button btnDiv = findViewById(R.id.btnDiv);
        Button btnMul = findViewById(R.id.btnMul);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSub = findViewById(R.id.btnSub);

        Button btnResult = findViewById(R.id.btnResult);
        Button btnReset = findViewById(R.id.btnReset);


        class calListener implements View.OnClickListener{
            String cal;
            public calListener(String cal){
                this.cal = cal;
            }

            @Override
            public void onClick(View v) {
                if(cal.equals("x")) {
                    calType = 1;
                    num2 = num1;
                    num1 = 0;
                }else if(cal.equals("+")){
                    calType = 2;
                    num2 = num1;
                    num1 = 0;
                }else if(cal.equals("-")){
                    calType = 3;
                    num2 = num1;
                    num1 = 0;
                }else if(cal.equals("÷")){
                    calType = 4;
                    num2 = num1;
                    num1 = 0;
                }else if(cal.equals("0")){
                    num1 = num1*10;
                }else if(cal.equals("1")){
                    num1 = num1*10+1;
                }else if(cal.equals("2")){
                    num1 = num1*10+2;
                }else if(cal.equals("3")){
                    num1 = num1*10+3;
                }else if(cal.equals("4")){
                    num1 = num1*10+4;
                }else if(cal.equals("5")){
                    num1 = num1*10+5;
                }else if(cal.equals("6")){
                    num1 = num1*10+6;
                }else if(cal.equals("7")){
                    num1 = num1*10+7;
                }else if(cal.equals("8")){
                    num1 = num1*10+8;
                }else if(cal.equals("9")){
                    num1 = num1*10+9;
                }
                if(flag==false){
                    et.setText("");
                    num1 = 0;
                    num2 = 0;
                    calType = 0;
                    flag=true;
                }
                et.setText(et.getText().append(cal));

            }
        }
        btnAdd.setOnClickListener(new calListener("+"));
        btnSub.setOnClickListener(new calListener("-"));
        btnMul.setOnClickListener(new calListener("x"));
        btnDiv.setOnClickListener(new calListener("÷"));
        btn0.setOnClickListener(new calListener("0"));
        btn1.setOnClickListener(new calListener("1"));
        btn2.setOnClickListener(new calListener("2"));
        btn3.setOnClickListener(new calListener("3"));
        btn4.setOnClickListener(new calListener("4"));
        btn5.setOnClickListener(new calListener("5"));
        btn6.setOnClickListener(new calListener("6"));
        btn7.setOnClickListener(new calListener("7"));
        btn8.setOnClickListener(new calListener("8"));
        btn9.setOnClickListener(new calListener("9"));

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1=0;
                num2=0;
                calType=0;
                et.setText("");
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (calType) {
                    case 1:
                        result = num2 * num1;
                        break;
                    case 2:
                        result = num2 + num1;
                        break;
                    case 3:
                        result = num2 - num1;
                        break;
                    case 4:
                        result = num2 / num1;
                        break;

                }
                et.setText(result+" ");
                flag = false;

            }
        });
    }
}
