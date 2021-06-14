package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class chracter_select extends Activity {//Activity
    private static MediaPlayer 캐릭터선택브금;
    int 전투기1=1;
    int 전투기2=2;
    int 전투기3=3;
    static int 현재_나의_선택지=2;
    ImageView 전투기_사진1;
    ImageView 전투기_사진2;
    ImageView 전투기_사진3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate 시작
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chracter_select);  // layout xml 과 자바파일을 연결

        Button 선택=(Button)findViewById(R.id.선택);
        Button 왼쪽비행기로전환=(Button)findViewById(R.id.왼쪽비행기로전환);
        Button 오른쪽비행기로전환=(Button)findViewById(R.id.오른쪽비행기로전환);
        전투기_사진1=(ImageView)findViewById(R.id.전투기1);
        전투기_사진2=(ImageView)findViewById(R.id.전투기2);
        전투기_사진3=(ImageView)findViewById(R.id.전투기3);

        캐릭터선택브금 = MediaPlayer.create(this, R.raw.craft_select);

        캐릭터선택브금.setLooping(true);

        캐릭터선택브금.start();

        선택.setOnClickListener(new View.OnClickListener(){ //선택버튼을 누를경우
            @Override
            public void onClick(View view){ // 버튼을 누른경우
                Intent resultIntent=new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("result", 현재_나의_선택지);
                setResult(RESULT_OK,resultIntent);
                startActivity(resultIntent);
                finish();
            }//게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서
        }); //선택 버튼을 누를경우

        왼쪽비행기로전환.setOnClickListener(new View.OnClickListener(){ //선택버튼을 누를경우
            @Override
            public void onClick(View view){ // 왼쪽버튼을 누른경우
                현재_나의_선택지--;
                if(현재_나의_선택지==2){
                    전투기_사진2.setVisibility(View.VISIBLE);
                    전투기_사진1.setVisibility(View.INVISIBLE);
                    전투기_사진3.setVisibility(View.INVISIBLE);
                }
                else if(현재_나의_선택지==1){
                    전투기_사진2.setVisibility(View.INVISIBLE);
                    전투기_사진1.setVisibility(View.VISIBLE);
                    전투기_사진3.setVisibility(View.INVISIBLE);
                }
                if(현재_나의_선택지==0){
                    현재_나의_선택지=1;
                    전투기_사진2.setVisibility(View.INVISIBLE);
                    전투기_사진1.setVisibility(View.VISIBLE);
                    전투기_사진3.setVisibility(View.INVISIBLE);
                }
            }//게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서
        }); //선택 버튼을 누를경우

        오른쪽비행기로전환.setOnClickListener(new View.OnClickListener(){ //선택버튼을 누를경우
            @Override
            public void onClick(View view){ // 버튼을 누른경우
                현재_나의_선택지++;
                if(현재_나의_선택지==2){
                    전투기_사진2.setVisibility(View.VISIBLE);
                    전투기_사진1.setVisibility(View.INVISIBLE);
                    전투기_사진3.setVisibility(View.INVISIBLE);
                }
                else if(현재_나의_선택지==3){
                    전투기_사진2.setVisibility(View.INVISIBLE);
                    전투기_사진1.setVisibility(View.INVISIBLE);
                    전투기_사진3.setVisibility(View.VISIBLE);
                }
                if(현재_나의_선택지==4){
                    현재_나의_선택지=3;
                    전투기_사진2.setVisibility(View.INVISIBLE);
                    전투기_사진1.setVisibility(View.INVISIBLE);
                    전투기_사진3.setVisibility(View.VISIBLE);
                }
            }//게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서
        }); //선택 버튼을 누를경우

    }//onCreate 끝

    @Override
    protected void onResume(){
        super.onResume();

        캐릭터선택브금.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        캐릭터선택브금.pause();
    }

}//Activity

