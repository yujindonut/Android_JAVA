package ddwu.com.mobile.multimedia.photomemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final static String TAG = "MainActivity";
    SimpleCursorAdapter memoAdapter;
    Cursor cursor;
    MemoDBHelper helper;
    ListView lvMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MemoDBHelper(this);

//        어댑터에 SimpleCursorAdapter 연결
        Log.d(TAG+"!!!",MemoDBHelper.PATH);
        memoAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                null, new String[]{MemoDBHelper.PATH},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        lvMemo = (ListView)findViewById(R.id.lv_memo);
        lvMemo.setAdapter(memoAdapter);

        lvMemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long _id) {
//                ShowMemoActivity 호출
            Intent intent = new Intent(MainActivity.this,ShowMemoActivity.class);
            intent.putExtra("_id", _id);
            startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        DB 에서 모든 레코드를 가져와 Adapter에 설정
        readTable();
    }

    protected void readTable(){
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + MemoDBHelper.TABLE_NAME, null);

        memoAdapter.changeCursor(cursor);
        helper.close();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
//                AddMemoActivity 호출
                Intent intent = new Intent(this, AddMemoActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
    }
}
