package com.example.avoid_bullet;

public class Comment {
    String 댓글_작성자;
    String 댓글_내용;

    public String get댓글_작성자() {
        return 댓글_작성자;
    }

    public void set댓글_작성자(String 댓글_작성자) {
        this.댓글_작성자 = 댓글_작성자;
    }

    public String get댓글_내용() {
        return 댓글_내용;
    }

    public void set댓글_내용(String 댓글_내용) {
        this.댓글_내용 = 댓글_내용;
    }

    public Comment(String 댓글_작성자, String 댓글_내용) {
        this.댓글_작성자 = 댓글_작성자;
        this.댓글_내용 = 댓글_내용;
    }
}
