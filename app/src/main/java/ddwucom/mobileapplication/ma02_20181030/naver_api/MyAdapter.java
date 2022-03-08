package ddwucom.mobileapplication.ma02_20181030.naver_api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ddwucom.mobileapplication.ma02_20181030.R;

public class MyAdapter extends BaseAdapter
{
    private ArrayList<NaverSearchDto> naverSearchDtoArrayList;
    private LayoutInflater layoutInflater;
    private int layout;
    private Context context;
    private ViewHolder viewHolder;

    public MyAdapter(Context context, int layout, ArrayList<NaverSearchDto> naverSearchDtoArrayList) {
        this.naverSearchDtoArrayList = naverSearchDtoArrayList;
        this.layout = layout;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return naverSearchDtoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return naverSearchDtoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return naverSearchDtoArrayList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final int pos = position;

        if(convertView == null){
            convertView = layoutInflater.inflate(layout, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.tvPostdate = convertView.findViewById(R.id.tvPostdate);
            viewHolder.tvDescription = convertView.findViewById(R.id.tvDescription);
            viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(naverSearchDtoArrayList.get(pos).getTitle());
        viewHolder.tvPostdate.setText(naverSearchDtoArrayList.get(pos).getPostdate());
        viewHolder.tvDescription.setText(naverSearchDtoArrayList.get(pos).getDescription());
        return convertView;
    }
    public void setList(ArrayList<NaverSearchDto> list) {
        this.naverSearchDtoArrayList = list;
        notifyDataSetChanged();
    }
    static class ViewHolder {
        TextView tvTitle;
        TextView tvPostdate;
        TextView tvDescription;
    }

}
