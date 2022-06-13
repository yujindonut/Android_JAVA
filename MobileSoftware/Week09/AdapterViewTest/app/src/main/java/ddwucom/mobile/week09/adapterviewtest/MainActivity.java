package ddwucom.mobile.week09.adapterviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SubjectManger subjectManager;
    ArrayAdapter<String> adapter;
    ArrayList<String> subjectList;
    ListView listView;
    EditText editText;

    int selectedPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjectManager = new SubjectManger();

        //얘는 DataManager클래스에서 관리, Activity에서 관리 X,클래스 멤버변수로 만들 필요는 없음
        subjectList = subjectManager.getSubjectList();
//        원본 데이터에 일반 배열을 사용할 경우
//        String[] subjectList = {"모바일소프트웨어", "네트워크", "웹서비스", "운영체제", "웹프로그래밍2"};
//        res/values/arrays.xml 만들어서 배열 생성
//        String[] subjectList = getResources().getStringArray(R.array.subjectList);
//        어댑터 생성
//      레이아웃에 원본데이터를 결합해서 화면을 만들고 화면의 개수만큼 원본데이터를 제공한다
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, subjectList
        );//      어댑터 뷰 준비
        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
//      어댑터 어댑터뷰 연결
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                //this에는 MainAcitivity가 들어와야함
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    editText.setText(subjectManager.getSubjectbyPos(position));
                    selectedPosition = position;
                }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               subjectManager.removeSubject(position);
               adapter.notifyDataSetChanged();

                return true;
            }
        });
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnAdd:
                subjectManager.addSubject(editText.getText().toString());
                adapter.notifyDataSetChanged();
            break;
            case R.id.btnUpdate:
                //원본 데이터 수정 (위치, 바꿀값)
                //어댑터 갱신 요청
                subjectManager.updateSubject(selectedPosition,editText.getText().toString());
            break;
        }
    }
}