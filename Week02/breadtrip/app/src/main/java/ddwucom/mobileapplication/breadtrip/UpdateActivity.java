package ddwucom.mobileapplication.breadtrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText etBakeryName;
    EditText etLocation;
    RatingBar rtRate;
    BakeryDTO bakeryDTO;
    BakeryDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        bakeryDTO = (BakeryDTO) getIntent().getSerializableExtra("updateDto");
        etBakeryName = findViewById(R.id.update_bakery_name);
        etLocation = findViewById(R.id.update_bakery_location);
        rtRate = findViewById(R.id.update_ratingbar);

        helper = new BakeryDBHelper(this);

        etBakeryName.setText(bakeryDTO.getBakery());
        etLocation.setText(bakeryDTO.getLocation());
        rtRate.setRating(Float.parseFloat(bakeryDTO.getRate()));

    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_update_update:
                String bakery_name = etBakeryName.getText().toString();
                String bakery_location = etLocation.getText().toString();
                String rating = String.valueOf(rtRate.getRating());

                if(TextUtils.isEmpty(bakery_name) || TextUtils.isEmpty(bakery_location)
                        || TextUtils.isEmpty(rating)){
                    Toast.makeText(this, "필수 항목이 입력이 안되었어요!", Toast.LENGTH_SHORT).show();
                }
                else {
                    SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                    ContentValues row = new ContentValues();
                    row.put(BakeryDBHelper.COL_BAKERY, etBakeryName.getText().toString());
                    row.put(BakeryDBHelper.COL_LOCATION,etLocation.getText().toString());
                    row.put(BakeryDBHelper.COL_RATINGBAR,rating);

                    String whereClause = BakeryDBHelper.COL_ID + "=?";
                    String[] whereArgs = new String[]{String.valueOf(bakeryDTO.getId())};
                    int result = sqLiteDatabase.update(BakeryDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                    helper.close();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("result", result);
                    setResult(RESULT_OK, resultIntent);
                }
                break;
            case R.id.btn_update_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        finish();
    }
}