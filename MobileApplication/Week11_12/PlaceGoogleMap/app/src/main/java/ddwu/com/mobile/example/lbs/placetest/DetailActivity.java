package ddwu.com.mobile.example.lbs.placetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    final static String TAG = "DetailActivity";
    private PlacesClient placesClient;
    EditText etName;
    EditText etPhone;
    EditText etAddress;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Places.initialize(getApplicationContext(), getResources().getString(R.string.api_key) );
        placesClient = Places.createClient(this);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        Place place = intent.getParcelableExtra("place");
//        Bitmap bitmap = intent.getParcelableExtra("bitmap");
//        String name = intent.getExtras().getString("name");
        String phone;
        if(place.getPhoneNumber() == null)
            phone = "정보 없음";
        else
            phone = place.getPhoneNumber();

        // Get the photo metadata.
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
        placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
           Bitmap bitmap = fetchPhotoResponse.getBitmap();
                            imageView.setImageBitmap(bitmap);
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                final ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + exception.getMessage());
                final int statusCode = apiException.getStatusCode();
                // TODO: Handle error with given status code.
            }
        });
        Log.d(TAG, "photo attribute: " + place.getPhotoMetadatas().get(0).getAttributions());
        etName.setText(place.getName());
        etPhone.setText(phone);
        etAddress.setText(place.getAddress());
//        imageView.setImageBitmap(bitmap);
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
