package ddwu.com.mobile.multimedia.photo.photocapturetest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    private final static int REQUEST_TAKE_THUMBNAIL = 100;
    private static final int REQUEST_TAKE_PHOTO = 200;

    private ImageView mImageView;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView);

//        외부 저장소 확인해보기
        Log.i(TAG, getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        Log.i(TAG, getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
    }


    public void onClick(View v)  {
        switch(v.getId()) {
            case R.id.btnCapture:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_THUMBNAIL);
                }
                break;
            case R.id.btnSave:
                dispatchTakePictureIntent();

                break;
        }
    }



    /*원본 사진 파일 저장*/
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){

            File photoFile = null;

            try{
                photoFile = createImageFile(); //이미지파일을 만드는 메소드
            }catch (IOException e){
                e.printStackTrace();
            }

            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(this,
                        "ddwu.com.mobile.multimedia.photo.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                //camera에게 fileprovider를 통해 이 전용path에 사진을 저장할 수 있도록
                //intent전달할때, uri도 같이 전달해줌줌
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
            }
        }

    }


    /*사진의 크기를 ImageView에서 표시할 수 있는 크기로 변경*/
    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

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
        mImageView.setImageBitmap(bitmap);
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
        mCurrentPhotoPath = image.getAbsolutePath();
        //카메라앱이 저장할 파일 path
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_THUMBNAIL && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extra.get("data");
            mImageView.setImageBitmap(imageBitmap);//사진의 해상도가 떨어짐
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
            //파일이 저장이 되면 mCurrentPhotoPath에 잘 저장이 되어있을거구 그걸 실행하면 사진이 올려질거
            //mnt->sdcard->android->data->패키지명->files->picture에 저장이됨
            //camera에게 fileprovider를 통해 이 전용path에 사진을 저장할 수 있도록
        }
    }
}
