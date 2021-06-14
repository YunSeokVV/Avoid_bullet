package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class see_board extends Activity {
    Button 댓글작성;
    TextView 작성자이름;
    TextView 글제목;
    ArrayList<Comment> mArrayList;  // 어레이리스트 mArrayList선언. 사용할 객체의 이름은 Comment
    comment_adapter mAdapter;  //어댑터를 사용하기위해 mAdapter선언.
    RecyclerView mRecyclerView;  //리사이클러뷰 데이터 mRecyclerView선언.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_board);

        댓글작성=(Button)findViewById(R.id.댓글작성);
        작성자이름=(TextView)findViewById(R.id.작성자이름);
        글제목=(TextView)findViewById(R.id.글제목);

        board_adapter board_adapter=new board_adapter();

        Intent intent2 = getIntent();  //제목,작성자
        String 제목=intent2.getStringExtra("게시글보기_제목");   //게시글보기_제목   제목
        String 작성자=intent2.getStringExtra("게시글보기_작성자");//게시글보기_작성자   작성자



        작성자이름.setText(작성자);
        글제목.setText(제목);
        System.out.println("ㅋㅋㅋ 제목 :"+제목);
        System.out.println("ㅋㅋㅋ 작성자 :"+작성자);

        댓글작성.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){




                //4번 절차
                Intent intent=new Intent(getApplicationContext(), notice_board.class);
            }
        });

    }
}
