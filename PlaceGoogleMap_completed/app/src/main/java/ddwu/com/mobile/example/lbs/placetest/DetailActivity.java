package ddwu.com.mobile.example.lbs.placetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    final static String TAG = "DetailActivity";

    EditText etName;
    EditText etPhone;
    EditText etAddress;
    ImageView imageView;

    String placeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        placesClient = Places.createClient(this);

        Intent intent = getIntent();

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        imageView = findViewById(R.id.imageView);

        etName.setText(intent.getStringExtra("name"));
        etPhone.setText(intent.getStringExtra("phone") == null ? "정보 없음" : intent.getStringExtra("phone"));
        etAddress.setText(intent.getStringExtra("address"));
        placeId = intent.getStringExtra("place_id");

        setImage();

    }

    private PlacesClient placesClient;


//  Place ID 에 해당하는 사진이 있을 경우 가져옴
    private void setImage() {

        final List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);
        final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);

        placesClient.fetchPlace(placeRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse response) {
                final Place place = response.getPlace();

                final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
                if (metadata == null || metadata.isEmpty()) {
                    Log.w(TAG, "No photo metadata.");
                    return;
                }

                final PhotoMetadata photoMetadata = metadata.get(0);

                final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                        .setMaxWidth(500) // Optional.
                        .setMaxHeight(300) // Optional.
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener(new OnSuccessListener<FetchPhotoResponse>() {
                    @Override
                    public void onSuccess(FetchPhotoResponse fetchPhotoResponse) {
                        Bitmap imgBitmap = fetchPhotoResponse.getBitmap();
                        imageView.setImageBitmap(imgBitmap);
                        Log.d(TAG, "photo!!!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "no photo");
                    }
                });
            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                Toast.makeText(this, "Save Place information", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnClose:
                finish();
                break;
        }
    }
}
