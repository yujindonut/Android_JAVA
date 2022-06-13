package ddwu.com.mobile.multimedia.photomemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ShowMemoActivity extends AppCompatActivity {

    final static String TAG = "ShowMemoActivity";

    MemoDBHelper helper;
    ImageView ivPhoto;
    TextView tvMemo;
    MemoDto memoDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_memo);

        ivPhoto = findViewById(R.id.ivPhoto);
        tvMemo = findViewById(R.id.tvMemo);
//        MainActivity 에서 전달 받은 _id 값을 사용하여 DB 레코드를 가져온 후 ImageView 와 TextView 설정
        String id = String.valueOf(getIntent().getSerializableExtra("_id"));
        helper = new MemoDBHelper(this);
        memoDto = findRecord(id);
        Log.d(TAG,memoDto.getPhotoPath());
        Log.d(TAG,memoDto.getMemo());
        Log.d(TAG,id);
        tvMemo.setText(memoDto.getMemo());
        setPic();
    }
    /*사진의 크기를 ImageView에서 표시할 수 있는 크기로 변경*/
    private void setPic() {
        // Get the dimensions of the View
        int targetW = ivPhoto.getWidth();
        int targetH = ivPhoto.getHeight();

        Log.d(TAG,"W: " + String.valueOf(targetW) );
        Log.d(TAG, "H: " + String.valueOf(targetH));
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(memoDto.getPhotoPath(), bmOptions);
        Log.d(TAG, "Path: " + memoDto.getPhotoPath());
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        Log.d(TAG,"PW: " + photoW);
        Log.d(TAG, "PH: " + photoH);

        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        int scaleFactor = 1;
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(memoDto.getPhotoPath(), bmOptions);

        ivPhoto.setImageBitmap(bitmap);
    }

    public MemoDto findRecord(String _id) {
        //			DB 검색 작업 수행
        MemoDto dto = new MemoDto();
        String result ="";
        SQLiteDatabase myDB = helper.getReadableDatabase(); //select
        String selection = helper.ID+ "=?";
        String[] selectArgs = new String[]{_id};
        Cursor cursor =
                myDB.query(helper.TABLE_NAME, null, selection, selectArgs,
                        null, null, null, null);
        if (cursor.getCount() > 0) {
            long id = 0;
            String photoPath = null;
            String memo = null;

            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(helper.ID));
                photoPath = cursor.getString(cursor.getColumnIndex(helper.PATH));
                memo = cursor.getString(cursor.getColumnIndex(helper.MEMO));
            }
            dto.set_id(id);
            dto.setMemo(memo);
            dto.setPhotoPath(photoPath);
        }
        return dto;
    }
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnClose:
                finish();
                break;
        }
    }
}
