package com.lec.android.a008_practice;

public class Data {
    //추가하기 전이기 때문에 아무것도 없다.
    public static int [] AGE = {

    };
    public static String[] NAME={

    };
    public static String[] ADDRESS= {

    };

    private static int idx = 0;
    public static int next(){
        return idx++;
    }
}
