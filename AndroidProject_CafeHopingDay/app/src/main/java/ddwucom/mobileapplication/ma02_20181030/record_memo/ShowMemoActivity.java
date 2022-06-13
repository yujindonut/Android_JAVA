package ddwucom.mobileapplication.ma02_20181030.record_memo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ddwucom.mobileapplication.ma02_20181030.R;

public class ShowMemoActivity extends AppCompatActivity {

    final static String TAG = "ShowMemoActivity";
    final int UPDATE_CODE = 200;

    MemoDBHelper helper;
    ImageView ivPhoto;
    TextView tvMemo;
    TextView tv_CafeName;
    TextView tv_Location;
    RatingBar rt_ratingbar;
    MemoDto memoDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_memo);

        ivPhoto = findViewById(R.id.iv_showphoto);
        tvMemo = findViewById(R.id.tvMemo);
        tv_CafeName = findViewById(R.id.tv_CafeName);
        tv_Location = findViewById(R.id.tv_Location);
        rt_ratingbar = findViewById(R.id.rt_ratingbar);
//        MainActivity 에서 전달 받은 _id 값을 사용하여 DB 레코드를 가져온 후 ImageView 와 TextView 설정
        String id = String.valueOf(getIntent().getSerializableExtra("_id"));
        helper = new MemoDBHelper(this);
        memoDto = findRecord(id);

    }

    protected void setDto(){
        tvMemo.setText(memoDto.getMemo());
        tv_CafeName.setText(memoDto.getCafeName());
        tv_Location.setText(memoDto.getLocation());
        rt_ratingbar.setRating(Float.valueOf(memoDto.getRatingbar()));

        setPic();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        setDto();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDto();
    }

    /*사진의 크기를 ImageView에서 표시할 수 있는 크기로 변경*/
    private void setPic() {
        // Get the dimensions of the View
        int targetW = 180;
        int targetH = 180;

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
            String location = null;
            String cafeName = null;
            String rate = null;
            String date = null;

            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(helper.ID));
                photoPath = cursor.getString(cursor.getColumnIndex(helper.PATH));
                memo = cursor.getString(cursor.getColumnIndex(helper.MEMO));
                date = cursor.getString(cursor.getColumnIndex(helper.DATE));
                location = cursor.getString(cursor.getColumnIndex(helper.LOCATION));
                cafeName = cursor.getString(cursor.getColumnIndex(helper.CAFENAME));
                rate = cursor.getString(cursor.getColumnIndex(helper.RATINGBAR));
            }
            dto.set_id(id);
            dto.setMemo(memo);
            dto.setPhotoPath(photoPath);
            dto.setCafeName(cafeName);
            dto.setDate(date);
            dto.setLocation(location);
            dto.setRatingbar(rate);
        }
        return dto;
    }
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnClose:
                finish();
                break;

            case R.id.btn_update_Update:
                Intent intent = new Intent(ShowMemoActivity.this, UpdateActivity.class);

                intent.putExtra("id", memoDto.get_id());
                startActivityForResult(intent, UPDATE_CODE);
                finish();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CODE) {    // UpdateActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
                    setDto();
                    Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
