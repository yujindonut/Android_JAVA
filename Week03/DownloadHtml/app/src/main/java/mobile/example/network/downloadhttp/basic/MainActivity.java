package mobile.example.network.downloadhttp.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity {

	EditText etUrl;
	TextView tvResult;
	ImageView imageView;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        etUrl = findViewById(R.id.etUrl);
        tvResult = findViewById(R.id.tvResult);
        imageView = findViewById(R.id.imageView);

/*        네트워크 제약 사항 적용을 해제하기 위해 사용
		실습 시 테스트용으로만 사용하며 추후 스레드 또는 AsyncTask 사용 방법으로 대체할 것		*/
		StrictMode.ThreadPolicy pol
				= new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
		StrictMode.setThreadPolicy(pol);

	}

	public void onClick(View v) {

		String address = etUrl.getText().toString();
//		String address = getResources().getString(R.string.image_url);

//		네트워크 사용 가능 여부 확인
		if (!isOnline()) {
			Toast.makeText(this, "Network is not available!", Toast.LENGTH_SHORT).show();
			return;
		}

		switch (v.getId()) {
			case R.id.btnDownload:
				if (!address.equals("")) {
					String result = downloadContents(address);
					tvResult.setText(result);
				}
				break;
			case R.id.btnImgDownload:
//				이미지를 다운로드 한 후 readStreamToBitmap() 호출
				String imageAddress = getResources().getString(R.string.image_url);
				Bitmap bitmap = downloadImage(imageAddress);
				imageView.setImageBitmap(bitmap);
				break;
		}

	}


//	네트워크 사용 가능 여부 확인
	private boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	private Bitmap downloadImage(String address){
		HttpsURLConnection conn = null;
		InputStream stream = null;
		Bitmap result = null;
		int responseCode = 200;

		try {
			//연결에 대한 객체를 만들어온다
			URL url = new URL(address);
			conn = (HttpsURLConnection)url.openConnection();

			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			conn.connect();

			responseCode = conn.getResponseCode(); //conn.connect()가 생략되어도 얘가 그 역할을 대신 해줌
			if (responseCode != HttpsURLConnection.HTTP_OK) {
				throw new IOException("HTTP error code: " + responseCode);
			}

			stream = conn.getInputStream();
			result = readStreamToBitmap(stream);	//문자열로 만들어주는 메소드
			// 이미지일 경우 readStreamToBitmap 사용
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

	private Bitmap readStreamToBitmap(InputStream stream) {
		return BitmapFactory.decodeStream(stream);
	}
//	문자열 형태의 웹 주소를 입력으로 서버 응답을 문자열로 만들어 반환
	private String downloadContents(String address){
		// 이미지일 경우 Bitmap을 반환하는 메소드가 되어야함
		HttpsURLConnection conn = null;
		InputStream stream = null;
		String result = null;
		int responseCode = 200;

		try {
			//연결에 대한 객체를 만들어온다
			URL url = new URL(address);
			conn = (HttpsURLConnection)url.openConnection();
			//url형식이 잘못되었다는 것//서버연결 설정 - MalformedURLException

			conn.setReadTimeout(5000);//읽기 타임아웃 지정 - SocketTimeoutException
			conn.setConnectTimeout(5000);//연결 타임아웃 지정 - SocketTimeoutException
			conn.setRequestMethod("GET");//서버에 GET방식으로 연결하겠다
			conn.setDoInput(true);//INPUTstream으로 데이터를 받아오겠다 // 서버응답지정

			conn.connect();//네트워크 트래픽 발생 & 데이터를 가지고 오는 // 통신링크 열기 -트래픽발생
			//통신링크 열기
			responseCode = conn.getResponseCode(); //conn.connect()가 생략되어도 얘가 그 역할을 대신 해줌
			//서버전송 및 응답 결과 수신
			if (responseCode != HttpsURLConnection.HTTP_OK) { //정상적으로 값을 받아온게 아니면
				throw new IOException("HTTP error code: " + responseCode);
			}

			stream = conn.getInputStream(); //응답결과 스트림 확인
			result = readStream(stream);	//문자열로 만들어주는 메소드
			// 이미지일 경우 readStreamToBitmap 사용
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
