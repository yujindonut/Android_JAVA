package ddwucom.mobileapplication.ma02_20181030.google_map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;

import ddwucom.mobileapplication.ma02_20181030.R;
import ddwucom.mobileapplication.ma02_20181030.record_memo.AddMemoActivity;

public class DetailActivity extends AppCompatActivity {

    final static String TAG = "DetailActivity";
    private static final int DETAIL_SUCCESS_CODE = 300;
    private PlacesClient placesClient;
    EditText etName;
    EditText etOpeningHours;
    EditText etAddress;
    ImageView imageView;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Places.initialize(getApplicationContext(), getResources().getString(R.string.api_key) );
        placesClient = Places.createClient(this);

        etName = findViewById(R.id.etName);
        etOpeningHours = findViewById(R.id.etOpeningHours);
        etAddress = findViewById(R.id.etAddress);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        place = intent.getParcelableExtra("place");

        if(place != null){
            OpeningHours openinghours = place.getOpeningHours();
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
            //사진 설정
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
            if(openinghours.toString() == null){
                etOpeningHours.setText("정보없음");
            }else{
                etOpeningHours.setText(openinghours.getWeekdayText().toString());
            }

            etAddress.setText(place.getAddress());
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                Toast.makeText(this, "Save Place information", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AddMemoActivity.class);
                intent.putExtra("place",place);
                startActivity(intent);

                finish();
                break;
            case R.id.btnClose:
                finish();
                setResult(RESULT_CANCELED);
                break;
        }
    }
}
