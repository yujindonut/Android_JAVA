package ddwucom.mobile.finalreport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Restaurant> myDataList;
    private LayoutInflater layoutInflater;

    //원본데이터를 밖에서 받을 수 있도록 세개의 변수를 생성자로 받음
    public MyAdapter(Context context, int layout, ArrayList<Restaurant> myDataList) {
        this.context = context;
        this.layout = layout;
        this.myDataList = myDataList;
        //시스템 서비스로부터 layoutInflater를 얻어오고, 자바 객체로 화면객체들을 만들면됨
        //getSystemService은 MainActivity가 상속받는 메소드이기 때문에 외부로부터 context 객체 받아서 연결
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myDataList.size();
    }

    @Override
    public Object getItem(int position) {
        //position에 해당하는 원본데이터를 꺼내서 반환
        return myDataList.get(position);
    }
    @Override
    public long getItemId(int position) { return myDataList.get(position)._id; }

    @Override
    //원본데이터의 개수만큼 getView를 호출해서, 어댑터뷰를 자기자신에 배치
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(layout, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.signatureMenu = convertView.findViewById(R.id.main_signature);
            viewHolder.restaurantName = convertView.findViewById(R.id.main_resturant_name);
            viewHolder.ratingBar = convertView.findViewById(R.id.main_ratingBar);
            viewHolder.menu_image = convertView.findViewById(R.id.main_menu_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.signatureMenu.setText(myDataList.get(pos).getSignatureMenu());
        viewHolder.restaurantName.setText(myDataList.get(pos).getRestaurantName());
        viewHolder.ratingBar.setRating(Float.parseFloat(myDataList.get(pos).getRatingBar()));
        if("kyeri".equals(myDataList.get(pos).getRestaurantName())){
            viewHolder.menu_image.setImageResource(R.mipmap.kyeri);
        } else if("salthouse".equals(myDataList.get(pos).getRestaurantName())){
            viewHolder.menu_image.setImageResource(R.mipmap.salthouse);
        }else if("montan".equals(myDataList.get(pos).getRestaurantName())){
            viewHolder.menu_image.setImageResource(R.mipmap.mongtan);
        }else if("oeuf".equals(myDataList.get(pos).getRestaurantName())){
            viewHolder.menu_image.setImageResource(R.mipmap.oeuf);
        }else if("kitchen205".equals(myDataList.get(pos).getRestaurantName())){
            viewHolder.menu_image.setImageResource(R.mipmap.kitchen205);
        }else if("uglybakery".equals(myDataList.get(pos).getRestaurantName())){
            viewHolder.menu_image.setImageResource(R.mipmap.uglybakery);
        }else {
            viewHolder.menu_image.setImageResource(R.mipmap.unnamed);
        }
        return convertView;
    }
    static class ViewHolder {
        TextView signatureMenu;
        TextView restaurantName;
        RatingBar ratingBar;
        ImageView menu_image;
    }
}
