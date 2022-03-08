package ddwucom.mobileapplication.ma02_20181030.record_memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ddwucom.mobileapplication.ma02_20181030.R;

public class UpdateActivity extends AppCompatActivity {

    final static String TAG = "UpdateActivity";
    private static final int REQUEST_TAKE_PHOTO = 200;

    private String PhotoPath;

    int click_flag = 0;

    ImageView iv_update_Photo;
    EditText et_update_CafeName;
    EditText et_update_Location;
    RatingBar update_ratingbar;
    EditText et_update_Memo;

    MemoDBHelper helper;
    MemoDto memoDto;

    File photoFile = null;
    String id;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        helper = new MemoDBHelper(this);
        intent = getIntent();
        id = String.valueOf(getIntent().getSerializableExtra("id"));
        memoDto = findRecord(id);

        iv_update_Photo = (ImageView)findViewById(R.id.iv_updatephoto);
        et_update_Memo = (EditText)findViewById(R.id.et_update_Memo);
        et_update_CafeName = (EditText)findViewById(R.id.et_update_CafeName);
        et_update_Location = (EditText)findViewById(R.id.et_update_LocationName);
        update_ratingbar = findViewById(R.id.update_ratingbar);

        et_update_Memo.setText(memoDto.getMemo());
        et_update_CafeName.setText(memoDto.getCafeName());
        et_update_Location.setText(memoDto.getLocation());
        update_ratingbar.setRating(Float.valueOf(memoDto.getRatingbar()));
        setPic(memoDto.getPhotoPath());

        iv_update_Photo.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    외부 카메라 호출
                    dispatchTakePictureIntent();
                    click_flag = 1;
                    return true;
                }
                return false;
            }
        });
    }

    /*사진의 크기를 ImageView에서 표시할 수 있는 크기로 변경*/
    private void setPic(String mCurrentPhotoPath) {
        // Get the dimensions of the View
        int targetW = 150;
        int targetH = 150;

        Log.d("width", String.valueOf(targetW));

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
        iv_update_Photo.setImageBitmap(bitmap);
    }

    public MemoDto findRecord(String _id) {
        //			DB 검색 작업 수행
        MemoDto dto = new MemoDto();
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

    public void savePhoto() {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues row = new ContentValues();
        if(click_flag == 1){
            row.put(MemoDBHelper.PATH, PhotoPath);//사진을 다시 저장했으면 새로운 사진으로
        } else{
            row.put(MemoDBHelper.PATH, memoDto.getPhotoPath()); //저장안했으면 원래 있던 사진으로
        }
        Log.d("ADD", "path : " + PhotoPath);
        row.put(MemoDBHelper.DATE, new SimpleDateFormat("yyyyMMdd").format(new Date()));//날짜
        row.put(MemoDBHelper.CAFENAME, et_update_CafeName.getText().toString());//이름
        row.put(MemoDBHelper.LOCATION, et_update_Location.getText().toString());//위치
        row.put(MemoDBHelper.RATINGBAR, Float.toString(update_ratingbar.getRating()));//rate
        row.put(MemoDBHelper.MEMO, et_update_Memo.getText().toString());//메모

        String whereClause = MemoDBHelper.ID + "=?";
        String[] whereArgs = new String[]{id};
        int result = db.update(MemoDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        helper.close();
        Log.d("result", String.valueOf(result));
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_update_Update:
//                DB에 촬영한 사진의 파일 경로 및 메모 저장
                savePhoto();
                helper.close();
                setResult(RESULT_OK);
                finish();
                Toast.makeText(this, "Update!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update_Cancel:
                photoFile.delete();
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
    /*원본 사진 파일 저장*/
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){

            try{
                photoFile = createImageFile(); //이미지파일을 만드는 메소드
            }catch (IOException e){
                e.printStackTrace();
            }

            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(this,
                        "ddwucom.mobileapplication.ma02_20181030.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                //camera에게 fileprovider를 통해 이 전용path에 사진을 저장할 수 있도록
                //intent전달할때, uri도 같이 전달해줌줌
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
            }
        }

    }
    /*현재 시간 정보를 사용하여 파일 정보 생성*/
    //내 전용 앱 내부 저장소 파일경로가 mCurrentPhotoPath
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        PhotoPath = image.getAbsolutePath();
        Log.d("ADD", PhotoPath);
        //카메라앱이 저장할 파일 path
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic(PhotoPath);
            //파일이 저장이 되면 mCurrentPhotoPath에 잘 저장이 되어있을거구 그걸 실행하면 사진이 올려질거
            //mnt->sdcard->android->data->패키지명->files->picture에 저장이됨
            //camera에게 fileprovider를 통해 이 전용path에 사진을 저장할 수 있도록
        }
    }

}