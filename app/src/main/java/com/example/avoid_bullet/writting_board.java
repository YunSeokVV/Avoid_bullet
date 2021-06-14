package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class writting_board extends Activity {//Activity
    TextView 작성자이름;
    EditText 제목입력;
    Button 작성완료버튼;
    String 작성자이름_데이터;
    String 제목입력_데이터;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writting_board);

        제목입력=(EditText)findViewById(R.id.제목입력);
        작성자이름=(TextView)findViewById(R.id.작성자이름);
        작성완료버튼=(Button)findViewById(R.id.작성완료버튼);

        작성완료버튼.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                작성자이름_데이터=작성자이름.getText().toString();
                제목입력_데이터=제목입력.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result1", 작성자이름_데이터);
                resultIntent.putExtra("result2", 제목입력_데이터);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });



    }//onCreate
}//Activity
