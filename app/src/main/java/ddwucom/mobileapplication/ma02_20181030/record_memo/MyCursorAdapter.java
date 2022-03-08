package ddwucom.mobileapplication.ma02_20181030.record_memo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import ddwucom.mobileapplication.ma02_20181030.R;

public class MyCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;
    int layout;
    ViewHolder holder;

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
    /*사진의 크기를 ImageView에서 표시할 수 있는 크기로 변경*/
    private void setPic(String mCurrentPhotoPath) {
        // Get the dimensions of the View
        int targetW = 86;
        int targetH = 86;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        holder.image.setImageBitmap(bitmap);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        holder = (ViewHolder) view.getTag();
        if(holder.tvName == null) {
            holder.tvName = view.findViewById(R.id.tvTitle);
            holder.tvDate = view.findViewById(R.id.tvDate);
            holder.rtRate = view.findViewById(R.id.item_ratingbar);
            holder.image = view.findViewById(R.id.iv_image);
        }
        holder.tvName.setText( cursor.getString(cursor.getColumnIndex(MemoDBHelper.CAFENAME)));
        holder.tvDate.setText(cursor.getString(cursor.getColumnIndex(MemoDBHelper.DATE)));
        holder.rtRate.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(MemoDBHelper.RATINGBAR))));
//        Log.d("adapter", cursor.getString(cursor.getColumnIndex(MemoDBHelper.PATH)));
        setPic(cursor.getString(cursor.getColumnIndex(MemoDBHelper.PATH)));
    }
    static class ViewHolder {

        public ViewHolder(){
            tvName = null;
            tvDate = null;
            rtRate = null;
            image = null;
        }
        TextView tvName;
        TextView tvDate;
        RatingBar rtRate;
        ImageView image;

    }
}
