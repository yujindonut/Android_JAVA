package ddwucom.mobile.test13.exam01;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    FoodDBHelper myDbHelper;
    EditText etAddFood;
    EditText etAddNation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //DB파일 만들어짐
        myDbHelper = new FoodDBHelper(this);
        etAddFood = findViewById(R.id.etAddFood);
        etAddNation = findViewById(R.id.etAddNation);
    }
    public void onClick(View v) {
        SQLiteDatabase myDB = myDbHelper.getWritableDatabase();
        Intent intent = getIntent();
        ContentValues row = new ContentValues();
        switch (v.getId()){
            case R.id.btnAddFood:
                String food = etAddFood.getText().toString();
                String nation = etAddNation.getText().toString();
                //               메소드 사용
                row.put(FoodDBHelper.COL_FOOD, food);
                row.put(FoodDBHelper.COL_NATION, nation);

                myDB.insert(FoodDBHelper.TABLE_NAME,null,row);
                break;
            case R.id.btnAddCancel:
                finish();
                break;
        }
        myDbHelper.close();
        finish();
    }
}
