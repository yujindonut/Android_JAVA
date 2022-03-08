package ddwucom.mobileapplication.breadtrip;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BakeryDBHelper extends SQLiteOpenHelper {

    final static String TAG = "BakeryDBHelper";
    final static String DB_NAME = "bakery.db";
    public final static String TABLE_NAME = "bakery_table";
    public final static String COL_ID = "_id";
    public final static String COL_LOCATION = "location";
    public final static String COL_BAKERY = "bakeryName";
    public final static String COL_RATINGBAR = "ratingBar";

    public BakeryDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COL_ID + " integer primary key autoincrement,"
                + COL_BAKERY + " TEXT, " + COL_LOCATION + " TEXT, " + COL_RATINGBAR + " TEXT);");
        //		샘플 데이터
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '키에리', '이태원역', '5');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '어글리베이커리', '망원역', '4');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '우프', '잠실역', '3');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '코코로카라', '홍대입구역', '4');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
