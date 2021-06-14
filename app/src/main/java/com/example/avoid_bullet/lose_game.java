package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class lose_game extends Activity {//Activity
    String 사용자_점수2;
    Button 공유;  //사용자가 다른사람들과 이 앱을 공유하기위해 존재하는 버튼이다.
    TextView 점수;  //사용자가 게임이 끝났을때 받는 점수이다.
    private static MediaPlayer 게임오버브금;
    TextView 패배;
    TextView 점수표시;
    TextView 최종점수;
    TextView 최고점수표시;
    TextView 최고점수;
    TextView 다시할지묻기;
    TextView 다시하기;     //게임을 in_game 액티비티로 넘어갑니다. 누르는순간 이전 점수와 비교해서 더 높을경우 점수를 저장하고 그렇지않을경우 저장하지않습니다.
    TextView 다시하지말기; //게임을 activity_main 액티비티로 넘어갑니다. 누르는순간 이전 점수와 비교해서 더 높을경우 점수를 저장하고 그렇지않을경우 저장하지않습니다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lose_game);

        //////////각종 변수들의 뷰아이디를 찾아준다/////////////

        다시하기=(TextView)findViewById(R.id.다시하기);
        다시하지말기=(TextView)findViewById(R.id.다시하지말기);
        공유=(Button)findViewById(R.id.공유);
        점수=(TextView)(findViewById(R.id.점수));

        //////////각종 변수들의 뷰아이디를 찾아준다/////////////



        //            저장을 하기위한 절차
        // 절차1. EditText(패배점수)의 값을 입력으로 받은뒤 "yes" 또는 "no" 를 누르면 저장이 진행된다.
        // 절차2. Shared의 객체를 선언하고 저장하는 파일이름을 UserScore로 지정한다.
        // 절차3. 저장할때의 key값은 "사용자의아이디" value값은 이전에 저장되있던 점수와 비교해서 더 높은점수로 저장한다.
        // 절차4. 로그로 저장된 값들을 확인해본다.

        //플레이어의 점수를 "점수" TextView에 출력
        Intent intent=getIntent();
        String score=intent.getExtras().getString("Player_score");
        점수.setText(score);

        사용자_점수2=score;

        다시하기.setOnClickListener(new View.OnClickListener(){ //yes 버튼을 눌렀을때
            @Override
            public void onClick(View view){ // TextView를 눌렀을때 일어나는 이벤트에 대해서

                /////////////////EditText에 담긴 현재 점수와 이전에 마지막으로 저장된 점수를 불러옵니다.///////////////

                //사용자가 EditText에 입력한 데이터가 실제로 SHared안에 존재하는지 찾기위해 객체를 선언해줍니다.
                SharedPreferences sharedPreferences = getSharedPreferences("UserScore",MODE_PRIVATE);

                String 불러올_데이터=sharedPreferences.getString(login_screen.사용자가입력한_아이디,"0" );
                //변수 "불러올_데이터" 에 키값이 "사용자입력_아이디" 값이 존재하는지 확인한다. 제대로됐다면 "비밀번호" 를 불러올_데이터에 저장한다.
                Log.v("check_data","불러올 데이터 사용자의 점수 값 :"+불러올_데이터);

                String 사용자의_점수=점수.getText().toString(); //사용자의 점수입니다. 변수에 담습니다.




                // 절차2. Shared의 객체를 선언하고 저장하는 파일이름을 UserScore로 지정한다.

                SharedPreferences sf=getSharedPreferences("UserScore",MODE_PRIVATE); //값을 저장할때 사용하기위해 Sharedpreference의 객체 sf를 선언.
                // 저장될때의 파일명은 "UserScore", MODE_PRIVATE 은 이 앱에서만 저장된값이 사용될것이라는것을 의미함.

                SharedPreferences.Editor editor = sf.edit();
                //Returns a new instance of the {@link Editor} interface, allowing
                //     * you to modify the values in this SharedPreferences object.
                // 번역 : Editor 인터페이스의 새로운 인스턴스(editor)를 돌려줍니다. ShraedpreFerence에 객체값을 수정합니다.

                // 절차3. 저장할때의 key값은 "사용자의아이디" value값은 JSONObject 형태로 한다. (JSONObect의 key값 : "사용자의아이디", value값 : "패배점수")

                //////////////////제이슨오브젝트를 만들어줘야합니다. 그래야지 "사용자아이디" 키값에 대한 value를 넣을수 있으니까.//////////////////////

                //사용자의 점수를 변수로 합니다.
                String 사용자_점수=점수.getText().toString();


                JSONObject data = new JSONObject();  //JSONObject 객체 data를 생성.

                //유저의 아이디 key값에 대한 value로 들어가게될 JSONObject
//                String JSONObject='{'+"\""+사용자_아이디+"\""+':'+"\""+사용자_점수+"\""+'}';


                try {
                    data.put(login_screen.사용자가입력한_아이디, 사용자_점수); //JSONObject 객체에 "사용자아이디" , "사용자_점수" 를 넣는다.
                    //ex. data.put("이름" , "덩치"); data.put("거주지","서울); 를 넣고 결과를보기위해 로그를 찍으면
                    //  {"이름":"덩치" , "거주지" : "서울"} 과 같은 결과가 나온다.


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                //JSONObject 값 확인
                Log.v("check_data","JSONObject 의 값 :  "+data);


                // Shared 의 키값을 "사용자아이디" 로 설정하고 value는 점수로 한다.
                editor.putString(login_screen.사용자가입력한_아이디, 사용자_점수);

                //저장결과를 반영합니다.
                editor.commit();


                /////////////제대로 저장됐는지 확인해보기위해서 로그를 찍어보기위해 Shared객체를 생성/////////////
                SharedPreferences sf3 = getSharedPreferences("UserScore",MODE_PRIVATE); //데이터를 불러오기위해서 Sharedpreference 객체 sf를 선언한다.
                String 저장데이터확인 = sf3.getString(login_screen.사용자가입력한_아이디,"");  //불러온 데이터를 사용하기위해 변수 "불러올데이터" 에 값을 넣는다.

                Log.v("check_data","현재 JSONObject 의 값 :  "+저장데이터확인);

                //점수화면으로 액티비티로 전환한다. 사용자의 아이디도 같이 넘겨준다.
                Intent intent2=new Intent(getApplicationContext(),in_game.class); //게임을 다시하게되면 점수를 저장.
                intent2.putExtra("사용자_아이디2", login_screen.사용자가입력한_아이디);
                startActivity(intent2); //다음 화면으로 넘어간다.

                finish();
            }// TextView를 눌렀을때 일어나는 이벤트에 대해서
        });  //yes 버튼을 눌렀을때

        다시하지말기.setOnClickListener(new View.OnClickListener(){ //no 버튼을 눌렀을때
            @Override
            public void onClick(View view){ // TextView를 눌렀을때 일어나는 이벤트에 대해서

                // 절차2. Shared의 객체를 선언하고 저장하는 파일이름을 UserScore로 지정한다.

                SharedPreferences sf=getSharedPreferences("UserScore",MODE_PRIVATE); //값을 저장할때 사용하기위해 Sharedpreference의 객체 sf를 선언.
                // 저장될때의 파일명은 "UserScore", MODE_PRIVATE 은 이 앱에서만 저장된값이 사용될것이라는것을 의미함.

                SharedPreferences.Editor editor = sf.edit();
                //Returns a new instance of the {@link Editor} interface, allowing
                //     * you to modify the values in this SharedPreferences object.
                // 번역 : Editor 인터페이스의 새로운 인스턴스(editor)를 돌려줍니다. ShraedpreFerence에 객체값을 수정합니다.

                // 절차3. 저장할때의 key값은 "사용자의아이디" value값은 JSONObject 형태로 한다. (JSONObect의 key값 : "사용자의아이디", value값 : "패배점수")

                //////////////////제이슨오브젝트를 만들어줘야합니다. 그래야지 "사용자아이디" 키값에 대한 value를 넣을수 있으니까.//////////////////////

                //사용자의 점수를 변수로 합니다.
                String 사용자_점수=점수.getText().toString();



                JSONObject data = new JSONObject();  //JSONObject 객체 data를 생성.

                //유저의 아이디 key값에 대한 value로 들어가게될 JSONObject
//                String JSONObject='{'+"\""+사용자_아이디+"\""+':'+"\""+사용자_점수+"\""+'}';


                try {
                    data.put(login_screen.사용자가입력한_아이디, 사용자_점수); //JSONObject 객체에 "사용자아이디" , "사용자_점수" 를 넣는다.
                    //ex. data.put("이름" , "덩치"); data.put("거주지","서울); 를 넣고 결과를보기위해 로그를 찍으면
                    //  {"이름":"덩치" , "거주지" : "서울"} 과 같은 결과가 나온다.


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                //JSONObject 값 확인
                Log.v("check_data","JSONObject 의 값 :  "+data);


                // Shared 의 키값을 "사용자아이디" 로 설정하고 value는 점수로 한다.
                editor.putString(login_screen.사용자가입력한_아이디, 사용자_점수);

                //저장결과를 반영합니다.
                editor.commit();


                /////////////제대로 저장됐는지 확인해보기위해서 로그를 찍어보기위해 Shared객체를 생성/////////////
                SharedPreferences sf3 = getSharedPreferences("UserScore",MODE_PRIVATE); //데이터를 불러오기위해서 Sharedpreference 객체 sf를 선언한다.
                String 저장데이터확인 = sf3.getString(login_screen.사용자가입력한_아이디,"");  //불러온 데이터를 사용하기위해 변수 "불러올데이터" 에 값을 넣는다.

                Log.v("check_data","현재 JSONObject 의 값 :  "+저장데이터확인);

                //점수화면으로 액티비티로 전환한다. 사용자의 아이디도 같이 넘겨준다.
                Intent intent2=new Intent(getApplicationContext(),MainActivity.class); //게임을 다시하게되면 점수를 저장.
                intent2.putExtra("사용자_아이디2", login_screen.사용자가입력한_아이디);
                startActivity(intent2); //다음 화면으로 넘어간다.
                finish();
            }// TextView를 눌렀을때 일어나는 이벤트에 대해서
        });  //no 버튼을 눌렀을때

        공유.setOnClickListener(new View.OnClickListener(){ //yes 버튼을 눌렀을때
            @Override
            public void onClick(View view){ // TextView를 눌렀을때 일어나는 이벤트에 대해서

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                // intent.setType("image/*");



                String text = "총알피하기로 점수낮은사람이 치킨사주기 ㄱ? 내점수 : "+사용자_점수2;

                intent.putExtra(Intent.EXTRA_TEXT, text);


                Intent chooser = Intent.createChooser(intent, "친구에게 공유하기");
                startActivity(chooser);

//                Intent intent=new Intent(Intent.ACTION_SEND);
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_TEXT, "구글 http://www.google.com #google");
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+mImagePath));






            }// TextView를 눌렀을때 일어나는 이벤트에 대해서
        });  //yes 버튼을 눌렀을때
        //sendShare();
        게임오버브금 = MediaPlayer.create(this, R.raw.game_over);

        게임오버브금.setLooping(true);

        게임오버브금.start();


    }//onCreate

    @Override
    protected void onResume(){
        super.onResume();

        게임오버브금.start();
    }

    @Override
    protected void onPause(){
        super.onPause();

        게임오버브금.pause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        // "최종점수" 와 my_profile의 "내프사", "닉네임 설정"을 불러와서 "score_screen"의 리사이클러뷰에 추가.
    }

    private void sendShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");

        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, 0);
        if (resInfo.isEmpty()) {
            return;
        }

        List<Intent> shareIntentList = new ArrayList<Intent>();

        for (ResolveInfo info : resInfo) {
            Intent shareIntent = (Intent) intent.clone();

            if (info.activityInfo.packageName.toLowerCase().equals("com.facebook.katana")) {
//facebook
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");
// shareIntent.setType("image/jpg");
// shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+mImagePath));
            } else {
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "제목");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "구글 http://www.google.com #");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+"mImagePath"));
            }
            shareIntent.setPackage(info.activityInfo.packageName);
            shareIntentList.add(shareIntent);
        }

        Intent chooserIntent = Intent.createChooser(shareIntentList.remove(0), "select");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, shareIntentList.toArray(new Parcelable[]{}));
        startActivity(chooserIntent);
    }



}//Activity
