package ddwu.mobile.week6.basicfiletest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    final static String IN_FILE_NAME = "infile.txt";
    final static String EXT_FILE_NAME = "extfile.txt";
    final static String CACHE_FILE_NAME = "cachefile.txt";

    EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = findViewById(R.id.etText);

        SharedPreferences pref = getSharedPreferences("config", 0);
        etText.setText(pref.getString("text_data",""));
    }
//액티비티가 화면에서 사라질 때 작업을 수행하기 위해서
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences pref = getSharedPreferences("config", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("text_data", etText.getText().toString());
        editor.commit();//저장
    }

    public void onInClick(View v) {
        switch(v.getId()) {
            case R.id.btnInWrite:
                String data = etText.getText().toString();
                File saveFile = new File(getFilesDir(), IN_FILE_NAME);
                try {
//                    FileOutputStream fos = new FileOutputStream(saveFile);
                    FileOutputStream fos = openFileOutput(IN_FILE_NAME, Context.MODE_APPEND);
                    fos.write(data.getBytes());
                    fos.flush();
                    fos.close();
                } catch(IOException e) {e.printStackTrace();}

                break;
            case R.id.btnInRead:
                String path = getFilesDir() + "/" + IN_FILE_NAME;
                File readFile = new File(path);
                try{
                    FileReader fileReader = new FileReader(readFile);
                    BufferedReader br = new BufferedReader(fileReader);
                    String line = "";
                    //변수를 두어서 추가하는 방법
                    String result = "";
                    while((line = br.readLine()) != null){
                        result += line;
                    }
                    etText.setText(result);
                    br.close();
                } catch (IOException e) {e.printStackTrace();}

                break;
            case R.id.btnInDelete:
//                File inFiles = getFilesDir();
                File inFiles = new File(getFilesDir() + "/" + IN_FILE_NAME);
                File[] files = inFiles.listFiles();
                for(File target : files) {
                    target.delete();
                }
                break;
        }
    }
//외부저장소
    public void onExtClick(View v) {
        switch (v.getId()) {
            case R.id.btnExtWrite:
                if(isExternalStorageWritable()){
                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum");
                    if(!file.mkdirs()){ //이미 실행한번이라도 하면 false출력됨
                        Log.d(TAG, "directory not created");
                    }
                    String data = etText.getText().toString();
                    File saveFile = new File(file.getPath(), EXT_FILE_NAME);
                    try {
                        FileOutputStream fos = new FileOutputStream(saveFile);
                        fos.write(data.getBytes());
                        fos.flush();
                        fos.close();
                    } catch(IOException e) {e.printStackTrace();}

                }
                break;
            case R.id.btnExtRead:
                if(isExternalStorageWritable()){
                    String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/myalbum/" + EXT_FILE_NAME;
                    File readFile = new File(path);
                    try{
                        FileReader fileReader = new FileReader(readFile);
                        BufferedReader br = new BufferedReader(fileReader);
                        String line = "";
                        //변수를 두어서 추가하는 방법
                        String result = "";
                        while((line = br.readLine()) != null){
                            result += line;
                        }
                        etText.setText(result);
                        br.close();
                    } catch (IOException e) {e.printStackTrace();}

                }

                break;
            case R.id.btnExtDelete:
                File inFiles = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/myalbum/" + EXT_FILE_NAME);
                File[] files = inFiles.listFiles();
                for(File target : files) {
                    target.delete();
                }
                break;
        }
    }

    public void onCacheClick(View v) {
        switch(v.getId()) {
            case R.id.btnCacheWrite:
                //내부
                String data = etText.getText().toString();
                File saveFile = new File(getCacheDir(), CACHE_FILE_NAME);
                try {
                    FileOutputStream fos = new FileOutputStream(saveFile);
//                    FileOutputStream fos = openFileOutput(CACHE_FILE_NAME, Context.MODE_APPEND);
                    fos.write(data.getBytes());
                    fos.flush();
                    fos.close();
                } catch(IOException e) {e.printStackTrace();}

//                getCacheDir();
                //외부
//                getExternalCacheDir();
                break;
            case R.id.btnCacheRead:
                String path = getFilesDir() + "/" + CACHE_FILE_NAME;
                File readFile = new File(path);
                try{
                    FileReader fileReader = new FileReader(readFile);
                    BufferedReader br = new BufferedReader(fileReader);
                    String line = "";
                    //변수를 두어서 추가하는 방법
                    String result = "";
                    while((line = br.readLine()) != null){
                        result += line;
                    }
                    etText.setText(result);
                    br.close();
                } catch (IOException e) {e.printStackTrace();}

                break;
            case R.id.btnCacheDelete:
                File inFiles = new File(getFilesDir() + "/" + CACHE_FILE_NAME);
                File[] files = inFiles.listFiles();
                for(File target : files) {
                    target.delete();
                }
                break;
        }
    }
    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }
}