package ddwucom.mobile.week10.custonadaptertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<MyData> myDataList;
    private LayoutInflater layoutInflater;

    //원본데이터를 밖에서 받을 수 있도록 세개의 변수를 생성자로 받음
    public MyAdapter(Context context, int layout, ArrayList<MyData> myDataList) {
        this.context = context;
        this.layout = layout;
        this.myDataList = myDataList;
        //시스템 서비스로부터 layoutInflater를 얻어오고, 자바 객체로 화면객체들을 만들면됨
        //getSystemService은 MainActivity가 상속받는 메소드이기 때문에 외부로부터 context 객체 받아서 연결
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    //어댑터뷰가 몇개의 원본데이터를 갖고 있는지!
    public int getCount() {
        return myDataList.size();
    }

    @Override
    public Object getItem(int position) {
        //position에 해당하는 원본데이터를 꺼내서 반환
        return myDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return myDataList.get(position).get_id();
    }

    @Override
    //원본데이터의 개수만큼 getView를 호출해서, 어댑터뷰를 자기자신에 배치
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(layout,parent, false);

            viewHolder = new ViewHolder();

            viewHolder.tvNo = convertView.findViewById(R.id.tvNo);
            viewHolder.tvName = convertView.findViewById(R.id.tvNae);
            viewHolder.tvPhone = convertView.findViewById(R.id.tvPhone);
            viewHolder.button = convertView.findViewById(R.id.button);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
//        button.setFocusable(false);

        viewHolder.tvNo.setText(String.valueOf(myDataList.get(position).get_id()));
        viewHolder.tvName.setText(myDataList.get(position).getName());
        viewHolder.tvPhone.setText(myDataList.get(position).getPhone());
        viewHolder.button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(context, myDataList.get(pos).getName() + " 선택", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
    static class ViewHolder {
        TextView tvNo;
        TextView tvName;
        TextView tvPhone;
        Button button;
    }
}
