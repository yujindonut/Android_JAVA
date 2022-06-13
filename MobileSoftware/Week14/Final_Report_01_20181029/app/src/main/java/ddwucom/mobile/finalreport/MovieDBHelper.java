package ddwucom.mobile.finalreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "movies.db";
    public final static String TABLE_NAME = "movie_table";

    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_ACTOR = "actor";
    public final static String COL_DIRECTOR = "director";
    public final static String COL_STORY = "story";
    public final static String COL_RELEASEDATE = "releaseDate";

    public MovieDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_TITLE + " TEXT, " + COL_ACTOR + " TEXT, " + COL_DIRECTOR + " TEXT, "
                + COL_STORY + " TEXT, " + COL_RELEASEDATE + " TEXT)";

        db.execSQL(sql);

        db.execSQL("insert into " + TABLE_NAME + " values (null, '인턴', '앤 해서웨이', '낸시 마이어스', " +
                "'열정적인 CEO에게 인생경험이 무기인 만능 벤이 인턴으로 취직하면서 생긴 이야기', '2015.09.24');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '라라랜드', '라이언 고슬링', '데이미언 셔젤', " +
                "'피아니스트 세바스찬과 배우 지망생 미아가 인생의 가장 빛나는 순간 만나 서로의 무대를 만들어 나가는 이야기', '2016.12.07');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '어벤져스:엔드게임', '로버트 다우니 주니어', '안소니 루소', " +
                "'절반만 살아남은 지구를 다시 되살리기 위해 힘쓰는 어벤져스 이야기', '2019.04.24');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '인사이드 아웃', '기쁨이', '피트 닥터', " +
                "'사람 머릿속에 존재하는 감정 컨트롤 본부에서 기쁨이, 슬픔이, 버럭이, 까질이, 소심이의 이야기', '2015.07.09');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '1987', '김윤석', '장준환', " +
                "'1987년도에 한국에서 일어났던 이야기', '2017.12.27');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '써니', '유호정', '강형철', " +
                "'나미가 춘하를 만나게 되면서 떠올리는 풋풋했던 학창시절 이야기', '2011.05.04');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
