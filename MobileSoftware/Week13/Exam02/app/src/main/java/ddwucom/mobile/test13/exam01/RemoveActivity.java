package ddwucom.mobile.test13.exam01;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RemoveActivity extends AppCompatActivity {

    FoodDBHelper myDbHelper;
    EditText etRemoveFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        //DB파일 만들어짐
        myDbHelper = new FoodDBHelper(this);
        etRemoveFood = findViewById(R.id.etRemoveFood);
    }
    public void onClick(View v){
        Intent intent = getIntent();
        SQLiteDatabase myDB = myDbHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        switch (v.getId()){
            case R.id.btnRemoveFood:
                String food = etRemoveFood.getText().toString();
                String whereClause = "food=?";
                String[] whereArgs = new String[]{food};

                myDB.delete(FoodDBHelper.TABLE_NAME, whereClause,whereArgs);
                break;
            case R.id.btnRemoveCancel:
                finish();
                break;
        }
        myDbHelper.close();
        finish();
    }
}
