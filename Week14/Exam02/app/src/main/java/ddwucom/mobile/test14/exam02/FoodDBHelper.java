package ddwucom.mobile.test14.exam02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FoodDBHelper extends SQLiteOpenHelper {
    final static String TAG = "FoodDBHelper";

    final static String DB_NAME = "foods.db";
    public final static String TABLE_NAME = "food_table";

    public static String COL_ID = "_id";
    public final static String COL_FOOD = "food";
    public final static String COL_NATION = "nation";

    public FoodDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_FOOD + " TEXT, " + COL_NATION + " TEXT)";
        Log.d(TAG, sql);
        db.execSQL(sql);
        db.execSQL("insert into " + TABLE_NAME + " values (null, '된장찌개', '한국');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '김치찌개', '한국');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '훠궈', '중국');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '딤섬', '중국');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '스시', '일본');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '오코노미야키', '일본');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}


