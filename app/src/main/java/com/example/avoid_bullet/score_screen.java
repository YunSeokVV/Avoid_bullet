package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class score_screen extends Activity { //Activity
    static String TAG="recyclerview_exapmle";  // ?
    ArrayList<profile> profile_ArrayList;   //ArrayList가 사용할 객체타입을 < > 안에 넣는다.
    CustomAdapter adapter; //CustomAdapter 클래스의 변수 adapter 를 선언한다.
    RecyclerView recyclerView;  //리사이클러뷰의 변수 recyclerView를 선언한다.
    LinearLayoutManager linearLayoutManager; //레이아웃 매니저로 각종 뷰들의 출력방향을 설정한다.

    ArrayList<String> user_key=new ArrayList<>();; //사용자의 아이디 key값을 담기위한 ArrayList이다.
    ArrayList<String> user_score=new ArrayList<>(); //사용자의 key값(아이디)에 대한 value(점수)를 담기위한 ArrayList이다
    ArrayList<String> user_image=new ArrayList<>(); //사용자가 key값(아이디)에 대한 vlaue(프로필사진)를 담기위한 ArrayList이다.
    ArrayList<Bitmap> user_image_bitmap=new ArrayList<>(); //사용자의 프로필사진을 Bitmap 형식으로 갖는 ArrayList이다.

    private static MediaPlayer 점수화면브금;
    TextView 값받아오기;
    Button 이전화면;

    ImageView 실험용;//불러온 이미지가 잘 왔는지 출력하기위해 존재함.

    Bitmap bitmap; //저장한 이미지데이터를 불러오기위해 선언된 변수.
    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate 시작
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);  // layout xml 과 자바파일을 연결

        실험용=(ImageView)findViewById(R.id.실험용); //불러온 이미지가 잘 왔는지 출력하기위해 존재함 아이디 대응시켜줌.

        //목표 : 리사이클러뷰에 Shared(파일명 : UserScore)에 저장했던 데이터 key(유저아이디) 와 key에 해당되는 value인 점수를 불러와서 리사이클러뷰에 추가시켜야함.

        // 절차 0. lose_screen 에서 임의로 사용자가 입력한 점수를 Shared에 저장시킨다. key값 : 사용자아이디 value : 점수
        // 절차 1. 리사이클러뷰의 기본적인 세팅들을 마친다.
        // 세팅이란? : Adapter클래스, 데이터클래스, item뷰 xml파일 만들기, score_screen 클래스에 리사이클러뷰, 어레이리스트 등등 필요한것들 추가시키기.
        // 절차 2. score_screen액티비티가  onCreate 되는 순간 Shared에 저장되있던 데이터들을 전부 불러와서 추가시켜준다.


        점수화면브금 = MediaPlayer.create(this, R.raw.score_screen);

        점수화면브금.setLooping(true);

        점수화면브금.start();

        /////////////값을 불러오기위해서 Shared객체를 생성/////////////
        SharedPreferences sf3 = getSharedPreferences("UserScore",MODE_PRIVATE); //데이터를 불러오기위해서 Sharedpreference 객체 sf를 선언한다.
        String 저장데이터확인 = sf3.getString(login_screen.사용자가입력한_아이디,"");  //불러온 데이터를 사용하기위해 변수 "불러올데이터" 에 값을 넣는다.

        Log.v("check_data","Shared 에서 불러온 값 :  "+저장데이터확인);


        //////////////////Shared에 존재하는 모든 key와 그에 관한 value를 뽑기위한 for문///////////////////
        int i=0;
        Map<String, ?> prefsMap = sf3.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {

            Log.v("check_data", "Shared안에 존재하는 모든 key값 : "+entry.getKey() + ":" + entry.getValue().toString());

            user_key.add(entry.getKey()); //어레이리스트 key에 모든 key값을 넣는다.
            user_score.add((String) entry.getValue()); //어레이리스트 score에 모든 value를 넣는다.

            Log.v("check_data", "ArrayList key 안에 존재하는 모든 값 : "+user_key.get(i));

            Log.v("check_data", "ArrayList score 안에 존재하는 모든 값 : "+user_score.get(i));
            i++;
        }

        //////////////////아래 코드는 이미지를 불러오기위한 절차//////////////////////

        SharedPreferences pref1 = getSharedPreferences("UserImage", MODE_PRIVATE);  //Shared에 저장된 이미지(key값 : 사용자아이디, value : 이미지 파일명 : UserImage)를 불러오기위해서 pref1 객체 선언.

        String image =  pref1.getString(login_screen.사용자가입력한_아이디, ""); //key값은 위에서 서술한대로 사용자가 입력했던 ID 이다. value를 불러온다.

       bitmap = StringToBitMap(image);  // 불러온 value를  다시 비트맵으로 형변환하고 변수 bitmap에 넣어준다.

        Log.v("check_data", "불러온 비트맵 이미지 값 : "+bitmap);

      //  실험용.setImageBitmap(bitmap);

        //////////////////Shared에 존재하는 모든 key(사용자아이디)와 그에 관한 value(프로필사진)를 뽑기위한 for문///////////////////
        int s=0;
        Map<String, ?> prefsMap2 = pref1.getAll();
        for (Map.Entry<String, ?> entry: prefsMap2.entrySet()) {

            Log.v("check_data", "Shared안에 존재하는 모든 key값 : "+entry.getKey() + ":" + entry.getValue().toString());

            user_image.add((String) entry.getValue()); //어레이리스트 key(아이디)에 모든 value(프로필사진)값을 넣는다.

            user_image_bitmap.add(StringToBitMap(user_image.get(s))); //user_image 어레이리스트에 존재하는 String형태의 데이터를 전부 비트맵으로 바꿔서 user_image_bitmap 리스트에 넣는다.

            Log.v("check_data", "ArrayList key 안에 존재하는 모든 이미지 값 : "+user_image.get(s));

            s++;
        }

        //////////////////위의 코드는 이미지를 불러오기위한 절차//////////////////////


        점수화면_리사이클러뷰_출력();

    }//onCreate 끝

    @Override
    protected void onResume(){
        super.onResume();

        점수화면브금.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        점수화면브금.pause();
    }



    public void 점수화면_리사이클러뷰_출력(){ //리사이클러뷰에 아이템뷰를 추가시켜주는 메소드이다.
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(this); // 이해못함. 하지만 과제 진행에 무리x
        recyclerView.setLayoutManager(linearLayoutManager);  //setLayoutManager 사용하게될 리사이클러뷰를 지정해준다. 매개변수에는 인자로 "RecyclerView.LayoutManager layout" 이 들어간다.
        profile_ArrayList=new ArrayList<>(); //profile에서 받게되는 데이터를 어레이 리스트화 시킨다.

        adapter=new CustomAdapter(profile_ArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true); //각 뷰들의크기를 고정해준다.
        //RecyclerView의 줄(row) 사이에 수평선을 넣기위해 사용됩니다.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        //getOrientation 메소드의 기능 : 최근 방향을  리턴한다.
        recyclerView.addItemDecoration(dividerItemDecoration);

        for(int i=0;i<user_key.size();i++){
            //score 클래스의 객체 score를 선언합니다. 생성자에 들어가는 값은 리사이클러뷰에서 값을 초기화해주는 역할을 합니다.
            profile profile2=new profile(user_key.get(i),user_score.get(i), user_image_bitmap.get(i));
            profile_ArrayList.add(profile2);
        }


        adapter.notifyDataSetChanged();
    }

    //몰라도 과제진행에 지장이없어서 따로 공부안함. 문제가 발생하면 그때 공부하겠음.
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

} //Activity
