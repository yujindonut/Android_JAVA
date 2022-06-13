package ddwucom.mobile.finalreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RatingBar;

import java.io.ByteArrayOutputStream;

public class RestaurantDBHelper extends SQLiteOpenHelper {

    final static String TAG = "RestaurantDBHelper";
    final static String DB_NAME = "restaurant.db";
    public final static String TABLE_NAME = "restaurant_table";
    public final static String COL_ID = "_id";
    public final static String COL_SIGNATUREMENU = "signatureMenu";
    public final static String COL_RESTAURANT = "restaurantName";
    public final static String COL_RATINGBAR = "ratingBar";
    public final static String COL_PRICE = "price";
    public final static String COL_LOCATION = "location";


    public RestaurantDBHelper(Context context) {super(context, DB_NAME, null, 1);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_SIGNATUREMENU + " TEXT, " + COL_RESTAURANT + " TEXT, " + COL_RATINGBAR + " TEXT, "
                + COL_PRICE + " TEXT, " + COL_LOCATION + " TEXT)";
        db.execSQL(sql);
        db.execSQL("insert into " + TABLE_NAME + " values (null, '치즈케이크', 'kyeri', '4', '9500', '이태원역');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '잠봉뵈르', 'salthouse', '4', '13000', '안국역');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '우대갈비', 'montan', '5', '25000', '삼각지역');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '올리브치아바타', 'oeuf', '5', '3000', '잠실역');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '딸기생크림케이크', 'kitchen205', '3', '36000', '잠실역');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '얼그레이크림빵', 'uglybakery', '2', '3300', '망원역');");
        Log.d(TAG, sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
