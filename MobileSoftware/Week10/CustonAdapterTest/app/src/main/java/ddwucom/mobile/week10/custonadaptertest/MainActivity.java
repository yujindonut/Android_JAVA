package ddwucom.mobile.week10.custonadaptertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MyData> myDataList;
    private ListView listView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataList = new ArrayList<MyData>();

        myDataList.add( new MyData(1,"홍길동","01234"));
        myDataList.add( new MyData(2,"전우치","22222"));
        myDataList.add( new MyData(3,"일지매","33333"));

        listView = findViewById(R.id.customListView);

        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, myDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, myDataList.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}