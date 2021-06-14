package com.example.avoid_bullet;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class in_game extends Activity{  //in_game

    //ImageView craft

    String score2; // 플레이어의 최종점수를 인텐트로 넘겨서 패배화면에서 보여주기위해 존재하는 변수. 이 변수에 최종점수를 담는다.


    boolean stop=true; //전역변수 stop은 플레이어의 전투기가 총알을 맞은 시점에서 플레이어점수증가 스레드, 총알잰 스레드, 플레이어의 전투기가 총알에 맞았을때 발동하는 스레드가 작동을 멈춘다.

    ImageView iv1;//전투기를 표현하는 이미지 뷰

    String numStr1;//총알의 x좌표를 담기위한 String 변수
    String numStr2;//총알의 y좌표를 담기위한 String 변수

    int score=0; //플레이어의 점수를 나타내기위한 변수

    TextView textView5;


    TextView textView2;
    TextView textView3;

    ImageView craft; //사용자가 조종할 전투기이다.
    ImageView craft2;
    ImageView craft3;

    float oldXvalue;
    float oldYvalue;

    int 총알_x_좌표_정수; //총알의 현재위치를 int 형 데이터로 담는 변수.
    int 총알_y_좌표_정수; //총알의 현재위치를 int 형 데이터로 담는 변수.

    private static MediaPlayer 게임화면브금;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate 시작
        super.onCreate(savedInstanceState);

        //setContentView : layout 파일에서 액티비티를 설정한다. (여기서는 전체 화면설정.)
        setContentView(R.layout.in_game);  // layout xml 과 자바파일을 연결

        //총알뷰가 앞쪽에서 보이는것을 막기위해 검정색으로 가렸습니다.
        ImageView black=(ImageView)findViewById(R.id.black) ;
        ImageView black2=(ImageView)findViewById(R.id.black2);
        black.bringToFront();
        black2.bringToFront();

        게임화면브금 = MediaPlayer.create(this, R.raw.boshy_bgm_1);


        게임화면브금.setLooping(true);

        게임화면브금.start();

        //craft=(ImageView)findViewById(R.id.craft);
        iv1=(ImageView)findViewById(R.id.red_bullet1);

//        Log.v("check_data", "first_craft.getX()"+craft.getX());
//        Log.v("check_data", "first_craft.getY()"+craft.getY());
//        Log.v("check_data", "first_iv1.getX()"+iv1.getX());
//        Log.v("check_data", "first_iv1.getY()"+iv1.getY());

        //Animation 변수 anim 선언.
        //AnimationUtils : 애니메이션 작업을위한 일반적인 유틸리티(컴퓨터 이용에 도움이 되는 각종 소프트웨어)를 정의합니다.
        //loadAnimation : resource(set_anim) 에서 객체를 가져온다
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.red_bullet);

        final ImageView red_bullet = (ImageView)findViewById(R.id.red_bullet1); // 이미지뷰의 아이디를 설정시켜줌.
        // TODO: 2019-07-31 전투기를 어떤 사진으로 쓸지 정해주는 변수.
        if(chracter_select.현재_나의_선택지==1){
            craft = (ImageView) findViewById(R.id.craft1);

            //전투기를 눌러서 이용자가 조종할 수 있게끔 설정.
            craft.setOnTouchListener(ViewListener);

            //나머지 전투기들은 보이지않게끔 처리해야한다.
            craft2=(ImageView)findViewById(R.id.craft2);
            craft3=(ImageView)findViewById(R.id.craft3);
            craft2.setVisibility(View.INVISIBLE);
            craft3.setVisibility(View.INVISIBLE);
        }
        else if(chracter_select.현재_나의_선택지==2){
            craft = (ImageView) findViewById(R.id.craft2);

            //전투기를 눌러서 이용자가 조종할 수 있게끔 설정.
            craft.setOnTouchListener(ViewListener);

            //나머지 전투기들은 보이지않게끔 처리해야한다.
            craft2=(ImageView)findViewById(R.id.craft1);
            craft3=(ImageView)findViewById(R.id.craft3);
            craft2.setVisibility(View.INVISIBLE);
            craft3.setVisibility(View.INVISIBLE);
        }
        else if(chracter_select.현재_나의_선택지==3){
            craft = (ImageView) findViewById(R.id.craft3);


            //전투기를 눌러서 이용자가 조종할 수 있게끔 설정.
            craft.setOnTouchListener(ViewListener);

            //나머지 전투기들은 보이지않게끔 처리해야한다.
            craft2=(ImageView)findViewById(R.id.craft1);
            craft3=(ImageView)findViewById(R.id.craft2);
            craft2.setVisibility(View.INVISIBLE);
            craft3.setVisibility(View.INVISIBLE);
        }

    }//onCreate 끝




    //전투기의 움직임을 표현하는 객체
    private final View.OnTouchListener ViewListener = new View.OnTouchListener() { //OnTouchListener

        @Override
        public boolean onTouch(View v, MotionEvent event){ //onTouch
            Log.d("check_data","코드의 흐름5");
            int parentWidth = ((ViewGroup)v.getParent()).getWidth();    // 부모 View 의 Width
            int parentHeight = ((ViewGroup)v.getParent()).getHeight();    // 부모 View 의 Height
            Log.d("check_data","코드의 흐름6");
            if(event.getAction() == MotionEvent.ACTION_DOWN){


                ImageView iv1=(ImageView)findViewById(R.id.red_bullet1);


                // 뷰 누름
                oldXvalue = event.getX();
                oldYvalue = event.getY();

                // TODO: 2019-07-25 전투기좌표
                Log.v("check_data", "전투기의 x좌표(누른상태) "+String.valueOf(craft.getX()));
                Log.v("check_data", "전투기의 y좌표(누른상태) "+String.valueOf(craft.getY()));

                Log.v("check_data", " craft.getLeft() (누른상태)"+craft.getLeft());
                Log.v("check_data", " craft.getTop() (누른상태)"+craft.getTop());
                Log.v("check_data", " craft.getRight() (누른상태)"+craft.getRight());
                Log.v("check_data", " craft.getBottom() (누른상태)"+ craft.getBottom());

                TextView textView14=(TextView)findViewById(R.id.textView14);  // 전투기의 x 좌표를 표시하기위한 TextView
                TextView textView13=(TextView)findViewById(R.id.textView13);  // 전투기의 y 좌표를 표시하기위한 TextView

                textView14.setText(String.valueOf(craft.getX()));//전투기의 x 좌표를 화면에 출력
                textView13.setText(String.valueOf(craft.getY()));//전투기의 y 좌표를 화면에 출력

            }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                // 뷰 이동 중



                TextView textView14=(TextView)findViewById(R.id.textView14);  // 전투기의 x 좌표를 표시하기위한 TextView
                TextView textView13=(TextView)findViewById(R.id.textView13);  // 전투기의 y 좌표를 표시하기위한 TextView

                textView14.setText(String.valueOf(craft.getX()));//전투기의 x 좌표를 화면에 출력
                textView13.setText(String.valueOf(craft.getY()));//전투기의 y 좌표를 화면에 출력

                Log.d("check_data","코드의 흐름8");
                v.setX(v.getX() + (event.getX()) - (v.getWidth()/2));
                v.setY(v.getY() + (event.getY()) - (v.getHeight()/2));

                Log.v("check_data", "전투기의 x좌표(뷰 이동중) "+String.valueOf(craft.getX()));
                Log.v("check_data", "전투기의 y좌표(뷰 이동중) "+String.valueOf(craft.getY()));


                Log.v("check_data", " craft.getLeft() (뷰 이동중) "+craft.getLeft());
                Log.v("check_data", " craft.getTop() (뷰 이동중) "+craft.getTop());
                Log.v("check_data", " craft.getRight() (뷰 이동중) "+craft.getRight());
                Log.v("check_data", " craft.getBottom() (뷰 이동중) "+ craft.getBottom());

                int[] location =new int[2];

                craft.getLocationOnScreen(location);

                int result1=location[0];
                int result2=location[1];

                Log.v("zzz", "전투기의 x 절대좌표  : " + result1);
                Log.v("zzz", "전투기의 y 절대 좌표 : " + result2);
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                // 뷰에서 손을 뗌



                TextView textView14=(TextView)findViewById(R.id.textView14);  // 전투기의 x 좌표를 표시하기위한 TextView
                TextView textView13=(TextView)findViewById(R.id.textView13);  // 전투기의 y 좌표를 표시하기위한 TextView

                textView14.setText(String.valueOf(craft.getX()));//전투기의 x 좌표를 화면에 출력
                textView13.setText(String.valueOf(craft.getY()));//전투F기의 y 좌표를 화면에 출력


                Log.v("check_data", "전투기의 x좌표(뷰에서 손 뗌) "+String.valueOf(craft.getX()));
                Log.v("check_data", "전투기의 y좌표(뷰에서 손 뗌) "+String.valueOf(craft.getY()));

                Log.v("check_data", " craft.getLeft() (뷰에서 손 뗌) "+craft.getLeft());
                Log.v("check_data", " craft.getTop() (뷰에서 손 뗌) "+craft.getTop());
                Log.v("check_data", " craft.getRight() (뷰에서 손 뗌) "+craft.getRight());
                Log.v("check_data", " craft.getBottom() (뷰에서 손 뗌) "+ craft.getBottom());

                Log.d("check_data","코드의 흐름9");
                if(v.getX() < 0){
                    Log.d("check_data","코드의 흐름10");
                    v.setX(0);
                }else if((v.getX() + v.getWidth()) > parentWidth){
                    Log.d("check_data","코드의 흐름11");
                    v.setX(parentWidth - v.getWidth());
                }

                if(v.getY() < 0){
                    Log.d("check_data","코드의 흐름12");
                    v.setY(0);
                }else if((v.getY() + v.getHeight()) > parentHeight){
                    Log.d("check_data","코드의 흐름13");
                    v.setY(parentHeight - v.getHeight());
                }
                Log.d("check_data","코드의 흐름14");
            }

            return true;
        } //onTouch

    };  //OnTouchListener

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    @Override
    protected void onResume(){
        super.onResume();
        게임화면브금.start();



        //총알 발사 스레드를 시작한다.
        RedBulletThread thread =new RedBulletThread();
        thread.start();


        textView5=(TextView)findViewById(R.id.textView5);

        //점수 증가 스레드를 시작한다.
        ScoreThread score_thread=new ScoreThread();
        score_thread.start();

//        //총알의 좌표를 표현하는 TextView
//        Red_Bullet_Coordination thread2=new Red_Bullet_Coordination();
//        thread2.start();

        //총알 피격을 표현하는 스레드
        Hit_CraftThread thread3=new Hit_CraftThread();
        thread3.start();

    }

    @Override
    protected void onPause(){  //onPause
        super.onPause();

        게임화면브금.pause();

//        Intent intent=new Intent(getApplicationContext(), pause_game.class);
//
//        startActivity(intent);


    }  //onPause

    //총알이 피격 판정을 해주는 스레드
    class Hit_CraftThread extends Thread{
//        boolean stop=true;

        @Override
        public void run(){ //run
            while(stop==true) { //while
                Log.v("check_stop","총알 피격판정 스레드 진행중");
                //1~28 번까지의 총알 아이디를 변수에 담습니다. 좌측벽면에서 나오는 총알에대한 정보입니다.
                ImageView iv1=findViewById(R.id.red_bullet1);
                ImageView iv2=findViewById(R.id.red_bullet2);
                ImageView iv3=findViewById(R.id.red_bullet3);
                ImageView iv4=findViewById(R.id.red_bullet4);
                ImageView iv5=findViewById(R.id.red_bullet5);
                ImageView iv6=findViewById(R.id.red_bullet6);
                ImageView iv7=findViewById(R.id.red_bullet7);
                ImageView iv8=findViewById(R.id.red_bullet8);
                ImageView iv9=findViewById(R.id.red_bullet9);
                ImageView iv10=findViewById(R.id.red_bullet10);
                ImageView iv11=findViewById(R.id.red_bullet11);
                ImageView iv12=findViewById(R.id.red_bullet12);
                ImageView iv13=findViewById(R.id.red_bullet13);
                ImageView iv14=findViewById(R.id.red_bullet14);
                ImageView iv15=findViewById(R.id.red_bullet15);
                ImageView iv16=findViewById(R.id.red_bullet16);
                ImageView iv17=findViewById(R.id.red_bullet17);
                ImageView iv18=findViewById(R.id.red_bullet18);
                ImageView iv19=findViewById(R.id.red_bullet19);
                ImageView iv20=findViewById(R.id.red_bullet20);
                ImageView iv21=findViewById(R.id.red_bullet21);
                ImageView iv22=findViewById(R.id.red_bullet22);
                ImageView iv23=findViewById(R.id.red_bullet23);
                ImageView iv24=findViewById(R.id.red_bullet24);
                ImageView iv25=findViewById(R.id.red_bullet25);
                ImageView iv26=findViewById(R.id.red_bullet26);
                ImageView iv27=findViewById(R.id.red_bullet27);
                ImageView iv28=findViewById(R.id.red_bullet28);

                //29~48 번까지의 총알 아이디를 변수에 담습니다. 상단벽면에서 나오는 총알에대한 정보입니다.
                ImageView iv29=findViewById(R.id.red_bullet29);
                ImageView iv30=findViewById(R.id.red_bullet30);
                ImageView iv31=findViewById(R.id.red_bullet31);
                ImageView iv32=findViewById(R.id.red_bullet32);
                ImageView iv33=findViewById(R.id.red_bullet33);
                ImageView iv34=findViewById(R.id.red_bullet34);
                ImageView iv35=findViewById(R.id.red_bullet35);
                ImageView iv36=findViewById(R.id.red_bullet36);
                ImageView iv37=findViewById(R.id.red_bullet37);
                ImageView iv38=findViewById(R.id.red_bullet38);
                ImageView iv39=findViewById(R.id.red_bullet39);
                ImageView iv40=findViewById(R.id.red_bullet40);
                ImageView iv41=findViewById(R.id.red_bullet41);
                ImageView iv42=findViewById(R.id.red_bullet42);
                ImageView iv43=findViewById(R.id.red_bullet43);
                ImageView iv44=findViewById(R.id.red_bullet44);
                ImageView iv45=findViewById(R.id.red_bullet45);
                ImageView iv46=findViewById(R.id.red_bullet46);
                ImageView iv47=findViewById(R.id.red_bullet47);
                ImageView iv48=findViewById(R.id.red_bullet48);
                ImageView iv49=findViewById(R.id.red_bullet49);

                //49~74 번까지의 총알 아이디를 변수에 담습니다. 오른쪽벽면에서 나오는 총알에대한 정보입니다.
                ImageView iv50=findViewById(R.id.red_bullet50);
                ImageView iv51=findViewById(R.id.red_bullet51);
                ImageView iv52=findViewById(R.id.red_bullet52);
                ImageView iv53=findViewById(R.id.red_bullet53);
                ImageView iv54=findViewById(R.id.red_bullet54);
                ImageView iv55=findViewById(R.id.red_bullet55);
                ImageView iv56=findViewById(R.id.red_bullet56);
                ImageView iv57=findViewById(R.id.red_bullet57);
                ImageView iv58=findViewById(R.id.red_bullet58);
                ImageView iv59=findViewById(R.id.red_bullet59);
                ImageView iv60=findViewById(R.id.red_bullet60);
                ImageView iv61=findViewById(R.id.red_bullet61);
                ImageView iv62=findViewById(R.id.red_bullet62);
                ImageView iv63=findViewById(R.id.red_bullet63);
                ImageView iv64=findViewById(R.id.red_bullet64);
                ImageView iv65=findViewById(R.id.red_bullet65);
                ImageView iv66=findViewById(R.id.red_bullet66);
                ImageView iv67=findViewById(R.id.red_bullet67);
                ImageView iv68=findViewById(R.id.red_bullet68);
                ImageView iv69=findViewById(R.id.red_bullet69);
                ImageView iv70=findViewById(R.id.red_bullet70);
                ImageView iv71=findViewById(R.id.red_bullet71);
                ImageView iv72=findViewById(R.id.red_bullet72);
                ImageView iv73=findViewById(R.id.red_bullet73);
                ImageView iv74=findViewById(R.id.red_bullet74);

                //75~90 번까지의 총알 아이디를 변수에 담습니다. 아래쪽벽면에서 나오는 총알에대한 정보입니다.
                ImageView iv75=findViewById(R.id.red_bullet75);
                ImageView iv76=findViewById(R.id.red_bullet76);
                ImageView iv77=findViewById(R.id.red_bullet77);
                ImageView iv78=findViewById(R.id.red_bullet78);
                ImageView iv79=findViewById(R.id.red_bullet79);
                ImageView iv80=findViewById(R.id.red_bullet80);
                ImageView iv81=findViewById(R.id.red_bullet81);
                ImageView iv82=findViewById(R.id.red_bullet82);
                ImageView iv83=findViewById(R.id.red_bullet83);
                ImageView iv84=findViewById(R.id.red_bullet84);
                ImageView iv85=findViewById(R.id.red_bullet85);
                ImageView iv86=findViewById(R.id.red_bullet86);
                ImageView iv87=findViewById(R.id.red_bullet87);
                ImageView iv88=findViewById(R.id.red_bullet88);
                ImageView iv89=findViewById(R.id.red_bullet89);
                ImageView iv90=findViewById(R.id.red_bullet90);
                

                Log.v("check_data", "craft.getX()"+craft.getX());
                Log.v("check_data", "craft.getY()"+craft.getY());
                Log.v("check_data", "iv1.getX()"+iv1.getX());
                Log.v("check_data", "iv1.getY()"+iv1.getY());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //총알과 전투기가 닿는경우 for문 돌려


                //이 액티비티를 처음 실행시키면 총알과 전투기의 X,Y좌표가 전부 0 이여서 총알이 "겹친다" 라고 판정이된다. 이를위해 조건문을 하나 걸어준다.
                if(craft.getX()==0.0 && craft.getY()==0.0){
                    Log.v("sss", "I Got the Error 잡았다요놈");
                }

                //1번부터 28번 총알을 맞을경우 게임패배를 게임패배 액티비티를 띄우는 조건문들.

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv1.getX() && craft.getX() + 60 >= iv1.getX() && craft.getY() + 60 >= iv1.getY() && craft.getY() - 60 <= iv1.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.


                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }


                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv2.getX() && craft.getX() + 60 >= iv2.getX() && craft.getY() + 60 >= iv2.getY() && craft.getY() - 60 <= iv2.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv3.getX() && craft.getX() + 60 >= iv3.getX() && craft.getY() + 60 >= iv3.getY() && craft.getY() - 60 <= iv3.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv4.getX() && craft.getX() + 60 >= iv4.getX() && craft.getY() + 60 >= iv4.getY() && craft.getY() - 60 <= iv4.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv5.getX() && craft.getX() + 60 >= iv5.getX() && craft.getY() + 60 >= iv5.getY() && craft.getY() - 60 <= iv5.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv6.getX() && craft.getX() + 60 >= iv6.getX() && craft.getY() + 60 >= iv6.getY() && craft.getY() - 60 <= iv6.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv7.getX() && craft.getX() + 60 >= iv7.getX() && craft.getY() + 60 >= iv7.getY() && craft.getY() - 60 <= iv7.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);
                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv8.getX() && craft.getX() + 60 >= iv8.getX() && craft.getY() + 60 >= iv8.getY() && craft.getY() - 60 <= iv8.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv9.getX() && craft.getX() + 60 >= iv9.getX() && craft.getY() + 60 >= iv9.getY() && craft.getY() - 60 <= iv9.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv10.getX() && craft.getX() + 60 >= iv10.getX() && craft.getY() + 60 >= iv10.getY() && craft.getY() - 60 <= iv10.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv11.getX() && craft.getX() + 60 >= iv11.getX() && craft.getY() + 60 >= iv11.getY() && craft.getY() - 60 <= iv11.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv12.getX() && craft.getX() + 60 >= iv12.getX() && craft.getY() + 60 >= iv12.getY() && craft.getY() - 60 <= iv12.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv13.getX() && craft.getX() + 60 >= iv13.getX() && craft.getY() + 60 >= iv13.getY() && craft.getY() - 60 <= iv13.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv14.getX() && craft.getX() + 60 >= iv14.getX() && craft.getY() + 60 >= iv14.getY() && craft.getY() - 60 <= iv14.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv15.getX() && craft.getX() + 60 >= iv15.getX() && craft.getY() + 60 >= iv15.getY() && craft.getY() - 60 <= iv15.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv16.getX() && craft.getX() + 60 >= iv16.getX() && craft.getY() + 60 >= iv16.getY() && craft.getY() - 60 <= iv16.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv17.getX() && craft.getX() + 60 >= iv17.getX() && craft.getY() + 60 >= iv17.getY() && craft.getY() - 60 <= iv17.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv18.getX() && craft.getX() + 60 >= iv18.getX() && craft.getY() + 60 >= iv18.getY() && craft.getY() - 60 <= iv18.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv19.getX() && craft.getX() + 60 >= iv19.getX() && craft.getY() + 60 >= iv19.getY() && craft.getY() - 60 <= iv19.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv20.getX() && craft.getX() + 60 >= iv20.getX() && craft.getY() + 60 >= iv20.getY() && craft.getY() - 60 <= iv20.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv21.getX() && craft.getX() + 60 >= iv21.getX() && craft.getY() + 60 >= iv21.getY() && craft.getY() - 60 <= iv21.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv22.getX() && craft.getX() + 60 >= iv22.getX() && craft.getY() + 60 >= iv22.getY() && craft.getY() - 60 <= iv22.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv23.getX() && craft.getX() + 60 >= iv23.getX() && craft.getY() + 60 >= iv23.getY() && craft.getY() - 60 <= iv23.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv24.getX() && craft.getX() + 60 >= iv24.getX() && craft.getY() + 60 >= iv24.getY() && craft.getY() - 60 <= iv24.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv25.getX() && craft.getX() + 60 >= iv25.getX() && craft.getY() + 60 >= iv25.getY() && craft.getY() - 60 <= iv25.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv26.getX() && craft.getX() + 60 >= iv26.getX() && craft.getY() + 60 >= iv26.getY() && craft.getY() - 60 <= iv26.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv27.getX() && craft.getX() + 60 >= iv27.getX() && craft.getY() + 60 >= iv27.getY() && craft.getY() - 60 <= iv27.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv28.getX() && craft.getX() + 60 >= iv28.getX() && craft.getY() + 60 >= iv28.getY() && craft.getY() - 60 <= iv28.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }
                
                //29~48 번 총알은 위쪽에서 나오는 총알들이다.

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv29.getX() && craft.getX() + 60 >= iv29.getX() && craft.getY() + 60 >= iv29.getY() && craft.getY() - 60 <= iv29.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv30.getX() && craft.getX() + 60 >= iv30.getX() && craft.getY() + 60 >= iv30.getY() && craft.getY() - 60 <= iv30.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv31.getX() && craft.getX() + 60 >= iv31.getX() && craft.getY() + 60 >= iv31.getY() && craft.getY() - 60 <= iv31.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv32.getX() && craft.getX() + 60 >= iv32.getX() && craft.getY() + 60 >= iv32.getY() && craft.getY() - 60 <= iv32.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv33.getX() && craft.getX() + 60 >= iv33.getX() && craft.getY() + 60 >= iv33.getY() && craft.getY() - 60 <= iv33.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv34.getX() && craft.getX() + 60 >= iv34.getX() && craft.getY() + 60 >= iv34.getY() && craft.getY() - 60 <= iv34.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv35.getX() && craft.getX() + 60 >= iv35.getX() && craft.getY() + 60 >= iv35.getY() && craft.getY() - 60 <= iv35.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv36.getX() && craft.getX() + 60 >= iv36.getX() && craft.getY() + 60 >= iv36.getY() && craft.getY() - 60 <= iv36.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv37.getX() && craft.getX() + 60 >= iv37.getX() && craft.getY() + 60 >= iv37.getY() && craft.getY() - 60 <= iv37.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv38.getX() && craft.getX() + 60 >= iv38.getX() && craft.getY() + 60 >= iv38.getY() && craft.getY() - 60 <= iv38.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv39.getX() && craft.getX() + 60 >= iv39.getX() && craft.getY() + 60 >= iv39.getY() && craft.getY() - 60 <= iv39.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv40.getX() && craft.getX() + 60 >= iv40.getX() && craft.getY() + 60 >= iv40.getY() && craft.getY() - 60 <= iv40.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv41.getX() && craft.getX() + 60 >= iv41.getX() && craft.getY() + 60 >= iv41.getY() && craft.getY() - 60 <= iv41.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv42.getX() && craft.getX() + 60 >= iv42.getX() && craft.getY() + 60 >= iv42.getY() && craft.getY() - 60 <= iv42.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv43.getX() && craft.getX() + 60 >= iv43.getX() && craft.getY() + 60 >= iv43.getY() && craft.getY() - 60 <= iv43.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv44.getX() && craft.getX() + 60 >= iv44.getX() && craft.getY() + 60 >= iv44.getY() && craft.getY() - 60 <= iv44.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv45.getX() && craft.getX() + 60 >= iv45.getX() && craft.getY() + 60 >= iv45.getY() && craft.getY() - 60 <= iv45.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv46.getX() && craft.getX() + 60 >= iv46.getX() && craft.getY() + 60 >= iv46.getY() && craft.getY() - 60 <= iv46.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv47.getX() && craft.getX() + 60 >= iv47.getX() && craft.getY() + 60 >= iv47.getY() && craft.getY() - 60 <= iv47.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv48.getX() && craft.getX() + 60 >= iv48.getX() && craft.getY() + 60 >= iv48.getY() && craft.getY() - 60 <= iv48.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //49~74까지 총알들은 오른쪽에서 나오는 총알들입니다.

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv49.getX() && craft.getX() + 60 >= iv49.getX() && craft.getY() + 60 >= iv49.getY() && craft.getY() - 60 <= iv49.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                
                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv50.getX() && craft.getX() + 60 >= iv50.getX() && craft.getY() + 60 >= iv50.getY() && craft.getY() - 60 <= iv50.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv51.getX() && craft.getX() + 60 >= iv51.getX() && craft.getY() + 60 >= iv51.getY() && craft.getY() - 60 <= iv51.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv52.getX() && craft.getX() + 60 >= iv52.getX() && craft.getY() + 60 >= iv52.getY() && craft.getY() - 60 <= iv52.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv53.getX() && craft.getX() + 60 >= iv53.getX() && craft.getY() + 60 >= iv53.getY() && craft.getY() - 60 <= iv53.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv54.getX() && craft.getX() + 60 >= iv54.getX() && craft.getY() + 60 >= iv54.getY() && craft.getY() - 60 <= iv54.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv55.getX() && craft.getX() + 60 >= iv55.getX() && craft.getY() + 60 >= iv55.getY() && craft.getY() - 60 <= iv55.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv56.getX() && craft.getX() + 60 >= iv56.getX() && craft.getY() + 60 >= iv56.getY() && craft.getY() - 60 <= iv56.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);
                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv57.getX() && craft.getX() + 60 >= iv57.getX() && craft.getY() + 60 >= iv57.getY() && craft.getY() - 60 <= iv57.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv58.getX() && craft.getX() + 60 >= iv58.getX() && craft.getY() + 60 >= iv58.getY() && craft.getY() - 60 <= iv58.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv59.getX() && craft.getX() + 60 >= iv59.getX() && craft.getY() + 60 >= iv59.getY() && craft.getY() - 60 <= iv59.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);
                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv60.getX() && craft.getX() + 60 >= iv60.getX() && craft.getY() + 60 >= iv60.getY() && craft.getY() - 60 <= iv60.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv61.getX() && craft.getX() + 60 >= iv61.getX() && craft.getY() + 60 >= iv61.getY() && craft.getY() - 60 <= iv61.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv62.getX() && craft.getX() + 60 >= iv62.getX() && craft.getY() + 60 >= iv62.getY() && craft.getY() - 60 <= iv62.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv63.getX() && craft.getX() + 60 >= iv63.getX() && craft.getY() + 60 >= iv63.getY() && craft.getY() - 60 <= iv63.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv64.getX() && craft.getX() + 60 >= iv64.getX() && craft.getY() + 60 >= iv64.getY() && craft.getY() - 60 <= iv64.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv65.getX() && craft.getX() + 60 >= iv65.getX() && craft.getY() + 60 >= iv65.getY() && craft.getY() - 60 <= iv65.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv66.getX() && craft.getX() + 60 >= iv66.getX() && craft.getY() + 60 >= iv66.getY() && craft.getY() - 60 <= iv66.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv67.getX() && craft.getX() + 60 >= iv67.getX() && craft.getY() + 60 >= iv67.getY() && craft.getY() - 60 <= iv67.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv68.getX() && craft.getX() + 60 >= iv68.getX() && craft.getY() + 60 >= iv68.getY() && craft.getY() - 60 <= iv68.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv69.getX() && craft.getX() + 60 >= iv69.getX() && craft.getY() + 60 >= iv69.getY() && craft.getY() - 60 <= iv69.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv70.getX() && craft.getX() + 60 >= iv70.getX() && craft.getY() + 60 >= iv70.getY() && craft.getY() - 60 <= iv70.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv71.getX() && craft.getX() + 60 >= iv71.getX() && craft.getY() + 60 >= iv71.getY() && craft.getY() - 60 <= iv71.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv72.getX() && craft.getX() + 60 >= iv72.getX() && craft.getY() + 60 >= iv72.getY() && craft.getY() - 60 <= iv72.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv73.getX() && craft.getX() + 60 >= iv73.getX() && craft.getY() + 60 >= iv73.getY() && craft.getY() - 60 <= iv73.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv74.getX() && craft.getX() + 60 >= iv74.getX() && craft.getY() + 60 >= iv74.getY() && craft.getY() - 60 <= iv74.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //75~90까지는 아래쪽에서 나오는 총알을 표현합니다.

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv75.getX() && craft.getX() + 60 >= iv75.getX() && craft.getY() + 60 >= iv75.getY() && craft.getY() - 60 <= iv75.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv76.getX() && craft.getX() + 60 >= iv76.getX() && craft.getY() + 60 >= iv76.getY() && craft.getY() - 60 <= iv76.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv77.getX() && craft.getX() + 60 >= iv77.getX() && craft.getY() + 60 >= iv77.getY() && craft.getY() - 60 <= iv77.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv78.getX() && craft.getX() + 60 >= iv78.getX() && craft.getY() + 60 >= iv78.getY() && craft.getY() - 60 <= iv78.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv79.getX() && craft.getX() + 60 >= iv79.getX() && craft.getY() + 60 >= iv79.getY() && craft.getY() - 60 <= iv79.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv80.getX() && craft.getX() + 60 >= iv80.getX() && craft.getY() + 60 >= iv80.getY() && craft.getY() - 60 <= iv80.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv81.getX() && craft.getX() + 60 >= iv81.getX() && craft.getY() + 60 >= iv81.getY() && craft.getY() - 60 <= iv81.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv82.getX() && craft.getX() + 60 >= iv82.getX() && craft.getY() + 60 >= iv82.getY() && craft.getY() - 60 <= iv82.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv83.getX() && craft.getX() + 60 >= iv83.getX() && craft.getY() + 60 >= iv83.getY() && craft.getY() - 60 <= iv83.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);
                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv84.getX() && craft.getX() + 60 >= iv84.getX() && craft.getY() + 60 >= iv84.getY() && craft.getY() - 60 <= iv84.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv85.getX() && craft.getX() + 60 >= iv85.getX() && craft.getY() + 60 >= iv85.getY() && craft.getY() - 60 <= iv85.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv86.getX() && craft.getX() + 60 >= iv86.getX() && craft.getY() + 60 >= iv86.getY() && craft.getY() - 60 <= iv86.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv87.getX() && craft.getX() + 60 >= iv87.getX() && craft.getY() + 60 >= iv87.getY() && craft.getY() - 60 <= iv87.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv88.getX() && craft.getX() + 60 >= iv88.getX() && craft.getY() + 60 >= iv88.getY() && craft.getY() - 60 <= iv88.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv89.getX() && craft.getX() + 60 >= iv89.getX() && craft.getY() + 60 >= iv89.getY() && craft.getY() - 60 <= iv89.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }

                //전투기의 getX() 좌표와  getY() 좌표가 각각 총알의 getX() 좌표와 getY()좌표에서 60이하의 오차값인경우 전투기가 총알에 맞았다고 판정된다.
                else if (craft.getX() - 60 <= iv90.getX() && craft.getX() + 60 >= iv90.getX() && craft.getY() + 60 >= iv90.getY() && craft.getY() - 60 <= iv90.getY()) {
                    //Toast.makeText(getApplicationContext(),"쳐맞음",Toast.LENGTH_LONG);
                    Log.v("sss", " 쳐맞음");

                    //게임에 패배하게되서 점수화면을 출력하는 액티비티로 넘어간다.
                    Intent intent=new Intent(getApplicationContext(),lose_game.class);
                    intent.putExtra("Player_score", score2);

                    startActivity(intent); //다음 화면으로 넘어간다.

                    //스레드를 flag로 종료해준다.
                    stop=false;
                    finish();
                }
                
            }
        } //run
    }

    //빨간총알이 발사되는 스레드
    class RedBulletThread extends Thread{  //RedBulletThread

        @Override
        public void run(){ //run

            while(stop==true) {//while
                Log.v("check_stop","빨간총알 잰 스레드 동작중");

                //1~28 사이의 총알중 랜덤으로 날라갈 수 있게끔 랜덤변수 만듦.
                double random_value = Math.random();
                int random = (int) (random_value * 90) + 1;  //1에서 28사이의 숫자 랜덤으로 생성.

                //TODO 총알이 제대로 나가는지 항상 체크해야한다
                //핸들러에게 메세지 전달  1~90사이의 랜덤수로.

                ImageView iv1=(ImageView)findViewById(R.id.red_bullet1);

                //총알의 좌표를 변수에 담는다.
                numStr1 = String.valueOf( iv1.getX());
                numStr2 = String.valueOf( iv1.getY());

                Log.v("check_data","총알의 x 좌표 값 "+iv1.getX());
                Log.v("check_data","총알의 y 좌표 값 "+iv1.getY());

                //1번 총알이 움직인다.
                handler.sendEmptyMessage(random);

                //총알이 다시 발사되기까지의 시간을 설정하는 sleep.
                try {
                    Thread.sleep(258);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }//while

        } //run

        //빨간 총알을 발사하기위한 핸들러객체이다.
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg){ //handleMessage

                //총알의 x,y 좌표를 표현하구이한 TextView
                textView2=(TextView)findViewById(R.id.textView2);
                textView3=(TextView)findViewById(R.id.textView3);

                //1~1000 사이의 수가 랜덤으로 생성됩니다. 이 수는 총알의 대각선으로 얼마나 기울어져서 진행되는지에 관한 변수입니다.
                double random_value=Math.random();
                int ran=(int)(random_value*1000)+1;


                //1~28 번 까지의 미사일은 왼쪽 벽면에서의 미사일을 표현한다.

                //1~28 사이의 총알을 설정해준다.
                if(msg.what==1) {
                    //총알1의 이미지뷰 변수입니다.
                    ImageView iv1=(ImageView)findViewById(R.id.red_bullet1);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv1, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv1, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();
                }

                else if(msg.what==2){
                    ImageView iv2=(ImageView)findViewById(R.id.red_bullet2);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv2, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv2, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==3){
                    ImageView iv3=(ImageView)findViewById(R.id.red_bullet3);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv3, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv3, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==4){
                    ImageView iv4=(ImageView)findViewById(R.id.red_bullet4);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv4, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv4, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==5){
                    ImageView iv5=(ImageView)findViewById(R.id.red_bullet5);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv5, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv5, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==6){
                    ImageView iv6=(ImageView)findViewById(R.id.red_bullet6);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv6, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv6, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==7){
                    ImageView iv7=(ImageView)findViewById(R.id.red_bullet7);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv7, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv7, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==8){
                    ImageView iv8=(ImageView)findViewById(R.id.red_bullet8);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv8, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv8, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==9){
                    ImageView iv9=(ImageView)findViewById(R.id.red_bullet9);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv9, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv9, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==10){
                    ImageView iv10=(ImageView)findViewById(R.id.red_bullet10);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv10, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv10, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==11){
                    ImageView iv11=(ImageView)findViewById(R.id.red_bullet11);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv11, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv11, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==12){
                    ImageView iv12=(ImageView)findViewById(R.id.red_bullet12);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv12, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv12, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==13){
                    ImageView iv13=(ImageView)findViewById(R.id.red_bullet13);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv13, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv13, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==14){
                    ImageView iv14=(ImageView)findViewById(R.id.red_bullet14);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv14, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv14, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==15){
                    ImageView iv15=(ImageView)findViewById(R.id.red_bullet15);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv15, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv15, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==16){
                    ImageView iv16=(ImageView)findViewById(R.id.red_bullet16);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv16, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv16, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==17){
                    ImageView iv17=(ImageView)findViewById(R.id.red_bullet17);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv17, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv17, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==18){
                    ImageView iv18=(ImageView)findViewById(R.id.red_bullet18);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv18, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv18, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==19){
                    ImageView iv19=(ImageView)findViewById(R.id.red_bullet19);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv19, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv19, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==20){
                    ImageView iv20=(ImageView)findViewById(R.id.red_bullet20);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv20, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv20, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==21){
                    ImageView iv21=(ImageView)findViewById(R.id.red_bullet21);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv21, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv21, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==22){
                    ImageView iv22=(ImageView)findViewById(R.id.red_bullet22);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv22, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv22, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==23){
                    ImageView iv23=(ImageView)findViewById(R.id.red_bullet23);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv23, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv23, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==24){
                    ImageView iv24=(ImageView)findViewById(R.id.red_bullet24);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv24, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv24, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==25){
                    ImageView iv25=(ImageView)findViewById(R.id.red_bullet25);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv25, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv25, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==26){
                    ImageView iv26=(ImageView)findViewById(R.id.red_bullet26);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv26, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv26, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==27){
                    ImageView iv27=(ImageView)findViewById(R.id.red_bullet27);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv27, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv27, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==28){
                    ImageView iv28=(ImageView)findViewById(R.id.red_bullet28);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv28, "translationX", 0F, 1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv28, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                //29부터 48번까지 총알은 화면의 상단부분에서 나옵니다.



                else if(msg.what==29){
                    ImageView iv29=(ImageView)findViewById(R.id.red_bullet29);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv29, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv29, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==30){
                    ImageView iv30=(ImageView)findViewById(R.id.red_bullet30);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv30, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv30, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==31){
                    ImageView iv31=(ImageView)findViewById(R.id.red_bullet31);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv31, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv31, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==32){
                    ImageView iv32=(ImageView)findViewById(R.id.red_bullet32);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv32, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv32, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==33){
                    ImageView iv33=(ImageView)findViewById(R.id.red_bullet29);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv33, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv33, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==34){
                    ImageView iv34=(ImageView)findViewById(R.id.red_bullet34);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv34, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv34, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==35){
                    ImageView iv35=(ImageView)findViewById(R.id.red_bullet35);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv35, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv35, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==36){
                    ImageView iv36=(ImageView)findViewById(R.id.red_bullet36);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv36, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv36, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==37){
                    ImageView iv37=(ImageView)findViewById(R.id.red_bullet37);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv37, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv37, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==38){
                    ImageView iv38=(ImageView)findViewById(R.id.red_bullet38);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv38, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv38, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==39){
                    ImageView iv39=(ImageView)findViewById(R.id.red_bullet39);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv39, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv39, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==40){
                    ImageView iv40=(ImageView)findViewById(R.id.red_bullet40);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv40, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv40, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==41){
                    ImageView iv41=(ImageView)findViewById(R.id.red_bullet41);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv41, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv41, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==42){
                    ImageView iv42=(ImageView)findViewById(R.id.red_bullet42);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv42, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv42, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==43){
                    ImageView iv43=(ImageView)findViewById(R.id.red_bullet43);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv43, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv43, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==44){
                    ImageView iv44=(ImageView)findViewById(R.id.red_bullet44);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv44, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv44, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==45){
                    ImageView iv45=(ImageView)findViewById(R.id.red_bullet45);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv45, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv45, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==46){
                    ImageView iv46=(ImageView)findViewById(R.id.red_bullet46);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv46, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv46, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==47){
                    ImageView iv47=(ImageView)findViewById(R.id.red_bullet47);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv47, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv47, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==48){
                    ImageView iv48=(ImageView)findViewById(R.id.red_bullet48);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv48, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv48, "translationY", 0F, 2600);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                //49~74 까지는 총알이 오른쪽에서 나갑니다.

                else if(msg.what==49){
                    ImageView iv49=(ImageView)findViewById(R.id.red_bullet49);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv49, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv49, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==50){
                    ImageView iv50=(ImageView)findViewById(R.id.red_bullet50);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv50, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv50, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==51){
                    ImageView iv51=(ImageView)findViewById(R.id.red_bullet51);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv51, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv51, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==52){
                    ImageView iv52=(ImageView)findViewById(R.id.red_bullet52);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv52, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv52, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==53){
                    ImageView iv53=(ImageView)findViewById(R.id.red_bullet53);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv53, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv53, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==54){
                    ImageView iv54=(ImageView)findViewById(R.id.red_bullet54);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv54, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv54, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==55){
                    ImageView iv55=(ImageView)findViewById(R.id.red_bullet55);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv55, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv55, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==56){
                    ImageView iv56=(ImageView)findViewById(R.id.red_bullet56);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv56, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv56, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==57){
                    ImageView iv57=(ImageView)findViewById(R.id.red_bullet57);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv57, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv57, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==58){
                    ImageView iv58=(ImageView)findViewById(R.id.red_bullet58);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv58, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv58, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==59){
                    ImageView iv59=(ImageView)findViewById(R.id.red_bullet59);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv59, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv59, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==60){
                    ImageView iv60=(ImageView)findViewById(R.id.red_bullet60);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv60, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv60, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==61){
                    ImageView iv61=(ImageView)findViewById(R.id.red_bullet61);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv61, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv61, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==62){
                    ImageView iv62=(ImageView)findViewById(R.id.red_bullet62);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv62, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv62, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==63){
                    ImageView iv63=(ImageView)findViewById(R.id.red_bullet63);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv63, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv63, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==64){
                    ImageView iv64=(ImageView)findViewById(R.id.red_bullet64);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv64, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv64, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==65){
                    ImageView iv65=(ImageView)findViewById(R.id.red_bullet65);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv65, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv65, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==66){
                    ImageView iv66=(ImageView)findViewById(R.id.red_bullet66);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv66, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv66, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==67){
                    ImageView iv67=(ImageView)findViewById(R.id.red_bullet67);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv67, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv67, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==68){
                    ImageView iv68=(ImageView)findViewById(R.id.red_bullet68);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv68, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv68, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==69){
                    ImageView iv69=(ImageView)findViewById(R.id.red_bullet69);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv69, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv69, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==70){
                    ImageView iv70=(ImageView)findViewById(R.id.red_bullet70);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv70, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv70, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==71){
                    ImageView iv71=(ImageView)findViewById(R.id.red_bullet71);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv71, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv71, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==72){
                    ImageView iv72=(ImageView)findViewById(R.id.red_bullet72);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv72, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv72, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==73){
                    ImageView iv73=(ImageView)findViewById(R.id.red_bullet73);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv73, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv73, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==74){
                    ImageView iv74=(ImageView)findViewById(R.id.red_bullet74);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv74, "translationX", 0F, -1250);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv74, "translationY", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                //75~90까지는 아래쪽에서 나오는 총알을 표현한다.

                else if(msg.what==75){
                    ImageView iv75=(ImageView)findViewById(R.id.red_bullet75);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv75, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv75, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==76){
                    ImageView iv76=(ImageView)findViewById(R.id.red_bullet76);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv76, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv76, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==77){
                    ImageView iv77=(ImageView)findViewById(R.id.red_bullet77);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv77, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv77, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==78){
                    ImageView iv78=(ImageView)findViewById(R.id.red_bullet78);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv78, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv78, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==79){
                    ImageView iv79=(ImageView)findViewById(R.id.red_bullet79);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv79, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv79, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==80){
                    ImageView iv80=(ImageView)findViewById(R.id.red_bullet80);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv80, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv80, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==81){
                    ImageView iv81=(ImageView)findViewById(R.id.red_bullet81);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv81, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv81, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==82){
                    ImageView iv82=(ImageView)findViewById(R.id.red_bullet82);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv82, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv82, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==83){
                    ImageView iv83=(ImageView)findViewById(R.id.red_bullet83);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv83, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv83, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==84){
                    ImageView iv84=(ImageView)findViewById(R.id.red_bullet84);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv84, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv84, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==85){
                    ImageView iv85=(ImageView)findViewById(R.id.red_bullet85);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv85, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv85, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==86){
                    ImageView iv86=(ImageView)findViewById(R.id.red_bullet86);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv86, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv86, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==87){
                    ImageView iv87=(ImageView)findViewById(R.id.red_bullet87);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv87, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv87, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==88){
                    ImageView iv88=(ImageView)findViewById(R.id.red_bullet88);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv88, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv88, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==89){
                    ImageView iv89=(ImageView)findViewById(R.id.red_bullet89);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv89, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv89, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }

                else if(msg.what==90){
                    ImageView iv90=(ImageView)findViewById(R.id.red_bullet90);

                    ObjectAnimator anim_x =ObjectAnimator.ofFloat(iv90, "translationX", 0F, ran);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. 가로로 1250칸 만큼 이동합니다.

                    ObjectAnimator anim_y =ObjectAnimator.ofFloat(iv90, "translationY", 0F, -2500);  //values 를 OF 로 하면 xml파일에서 설정한 위치부터 시작한다. ran 만큼 기울어져서 이동합니다.

                    //총알이 대각선으로 날아갈 수 있게끔 해주는 코드입니다.
                    //참고 사이트 https://stackoverflow.com/questions/23603813/how-to-translate-animation-on-an-image-diagonally

                    //playTogether : 애니메이션이 동시에 시작합니다.
                    //playTogether "playTogether" 메소드 쓸기위해서 해당 메소드의 클래스에대한 객체를 만들었습니다.
                    AnimatorSet playTogether=new AnimatorSet();
                    playTogether.playTogether(anim_x, anim_y);

                    //진행시간
                    playTogether.setDuration(3000);

                    //애니메이션 시작
                    playTogether.start();

                }




            } //handleMessage
        } ;

    }  //RedBulletThread



    //점수를 표현하는 스레드 & 핸들러
    class ScoreThread extends Thread{  //ScoreThread

        @Override
        public void run(){
           // boolean stop=true;

            //플레이어의 전투기가 총알에 맞을경우 스레드가 종료된다.
            while(stop==true){
                Log.v("check_stop","점수증가 스레드 진행중");

                score++;

                handler_score.sendEmptyMessage(315);

                //1초마다 점수를 1씩 증가시켜줌.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        }
    }  //ScoreThread

    //점수 추가를 위한 핸들러 객체를 생성한다.
    Handler handler_score=new Handler(){

        @Override
        public void handleMessage(Message msg){
            if(msg.what==315){

                //문자로된 점수를 숫자로 바꿔준다.
                 score2 = Integer.toString(score);

                textView5.setText(score2);
            }
        }
    };

    //총알의 좌표를 확인하는 스레드 & 핸들러
    class Red_Bullet_Coordination extends Thread{//Red_Bullet_Coordination
        @Override
        public void run(){
            //현재 조건식은 무한이지만, 전투기와 총알의 충돌판정문제를 해결하고나면 충돌된 시점에 이 스레드를 멈춰야한다.
            while(true){
                handler_bullet_coordination.sendEmptyMessage(317);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }//Red_Bullet_Coordination

    //총알의 좌표를 표현하기위한 핸들러
    Handler handler_bullet_coordination=new Handler(){

        @Override
        public void handleMessage(Message msg){
            textView2=(TextView)findViewById(R.id.textView2);
            textView3=(TextView)findViewById(R.id.textView3);

            if(msg.what==317){
                ImageView iv1=(ImageView)findViewById(R.id.red_bullet1);
                String x좌표= String.valueOf(iv1.getX());
                String y좌표= String.valueOf(iv1.getY());
                textView2.setText(x좌표);
                textView3.setText(y좌표);
            }
        }
    };



}  //in_game

//////////////////////////////////////////////////////////////////////////////////////////////////







