package ddwu.mobile.network.sample.openapi_with_file;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import ddwu.mobile.network.sample.openapi_with_file.R;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    EditText etTarget;
    ListView lvList;
    String apiAddress;

    String query;//입력받을 단어

    MyBookAdapter adapter;
    ArrayList<NaverBookDto> resultList;
    NaverBookXmlParser parser;
    NaverNetworkManager networkManager;
    ImageFileManager imgFileManager;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTarget = findViewById(R.id.etTarget);
        lvList = findViewById(R.id.lvList);

        resultList = new ArrayList();
        adapter = new MyBookAdapter(this, R.layout.listview_book, resultList);
        //현재는 빈 resultList
        lvList.setAdapter(adapter);

        apiAddress = getResources().getString(R.string.api_url);
        parser = new NaverBookXmlParser();
        networkManager = new NaverNetworkManager(this);
        networkManager.setClientId(getResources().getString(R.string.client_id));
        networkManager.setClientSecret(getResources().getString(R.string.client_secret));
        //clientSecret은 검색api에서만 사용한다.
        imgFileManager = new ImageFileManager(this);

        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                /* 작성할 부분 */
                /*롱클릭한 항목의 이미지 주소를 가져와 내부 메모리에 지정한 이미지 파일을 외부저장소로 이동
                * ImageFileManager 의 이동 기능 사용
                * 이동을 성공할 경우 파일 명, 실패했을 경우 null 을 반환하므로 해당 값에 따라 Toast 출력*/
                NaverBookDto naverBookDto = resultList.get(pos);
                Log.d(TAG,naverBookDto.getTitle());
                String imageUrl = naverBookDto.getImageLink();
                String fileName = imgFileManager.moveFileToExt(imageUrl);
                Log.d(TAG,imageUrl);
                if(fileName == null){
                    Log.d(TAG, "실패!");
                }
                else {
                    Log.d(TAG, "내부 파일을 외부파일로 옮겼습니다!");
                }
                return true;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 임시 파일 삭제
        imgFileManager.clearTemporaryFiles();
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                query = etTarget.getText().toString();  // UTF-8 인코딩 필요
                // OpenAPI 주소와 query 조합 후 서버에서 데이터를 가져옴
                // 가져온 데이터는 파싱 수행 후 어댑터에 설정
                try{
                    new NetworkAsyncTask().execute(apiAddress + URLEncoder.encode(query,"UTF-8"));
                    //query가 한글일 경우
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }

                break;
        }
    }


    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //별도의 thread가 진행되기전에 dialog가 뜸
            progressDlg = ProgressDialog.show(MainActivity.this, "Wait", "Downloading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = null;
            // networking
            result = networkManager.downloadContents(address);
//            result = networkManager.download(address,false);
            //xml결과 나온다
            if(result == null) return "error!";
            Log.d(TAG, result);

            // parsing 을 이곳에서 수행할 경우 AsyncTask의 반환타입을 적절히 변경
            // parsing - 수행시간이 짧을 경우 이 부분에서 수행하는 것을 고려
            resultList = parser.parse(result);

            //adapter에 연결하기 전에 이미지 파일을 다 가져와서 loading함
            //이미지가 모두 가져온 상태이지만 처음 로딩이 느림
            for (NaverBookDto dto : resultList) {

                Bitmap bitmap = networkManager.downloadImage(dto.getImageLink());
                if(bitmap != null) imgFileManager.saveBitmapToTemporary(bitmap, dto.getImageLink());
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {

            adapter.setList(resultList);    // Adapter 에 결과 List 를 설정 후 notify
            //setList안에서 notifySetChanged()까지! 처음에 빈 resultList를 채워줌
            progressDlg.dismiss();
        }

    }

}
