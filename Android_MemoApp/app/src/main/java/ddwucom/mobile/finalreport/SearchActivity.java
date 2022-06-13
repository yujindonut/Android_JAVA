package ddwucom.mobile.finalreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static String TAG = "SearchActivity";
    TextView tvDisplay;
    TextView restaurantName;
    RestaurantDBManager restaurantDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tvDisplay = findViewById(R.id.tvDisplay);
        restaurantName = findViewById(R.id.search_restaurant_name);
        restaurantDBManager = new RestaurantDBManager(this);
    }

    public void onClick(View v) {
        String result = "";
        String restaurant = restaurantName.getText().toString();
        switch (v.getId()) {
            case R.id.btn_search:
                Restaurant search = restaurantDBManager.getRestaurant(restaurant);
                Log.d(TAG, String.valueOf(search));
                if(search != null){
                    result += restaurant + "의 자세한 정보를 소개해드릴게요!\n";
                    result += "시그니처 메뉴는 " + search.getSignatureMenu() + "입니다\n";
                    result += "유진슐랭 평점은 " + search.getRatingBar()+ "점 입니다\n";
                    if (Float.parseFloat(search.getRatingBar()) == 5) {
                        result += "유진슐랭 별이 5개라니 죽기전에는 꼭 가야합니다!\n";
                    } else if (Float.parseFloat(search.getRatingBar()) >= 3.5){
                        result += "유진슐랭 별을 이정도로 주다니, 엄청난 맛집인가보군요!\n";
                    }
                    else {
                        result += "유진슐랭은 까다롭게 점수가 매겨집니다.\n " +
                                "평점이 낮더라도 갈 이유가 충분합니다.\n";
                    }
                    result += "위치는 " + search.getLocation() + "이구요.\n ";
                    result += search.getSignatureMenu() +"의 가격은 " + search.getPrice() + "입니다\n ";
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("Restaurant", search.getSignatureMenu());
                    setResult(RESULT_OK, resultIntent);
                }
                else{
                    result = "찾는 음식점 정보가 없습니다~ㅠㅠ \n 더 분발하도록 하겠습니다~";
                    setResult(RESULT_CANCELED);
                }
                tvDisplay.setText(result);
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}