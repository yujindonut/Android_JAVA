package mobile.example.lbs.geocodingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static int MY_PERMISSIONS_REQ_LOC = 100;
    private AddressResultReceiver addressResultReceiver;
    private LatLngResultReceiver latLngResultReceiver;

    /*UI*/
    EditText etLat;
    EditText etLng;
    EditText etAddress;

    private LocationManager locManager;
    private String bestProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLat = findViewById(R.id.etLat);
        etLng = findViewById(R.id.etLon);
        etAddress = findViewById(R.id.etAddress);

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        IntentService가 생성하는 결과 수신용 ResultReceiver
        bestProvider = LocationManager.GPS_PROVIDER;

        addressResultReceiver = new AddressResultReceiver(new Handler());
        latLngResultReceiver = new LatLngResultReceiver(new Handler());

    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnFindAddress:
                if (etLat.getText().toString().equals("")) etLat.setText(etLat.getHint());
                if (etLng.getText().toString().equals("")) etLng.setText(etLng.getHint());
                startAddressService();
                break;
            case R.id.btnLatLon:
                if (etAddress.getText().toString().equals("")) etAddress.setText(etAddress.getHint());
                startLatLngService();
                break;
            case R.id.btnClear:
                etAddress.setText("");
                etLat.setText("");
                etLng.setText("");
        }
    }
    private void getLastLocation() {
        if (checkPermission()) { //실행중에 위치확인하는 permission필요 - dialog뜸
            Location lastLocation = locManager.getLastKnownLocation(bestProvider);
            //마지막 위치를 가져오는 메소드 - 이전 실행 시에 알아냈던 최종위치 반환
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();
//			tvDisplay.setText("위도: " + latitude + " 경도: " + longitude);
        }
    }

    /* 위도/경도 → 주소 변환 IntentService 실행 */
    private void startAddressService() {
        double latitude = 0;
        double longitude = 0;
        if (checkPermission()) { //실행중에 위치확인하는 permission필요 - dialog뜸
            Location lastLocation = locManager.getLastKnownLocation(bestProvider);
            //마지막 위치를 가져오는 메소드 - 이전 실행 시에 알아냈던 최종위치 반환
            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
        }
        Intent intent = new Intent(this, FetchAddressIntentService.class);
//        String latitude = etLat.getText().toString();
//        String longitude = etLng.getText().toString();
        intent.putExtra(Constants.RECEIVER, addressResultReceiver);
        intent.putExtra(Constants.LAT_DATA_EXTRA, latitude);
        intent.putExtra(Constants.LNG_DATA_EXTRA, Double.valueOf(longitude));
        startService(intent);
    }

    /* 주소 → 위도/경도 변환 IntentService 실행 */
    private void startLatLngService() {
        String address = etAddress.getText().toString();
        Intent intent = new Intent(this, FetchLatLngIntentService.class);
        intent.putExtra(Constants.RECEIVER, latLngResultReceiver);
        intent.putExtra(Constants.ADDRESS_DATA_EXTRA, address);
        startService(intent);
    }
    /* 위도/경도 → 주소 변환 ResultReceiver */
//    recieve.send()이게 되면 onReceiveResult가 작동하게 됨
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String addressOutput = null;
            if (resultCode == Constants.SUCCESS_RESULT) {
                if (resultData == null) return;
                addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
                if (addressOutput == null) addressOutput = "";
                etAddress.setText(addressOutput);
            }
        }
    }
    /* 주소 → 위도/경도 변환 ResultReceiver */
    class LatLngResultReceiver extends ResultReceiver {
        public LatLngResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            String lat;
            String lng;
            ArrayList<LatLng> latLngList = null;

            if (resultCode == Constants.SUCCESS_RESULT) {
                if (resultData == null) return;
                latLngList = (ArrayList<LatLng>) resultData.getSerializable(Constants.RESULT_DATA_KEY);
                if (latLngList == null) {
                    lat = (String) etLat.getHint();
                    lng = (String) etLng.getHint();
                } else {
                    LatLng latlng = latLngList.get(0);
                    lat = String.valueOf(latlng.latitude);
                    lng = String.valueOf(latlng.longitude);
                }

                etLat.setText(lat);
                etLng.setText(lng);
            } else {
                etLat.setText(getString(R.string.no_address_found));
                etLng.setText(getString(R.string.no_address_found));
            }
        }
    }

    /*위치 관련 권한 확인 메소드 - 필요한 부분이 여러 곳이므로 메소드로 구성*/
    /*ACCESS_FINE_LOCATION - 상세 위치 확인에 필요한 권한
    ACCESS_COARSE_LOCATION - 대략적 위치 확인에 필요한 권한*/
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQ_LOC);
                return false;
            } else
                return true;
        }
        return false;
    }
}
