package com.example.avoid_bullet;

public class Board {

    String 작성자;
    String 제목;

    public String get작성자() {
        return 작성자;
    }

    public void set작성자(String 작성자) {
        this.작성자 = 작성자;
    }

    public String get제목() {
        return 제목;
    }

    public void set제목(String 제목) {
        this.제목 = 제목;
    }

    public Board(String 작성자, String 제목) {
        this.작성자 = 작성자;
        this.제목 = 제목;
    }
}
