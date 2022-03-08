package ddwucom.mobileapplication.breadtrip;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;
    int layout;

    public MyCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(layout, parent, false);
        ViewHolder holder = new ViewHolder();
        view.setTag(holder); // 빈 holder객체를 보관만함
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder.tvBakeryName == null) {
            holder.tvBakeryName = view.findViewById(R.id.tvBakerytName);
            holder.tvBakeryLocation = view.findViewById(R.id.tvBakeryLocation);
            holder.rtBakeryRate = view.findViewById(R.id.rtBakeryRate);
        }
        holder.tvBakeryName.setText( cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_BAKERY)));
        holder.tvBakeryLocation.setText(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_LOCATION)));
        holder.rtBakeryRate.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_RATINGBAR))));
    }
    static class ViewHolder {

        public ViewHolder(){
            tvBakeryName = null;
            tvBakeryLocation = null;
            rtBakeryRate = null;
        }
        TextView tvBakeryName;
        TextView tvBakeryLocation;
        RatingBar rtBakeryRate;
    }
}
