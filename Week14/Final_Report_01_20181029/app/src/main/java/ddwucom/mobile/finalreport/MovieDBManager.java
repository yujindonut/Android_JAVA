package ddwucom.mobile.finalreport;

import android.content.Context;
import android.content.ContentValues;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MovieDBManager {

    private ArrayList<MovieData> movieList;
    MovieDBHelper helper;

    public MovieDBManager(Context context) {
        helper = new MovieDBHelper(context);
//        movieList = new ArrayList<>();
//        movieList.add(new MovieData("인턴", "앤 해서웨이", "낸시 마이어스",
//                "열정적인 CEO에게 인생경험이 무기인 만능 벤이 인턴으로 취직하면서 생긴 이야기", "2015.09.24"));
//        movieList.add(new MovieData("라라랜드", "라이언 고슬링", "데이미언 셔젤",
//                "피아니스트 세바스찬과 배우 지망생 미아가 인생의 가장 빛나는 순간 만나 서로의 무대를 만들어 나가는 이야기", "2016,12,07"));
//        movieList.add(new MovieData("어벤져스:엔드게임", "로버트 다우니 주니어", "안소니 루소",
//                "절반만 살아남은 지구를 다시 되살리기 위해 힘쓰는 어벤져스 이야기", "2019.04.24"));
//        movieList.add(new MovieData("인사이드 아웃", "기쁨이", "피트 닥터",
//                "사람 머릿속에 존재하는 감정 컨트롤 본부에서 기쁨이, 슬픔이, 버럭이, 까질이, 소심이의 이야기", "2015.07.09"));
//        movieList.add(new MovieData("1987", "김윤석", "장준환",
//                "1987년도에 한국에서 일어났던 이야기", "2017.12.27"));
//        movieList.add(new MovieData("써니", "유호정", "강형철",
//                "나미가 춘하를 만나게 되면서 떠올리는 풋풋했던 학창시절 이야기", "2011.05.04"));
    }

    // 전체 데이터 출력
    public ArrayList<MovieData> getMovieList() {
        movieList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_TITLE));
            String actor = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ACTOR));
            String directory = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
            String story = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_STORY));
            String releaseDate = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RELEASEDATE));

            movieList.add ( new MovieData (id, title, actor, directory, story, releaseDate) );
        }

        cursor.close();
        helper.close();
        return movieList;
    }

    // 추가
    public boolean addMovie(MovieData movie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues value = new ContentValues();  //데이터를 집어넣기 위한 하나의 row를 만들고
        value.put(MovieDBHelper.COL_TITLE, movie.getTitle());
        value.put(MovieDBHelper.COL_ACTOR, movie.getActor());
        value.put(MovieDBHelper.COL_DIRECTOR, movie.getDirector());
        value.put(MovieDBHelper.COL_STORY, movie.getStory());
        value.put(MovieDBHelper.COL_RELEASEDATE, movie.getReleaseDate());

//        insert 메소드를 사용할 경우 데이터 삽입이 정상적으로 이루어질 경우 1 이상, 이상이 있을 경우 0 반환 확인 가능
        long count = db.insert(MovieDBHelper.TABLE_NAME, null, value);
        helper.close();

        if(count > 0) {
            return true;
        }
        return false;
    }

    // 수정
    public boolean modifyMovie(MovieData movie) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put(MovieDBHelper.COL_TITLE, movie.getTitle());
        row.put(MovieDBHelper.COL_ACTOR, movie.getActor());
        row.put(MovieDBHelper.COL_DIRECTOR, movie.getDirector());
        row.put(MovieDBHelper.COL_STORY, movie.getStory());
        row.put(MovieDBHelper.COL_RELEASEDATE, movie.getReleaseDate());

        String whereClause = MovieDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] {String.valueOf(movie.get_id())};

        int result = sqLiteDatabase.update(MovieDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        helper.close();

        if(result > 0){
            return true;
        }
        return false;
    }

    // 삭제
    public boolean removeMovie(long id) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        String whereClause = helper.COL_ID + "=?";
        String[] whereArgs = new String[] {String.valueOf(id)};

        int result = sqLiteDatabase.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs);

        helper.close();
        if(result > 0){
            return true;
        }
        return false;
    }

    // 검색- 영화 제목 검색
    public ArrayList<MovieData> getMovieByTitle(String searchTitle) {
        movieList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        String[] columns = null;
        String selection = "title=?";
        String[] selectArgs = new String[]{searchTitle};
        Cursor cursor = sqLiteDatabase.query(MovieDBHelper.TABLE_NAME, columns, selection, selectArgs,
                                        null, null, null, null);

        String result = "";

        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_TITLE));
            String actor = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ACTOR));
            String directory = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
            String story = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_STORY));
            String releaseDate = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RELEASEDATE));

            movieList.add ( new MovieData (id, title, actor, directory, story, releaseDate) );
            result += "제목이 " + searchTitle + "인 영화\n 주연 배우 : " + actor + "\n 감독 : " +
                    directory + "\n 내용 : " + story + "\n 개봉일 : " + releaseDate;

        }

        cursor.close();
        helper.close();

        return movieList;
    }
}
