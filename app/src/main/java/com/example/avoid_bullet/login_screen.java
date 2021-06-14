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
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class login_screen extends Activity {


    ArrayList<String> key_list=new ArrayList<>(); //추출한 키값들을 담아놓는 ArrayList
    ArrayList<String> value_list = new ArrayList<>();//추출한 키값들에대한 value를 담아놓는 ArrayList

    ///////////////////////사용자의 정보를 표현하는 데이터이다.///////////////
    //자료출처 : http://blog.naver.com/PostView.nhn?blogId=luku756&logNo=221224337262&parentCategoryNo=23&categoryNo=&viewDate=&isShowPopularPosts=true&from=search

    private static final String TAG = "OAuthSampleActivity";

    /**
     * client 정보를 넣어준다.
     */
    private static String OAUTH_CLIENT_ID = "S8m26QdhaBYCYffqVupU"; //어플리케이션 정보에 대한 ClientID
    private static String OAUTH_CLIENT_SECRET = "qu_5fFplgr";       //어플리케이션 정보에 대한 Client Secret
    private static String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인 테스트";

    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;

    ///////////////////////사용자의 정보를 표현하는 데이터이다.///////////////

    ///////////////////////네아로 예제에서 사용하는 UI 변수들///////////

    /**
     * UI 요소들
     */
    private TextView mApiResultText;
    private static TextView mOauthAT;  //이 친구가 없어서 에러나는거임.
    private static TextView mOauthRT;
    private static TextView mOauthExpires;
    private static TextView mOauthTokenType;
    private static TextView mOAuthState;

    private OAuthLoginButton mOAuthLoginButton;

    ///////////////////////네아로 예제에서 사용하는 UI 변수들///////////


    Button 로그인버튼; //아이디와 비밀번호가 맞을경우 게임메인화면으로 전환합니다.
    Button 계정생성;   //회원가입을 하는 액티비티로 전환합니다.

    EditText 아이디를입력해주세요;     //사용자가 아이디를 입력하기위한 EditText
    EditText 패스워드를입력해주세요;   //사용자가 패스워드를 입력하기위한 EditText

    public static String 사용자가입력한_아이디; //사용자가 입력한 아이디를 변수에 담습니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        //////////////네아로 예제에서 가져온 코드//////////////

        mContext = this;

        initData();
        initView();

        this.setTitle("OAuthLoginSample Ver." + OAuthLogin.getVersion());

        //////////////네아로 예제에서 가져온 코드//////////////

        Log.v("check_data","OnCreate 진입");

        로그인버튼=(Button)findViewById(R.id.로그인버튼);
        계정생성=(Button)findViewById(R.id.계정생성);

        아이디를입력해주세요=(EditText)findViewById(R.id.아이디를입력해주세요) ;
        패스워드를입력해주세요=(EditText)findViewById(R.id.패스워드를입력해주세요);

        로그인버튼=(Button)findViewById(R.id.로그인버튼);
        계정생성=(Button)findViewById(R.id.계정생성);

        로그인버튼.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //1. 아이디가 Sharedpreference에 존재하는지 확인한다.
                //2. 존재할 경우 해당 아이디와 사용자가 입력한 비밀번호가 일치하는지 확인한다.
                //3. 일치할 경우 다음  인텐트로 다음액티비티로 이동.
                //4. 일치하지않을경우 토스트메세지로 "아이디 또는 비밀번호가 틀립니다." 출력

                사용자가입력한_아이디=아이디를입력해주세요.getText().toString();  //사용자가 EditText에 입력한 ID값을 변수에 담아줍니다.
                String 사용자입력_패스워드=패스워드를입력해주세요.getText().toString();  //사용자가 EditText에 입력한 PW값을 변수에 담아줍니다.

                //사용자가 EditText에 입력한 데이터가 실제로 SHared안에 존재하는지 찾기위해 객체를 선언해줍니다.
                SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);

                String 불러올_데이터=sharedPreferences.getString(사용자가입력한_아이디,"제대로저장안됨" );
                //변수 "불러올_데이터" 에 키값이 "사용자입력_아이디" 값이 존재하는지 확인한다. 제대로됐다면 "비밀번호" 를 불러올_데이터에 저장한다.
                Log.v("check_data","불러올_데이터(비밀번호) 의 값 :"+불러올_데이터);

                //사용자가 입력한 비밀번호가 "사용자입력_아이디" 를 기반으로한 key값에 대한 value가 맞는지 확인한다. (사용자가 입력한 비밀번호가 저장된 비밀번호와 같은지 비교한다.)
                //맞다.
                if(사용자입력_패스워드.equals(불러올_데이터)){
                    Toast.makeText(login_screen.this, "로그인 완료!", Toast.LENGTH_SHORT).show();


                    //사망시 액티비티로 전환한다. 본 프로젝트에서는 게임메인화면으로 가야한다.
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class); //다음 넘어갈 클래스를 지정

                    //"사용자의 아이디값을 인텐트로 넘겨줍니다."
                    intent.putExtra("사용자_아이디", 사용자가입력한_아이디);

                    Log.v("check_data","인텐트로 넘기는 사용자의 아이디 :"+사용자가입력한_아이디);

                    startActivity(intent); //다음 화면으로 넘어간다.

                }
                //아니다.
                else{
                    Toast.makeText(login_screen.this, "아이디 또는 비밀번호가 틀렸습니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        계정생성.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //Sharedpreference를 못배워서 startaAcitivityforResult로 일단 아이디와 비번을 make_id 에서 생성한후 다시 여기로 가지고 돌아온와서 검사 ㄱㄱ

                Intent intent=new Intent(
                        getApplicationContext(),make_id.class); //다음 넘어갈 클래스를 지정
                startActivity(intent); //다음 화면으로 넘어간다.
            }
        });

    } //onCreate

    /////////////////////////////////////////////////////////////////////

    private void initData() {
        Log.v("check_data","코드의 흐름 6");
        mOAuthLoginInstance = OAuthLogin.getInstance();

        Log.v("check_data","코드의 흐름 7 mOAuthLoginInstance 의 값"+ mOAuthLoginInstance);

        mOAuthLoginInstance.showDevelopersLog(true);
        Log.v("check_data","코드의 흐름 8 mOAuthLoginInstance 의 값 "+mOAuthLoginInstance);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
        Log.v("check_data","코드의 흐름 9");

        /*
         * 2015년 8월 이전에 등록하고 앱 정보 갱신을 안한 경우 기존에 설정해준 callback intent url 을 넣어줘야 로그인하는데 문제가 안생긴다.
         * 2015년 8월 이후에 등록했거나 그 뒤에 앱 정보 갱신을 하면서 package name 을 넣어준 경우 callback intent url 을 생략해도 된다.
         */
        //mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME, OAUTH_callback_intent_url);
    }

    private void initView() {
        Log.v("check_data","코드의 흐름 10");
//        mApiResultText = (TextView) findViewById(R.id.api_result_text);  //액티비티에서 가장 아래쪽에 존재하는 TextView를 의미한다.
//        Log.v("check_data","코드의 흐름 11");
//        mOauthAT = (TextView) findViewById(R.id.oauth_access_token);  //액티비티에서 가장 산단에 존재하는 TextView를 의미한다.
//        Log.v("check_data","코드의 흐름 12");
//        mOauthRT = (TextView) findViewById(R.id.oauth_refresh_token);  //바로 아래쪽 TextView
//        Log.v("check_data","코드의 흐름 13");
//        mOauthExpires = (TextView) findViewById(R.id.oauth_expires); // 얘도
//        Log.v("check_data","코드의 흐름 14");
//        mOauthTokenType = (TextView) findViewById(R.id.oauth_type); //..
//        Log.v("check_data","코드의 흐름 15");
//        mOAuthState = (TextView) findViewById(R.id.oauth_state); //..
        Log.v("check_data","코드의 흐름 16");

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);  //네이버 아이디로 로그인 버튼  (필요한거)
        Log.v("check_data","코드의 흐름 17");
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);  //정확하지는않지만 "네아로" 버튼을 누르는순간  mOAuthLoginHandler 가 발동되는것같다.
        Log.v("check_data","코드의 흐름 18");

        updateView(); //TextView 들을 업데이트한다.
        Log.v("check_data","코드의 흐름 19");
    }


    private void updateView() {
        Log.v("check_data","코드의 흐름 20");
//        mOauthAT.setText(mOAuthLoginInstance.getAccessToken(mContext));
//        Log.v("check_data","코드의 흐름 21 mOAuthLoginInstance.getAccessToken(mContext) 의 값 "+mOAuthLoginInstance.getAccessToken(mContext) );
//        mOauthRT.setText(mOAuthLoginInstance.getRefreshToken(mContext));
//        Log.v("check_data","코드의 흐름 22 mOAuthLoginInstance.getRefreshToken(mContext) 의 값 "+mOAuthLoginInstance.getRefreshToken(mContext));
//        mOauthExpires.setText(String.valueOf(mOAuthLoginInstance.getExpiresAt(mContext)));
//        Log.v("check_data","코드의 흐름 23 mOAuthLoginInstance.getExpiresAt(mContext)) 의 값 "+mOAuthLoginInstance.getExpiresAt(mContext));
//        mOauthTokenType.setText(mOAuthLoginInstance.getTokenType(mContext));
//        Log.v("check_data","코드의 흐름 24 의 값 "+mOAuthLoginInstance.getTokenType(mContext));
//        mOAuthState.setText(mOAuthLoginInstance.getState(mContext).toString());
//        Log.v("check_data","코드의 흐름 25 의 값 "+mOAuthLoginInstance.getState(mContext).toString());
    }

    @Override
    protected void onResume() {
        Log.v("check_data","코드의 흐름 26");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Log.v("check_data","코드의 흐름 27");
        super.onResume();
        Log.v("check_data","코드의 흐름 28");

    }

    /**
     * startOAuthLoginActivity() 호출시 인자로 넘기거나, OAuthLoginButton 에 등록해주면 인증이 종료되는 걸 알 수 있다.
     */
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            Log.v("check_data","코드의 흐름 29");
            if (success) {
                Log.v("check_data","코드의 흐름 30 네이버 아이디로 로그인 성공!");
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                Log.v("check_data","코드의 흐름 34");

                new RequestApiTask().execute(); //buttonVerifier 버튼을 눌렀을때 호출되는 메소드

//                mOauthAT.setText(accessToken);  //이 친구
//                Log.v("check_data","코드의 흐름 35");
//                mOauthRT.setText(refreshToken);
//                Log.v("check_data","코드의 흐름 36");
//                mOauthExpires.setText(String.valueOf(expiresAt));
//                Log.v("check_data","코드의 흐름 37");
//                mOauthTokenType.setText(tokenType);
//                Log.v("check_data","코드의 흐름 38");
//                mOAuthState.setText(mOAuthLoginInstance.getState(mContext).toString());
//                Log.v("check_data","코드의 흐름 39");

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            } else {
                Log.v("check_data","코드의 흐름 40");
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                Log.v("check_data","코드의 흐름 41");
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Log.v("check_data","코드의 흐름 42");
                //Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, "로그인이 취소되었습니다. 로그인을 다시 시도해주세요!", Toast.LENGTH_SHORT).show();
                Log.v("check_data","코드의 흐름 43");
            }
            Log.v("check_data","코드의 흐름 44");
        }

    };

    //유저에대한 각종 정보를 가져오는 메소드이다.
    public void onButtonClick(View v) throws Throwable {
        Log.v("check_data","코드의 흐름 45");
//        switch (v.getId()) {
//
//            case R.id.buttonOAuth: {
//                Log.v("check_data","코드의 흐름 46");
//                mOAuthLoginInstance.startOauthLoginActivity(Main2Activity.this, mOAuthLoginHandler);
//                Log.v("check_data","코드의 흐름 47");
//                break;
//            }
//            //buttonVerifier 를 누르면 유저에대한 각종 정보를 TextView에 출력한다.
//            case R.id.buttonVerifier: {
//                Log.v("check_data","코드의 흐름 48");
//                new RequestApiTask().execute();
//                Log.v("check_data","코드의 흐름 49");
//                break;
//            }
//            case R.id.buttonRefresh: {
//                Log.v("check_data","코드의 흐름 50");
//                new RefreshTokenTask().execute();
//                Log.v("check_data","코드의 흐름 51");
//                break;
//            }
//            case R.id.buttonOAuthLogout: {
//                Log.v("check_data","코드의 흐름 52");
//                mOAuthLoginInstance.logout(mContext);
//                Log.v("check_data","코드의 흐름 53");
//                updateView();
//                Log.v("check_data","코드의 흐름 54");
//                break;
//            }
//            case R.id.buttonOAuthDeleteToken: {
//                Log.v("check_data","코드의 흐름 55");
//                new DeleteTokenTask().execute();
//                Log.v("check_data","코드의 흐름 56");
//                break;
//            }
//            default:
//                Log.v("check_data","코드의 흐름 57");
//                break;
//        }
        Log.v("check_data","코드의 흐름 58");
    }


    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.v("check_data","코드의 흐름 59");
            boolean isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(mContext);
            Log.v("check_data","코드의 흐름 60");

            if (!isSuccessDeleteToken) {
                Log.v("check_data","코드의 흐름 61");
                // 서버에서 token 삭제에 실패했어도 클라이언트에 있는 token 은 삭제되어 로그아웃된 상태이다
                // 실패했어도 클라이언트 상에 token 정보가 없기 때문에 추가적으로 해줄 수 있는 것은 없음
                Log.v("check_data","코드의 흐름 62");
                Log.d(TAG, "errorCode:" + mOAuthLoginInstance.getLastErrorCode(mContext));
                Log.v("check_data","코드의 흐름 63");
                Log.d(TAG, "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(mContext));
                Log.v("check_data","코드의 흐름 64");
            }
            Log.v("check_data","코드의 흐름 65");
            return null;
        }

        protected void onPostExecute(Void v) {
            Log.v("check_data","코드의 흐름 66");
            updateView();
            Log.v("check_data","코드의 흐름 67");
        }
    }

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            Log.v("check_data","코드의 흐름 68");
           // mApiResultText.setText((String) "");
            Log.v("check_data","코드의 흐름 69");
        }

        //이게 뭐하는 메소드지??? 유저에대한 정보를 얻어오기위해선 이 메소드가 호출되는것까지는 확인했다.
        @Override
        protected String doInBackground(Void... params) {
            Log.v("check_data","코드의 흐름 70");
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            Log.v("check_data","코드의 흐름 71");
            return mOAuthLoginInstance.requestApi(mContext, at, url);
        }

        //이게 뭐하는 메소드지??? 유저에대한 정보를 얻어오기위해선 이 메소드가 호출되는것까지는 확인했다.
        protected void onPostExecute(String content) {
            Log.v("check_data","코드의 흐름 72");

            //유저에대한 최종적인 정보를 추출해주는 코드이다. 이 녀석의 형태는 JSONObject 형태로 되어있다.
           // mApiResultText.setText((String) content);
            Log.v("check_data","코드의 흐름 73 (String) content 의 값 "+(String) content);

            //아래의 코드는 key 에 대한 value 를 추출하는 코드.

            try
            {
                JSONObject jsonObject = new JSONObject((String)content);          //JsonObject 객체 생성
                String fruitValue = jsonObject.getString("response");  //변수 fruitValue 안에 "fruit" 라고하는 키값에 대한 value를 전부 넣는다.
                JSONObject fruitObject = new JSONObject(fruitValue);        //JsonObject 객체 fruitObject 생성. fruitObject 안에 변수 fruitValue 를 넣어준다.

                //Iterator : 추출한 키값을 관리해주는 녀석.
                Iterator i = fruitObject.keys();

                //whiel 문이 돌면서 추출된 키값들을 확인, ArrayLIst에 추가할 수 있다.
                while(i.hasNext())
                {
                    String b = i.next().toString();
                    Log.d("ITPangpang",b);
                    key_list.add(b); //key값을 담기위한 ArrayLIst 안에 추출한 key값을 담아준다.
                }

                //for 문이 돌면서 추출된 value 값들을 확인, ArrayList에 추가할 수 있다.
                for(int j = 0; j<key_list.size();j++)
                {
                    value_list.add(fruitObject.getString(key_list.get(j)));  //key에 대한 value를 담기위한 ArrayList안에 value를 담아준다.
                    Log.d("ITPangpang",(j)+"번째 과일이름->"+key_list.get(j));
                    Log.d("ITPangpang",(j)+"번째 과일가격->"+value_list.get(j));
                }

                // TODO: 2019-07-28  플레이어의 아이디를 뽑아내는 코드
                String ID=value_list.get(1);
                Log.v("check_data","플레이어 ID->"+value_list.get(1));

                사용자가입력한_아이디=value_list.get(1);  //static 변수인 "사용자가입력한_아이디" 에다가 "네아로" 를 통해 받은 사용자의 ID를 대입한다.
                Log.v("check_data","사용자가입력한_아이디 의 값->"+사용자가입력한_아이디);


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            ///////////////////////////////////////////////////
        }
    }

    private class RefreshTokenTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            Log.v("check_data","코드의 흐름 74");
            return mOAuthLoginInstance.refreshAccessToken(mContext);
        }


        protected void onPostExecute(String res) {
            Log.v("check_data","코드의 흐름 75");
            updateView();
            Log.v("check_data","코드의 흐름 76");
            Log.v("check_data","코드의 흐름 76");
        }

    }

    ///////////////////////////////////////////////////////////////////////////


}
