package com.example.avoid_bullet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class board_adapter extends RecyclerView.Adapter<board_adapter.BoardViewHolder> {  //board_adapter
    ArrayList<Board> mList;
    Context mContext;

    public board_adapter() {

    }


    public class BoardViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{  //ContextMenu 만들기. 해당 뷰를 길게 누르고있으면 나타나는 메뉴이다.
        protected TextView 작성자;
        protected TextView 제목;


        public BoardViewHolder(View view) {
            super(view);

            this.작성자 = (TextView) view.findViewById(R.id.textview_recyclerview_writer);
            this.제목 = (TextView) view.findViewById(R.id.textview_recyclerview_contents);


            view.setOnCreateContextMenuListener(this); //2. 리스너 등록
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 메뉴 추가U
            System.out.println("여기다1");

            //MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");  수정기능은 다른곳에서 구현함.
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
           // Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);

        }   // 3. 메뉴 추가U

        // 4. 캔텍스트 메뉴 클릭시 동작을 설정
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {//OnMenuItemClickListener
            @Override
            public boolean onMenuItemClick(MenuItem item) {//onMenuItemClick
                System.out.println("여기다1");

                switch (item.getItemId()) {
                    case 1001:
//                        notice_board notice_board=new notice_board();
//                        notice_board.수정하기();



                        break;

                    //1. 아이디가 작성자의 아이디와 일치하는지 확인한다.
                    //2. 일치하지않는경우 토스트메세지로 "게시글의 작성자만 글을 삭제할 수 있습니다." 일치하는경우 writting_board 액티비티로 이동한다.


                    case 1002:

                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mList.size());
                        System.out.println("여기다3");
                        break;

                }
                return true;
            }//onMenuItemClick
        };//OnMenuItemClickListener



    }  //ContextMenu 만들기. 해당 뷰를 길게 누르고있으면 나타나는 메뉴이다.


//    public void 수정하기(String 작성자2,String 글제목2){
//
//        String 작성자_임시저장=작성자2;
//        String 글제목_임시저장=글제목2;
//
//        Board Board=new Board(작성자_임시저장,글제목_임시저장);
//        mList.set(getAdapterPosition(),Board);
//        notifyItemChanged(getAdapterPosition());
//            작성자.setText(mList.get(getAdapterPosition()).get작성자());
//            제목.setText(mList.get(getAdapterPosition()).get제목());
//    }


    public board_adapter(Context context, ArrayList<Board> list) {
        mList = list;
        mContext = context;

        System.out.println("여기다4");
    }



    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //뷰 홀더 상속받음.  뷰홀더 객체를 생성
        System.out.println("여기다5");
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.board_item_list, viewGroup, false);

        BoardViewHolder viewHolder = new BoardViewHolder(view);

        return viewHolder;
    }//뷰 홀더 상속받음.  뷰홀더 객체를 생성

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder viewholder, int position) {   //onBindViewHolder 아이템에 데이터를 설정해준다
        System.out.println("여기다6");
        viewholder.작성자.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.제목.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);


        viewholder.작성자.setGravity(Gravity.CENTER);
        viewholder.제목.setGravity(Gravity.CENTER);


        viewholder.작성자.setText(mList.get(position).get작성자());
        viewholder.제목.setText(mList.get(position).get제목());


    }  //onBindViewHolder  아이템에 데이터를 설정해준다

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);


    }





}  //board_adapter
