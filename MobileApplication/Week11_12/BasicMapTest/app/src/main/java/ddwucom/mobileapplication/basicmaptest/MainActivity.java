package ddwucom.mobileapplication.basicmaptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    final static int PERMISSION_REQ_CODE = 100;
    private GoogleMap mGoogleMap;
    private LocationManager locationManager;

    private AddressResultReceiver addressResultReceiver;

    ArrayList<Marker> marker_list;
    private Marker centerMarker;
    private PolylineOptions pOptions;
    private String addressOutput = null;
    private String bestProvider;

    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        addressResultReceiver = new AddressResultReceiver(new Handler());

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallBack);

        geocoder = new Geocoder(this, Locale.getDefault());

        bestProvider = LocationManager.GPS_PROVIDER;
        marker_list = new ArrayList<>();
        //선옵션 설정
        pOptions = new PolylineOptions();
        pOptions.color(Color.RED);
        pOptions.width(5);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnStart:
//                getLastLocation();
                locationUpdate();
                break;
            case R.id.btnStop:
                locationManager.removeUpdates(locationListener);
                break;
        }
    }
    private void locationUpdate() {
        if(checkPermission()){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,5,locationListener);
        }
    }
    private void getLastLocation() {
        if (checkPermission()) { //실행중에 위치확인하는 permission필요 - dialog뜸
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //마지막 위치를 가져오는 메소드 - 이전 실행 시에 알아냈던 최종위치 반환
            LatLng lastLoc = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            MarkerOptions options = new MarkerOptions();
            options.position(lastLoc);
            options.title(lastLoc.toString());
            options.snippet(addressOutput);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            Marker marker= mGoogleMap.addMarker(options);
            marker.setPosition(lastLoc);
            marker_list.add(marker);
        }
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,17));
            //animateCamera를 써서 경로가 이동이 된 것을 볼 수 있음4

            //이동중이면, marker가 이동
//            centerMarker.setPosition(currentLoc);
            
            //현재 위치를 선 그리기 위치로 지정, 처음일 경우 시작점
            pOptions.add(currentLoc);
            //선그리기 수행
            mGoogleMap.addPolyline(pOptions);
        }
    };
    OnMapReadyCallback mapReadyCallBack = new OnMapReadyCallback() {

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mGoogleMap = googleMap;
            LatLng currentLoc = new LatLng(37.606320, 127.041808);
            //xml에서 설정할 수도 있고, 코드상에서도 변경가능하다!
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,17));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc,17));

            //마커 위의 윈도우 클릭 이벤트 처리
            mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(@NonNull Marker marker) {

                    List<String> address = getAddress(marker.getPosition().latitude, marker.getPosition().longitude);
                    Toast.makeText(MainActivity.this, address.toString(),Toast.LENGTH_SHORT).show();
                }
            });
            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    String loc = String.format("위도:%f, 경도:%f",latLng.latitude, latLng.longitude);
                    Toast.makeText(MainActivity.this, loc,Toast.LENGTH_SHORT).show();
                }
            });
            //long클릭할 경우 -> 마커 추가
            mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(@NonNull LatLng latLng) {
                    startAddressService(latLng);
                    String loc = String.format("위도:%.6f, 경도:%.6f",latLng.latitude, latLng.longitude);
                    MarkerOptions options = new MarkerOptions();
                    options.position(latLng);
                    options.title(loc);
                    options.snippet(addressOutput);
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                    Marker marker= mGoogleMap.addMarker(options);
                    marker.setPosition(latLng);
                    marker_list.add(marker);
                    marker.showInfoWindow();

                    pOptions.add(latLng);
                    //선그리기 수행
                    mGoogleMap.addPolyline(pOptions);

                }
            });
        }
    };
    //    Geocoding
    private List<String> getAddress(double latitude, double longitude) {

        List<Address> addresses = null;
        ArrayList<String> addressFragments = null;

//        위도/경도에 해당하는 주소 정보를 Geocoder 에게 요청
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (addresses == null || addresses.size()  == 0) {
            return null;
        } else {
            Address addressList = addresses.get(0);
            addressFragments = new ArrayList<String>();

            for(int i = 0; i <= addressList.getMaxAddressLineIndex(); i++) {
                addressFragments.add(addressList.getAddressLine(i));
            }
        }

        return addressFragments;
    }

    /* 위도/경도 → 주소 변환 IntentService 실행 */
    private void startAddressService(@NonNull LatLng latLng) {
        double latitude = 0;
        double longitude = 0;
        if (checkPermission()) { //실행중에 위치확인하는 permission필요 - dialog뜸
//            Location lastLocation = locationManager.getLastKnownLocation(bestProvider);
            //마지막 위치를 가져오는 메소드 - 이전 실행 시에 알아냈던 최종위치 반환
            latitude = latLng.latitude;
            longitude = latLng.longitude;
        }
        Intent intent = new Intent(this, FetchAddressIntentService.class);
//        String latitude = etLat.getText().toString();
//        String longitude = etLng.getText().toString();
        intent.putExtra(Constants.RECEIVER, addressResultReceiver);
        intent.putExtra(Constants.LAT_DATA_EXTRA, latitude);
        intent.putExtra(Constants.LNG_DATA_EXTRA, Double.valueOf(longitude));
        startService(intent);
        //FetchAddressIntentService가 실행됨
    }
    /* 위도/경도 → 주소 변환 ResultReceiver */
//    recieve.send()이게 되면 onReceiveResult가 작동하게 됨
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == Constants.SUCCESS_RESULT) {
                if (resultData == null) return;
                addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
                Log.d("MainActivity", addressOutput);
                if (addressOutput == null) addressOutput = "";
            }
        }
    }
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQ_CODE);
                return false;
            }
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationUpdate();
            } else {
                Toast.makeText(this, "Permission required.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}