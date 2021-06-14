package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity{//Activity 시작 , TextView  를 클릭하였을때 다른 액티비티로 넘어갈수있게끔 onClickLisstener implements 했음.
    //어플의 액티비티들은 대부분 Activity클래스, AppCompactAcvitivy, FragmentActivity중 하나로 만들어진다.
    private static MediaPlayer 메인화면브금;
    ImageView 전투기_사진1;
    ImageView 전투기_사진2;
    ImageView 전투기_사진3;
    ImageView 이미지;

    Point 좌표;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate() 시작
        //Bundle 키값과 여러가지 타입의 값을 저장하는 클래스이다. 쉽게 표현하자면 HashMap이다.

        super.onCreate(savedInstanceState);
        Log.v("check_data","onCreate 시작");


        //savedInstanceState 의 역할 : 화면이 가로, 세로가 반전되었을때 생명주기가 다시 onCreate로 가면서 화면내의 데이터가 초기화되는데 이때 날라가는 데이터를 유지할 수 있다.
        //데이터가 날라가는것을 방지하기위해선 https://doraeul.tistory.com/43 이 사이트를 참고해보도록하자. 내가 만들어플에서 나는 화면을 세로로 고정할 생각이기때문에 현재 나의 진행과는 무관하다.

        setContentView(R.layout.activity_main); // 이 문장은 main 액티비티의 디자인을 화면에 출력하는 메소드이다.

        TextView 자유_게시판=(TextView)findViewById(R.id.자유_게시판);
        TextView 총알피하기=(TextView)findViewById(R.id.총알피하기);
        TextView 게임시작=(TextView)findViewById(R.id.게임시작);
        TextView 내점수보기=(TextView)findViewById(R.id.점수보기);
        TextView 비행기선택=(TextView)findViewById(R.id.비행기선택);
        TextView 내프로필보기=(TextView)findViewById(R.id.프로필보기);
        TextView 개발자에게의견보내기=(TextView)findViewById(R.id.개발자에게의견보내기);
        전투기_사진1=(ImageView)findViewById(R.id.현재내전투기1);
        전투기_사진2=(ImageView)findViewById(R.id.현재내전투기2);
        전투기_사진3=(ImageView)findViewById(R.id.현재내전투기3);

        이미지=(ImageView)findViewById(R.id.이미지);

        메인화면브금 = MediaPlayer.create(this, R.raw.main_screen);

        메인화면브금.setLooping(true);

        메인화면브금.start();

        자유_게시판.setVisibility(View.GONE);//사라진 기능인 게시판기능을 보이지않게한다.

        총알피하기.setOnClickListener(new View.OnClickListener(){ //게임시작
            @Override
            public void onClick(View view){ //게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서
                Intent intent=new Intent(
                        getApplicationContext(), //현재 화면의 제어
                        lose_game.class); //다음 넘어갈 클래스를 지정
                //startActivity(intent); //다음 화면으로 넘어간다.
            }//게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서
        }); //게임시작

        게임시작.setOnClickListener(new View.OnClickListener(){ //게임시작
            @Override
            public void onClick(View view){ //게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서

                Intent intent=new Intent(
                        getApplicationContext(), //현재 화면의 제어
                        in_game.class); //다음 넘어갈 클래스를 지정
                startActivity(intent); //다음 화면으로 넘어간다.
                finish();
                System.out.println("log");
            }//게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서
        }); //게임시작

        내점수보기.setOnClickListener(new View.OnClickListener(){ //내 점수보기
            @Override
            public void onClick(View view){ //TextView를 눌렀을때 일어나는 일

                Intent intent=new Intent(
                        getApplicationContext(), //현재 화면의 제어
                        score_screen.class); //다음 넘어갈 클래스를 지정
                startActivity(intent); //다음 화면으로 넘어간다.
            }  //TextView를 눌렀을때 일어나는 일
        }); //내 점수보기

        비행기선택.setOnClickListener(new View.OnClickListener(){ //비행기선택
            @Override
            public void onClick(View view){ // TextView를 눌렀을때 일어나는 이벤트에 대해서
                Intent intent=new Intent(getApplicationContext(), chracter_select.class); //다음 넘어갈 클래스를 지정
                startActivityForResult(intent,1); //다음 화면으로 넘어간다.
            }// TextView를 눌렀을때 일어나는 이벤트에 대해서

        }); //비행기 선택

        내프로필보기.setOnClickListener(new View.OnClickListener(){ //내 프로필 포기
            @Override
            public void onClick(View view){ // TextView를 눌렀을때 일어나는 이벤트에 대해서
                Intent intent=new Intent(
                        getApplicationContext(),
                        my_profile.class); //다음 넘어갈 클래스를 지정
                startActivity(intent); //다음 화면으로 넘어간다.
                finish();
            }// TextView를 눌렀을때 일어나는 이벤트에 대해서
        }); //내 프로필 보기

        개발자에게의견보내기.setOnClickListener(new View.OnClickListener(){ //개발자에게의견보내기
            @Override
            public void onClick(View view){ // TextView를 눌렀을때 일어나는 이벤트에 대해서

                Uri uri = Uri.parse("mailto:agaroseson@naver.com");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                //it.setType("text/html");
                startActivity(it);

            }// TextView를 눌렀을때 일어나는 이벤트에 대해서
        }); //개발자에게의견보내기


//        Intent intnet=getIntent();
//        byte[] arr = getIntent().getByteArrayExtra("image");
//        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//        ImageView BigImage = (ImageView)findViewById(R.id.이미지);
//        BigImage.setImageBitmap(image);



//        Bitmap name = intnet.getExtras().getString("사진"); /*String형*/
//        이미지.setImageBitmap(name);

        자유_게시판.setOnClickListener(new View.OnClickListener(){ //게임시작
            @Override
            public void onClick(View view){ //게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서
                Intent intent=new Intent(getApplicationContext(),notice_board.class); //다음 넘어갈 클래스를 지정
                startActivity(intent); //다음 화면으로 넘어간다.
            }//게임시작 TextView를 눌렀을때 일어나는 이벤트에 대해서
        }); //게임시작





    }//onCreate() 끝


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                int 현재_나의_선택지=data.getIntExtra("result",0);
                System.out.println("시발:"+현재_나의_선택지);
                if(현재_나의_선택지==1){
                    전투기_사진1.setVisibility(View.VISIBLE);
                    전투기_사진2.setVisibility(View.INVISIBLE);
                    전투기_사진3.setVisibility(View.INVISIBLE);
                }
                else if(현재_나의_선택지==2){
                    전투기_사진1.setVisibility(View.INVISIBLE);
                    전투기_사진2.setVisibility(View.VISIBLE);
                    전투기_사진3.setVisibility(View.INVISIBLE);
                }
                else if(현재_나의_선택지==3){
                    전투기_사진1.setVisibility(View.INVISIBLE);
                    전투기_사진2.setVisibility(View.INVISIBLE);
                    전투기_사진3.setVisibility(View.VISIBLE);
                }
            }
            if(resultCode==RESULT_CANCELED) {

            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        System.out.println("메인액티비티 : onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("메인액티비티 : onResume");
        메인화면브금.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        System.out.println("메인액티비티 : onPause");
        메인화면브금.pause();
    }
    @Override
    protected void onStop(){
        super.onStop();
        System.out.println("메인액티비티 : onStop");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        System.out.println("메인액티비티 : onRestart");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.out.println("메인액티비티 : onDestroy");
    }

}//Activity 끝
