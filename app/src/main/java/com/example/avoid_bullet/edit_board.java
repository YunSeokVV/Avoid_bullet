package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class edit_board extends Activity { //Activity
    TextView 작성자이름;
    EditText 제목입력;
    Button 수정완료버튼;
    String 변경될_제목;
    String 변경될_작성자;
    int 포지션값;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_board);

        제목입력=(EditText)findViewById(R.id.제목입력);
        작성자이름=(TextView)findViewById(R.id.작성자이름);
        수정완료버튼=(Button)findViewById(R.id.수정완료버튼);

        Intent intent=getIntent();
        String 작성자_받아오기=intent.getStringExtra("게시글수정_작성자");
        String 제목_받아오기=intent.getStringExtra("게시글수정_제목");
        포지션값=intent.getIntExtra("포지션값",0 );

        제목입력.setText(제목_받아오기);
        작성자이름.setText(작성자_받아오기);

        수정완료버튼.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                변경될_제목=제목입력.getText().toString();
                변경될_작성자=작성자이름.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("변경될_제목", 변경될_제목);
                resultIntent.putExtra("변경될_작성자",변경될_작성자);
                resultIntent.putExtra("position", 포지션값);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }//onCreate
} //Activity
