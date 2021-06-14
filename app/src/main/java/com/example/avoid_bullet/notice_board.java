package com.example.avoid_bullet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class notice_board extends Activity implements View.OnCreateContextMenuListener {//notice_board
    String 수정때_받아온_제목;
    Button 게시글_작성하기;
    ArrayList<Board> mArrayList;  //count == mArrayList
    board_adapter mAdapter;
    RecyclerView mRecyclerView;



    public static final String TAG = "재밌는로그찍기";




    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_board);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArrayList = new ArrayList<>();
        mAdapter = new board_adapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        //registerForContextMenu(mRecyclerView);  //ContextMenu 를 사용할 뷰를 선정해준다. onCreateContextMEnu() 와 onContextItemSelected() 메소드를 오버라이딩해야한다.
        게시글_작성하기=(Button)findViewById(R.id.게시글_작성하기);

        리사이클러뷰_생성();

        게시글_작성하기.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),writting_board.class);
                startActivityForResult(intent,1); //다음 화면으로 넘어간다.
            }
        });

        //GestureDetector 추가 이녀석없이 addOnItemTouchListener 이놈만 있으면 한번클릭해도 두번 클릭한것처럼 인식한다. 그래서 이놈을 넣어줌.
        final GestureDetector gestureDetector = new GestureDetector(notice_board.this,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });




        //addOnItemTouchListener 추가  아이템뷰를 누르면 발생하는 이벤트.
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {  //addOnItemTouchListener
            @Override
            public void onClick(View view, final int position) {  //int position 이녀석으로 특정 아이템뷰에 접근한것같음.

                final Board board = mArrayList.get(position);   // get 메소드 : 리스트 내의 지정된 위치의 요소를 돌려줍니다.  // 이 코드가 아이템뷰를 하나씩 접근하는데 결정적인 역할을 하는것같다.
                AlertDialog.Builder builder = new AlertDialog.Builder(notice_board.this);
                builder.setTitle("무엇을 하시겠습니까?");
                //builder.setMessage("AlertDialog Content");
                builder.setPositiveButton("게시글수정",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Log.e("gd",position+" 포지션값");

                                //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getApplicationContext(),edit_board.class);
                                intent.putExtra("게시글수정_제목", board.get제목());
                                intent.putExtra( "게시글수정_작성자", board.get작성자());
                                intent.putExtra("포지션값", position);
                                startActivityForResult(intent, 3);

                            }
                        });
                builder.setNegativeButton("게시글보기",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                                Intent intent2=new Intent(getApplicationContext(),see_board.class);
                                intent2.putExtra("게시글보기_제목", board.get제목());
                                intent2.putExtra( "게시글보기_작성자", board.get작성자());
                                startActivityForResult(intent2, 2);
                            }
                        });
                builder.show();




                ///////원본예제코드.//////

//                Board board = mArrayList.get(position);   // get 메소드 : 리스트 내의 지정된 위치의 요소를 돌려줍니다.  // 이 코드가 아이템뷰를 하나씩 접근하는데 결정적인 역할을 하는것같다.
//                System.out.println("로그 2");
//
//                Intent intent = new Intent(getBaseContext(), see_board.class);   //일반 인텐트랑 다른것같다! 이점을 잘 보도록
//
//                intent.putExtra("제목", board.get제목());   //본인이 넘기고싶은 값들을 인텐트로 넘겼음.
//
//                intent.putExtra( "작성자", board.get작성자());
//
//                //intent.putExtra("english", board.get작성자;
//
//
//               startActivityForResult(intent,2);
//               System.out.println("ㅋㅋㅋ 제목 :"+board.get제목());
//                System.out.println("ㅋㅋㅋ 작성자 :"+board.get작성자());


                ///////원본예제코드.//////


            }

            @Override
            public void onLongClick(View view, int position) {  //onLongClick


            }  //onLongClick
        }));  //addOnItemTouchListener


    }//onCreate

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // 컨텍스트 메뉴가 최초로 한번만 호출되는 콜백 메서드
        Log.d("test", "onCreateContextMenu");
//        getMenuInflater().inflate(R.menu.main, menu);

        // menu.setHeaderTitle("따이뜰");
        // menu.add(0,1,100,"수정");
//        menu.add(0,2,100,"녹색");
//        menu.add(0,3,100,"파랑");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 롱클릭했을 때 나오는 context Menu 의 항목을 선택(클릭) 했을 때 호출
        switch(item.getItemId()) {
            case 1 :// 빨강 메뉴 선택시
                //1.startActivityforResult로 writting_board 액티비티로넘어간다. 단, 작성자값과 내용값을 가지고간다.
                //2.가지고갔던 작성자값과 내용값을 작성자TextView와 제목입력EditText에 출력시킨다.
                //3."작성완료" 버튼을 눌렀을때 작성자TextView와 제목입력EditText 의 값을 가지고 notice_board 액티비티로 돌아온다.
                //4. 가지고온 값을 리사이클러뷰 아이템에 넣어준다.



                //intent.putExtra( "작성자", board.get작성자());

                return true;
//            case 2 :// 녹색 메뉴 선택시
//                Toast.makeText(getApplicationContext(),"시발2",Toast.LENGTH_SHORT).show();
//                return true;
//            case 3 :// 파랑 메뉴 선택시
//                Toast.makeText(getApplicationContext(),"시발3",Toast.LENGTH_SHORT).show();
//                return true;
        }

        return super.onContextItemSelected(item);
    }





    public void 리사이클러뷰_생성(){


        Board Board=new Board("치킨이","고수분들봐주세요");
        Board Board2=new Board("라면이","나랑점수내기할사람?");
        mArrayList.add(Board);
        mArrayList.add(Board2);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  //onActivityResult
        super.onActivityResult(requestCode, resultCode, data);


        Log.e("aaaaasdfsdf", requestCode+"");



        if (requestCode == 1) {

            Log.e("aaaaa", RESULT_OK+"");

            if (resultCode == RESULT_OK) {//작업이성공한경우
                String 받아온_작성자이름=data.getStringExtra("result1");
                String 받아온_제목=data.getStringExtra("result2");


                Board Board=new Board(받아온_작성자이름,받아온_제목);
                mArrayList.add(Board);
                mAdapter.notifyDataSetChanged();

            }//작업이성공한경우
            if (resultCode == RESULT_CANCELED) {//작업이실패한경우


            }//작업이실패한경우
        }//requestCode == 1

        //requestCode ==2 인경우

        else if(requestCode==3){

            Log.e("aaaaacccc", RESULT_OK+"");

            if(resultCode == RESULT_OK){

                Log.e("aaaaabbbbb", RESULT_OK+"");

                String 받아온_작성자이름=data.getStringExtra("변경될_제목");
                String 받아온_제목이름=data.getStringExtra("변경될_작성자");
                Log.e("aaaaa받은작성자이름", 받아온_작성자이름+"");
                Log.e("aaaaa받은제목이름", 받아온_제목이름+"");

                String 받아온_제목=data.getStringExtra("result2");
                int position=data.getIntExtra("position", 0);
                Log.e("aaaaa", position+"");

                // Board Board=new Board(받아온)작성자이름,받아온_제목)  -> 수정전의코드
                // Board Board=new Board(받아온_작성자이름,받아온_제목이름);  -> 수정후의 코드
                Board Board=new Board(받아온_제목이름,받아온_작성자이름);
                mArrayList.set(position,Board);
                mAdapter.notifyDataSetChanged();



            }
            if(resultCode==RESULT_CANCELED){

            }
        }

//        mArrayList.set()
    }//onActivityResult

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;  //GestureDetector 안드로이드에서 사용자의 제스처를 쉽게 구분할 수 있도록 하는 Gesture Detector가 존재.
        private notice_board.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final notice_board.ClickListener clickListener) {
            System.out.println("로그 14");
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    System.out.println("로그 15");
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    System.out.println("로그 16");
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        System.out.println("로그 17");
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());    //findChildViewUnder   : 주어진 지점에서 최상위 보기를 찾습니다.

            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

                clickListener.onClick(child, rv.getChildAdapterPosition(child));   //getChildAdapterPosition : 지정된 새끼뷰가 대응되는 어댑터 위치를 돌려줍니다.

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            System.out.println("로그 22");
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            System.out.println("로그 23");
        }
    }

    void 게시글_삭제_수정_선택지()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("무엇을 하시겠습니까?");
        builder.setMessage("AlertDialog Content");
        builder.setPositiveButton("게시글수정", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("게시글삭제",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();

                    }
                });
        builder.show();


    }
    @Override
    protected void onResume(){
        super.onResume();

        Board board=mArrayList.get(1);
        System.out.println("수정때_받아온_제목"+수정때_받아온_제목);
        mArrayList.set(1,board);

    }

}//notice_board
