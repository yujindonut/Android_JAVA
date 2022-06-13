package ddwucom.mobile.test13.exam01;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    private static final String TAG = "UpdateActivity";
    FoodDBHelper myDbHelper;
    EditText etUpdateId;
    EditText etUpdateFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        //DB파일 만들어짐
        myDbHelper = new FoodDBHelper(this);
        etUpdateId = findViewById(R.id.etUpdateId);
        etUpdateFood = findViewById(R.id.etUpdateFood);
    }
    public void onClick(View v){
        Intent intent = getIntent();
        SQLiteDatabase myDB = myDbHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        switch (v.getId()){
            case R.id.btnUpdateFood:
                String _id = etUpdateId.getText().toString();
                String food = etUpdateFood.getText().toString();
                row.put("food", food);
                String whereClause = "_id=?";
                String[] whereArgs = new String[]{_id};

                int i = myDB.update(FoodDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                Log.d(TAG,Integer.toString(i));
                break;
            case R.id.btnUpdateCancel:
                finish();
                break;
        }
        myDbHelper.close();
        finish();
    }
}
