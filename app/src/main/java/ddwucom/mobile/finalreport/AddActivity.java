package ddwucom.mobile.finalreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    final static String TAG = "ADDActivity";

    TextView signatureMenu;
    TextView restaurantName;
    RatingBar ratingBar;
    ImageView menu_image;
    TextView price;
    TextView location;
    Restaurant restaurant;
    RestaurantDBManager restaurantDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        signatureMenu = findViewById(R.id.add_signature_menu);
        restaurantName = findViewById(R.id.add_restaurant_name);
        ratingBar = findViewById(R.id.add_ratingbar);
        price = findViewById(R.id.add_price);
        location = findViewById(R.id.add_location);
        menu_image = findViewById(R.id.add_foodpic);
        menu_image.setImageResource(R.mipmap.unnamed);
        restaurantDBManager = new RestaurantDBManager(this);
        Log.d(TAG,"oncreate");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_add:
                String signature = signatureMenu.getText().toString();
                String name = restaurantName.getText().toString();
                String rating = String.valueOf(ratingBar.getRating());
                if(TextUtils.isEmpty(signature) || TextUtils.isEmpty(name)
                        || TextUtils.isEmpty(rating)){
                    Toast.makeText(this, "필수 항목이 입력이 안되었어요!", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean result = restaurantDBManager.addNewRestaurant(
                            new Restaurant(signature, name, rating,
                                    price.getText().toString(), location.getText().toString())
                    );
                    if (result) {    // 정상수행에 따른 처리
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Restaurant", signatureMenu.getText().toString());
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                }
                break;
            case R.id.btn_add_cancel:   // 취소에 따른 처리
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}