package com.example.avoid_bullet;

import android.graphics.Bitmap;

public class profile {
    String 아이디;
    String 점수;
    Bitmap 프로필사진;

    public profile(String 아이디, String 점수, Bitmap 프로필사진) {//new생성자를 통해서 생성자가 만들어진다.
        this.아이디 = 아이디;
        this.점수 = 점수;
        this.프로필사진 = 프로필사진;
    }

    public String get아이디() { //외부로 id값을 리턴해서 보내준다.
        return 아이디;
    }

    public void set아이디(String 아이디) { // 외부에서 받은 아이디 값을 내부로 넣어준다.
        this.아이디 = 아이디;
    }

    public String get점수() { //외부로 점수를 리턴해서 보내준다
        return 점수;
    }

    public void set점수(String 점수) { //외부에서 받은 점수 값을 내부로 넣어준다
        this.점수 = 점수;
    }

    public Bitmap get프로필사진() { //외부로 프로필사진을 리턴해서 보내준다
        return 프로필사진;
    }

    public void set프로필사진(Bitmap 프로필사진) { //외부에서 받은 사진을 내부로 넣어준다.
        this.프로필사진 = 프로필사진;
    }
}
