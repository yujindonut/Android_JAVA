package mobile.database.dbtest02;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText etName;
    EditText etPhone;
    EditText etCategory;
    ContactDto contactDto;
    ContactDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //intent로 넘어온 update객체
        contactDto = (ContactDto) getIntent().getSerializableExtra("updateDto");

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etCategory = findViewById(R.id.etCategory);

        helper = new ContactDBHelper(this);

        etName.setText(contactDto.getName());
        etPhone.setText(contactDto.getPhone());
        etCategory.setText(contactDto.getCategory());
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdateContact:
//                DB 데이터 업데이트 작업 수행
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String category = etCategory.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(category)){
                    Toast.makeText(this, "필수 항목이 입력이 안되었어요!", Toast.LENGTH_SHORT).show();
                }
                else{
                    SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                    ContentValues row = new ContentValues();
                    row.put(ContactDBHelper.COL_NAME, etName.getText().toString());
                    row.put(ContactDBHelper.COL_PHONE,etPhone.getText().toString());
                    row.put(ContactDBHelper.COL_CATEGORY,etCategory.getText().toString());

                    String whereClause = ContactDBHelper.COL_ID + "=?";
                    String[] whereArgs = new String[]{String.valueOf(contactDto.getId())};
                    int result = sqLiteDatabase.update(ContactDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                    helper.close();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("result", result);
                    setResult(RESULT_OK, resultIntent);
                }
                break;
            case R.id.btnUpdateContactClose:
//                DB 데이터 업데이트 작업 취소
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        finish();
    }


}
