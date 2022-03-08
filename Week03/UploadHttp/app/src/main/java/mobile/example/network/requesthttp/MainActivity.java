package mobile.example.network.requesthttp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";

    EditText etDate;
    TextView tvResult;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDate = findViewById(R.id.etUrl);
        tvResult = findViewById(R.id.tvResult);

        StrictMode.ThreadPolicy pol
                = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(pol);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View v) {

        date = etDate.getText().toString();

        //		네트워크 사용 가능 여부 확인
        if (!isOnline()) {
            Toast.makeText(this, "Network is not available!", Toast.LENGTH_SHORT).show();
            return;
        }


        switch (v.getId()) {
            case R.id.btn_request:
                if (!date.equals("")) {
                    HashMap<String, String> paramMap = new HashMap<String, String>();

                    paramMap.put("key"          , "06466417fbd72a078f806ae2ddf52309");                        // 발급받은 인증키
                    paramMap.put("targetDt"     , date);  // 조회하고자 하는 날짜
                    paramMap.put("itemPerPage"  , "10");                            // 결과 ROW 의 개수( 최대 10개 )
                    paramMap.put("multiMovieYn" , "N");                             // Y:다양성 영화, N:상업영화, Default:전체
                    paramMap.put("repNationCd"  , "K");                             // K:한국영화, F:외국영화, Default:전체

                    String result = downloadContents(getResources().getString(R.string.url)+"?"+makeQueryString(paramMap));
                    tvResult.setText(result);
                }
                break;
        }
    }

    // Map -> QueryString
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String makeQueryString(Map<String, String> paramMap) {
        final StringBuilder sb = new StringBuilder();

        paramMap.entrySet().forEach(( entry )->{
            if( sb.length() > 0 ) { //맨처음에는 &안붙임
                sb.append('&');
            }
            sb.append(entry.getKey()).append('=').append(entry.getValue());
        });
        Log.d("usingMap",sb.toString());
        return sb.toString();
    }
    //	네트워크 사용 가능 여부 확인
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    //	문자열 형태의 웹 주소를 입력으로 서버 응답을 문자열로 만들어 반환
    private String downloadContents(String pURL){
        // 이미지일 경우 Bitmap을 반환하는 메소드가 되어야함
        //https인지 http인지 상위객체에 넣어주기 : http로 되어있는 url을 https객체에 넣어줘서 오류남

        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;
        int responseCode = 200;

        try {
            //연결에 대한 객체를 만들어온다
            URL url = new URL(pURL);
            conn = (HttpURLConnection)url.openConnection();

            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            //api에서 데이터를 가져오는 것 get
            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
            responseCode = conn.getResponseCode(); //conn.connect()가 생략되어도 얘가 그 역할을 대신 해줌
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            stream = conn.getInputStream();
            result = readStream(stream);	//문자열로 만들어주는 메소드
        } catch (MalformedURLException e) {
            Toast.makeText(this, "주소 오류", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkOnMainThreadException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try { stream.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
            if (conn != null) conn.disconnect();
        }

        return result;
    }


    public String readStream(InputStream stream){
        StringBuilder result = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                result.append(readLine + "\n");
                readLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}

