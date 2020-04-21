package com.lec.android.a014_dialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    // 다이얼로그의 ID를 보기 좋은 상수로 선언해서 사용한다
    final int DIALOG_TEXT = 1;
    final int DIALOG_LIST = 2; // 리스트 형식의 다이얼로그 ID
    final int DIALOG_RADIO= 3; // 하나만 선택할 수 있는 다이얼로그 ID
    final int DIALOG_MULTICHOICE= 4;

    TextView tvResult;

    int choice=-1;//라디오버튼 선택값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // AlertDialog : Dialog 를 상속받은 자식클래스로
//          다이얼로그를 쉽게 만들수 있도록 지원해주고,
//          Activity 라이프 사이클과 다이얼로그의 라이프사이클을
//          맞춰줌

        tvResult = findViewById(R.id.tvResult);

        Button btnText = findViewById(R.id.btnText);
        Button btnList = findViewById(R.id.btnList);
        Button btnRadio = findViewById(R.id.btnRadio);
        Button btnMultiChoice = findViewById(R.id.btnMultiChoice);

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(DIALOG_TEXT);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(DIALOG_LIST);
            }
        });

        btnRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(DIALOG_RADIO);
            }
        });

        btnMultiChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(DIALOG_MULTICHOICE);
            }
        });

    }//end onCreate()

    protected AlertDialog.Builder showAlert(int id){ //Builder 리턴
        switch (id){
            case DIALOG_TEXT:
                AlertDialog.Builder builder1 =
                        new AlertDialog.Builder(this);
                builder1.setTitle("다이얼로그 제목")
                .setMessage("안녕들하세요")
                .setPositiveButton("금정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "금정", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("부정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"부정",Toast.LENGTH_SHORT).show();

                    }
                })
                .setNeutralButton("중립", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"중립",Toast.LENGTH_SHORT).show();
                    }
                });

                builder1.show();
                return builder1;
            case DIALOG_LIST:
                AlertDialog.Builder builder2 =
                        new AlertDialog.Builder(this);
                final String str[] = {"사과","딸기","수박","배"};
                builder2.setTitle("리스트 형식 다이올로그 제목")
                        .setNegativeButton("취소",null)
                        .setItems(str, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"선택은 : "+str[which],Toast.LENGTH_LONG).show();
                            }
                        });
                builder2.show();
                return builder2;
            case DIALOG_RADIO:
                //커스텀 스타일 적용  styles.xml 편집
                AlertDialog.Builder builder3 =
                        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

                final String[] str2 = {"빨강","녹색","파랑"};
                builder3.setTitle("색을 고르세요")
                        .setPositiveButton("선택완료",

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), str2[choice]+"를 선택", Toast.LENGTH_LONG).show();
                                    }
                                })
                        .setNegativeButton("취소",null)
                        .setSingleChoiceItems(str2, // 리스트배열목록
                                -1,//기본설정값
                                new DialogInterface.OnClickListener() {
                            //라디오 버튼에서 선택했을 때 콜백
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        choice=which;
                                    }
                                });
                builder3.show();
                return  builder3;
            case DIALOG_MULTICHOICE :
                AlertDialog.Builder builder4 =
                        new AlertDialog.Builder(this);
                final String [] str3 =  {"김우경","코구멍","똥꾸멍","우주"};
                final boolean [] checked =  {true,false,true,false};

                builder4.setTitle("MultiChoice 다이얼로그 제목")
                        .setPositiveButton("선택완료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String str = "선택된 값은 :";
                                for(int i = 0;i<checked.length;i++){
                                    if(checked[i]){
                                        str = str+str3[i]+ ", ";
                                    }
                                }
                                tvResult.setText(str);
                            }
                        })
                    .setNegativeButton("취소",null)
                    .setMultiChoiceItems(str3,//check 박스 리스트 항목
                        checked, //초기값 (선택여부) 배열
                        //선택했을 때 호출되는 콜백
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checked[which]=isChecked;
                            }
                        })
                ;
                builder4.show();
                return builder4;

        }
        return  null;

    }
}//end Activity
