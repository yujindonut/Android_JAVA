package ddwucom.mobile.week10.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MyData> myDataList;
    private ListView listView;
    private MyAdapter myAdapter;
    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataList = new ArrayList<MyData>();

        myDataList.add(new MyData( 1,"하월곡동","서울시 성북구","괜찮음"));
        myDataList.add(new MyData(2,"잠실2동","서울시 송파구","좋음"));
        myDataList.add(new MyData(3,"삼성동","서울시 강남구","좋음"));
        myDataList.add(new MyData(4,"대치2동","서울시 강남구","나쁨"));
        myDataList.add(new MyData(5,"잠실1동","서울시 송파구","좋음"));
        myDataList.add(new MyData(6,"이태원동","서울시 용산구","매우좋음"));

        listView = findViewById(R.id.customListView);

        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, myDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                myDataList.remove(position);
                myAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}