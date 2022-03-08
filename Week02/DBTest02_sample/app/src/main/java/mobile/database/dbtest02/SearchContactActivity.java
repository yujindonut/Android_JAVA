package mobile.database.dbtest02;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class SearchContactActivity extends AppCompatActivity {

	EditText etSearchName;
	ContactDBHelper helper;
	TextView tvSearchResult = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contact);

		etSearchName = findViewById(R.id.etSearchName);
		tvSearchResult = findViewById(R.id.tvSearchResult);
		helper = new ContactDBHelper(this);
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnSearchContactSave:
//			DB 검색 작업 수행
			String result ="";
			SQLiteDatabase myDB = helper.getReadableDatabase(); //select
			String selection = helper.COL_NAME+ "=?";
			String[] selectArgs = new String[]{etSearchName.getText().toString()};
			Cursor cursor =
					myDB.query(helper.TABLE_NAME, null, selection, selectArgs,
							null, null, null, null);
			if (cursor.getCount() > 0) {
				long id;
				String name = null;
				String phone = null;
				String category = null;
				while (cursor.moveToNext()) {
					id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
					name = cursor.getString(cursor.getColumnIndex(helper.COL_NAME));
					phone = cursor.getString(cursor.getColumnIndex(helper.COL_PHONE));
					category = cursor.getString(cursor.getColumnIndex(helper.COL_CATEGORY));
				}
				result += name + " : " + phone + " : " + category + "\n";
			}
			tvSearchResult.setText(result);
			Intent resultIntent = new Intent();
			resultIntent.putExtra("select", etSearchName.getText().toString());
			setResult(RESULT_OK, resultIntent);
			break;
		case R.id.btnClose :
			setResult(RESULT_CANCELED);
			finish();
			break;
		}
	}
	
	
	
}
