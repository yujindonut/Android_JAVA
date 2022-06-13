package ddwu.com.mobile.example.lbs.placetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    final static String TAG = "MainActivity";
    final static int PERMISSION_REQ_CODE = 100;

    /*UI*/
    private EditText etKeyword;
    private GoogleMap mGoogleMap;
    private MarkerOptions markerOptions;
    /*DATA*/
    private PlacesClient placesClient;
    Bitmap bitmap = null;
    Location myLocation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etKeyword = findViewById(R.id.etKeyword);

        mapLoad();

        Places.initialize(getApplicationContext(), getResources().getString(R.string.api_key) );
        placesClient = Places.createClient(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:

                if (etKeyword.getText().toString().equals("cafe")) {
                    searchStart(PlaceType.CAFE);
                } else if (etKeyword.getText().toString().equals("restaurant")) {
                    searchStart(PlaceType.RESTAURANT);
                }
                else if (etKeyword.getText().toString().equals("park")) {
                    searchStart(PlaceType.PARK);
                }

                break;
        }
    }

    
    /*입력된 유형의 주변 정보를 검색*/
    private void searchStart(String type) {
        new NRPlaces.Builder().listener(placesListener)
                .key(getResources().getString(R.string.api_key))
                .latlng(Double.valueOf(getResources().getString(R.string.init_lat)),
                        Double.valueOf( getResources().getString(R.string.init_lng)))
                .radius(49999)
                .type(type)
                .build()
                .execute();
    }


    /*Place ID 의 장소에 대한 세부정보 획득*/
    private void getPlaceDetail(String placeId) {
        //field : Google api의 field
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHONE_NUMBER,
                Place.Field.ADDRESS, Place.Field.PHOTO_METADATAS);
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();

        placesClient.fetchPlace(request).addOnSuccessListener(
                new OnSuccessListener<FetchPlaceResponse>() {

                    @Override
                    public void onSuccess(FetchPlaceResponse response) {
                        Place place = response.getPlace();//placeFileds의 요청 정보가 들어있음

                        Log.d(TAG, "Place found " + place.getName());
                        Log.d(TAG, "Address: " + place.getAddress());
                        Log.d(TAG, "Phone: " + place.getPhoneNumber());

                        // Get the photo metadata.
                        final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
                        if (metadata == null || metadata.isEmpty()) {
                            Log.w(TAG, "No photo metadata.");
                            return;
                        }
                        final PhotoMetadata photoMetadata = metadata.get(0);

                        // Get the attribution text.
                        final String attributions = photoMetadata.getAttributions();
                        Log.d(TAG, "photo attribute: " + attributions);
                        // Create a FetchPhotoRequest.
                        final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                                .setMaxWidth(500) // Optional.
                                .setMaxHeight(300) // Optional.
                                .build();
                        placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                            bitmap = fetchPhotoResponse.getBitmap();
//                            imageView.setImageBitmap(bitmap);
                            callDetailActivity(place);
                        }).addOnFailureListener((exception) -> {
                            if (exception instanceof ApiException) {
                                final ApiException apiException = (ApiException) exception;
                                Log.e(TAG, "Place not found: " + exception.getMessage());
                                final int statusCode = apiException.getStatusCode();
                                // TODO: Handle error with given status code.
                            }
                        });
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ApiException apiException = (ApiException) e;
                        int statusCode = apiException.getStatusCode();
                        Log.e(TAG, "Place not found: " + statusCode + " " + e.getMessage());
                    }
                }
        );
    }


    private void callDetailActivity(Place place) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("name",place.getName());
                        intent.putExtra("phone",place.getPhoneNumber());
                        intent.putExtra("address",place.getAddress());
//                        photoMetadata보내기
                        intent.putExtra("photo",place.getPhotoMetadatas().get(0));
                        intent.putExtra("place",place);
                        intent.putExtra("image", bitmap);
        startActivity(intent);
    }


    PlacesListener placesListener = new PlacesListener() {

        @Override
        public void onPlacesFailure(PlacesException e) {

        }

        @Override
        public void onPlacesStart() {

        }

        @Override
        public void onPlacesSuccess(final List<noman.googleplaces.Place> places) {
            Log.d(TAG, "Adding Markers");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //마커추가
                    for(noman.googleplaces.Place place : places) {
                        markerOptions.title(place.getName());
                        markerOptions.position(new LatLng(place.getLatitude(), place.getLongitude()));
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                        Marker newMarker = mGoogleMap.addMarker(markerOptions);
                        newMarker.setTag(place.getPlaceId());
                        //이렇게 마커안에 넣어야지 꺼낼수있음 getTag로 google상세정보를 가져올 수 있음
                        Log.d(TAG, place.getName() + " " + place.getPlaceId());
                    }
                }
            });

        }

        @Override
        public void onPlacesFinished() {

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        markerOptions = new MarkerOptions();
        mGoogleMap = googleMap;

        Log.d(TAG,"Map ready");

        if(checkPermission()){
            mGoogleMap.setMyLocationEnabled(true);
        }
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mGoogleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                Toast.makeText(MainActivity.this, String.format("현재위치:(%f, %f)", location.getLatitude(), location.getLongitude()), Toast.LENGTH_SHORT).show();
            }
        });
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String placeId = marker.getTag().toString();
                getPlaceDetail(placeId);
            }
        });
    }


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




    /*구글이 현재 위치의 주변 정보를 확인하는 기본 방법 사용 메소드*/

/*
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
*/

}
