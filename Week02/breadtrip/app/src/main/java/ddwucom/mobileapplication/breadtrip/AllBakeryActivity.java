package ddwucom.mobileapplication.breadtrip;

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
import android.widget.ListView;
import android.widget.Toast;

public class AllBakeryActivity extends AppCompatActivity {

    final int UPDATE_CODE = 200;
    ListView lvBakeries = null;
    BakeryDBHelper helper;
    Cursor cursor;
    MyCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bakery);

        lvBakeries = (ListView)findViewById(R.id.lvBakeries);
        helper = new BakeryDBHelper(this);
        adapter = new MyCursorAdapter(this, R.layout.listview_layout, null );
        lvBakeries.setAdapter(adapter);

        //		리스트 뷰 클릭 처리 //수정
        lvBakeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //db table의 _id값을 바로 가져옴(long id)자리에!
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //"delete from contact_table where _id = " + id : 쿼리에 저 id를 바로 적용가능
                //cursor.moveToPosition(position) : 화면의 listview와 db의 데이터 순서가 똑같아서 이렇게도 사용가능함
                cursor.moveToPosition(position);
                BakeryDTO bakeryDTO = new BakeryDTO();
                bakeryDTO.setId(id);
                bakeryDTO.setBakery(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_BAKERY)));
                bakeryDTO.setLocation(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_LOCATION)));
                bakeryDTO.setRate(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_RATINGBAR)));
                Log.d("rate", cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_RATINGBAR)));
                Intent intent = new Intent(AllBakeryActivity.this, UpdateActivity.class);
                intent.putExtra("updateDto", bakeryDTO);
                startActivityForResult(intent, UPDATE_CODE);
            }
        });

//		리스트 뷰 롱클릭 처리
        lvBakeries.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                cursor.moveToPosition(pos);
                AlertDialog.Builder builder = new AlertDialog.Builder(AllBakeryActivity.this);
                builder.setTitle("삭제")
                        .setMessage(cursor.getString(cursor.getColumnIndex(BakeryDBHelper.COL_BAKERY))+"를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

                                String whereClause = BakeryDBHelper.COL_ID + "=?";
                                String[] whereArgs = new String[]{String.valueOf(id)};
                                int result = sqLiteDatabase.delete(BakeryDBHelper.TABLE_NAME, whereClause, whereArgs);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CODE) {    // UpdateActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    protected void readTable(){
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + BakeryDBHelper.TABLE_NAME, null);
        adapter.changeCursor(cursor);
        helper.close();
    }
    @Override
    protected void onResume() {
        super.onResume();
//        DB에서 데이터를 읽어와 Adapter에 설정
        readTable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        cursor 사용 종료
        if (cursor != null) cursor.close();
    }
}