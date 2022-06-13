package ddwucom.mobile.finalreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    Restaurant restaurant;
    TextView signatureMenu;
    TextView restaurantName;
    RatingBar ratingBar;
    ImageView menu_image;
    TextView price;
    TextView location;
    RestaurantDBManager restaurantDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant"); // serizable객체가 반환이 되서 typecasting

        signatureMenu = findViewById(R.id.update_signature_menu);
        restaurantName = findViewById(R.id.update_restaurant_name);
        ratingBar = findViewById(R.id.update_ratingbar);
        price = findViewById(R.id.update_price);
        location = findViewById(R.id.update_location);
        menu_image = findViewById(R.id.update_foodpic);
        restaurantDBManager = new RestaurantDBManager(this);

        signatureMenu.setText((restaurant.getSignatureMenu()));
        restaurantName.setText((restaurant.getRestaurantName()));
        ratingBar.setRating(Float.parseFloat(restaurant.getRatingBar()));
        if("kyeri".equals(restaurant.getRestaurantName())){
            menu_image.setImageResource(R.mipmap.kyeri);
        } else if("salthouse".equals(restaurant.getRestaurantName())){
            menu_image.setImageResource(R.mipmap.salthouse);
        }else if("montan".equals(restaurant.getRestaurantName())){
            menu_image.setImageResource(R.mipmap.mongtan);
        }else if("oeuf".equals(restaurant.getRestaurantName())){
            menu_image.setImageResource(R.mipmap.oeuf);
        }else if("kitchen205".equals(restaurant.getRestaurantName())){
            menu_image.setImageResource(R.mipmap.kitchen205);
        }else if("uglybakery".equals(restaurant.getRestaurantName())){
            menu_image.setImageResource(R.mipmap.uglybakery);
        }else {
            menu_image.setImageResource(R.mipmap.unnamed);
        }
        price.setText((restaurant.getPrice()));
        location.setText((restaurant.getLocation()));
    }
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_update_update:
                String signature = signatureMenu.getText().toString();
                String name = restaurantName.getText().toString();
                String rating = String.valueOf(ratingBar.getRating());
                String price_ = price.getText().toString();
                String location_ = location.getText().toString();
                if(TextUtils.isEmpty(signature) || TextUtils.isEmpty(name)
                        || TextUtils.isEmpty(rating)){
                    Toast.makeText(this, "필수 항목이 입력이 안되었어요!", Toast.LENGTH_SHORT).show();
                }
                else{
                    restaurant.setSignatureMenu(signature);
                    restaurant.setRestaurantName(name);
                    restaurant.setRatingBar(rating);
                    restaurant.setPrice(price_);
                    restaurant.setLocation(location_);

                    if(restaurantDBManager.modifyRestaurant(restaurant)){
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("restaurant", restaurant);
                        setResult(RESULT_OK, resultIntent);
                    } else{
                        setResult(RESULT_CANCELED);
                    }
                }
                break;
            case R.id.btn_update_cancel:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();
    }
}
