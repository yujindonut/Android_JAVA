package mobile.database.dbtest02;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AllContactsActivity extends AppCompatActivity {

	final int ADD_CODE = 100;
	final int UPDATE_CODE = 200;
	final int SEARCH_CODE = 300;
	ListView lvContacts = null;
	ContactDBHelper helper;
	Cursor cursor;
//	SimpleCursorAdapter adapter;
	MyCursorAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_contacts);
		lvContacts = (ListView)findViewById(R.id.lvContacts);

		helper = new ContactDBHelper(this);

////		  SimpleCursorAdapter 객체 생성
//        adapter = new SimpleCursorAdapter ( this, android.R.layout.simple_expandable_list_item_2, null,
//										new String[] {ContactDBHelper.COL_NAME, ContactDBHelper.COL_PHONE}
//										, new int[] { android.R.id.text1, android.R.id.text2},
//											CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		adapter = new MyCursorAdapter(this, R.layout.listview_layout, null );

		lvContacts.setAdapter(adapter);

//		리스트 뷰 클릭 처리 //수정
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
			//db table의 _id값을 바로 가져옴(long id)자리에!
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            	//"delete from contact_table where _id = " + id : 쿼리에 저 id를 바로 적용가능
				//cursor.moveToPosition(position) : 화면의 listview와 db의 데이터 순서가 똑같아서 이렇게도 사용가능함
				cursor.moveToPosition(position);
				ContactDto contactDto = new ContactDto();
				contactDto.setId(id);
				contactDto.setName(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME)));
				contactDto.setPhone(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_PHONE)));
            	contactDto.setCategory(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_CATEGORY)));
				Intent intent = new Intent(AllContactsActivity.this, UpdateActivity.class);
				intent.putExtra("updateDto", contactDto);
				startActivityForResult(intent, UPDATE_CODE);
            }
        });

//		리스트 뷰 롱클릭 처리
		lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				final int pos = position;
				cursor.moveToPosition(pos);
				AlertDialog.Builder builder = new AlertDialog.Builder(AllContactsActivity.this);
				builder.setTitle("삭제")
						.setMessage(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_NAME))+"를 삭제하시겠습니까?")
						.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

								String whereClause = ContactDBHelper.COL_ID + "=?";
								String[] whereArgs = new String[]{String.valueOf(id)};
								int result = sqLiteDatabase.delete(ContactDBHelper.TABLE_NAME, whereClause, whereArgs);
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
		cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME, null);

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




