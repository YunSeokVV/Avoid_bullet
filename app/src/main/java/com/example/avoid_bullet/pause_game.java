package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class pause_game extends Activity {
    TextView 계속하기;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate 시작
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pause_game);  // layout xml 과 자바파일을 연결

        계속하기=(TextView)findViewById(R.id.계속하기);

        계속하기.setOnClickListener(new View.OnClickListener(){ //선택버튼을 누를경우
            @Override
            public void onClick(View view){ // 버튼을 누른경우
//                Intent intent=new Intent(
//                        getApplicationContext(), in_game.class);
//                startActivity(intent);
                finish();
            }//게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서
        }); //선택 버튼을 누를경우


    }//onCreate 끝

}
