package ddwucom.mobileapplication.ma02_20181030.record_memo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MemoDBHelper extends SQLiteOpenHelper {

    private final static String TAG = "MemoDBHelper";

    private final static String DB_NAME ="memo_db";
    public final static String TABLE_NAME = "memo_table";
    public final static String ID = "_id";
    public final static String PATH = "path";
    public final static String DATE = "date";
    public final static String CAFENAME = "cafe_name";
    public final static String LOCATION = "location";
    public final static String RATINGBAR = "ratingBar";
    public final static String MEMO = "memo";

    public MemoDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE_NAME + " (" + ID + " integer primary key autoincrement, "
                + PATH + " text, " + MEMO + " text, "
                + DATE + " TEXT, " + CAFENAME + " TEXT, "
                + LOCATION + " TEXT, " + RATINGBAR + " TEXT)";
        Log.d(TAG, sql);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
