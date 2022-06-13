package mobile.database.dbtest02;

import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        if(holder.tvContactName == null){
            holder.tvContactName = view.findViewById(R.id.tvContactName);
            holder.tvContactPhone = view.findViewById(R.id.tvContactPhone);
        }

        holder.tvContactName.setText( cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME)));
        holder.tvContactPhone.setText( cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE)));
    }

    static class ViewHolder {

        public ViewHolder(){
            tvContactPhone = null;
            tvContactName = null;
        }
        TextView tvContactName;
        TextView tvContactPhone;
    }
}
