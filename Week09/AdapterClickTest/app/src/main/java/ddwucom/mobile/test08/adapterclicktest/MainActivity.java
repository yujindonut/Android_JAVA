package ddwucom.mobile.test08.adapterclicktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SubjectManager subjectManager;
    ArrayList<String> subjectList;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjectManager = new SubjectManager();
        subjectList = subjectManager.getSubjectList();

        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, subjectList
        );

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        editText = findViewById(R.id.etItem);

//        항목 클릭 시 해당 항목을 EditText에 표시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText(subjectManager.getSubjectByPos(position));
                selectedPosition = position;
            }
        });

//        롱클릭 시 해당 항목 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                subjectManager.removeData(position);
                adapter.notifyDataSetChanged();

                return true;
            }
        });
    }
//    추가버튼과 수정버튼
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.btnInsert:
                subjectManager.addData(editText.getText().toString());
                editText.setText("");
                adapter.notifyDataSetChanged();
            break;

            case R.id.btnUpdate:
                subjectManager.updateSubject(selectedPosition,editText.getText().toString());
                adapter.notifyDataSetChanged();
            break;
        }
    }

}
