package ddwucom.mobileapplication.ma02_20181030.record_memo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ddwucom.mobileapplication.ma02_20181030.R;

public class RecordListActivity extends AppCompatActivity {
    final static String TAG = "PictureMemoActivity";
    Cursor cursor;
    MemoDBHelper helper;
    MyCursorAdapter adapter;
    ListView lvMemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list_memo);
        helper = new MemoDBHelper(this);
        adapter = new MyCursorAdapter(this, R.layout.record_item, null );
//        어댑터에 SimpleCursorAdapter 연결
        Log.d(TAG,MemoDBHelper.PATH);

        lvMemo = (ListView)findViewById(R.id.lv_memo);
        lvMemo.setAdapter(adapter);

        lvMemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long _id) {
//                ShowMemoActivity 호출
                Intent intent = new Intent(RecordListActivity.this,ShowMemoActivity.class);
                intent.putExtra("_id", _id);
                startActivity(intent);
            }
        });
        lvMemo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(RecordListActivity.this);
                builder.setTitle("삭제")
                        .setMessage(cursor.getString(cursor.getColumnIndex(MemoDBHelper.CAFENAME))+"를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

                                String whereClause = MemoDBHelper.ID + "=?";
                                String[] whereArgs = new String[]{String.valueOf(id)};
                                int result = sqLiteDatabase.delete(MemoDBHelper.TABLE_NAME, whereClause, whereArgs);
                                helper.close();
                                readTable();
                            }
                        })
                        .setNegativeButton("취소",null)
                        .setCancelable(false)
                        .show();
                return true;
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

        adapter.changeCursor(cursor);
        helper.close();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
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