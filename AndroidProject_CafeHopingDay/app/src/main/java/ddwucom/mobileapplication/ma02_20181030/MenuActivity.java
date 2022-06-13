package ddwucom.mobileapplication.ma02_20181030;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ddwucom.mobileapplication.ma02_20181030.google_map.MapActivity;
import ddwucom.mobileapplication.ma02_20181030.naver_api.SearchActivity;
import ddwucom.mobileapplication.ma02_20181030.pedometer.PedometerActivity;
import ddwucom.mobileapplication.ma02_20181030.record_memo.RecordListActivity;

public class MenuActivity extends AppCompatActivity {

    public static final int MAP_REQUEST_CODE = 100;
    public static final int RECORD_REQUEST_CODE = 200;
    public static final int RESTUARANT_REQUEST_CODE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btn_map:
                intent = new Intent(this, MapActivity.class);
                startActivityForResult(intent, MAP_REQUEST_CODE);
                break;
            case R.id.btn_record:
                intent = new Intent(this, RecordListActivity.class);
                startActivityForResult(intent, RECORD_REQUEST_CODE);
                break;
            case R.id.btn_restuarant:
                intent = new Intent(this, SearchActivity.class);
                startActivityForResult(intent, RESTUARANT_REQUEST_CODE);
                break;
            case R.id.btn_Pedometer:
                intent = new Intent(this, PedometerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
