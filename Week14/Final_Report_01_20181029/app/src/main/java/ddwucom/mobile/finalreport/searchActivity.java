package ddwucom.mobile.finalreport;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {

    EditText searchTitle;
    TextView content;
    Button button;
    ArrayList<MovieData> movieList;
    MovieDBHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        movieList = new ArrayList<>();

        searchTitle = findViewById(R.id.et_searchTitle);
        content = findViewById(R.id.tvContent);
        button = findViewById(R.id.button);

        helper = new MovieDBHelper(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

                String[] columns = null;
                String selection = "title=?";
                String[] selectArgs = new String[]{searchTitle.getText().toString()};
                Cursor cursor = sqLiteDatabase.query(MovieDBHelper.TABLE_NAME, columns, selection, selectArgs,
                        null, null, null, null);

                String result = "";
                long id;
                String title = null;
                String actor = null;
                String directory = null;
                String story = null;
                String releaseDate = null;

                while(cursor.moveToNext()) {
                    id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
                    title = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_TITLE));
                    actor = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ACTOR));
                    directory = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
                    story = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_STORY));
                    releaseDate = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RELEASEDATE));

                }
                result += "제목이 '" + searchTitle.getText().toString() + "'인 영화는\n 주연 배우 : " + actor + "\n 감독 : " +
                        directory + "\n 내용 : " + story + "\n 개봉일 : " + releaseDate + "입니다.";

                content.setText(result);

                cursor.close();
                helper.close();
                break;
            case R.id.button2:
                finish();
                break;
        }

    }
}
