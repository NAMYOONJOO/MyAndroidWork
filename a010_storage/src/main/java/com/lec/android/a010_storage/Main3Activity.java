package com.lec.android.a010_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
// SQLite3 : 안드로이드에 기본탑재된 모바일 용으로 제작된 경량화 DB
//          C언어로 엔진이 제작되어 가볍다
//          안드로이드에서 sqLite3 를 쉽게 사용할 수 있도록 SQLiteOpenHelper클래스제공

// SQLite 를 사용한 데이터 저장
//   1. SQLiteOpenHelper 를 상속받은 클래스 정의
//      onCreate(), onUpgrade() 작성
//   2. 위 Helper 로부터 SQLiteDatabase  DB 객체 추출
//   3. DML 명령은 : execSQL()
//      SELECT 명령은 : rawQuery() --> 결과는 Cursor 객체 사용하여 다룸

//로그 잘보기
public class Main3Activity extends AppCompatActivity {


    MySQLiteOpenHelper3 helper;
    String dbName = "st_file.db"; //'파일'의 형태로 DB가 저장 된다.
    int dbVersion = 2; //데이터베이스 버전
    SQLiteDatabase db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        helper = new MySQLiteOpenHelper3(
                this,//현재화면의 제어권자
                dbName,//DB이름
                null,//커서팩토리 null : 표준 커서가 사용됨
                dbVersion//DB버전
        );
        try {
            db = helper.getWritableDatabase();//읽고 쓰기 가능한 DB

            //읽기전용으로 뽑을 때(Select만 사용하는 경우)
//        db = helper.getReadableDatabase();
        }catch (SQLException e){
            e.printStackTrace();
            Log.e("myapp","데이터베이스를 얻어올 수 없음");
            finish();//액티비티 종료

        }



        delete();
        select();

    }//end onCreate

    void insert(){
        db.execSQL("insert into mytable (name) values ('나뮨주')");
        db.execSQL("insert into mytable (name) values ('김무경')");
        db.execSQL("insert into mytable (name) values ('김밈호')");
        db.execSQL("insert into mytable (name) values ('섬명철')");
        db.execSQL("insert into mytable (name) values ('김무주')");
        Log.d("myapp","Insert 성공");
    }
    void select(){
        Cursor c = db.rawQuery("select *from mytable",null);
        while (c.moveToNext()){
            int id = c.getInt(0);//컬럼인덱스 0부터 시작
            String name = c.getString(1);
            Log.d("myapp","id : "+id+"/name : "+name);
        }
    }
    void update(){
        db.execSQL("update mytable set name = '우주우주 졸귀우주' where id = 5");
        Log.d("myapp","업데이트완료");
    }
    void delete(){
        db.execSQL("delete from mytable where id = '2'");
        Log.d("myapp","delete완료");
    }





}// end Activity
