package ddwucom.mobile.finalreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<MovieData> myDataList;   //원본데이터 저장한 곳
    private LayoutInflater inflater;
    ViewHolder holder;
    ImageView imageView;

    public MyAdapter(Context context, int layout, ArrayList<MovieData> myDataList) {
        this.context = context;
        this.layout = layout;
        this.myDataList = myDataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return myDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
            holder = new ViewHolder();
            imageView = convertView.findViewById(R.id.imageView);
            if(pos == 0) {
                imageView.setImageResource(R.drawable.m1);
            }if(pos == 1) {
                imageView.setImageResource(R.drawable.m2);
            }if(pos == 2) {
                imageView.setImageResource(R.drawable.m3);
            }if(pos == 3) {
                imageView.setImageResource(R.drawable.m4);
            }if(pos == 4) {
                imageView.setImageResource(R.drawable.m5);
            }if(pos == 5) {
                imageView.setImageResource(R.drawable.m6);
            }if(pos > 5){
                imageView.setImageResource(R.drawable.newmovie);
            }


//            holder.tvImage = convertView.findViewById(R.id.imageView);
            holder.tvTitle = convertView.findViewById(R.id.tvmovieName);
            holder.tvDirectory = convertView.findViewById(R.id.tvdirectory);
            holder.tvReleaseDate = convertView.findViewById(R.id.tvreleaseDate);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.tvImage.setImageResource();
        holder.tvTitle.setText(myDataList.get(pos).getTitle());
        holder.tvDirectory.setText(myDataList.get(pos).getDirector());
        holder.tvReleaseDate.setText(myDataList.get(pos).getReleaseDate());
        return convertView;
    }

    static  class ViewHolder{
//        ImageView tvImage;
        TextView tvTitle;
        TextView tvDirectory;
        TextView tvReleaseDate;
    }
}
