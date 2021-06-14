package com.example.avoid_bullet;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class my_profile extends Activity {  //Activity
    private static MediaPlayer 프로필브금;
    ImageView 내프사;
    Button 설정저장;
    Bitmap originalBm;  //비트맵 이미지 변수 갤러리에서 불러온 데이터를 저장하기위한 변수이다.
    private File tempFile;
    private static final int PICK_FROM_ALBUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate 시작
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_profile);  // layout xml 과 자바파일을 연결


        내프사=(ImageView)findViewById( R.id.내프사);
        설정저장=(Button)findViewById(R.id.설정저장);

        설정저장.setOnClickListener(new View.OnClickListener(){ //설정저장

            //여기서 "내프사" 와 "닉네임표시" 를 저장해야함. 이 기능은 Sharedpreference 주차때 하는걸로.

            @Override
            public void onClick(View view){ // TextView를 눌렀을때 일어나는 이벤트에 대해서
                Intent intent=new Intent(
                        getApplicationContext(), MainActivity.class); //다음 넘어갈 클래스를 지정

                // getApplicationContext(), experiment.class); //다음 넘어갈 클래스를 지정
                // intent.putExtra("사진",originalBm);

//                Bitmap sendBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.air_craft1 );
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("image",byteArray);


                startActivity(intent); //다음 화면으로 넘어간다.
                 finish();
            }// 설정저장
        }); //설정저장

        내프사.setOnClickListener(new View.OnClickListener(){ //내 프사변경

            @Override
            public void onClick(View view){ // TextView를 눌렀을때 일어나는 이벤트에 대해서
                tedPermission();
                goToAlbum();
            }// 설정저장
        }); //내 프사변경

        프로필브금 = MediaPlayer.create(this, R.raw.my_profile);

        프로필브금.setLooping(true);

        프로필브금.start();

    }//onCreate 끝

    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onResume(){
        super.onResume();

        프로필브금.start();
    }

    @Override
    protected void onPause(){
        super.onPause();

        프로필브금.pause();
    }

    @Override
    protected void onRestart(){
        super.onRestart();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    private void tedPermission() {//tedPermission

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }//tedPermission

    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //onActivityResult

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        }
    } //onActivityResult

    private void setImage() { //setImage start
        BitmapFactory.Options options = new BitmapFactory.Options();

        originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        //Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        내프사.setImageBitmap(originalBm);

        //불러온 사진의 값을 로그로 확인해본다,
        Log.v("check_data","내프사 라고하는 비트맵 변수에 들어있는 값 : "+ originalBm);

        String 이미지담을_문자열=BitMapToString(originalBm); //String 변수에 비트맵을 담아줍니다

        SharedPreferences pref=getSharedPreferences("UserImage", MODE_PRIVATE); //SharedPreferences 의 객체 pref 를 만들고 파일명을 "UserImage"로 만든다.
        SharedPreferences.Editor editor=pref.edit(); // 아직 명확하게 이해못함. 하지만 과제진행에 여태까지 크게 문제가 없었기에 넘어가겠음.
        editor.putString(login_screen.사용자가입력한_아이디,이미지담을_문자열); // key값을 사용자의 아이디, value로는 이미지를 담았던 변수를 넣어준다.

        Log.v("check_data","이미지가 잘 저장되었는지 로그로 확인 : "+ editor.putString(login_screen.사용자가입력한_아이디,이미지담을_문자열));

        //최종 데이터 저장
        editor.commit();

    }  //setImage end

    ///몰라도 구현하는데 지장이없어서 따로 해석안함.///
    public String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);

        byte [] b=baos.toByteArray();

        String temp= Base64.encodeToString(b, Base64.DEFAULT);

        return temp;

    }

}  //Activity
