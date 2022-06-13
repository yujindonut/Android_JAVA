package ddwu.com.mobile.example.lbs.placetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    final static String TAG = "MainActivity";
    final static int PERMISSION_REQ_CODE = 100;

    /*UI*/
    private GoogleMap mGoogleMap;
    private MarkerOptions markerOptions;
    private EditText etKeyword;

    /*DATA*/

    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etKeyword = findViewById(R.id.etKeyword);

        mapLoad();

        // Places 초기화 및 클라이언트 생성
        Places.initialize(getApplicationContext(), getString(R.string.api_key));
        placesClient = Places.createClient(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Log.d(TAG, "Map ready");

        if (checkPermission())
            mGoogleMap.setMyLocationEnabled(true);

        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(MainActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mGoogleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                Toast.makeText(MainActivity.this,
                        String.format("현재 위치: (%f, %f)", location.getLatitude(), location.getLongitude()),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // TODO: 맵 로딩 후 초기에 해야 할 작업 구현
        markerOptions = new MarkerOptions();
//        mGeoDataClient = Places.getGeoDataClient(MainActivity.this);

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                String placeId = marker.getTag().toString();    // 마커의 setTag() 로 저장한 Place ID 확인
                getPlaceDetail(placeId);
            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:

                if (etKeyword.getText().toString().equals("카페")) {
                    searchStart(PlaceType.CAFE);
                } else if (etKeyword.getText().toString().equals("식당")) {
                    searchStart(PlaceType.RESTAURANT);
                }

                break;
        }
    }


    private void getPlaceDetail(String placeId) {
        List<Place.Field> placeFields       // 상세정보로 요청할 정보의 유형 지정
                = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHONE_NUMBER, Place.Field.ADDRESS);

        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();    // 요청 생성

        // 요청 처리 및 요청 성공/실패 리스너 지정
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override                    // 요청 성공 시 처리 리스너 연결
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {  // 요청 성공 시
                final Place place = fetchPlaceResponse.getPlace();
                Log.i(TAG, "Place found: " + place.getName());  // 장소 명 확인 등
                Log.i(TAG, "Phone: " + place.getPhoneNumber());
                Log.i(TAG, "Address: " + place.getAddress());
                Log.i(TAG, "ID: " + place.getId());
                callDetailActivity(place);
            }
        }).addOnFailureListener(new OnFailureListener() {   // 요청 실패 시 처리 리스너 연결
            @Override
            public void onFailure(@NonNull Exception exception) {   // 요청 실패 시
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    int statusCode = apiException.getStatusCode();  // 필요 시 확인
                    Log.e(TAG, "Place not found: " + exception.getMessage());
                }
            }
        });

    }


    private void callDetailActivity(Place place) {
        final Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        intent.putExtra("name",place.getName());
        intent.putExtra("phone",place.getPhoneNumber());
        intent.putExtra("address",place.getAddress());
        intent.putExtra("place_id", place.getId());

        startActivity(intent);
    }


    private void searchStart(String type) {
        new NRPlaces.Builder().listener(placesListener)
                .key(getString(R.string.api_key))
//                .latlng(Double.valueOf(getResources().getString(R.string.init_lat)), Double.valueOf(getResources().getString(R.string.init_lng)))   // 현재 위치값 사용 필요
                .latlng(mGoogleMap.getMyLocation().getLatitude(), mGoogleMap.getMyLocation().getLongitude())
                .radius(100)        // 상수 지정 필요
                .type(type)
                .build()
                .execute();
        mGoogleMap.clear();
    }



    PlacesListener placesListener = new PlacesListener() {
        @Override
        public void onPlacesSuccess(final List<noman.googleplaces.Place> places) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Adding markers");

                    for (noman.googleplaces.Place nPlace : places) {
                        markerOptions.title(nPlace.getName());
                        markerOptions.position(new LatLng(nPlace.getLatitude(), nPlace.getLongitude()));
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        Marker newMarker = mGoogleMap.addMarker(markerOptions);
                        newMarker.setTag(nPlace.getPlaceId());
                        Log.d(TAG, "ID: " + nPlace);
                    }
                }
            });
        }
        @Override
        public void onPlacesFailure(PlacesException e) {
            e.printStackTrace();
        }
        @Override
        public void onPlacesStart() {}
        @Override
        public void onPlacesFinished() {}
    };



    /*구글맵을 멤버변수로 로딩*/
    private void mapLoad() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);      // 매배변수 this: MainActivity 가 OnMapReadyCallback 을 구현하므로
    }


    /* 필요 permission 요청 */
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQ_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 퍼미션을 획득하였을 경우 맵 로딩 실행
                mapLoad();
            } else {
                // 퍼미션 미획득 시 액티비티 종료
                Toast.makeText(this, "앱 실행을 위해 권한 허용이 필요함", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }



    private void startCPSearch() {
        // Use fields to define the data types to return.
        List<Place.Field> placeFields = Collections.singletonList(Place.Field.ID);

        // Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

        // Call findCurrentPlace and handle the response (first check that the user has granted permission).

        if (checkPermission()) {
            Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
            placeResponse.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
                @Override
                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                    if (task.isSuccessful()){
                        FindCurrentPlaceResponse response = task.getResult();
                        for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
//                            Log.i(TAG, String.format("Place ID: %s", placeLikelihood.getPlace().getId()));
                            Log.i(TAG, String.format("Place '%s' has likelihood: %f",
                                    placeLikelihood.getPlace().getId(),
                                    placeLikelihood.getLikelihood()));
                        }
                    } else {
                        Exception exception = task.getException();
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                        }
                    }
                }
            });
        }
    }
}
