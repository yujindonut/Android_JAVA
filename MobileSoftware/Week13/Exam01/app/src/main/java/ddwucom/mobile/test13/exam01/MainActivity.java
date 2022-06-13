package ddwucom.mobile.test13.exam01;

import android.content.ContentValues;
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
        SQLiteDatabase myDB = myDbHelper.getWritableDatabase();
//        SQLiteDatabase myDB = myDbHelper.getReadableDatabase(); //select
        ContentValues row = new ContentValues();
        String whereClause;
        String[] whereArgs;
        switch(v.getId()) {
            case R.id.btnSelect:

                String selection = "food=?";
                String[] selectArgs = new String[] {"된장찌개"};

                Cursor cursor =
                        myDB.query(FoodDBHelper.TABLE_NAME,null,selection,selectArgs,
                                null,null,null,null);
//                cursor = myDB.rawQuery("select * from " + FoodDBHelper.TABLE_NAME
//                        + " where foods='된장찌개'", null);

                String result = "";
                while (cursor.moveToNext()) {
                    result += cursor.getInt( cursor.getColumnIndex( FoodDBHelper.COL_ID)) + " : ";
                    result += cursor.getInt( cursor.getColumnIndex( FoodDBHelper.COL_FOOD)) + " : ";
                    result += cursor.getInt( cursor.getColumnIndex( FoodDBHelper.COL_NATION)) + " : ";
                }

                tvDisplay.setText(result);
                cursor.close();

                break;
            case R.id.btnAdd:
//               메소드 사용
                row.put(FoodDBHelper.COL_FOOD, "된장찌개");
                row.put(FoodDBHelper.COL_NATION, "한국");

                myDB.insert(FoodDBHelper.TABLE_NAME,null,row);

//                SQL 사용
                //큰따옴표 작은 따옴표를 생각해줘야함
//                String food = "김치찌개";
//                myDB.execSQL("insert into " + FoodDBHelper.TABLE_NAME
//                + " values ( null, '" + food + "', '한국')");
                break;
                
            case R.id.btnUpdate:
                row.put("nation", "한국");
                whereClause = "food=?";
                whereArgs = new String[]{"된장찌개"};

                myDB.update(FoodDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                break;
            case R.id.btnRemove:
                whereClause = "food=?";
                whereArgs = new String[]{"된장찌개"};

                myDB.delete(FoodDBHelper.TABLE_NAME, whereClause,whereArgs);
                break;
        }
        myDbHelper.close();
    }

}
