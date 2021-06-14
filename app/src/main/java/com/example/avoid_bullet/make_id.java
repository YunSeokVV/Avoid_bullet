package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class make_id extends Activity {//Activity
    EditText 아이디_입력; //사용자가 아이디를 입력하기위한 EditText
    EditText 패스워드_입력; //"패스워드를 입력해주세요" 라고 힌트처리된 EditText이다. 사용자가 설정하고싶은 PW를 여기에 입력한다.

    Button 계정생성;  //모든 조건식이 만족했을때, 최종적으로 아이디를 만들기위한 버튼.

    ///////////////////////계정을 생성하기위해서 필요한 변수들 모임입니다.//////////////////

    ArrayList<String> 계정=new ArrayList<>(); //사용자가 입력한  ID값 을 저장하기위한 ArrayList입니다.
    ArrayList<String> 비번=new ArrayList<>(); //사용자가 입력한  PW값 을 저장하기위한 ArrayList입니다.

    ///////////////////////계정을 생성하기위해서 필요한 변수들 모임입니다.//////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_id);

        패스워드_입력=(EditText)findViewById(R.id.editText5) ; //패스워드를 입력하는 EditText
        아이디_입력=(EditText)findViewById(R.id.editText6); //아이디를 입력하는 EditText
        계정생성=(Button)findViewById(R.id.계정생성);  //모든 조건식을 만족하였을때 계정을 생성하기위해 존재하는 버튼.



        계정생성.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //SharedPreferences에 UserData라는 파일에 key와 value 값을 저장합니다.
                SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);

                //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //Editor : SharedPreferences 객체의 값을 수정하는 데 사용되는 인터페이스입니다. -> 인터페이스의 개념을 공부하려다가 굳이 거기까지 몰라도 저장기능을 만드는데 문제가없어서 안했습니다.

                //사용자가 입력한 아이디를 담는 변수입니다.
                login_screen.사용자가입력한_아이디=아이디_입력.getText().toString();

                //사용자가 입력한 비밀번호를 담는 변수입니다.
                String 사용자_입력_비번=패스워드_입력.getText().toString();

                //중복되는 아이디가 존재하는지 체크합니다.
                String 중복여부_변수=sharedPreferences.getString(login_screen.사용자가입력한_아이디,"nothing");  //중복여부_변수 에다가  사용자가 EditText에 입력한 값을 넣어줍니다. 없는경우 nothing 을 넣습니다.

                if(중복여부_변수.equals("nothing")){ //중복되는 아이디가 존재하는 경우
                    //SharedpreFerence에 사용자가 입력한 ID값을 key값, 사용자가 입력한PW를 value값으로 넣어줍니다.
                    editor.putString(login_screen.사용자가입력한_아이디,사용자_입력_비번);

                    //commit 메소드를 통해서 저장합니다. apply 메소드도 있다고하지만 거기까진 공부할 필요가 없다고 생각해서 안했습니다.
                    editor.commit();
                    Log.v("check_data","생성된 계정 :"+중복여부_변수);
                    Log.v("check_data","사용자가 입력한 아이디 확인 :"+login_screen.사용자가입력한_아이디);
                    Toast.makeText(make_id.this, "계정생성이 완료되었습니다!", Toast.LENGTH_SHORT).show();

                    //로그인 액티비티로 전환한다.
                    Intent intent=new Intent(getApplicationContext(),login_screen.class); //다음 넘어갈 클래스를 지정
                    startActivity(intent); //다음 화면으로 넘어간다.

                }
                else{//중복되는 아이디가 존재하지않는 경우
                    Toast.makeText(make_id.this, "중복되는 아이디가 존재합니다!", Toast.LENGTH_SHORT).show();
                    Log.v("check_data","중복여부_변수 값 :"+중복여부_변수);
                }

            }
        });




    }//onCreate
}//Activity
