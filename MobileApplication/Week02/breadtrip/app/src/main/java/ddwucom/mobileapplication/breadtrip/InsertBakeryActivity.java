package ddwucom.mobileapplication.breadtrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class InsertBakeryActivity extends AppCompatActivity {

    EditText etBakeryName;
    EditText etLocation;
    RatingBar rtRate;

    BakeryDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_bakery);

        etBakeryName = findViewById(R.id.insert_bakery_name);
        etLocation = findViewById(R.id.insert_location);
        rtRate = findViewById(R.id.insert_ratingbar);

        helper = new BakeryDBHelper(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert_add:
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues row = new ContentValues();
                row.put(BakeryDBHelper.COL_BAKERY, etBakeryName.getText().toString());
                row.put(BakeryDBHelper.COL_LOCATION, etLocation.getText().toString());
                row.put(BakeryDBHelper.COL_RATINGBAR, Float.toString(rtRate.getRating()));
//                Log.d("rate", Float.toString(rtRate.getRating()));
                long result = db.insert(BakeryDBHelper.TABLE_NAME, null, row);
//                Log.d("result", String.valueOf(result));
                helper.close();
                finish();
                break;
            case R.id.btn_insert_cancel:

                finish();
                break;
        }
    }
}