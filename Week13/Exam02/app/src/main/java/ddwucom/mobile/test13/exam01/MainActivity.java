package ddwucom.mobile.test13.exam01;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay;
    FoodDBHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        //DB파일 만들어짐
        myDbHelper = new FoodDBHelper(this);
    }


    public void onClick(View v) {
        //클릭하는 순간 데이터의 table이 최초 1회 만들어짐
//        SQLiteDatabase myDB = myDbHelper.getWritableDatabase();
        SQLiteDatabase myDB = myDbHelper.getReadableDatabase(); //select
        ContentValues row = new ContentValues();
        Intent intent;
        String whereClause;
        String[] whereArgs;
        switch(v.getId()) {
            case R.id.btnSelect:

//                String selection = "food=?";
//                String[] selectArgs = new String[] {"된장찌개"};

                Cursor cursor =
                        myDB.query(FoodDBHelper.TABLE_NAME,null,null,null,
                                null,null,null,null);
//                cursor = myDB.rawQuery("select * from " + FoodDBHelper.TABLE_NAME
//                        + " where foods='된장찌개'", null);

                String result = "";
                while (cursor.moveToNext()) {
                    result += cursor.getInt( cursor.getColumnIndex( FoodDBHelper.COL_ID)) + " : ";
                    result += cursor.getString( cursor.getColumnIndex( FoodDBHelper.COL_FOOD)) + " - ";
                    result += cursor.getString( cursor.getColumnIndex( FoodDBHelper.COL_NATION)) + " \n ";
                }
                tvDisplay.setText(result);
                cursor.close();
                break;
            case R.id.btnAdd:
                intent = new Intent(this,AddActivity.class);
                startActivity(intent);
                break;
            case R.id.btnUpdate:
                intent = new Intent (this, UpdateActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRemove:
                intent = new Intent (this, RemoveActivity.class);
                startActivity(intent);
                break;
        }
        myDbHelper.close();
    }

}
