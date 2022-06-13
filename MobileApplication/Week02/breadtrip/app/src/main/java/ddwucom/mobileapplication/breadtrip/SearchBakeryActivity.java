package ddwucom.mobileapplication.breadtrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchBakeryActivity extends AppCompatActivity {

    TextView tvDisplay;
    EditText etSearchName;
    BakeryDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bakery);

        tvDisplay = findViewById(R.id.tvDisplay);
        etSearchName = findViewById(R.id.search_bakery_name);
        helper = new BakeryDBHelper(this);

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_search:
                //			DB 검색 작업 수행
                String result ="";
                SQLiteDatabase myDB = helper.getReadableDatabase(); //select
                String selection = helper.COL_BAKERY + "=?";
                String[] selectArgs = new String[]{etSearchName.getText().toString()};
                Cursor cursor =
                        myDB.query(helper.TABLE_NAME, null, selection, selectArgs,
                                null, null, null, null);
                if (cursor.getCount() > 0) {
                    long id;
                    String bakery_name = null;
                    String bakery_location = null;
                    float rating = 0;
                    while (cursor.moveToNext()) {
                        id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                        bakery_name = cursor.getString(cursor.getColumnIndex(helper.COL_BAKERY));
                        bakery_location = cursor.getString(cursor.getColumnIndex(helper.COL_LOCATION));
                        rating = Float.parseFloat(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_RATINGBAR)));
                    }
                    result +="빵집 이름 : " + bakery_name + "\n 위치 : " + bakery_location + " \n 평점 " + rating + "\n";
                } else{
                    result += "정보 없음";
                }
                tvDisplay.setText(result);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("select", etSearchName.getText().toString());
                setResult(RESULT_OK, resultIntent);
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}