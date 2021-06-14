package com.example.avoid_bullet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    //CusomAdapter 클래스는 추상클래스인 Adapter 클래스를 상속받았다. 추상클래스를 상속받은시점에서 추상메소드를 전부 구현하여야한다.
    //CustomAdapter 클래스는 문법상 Adapter<VH extends RecyclerView.ViewHolder> 를 상속받아야한다.
    //CustomViewHolder 에서 초기적으로 세팅. (각 ItemForm에 맞게)

    ArrayList<profile> List;

    public class CustomViewHolder extends RecyclerView.ViewHolder{ // CustomViewHolder
        //CustomViewHolder : xml파일의 뷰들의 데이터에 접근할 수 있게끔 해준다.
        //상속받은 ViewHolder 는 추상클래스이다.

        ImageView 프로필사진;
        TextView 아이디;
        TextView 점수;

        public CustomViewHolder(View view){ //CustomViewHolder 의 생성자이다. 매개변수로는 view가 들어간다.
            super(view);
            this.프로필사진=(ImageView)view.findViewById(R.id.textview_recyclerview_korean) ;
            this.아이디=(TextView)view.findViewById(R.id.닉네임);                                 //각 뷰의 아이디들을 매칭시켜준다.
            this.점수=(TextView)view.findViewById(R.id.점수);
        } //CustomViewHolder
    }// CustomViewHolder

    public CustomAdapter(ArrayList<profile>List){ //CustomAdapter 생성자이다. 매개변수로 ArrayList<profile>List 을 넣는다.
        this.List=List;     //어레이리스트를 초기화한다.
    }

    // RecyclerView에 새로운 데이터를 보여주기 위해 필요한 아이템뷰를 생성한다.
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //뷰홀더를 상속받으면 onCreateViewHolder 메소드를 무조건 완성시켜야한다. 추상클래스에서 선언된 추상메서드이기때문에.
        // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        //LayoutInflater 클래스의  public static LayoutInflater from(Context context) 메소드를 사용했다. 뒤에 false 이놈은 뭔지모르겠다.
        //이놈의 정체를 모르고도 팀원들이 리사이클러뷰를 잘 구현한것을 미루어보아 무조건 알아야하는 사항은 아닌것같아서 그냥 넘어가겠다.

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
        //뷰홀더 객체를 리턴한다.
    }

    // Adapter의 특정 위치(position)에 있는 데이터를 보여줘야 할때 호출됩니다.
    @Override
    public void onBindViewHolder(CustomAdapter. CustomViewHolder viewholder, int position) {
        //onBindViewHolder : 각종 뷰안에 들어갈 데이터들의 값을 넣어준다.  position의 역할은 여러개의 아이템뷰를 구분해주는 역할을 해준다.

        profile data = List.get(position);// 위치에 따라 그에맞는 데이터를 얻어온다.
        viewholder.아이디.setText(data.get아이디());//앞서 뷰홀더에 세팅해준 것을 각 위치에 맞는 것들로 보여주게 하기 위해서 세팅해준다.
        viewholder.프로필사진.setImageBitmap(List.get(position).get프로필사진()); // 프로필사진 ImageView 에 입력한 데이터를 넣어준다.  넣고싶은 매개변수 : data.get프로필사진()
        viewholder.점수.setText(data.get점수()); //점수 TextView에 입력한 데이터를 넣어준다.



//        viewholder.아이디.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        viewholder.점수.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//
//        viewholder.아이디.setGravity(Gravity.CENTER);
//        viewholder.점수.setGravity(Gravity.CENTER);
//
//        viewholder.아이디.setText(List.get(position).get아이디());
//        //viewholder.점수.setText("35");
//        viewholder.점수.setText(List.get(position).get점수());


    }

    @Override
    public int getItemCount() {
        return (null != List ? List.size() : 0);
        //List가 null이 아니면 List.size를 리턴하라.
    }

}//CusomAdapter
