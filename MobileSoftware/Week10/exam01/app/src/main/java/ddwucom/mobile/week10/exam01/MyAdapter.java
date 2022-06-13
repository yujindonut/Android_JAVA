package ddwucom.mobile.week10.exam01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<MyData> myDataList;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, int layout, ArrayList<MyData> myDataList) {
        this.context = context;
        this.layout = layout;
        this.myDataList = myDataList;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //layout을 받아 자바객체화시켜 화면 객체들을 만들어냄
    }

    @Override
    //전체 원본 데이터의 개수 반환
    public int getCount() {
        return myDataList.size();
    }

    @Override
    //특정 위치의 데이터 항목 반환
    public Object getItem(int position) {
        return myDataList.get(position);
    }

    @Override
    //특정 위치의 데이터 항목 아이디 반환
    public long getItemId(int position) {
        return myDataList.get(position).get_id();
    }

    @Override
    //원본 데이터의 개수만큼 반복 호출
    //리스트 뷰 순서대로 원본 데이터로 뷰를 만들어 반환
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final int pos = position;
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(layout, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.no = convertView.findViewById(R.id.No);
            viewHolder.address = convertView.findViewById(R.id.address);
            viewHolder.detailAddress = convertView.findViewById(R.id.detail_address);
            viewHolder.state = convertView.findViewById(R.id.state);

            convertView.setTag(viewHolder);
            //ViewHolder객체를 저장하는 setTag() / getTag()를 사용하여 로딩할 수 있음
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.no.setText(Integer.valueOf(myDataList.get(position).get_id()).toString());
        viewHolder.address.setText(myDataList.get(position).getAddress());
        viewHolder.detailAddress.setText(myDataList.get(position).getDetailAddress());
        viewHolder.state.setText(myDataList.get(position).getState());

        viewHolder.no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(context,String.valueOf(myDataList.get(pos).get_id()),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        viewHolder.state.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(context,myDataList.get(pos).getState(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        viewHolder.detailAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(context,myDataList.get(pos).getDetailAddress(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        viewHolder.address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(context,myDataList.get(pos).getAddress(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return convertView;
    }
    static class ViewHolder {
        TextView no;
        TextView address;
        TextView detailAddress;
        TextView state;
    }
}
