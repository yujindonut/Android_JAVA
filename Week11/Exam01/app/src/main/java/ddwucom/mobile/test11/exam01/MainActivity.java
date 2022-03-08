package ddwucom.mobile.test11.exam01;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    ArrayList<Food> foodList;
    FoodManager foodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        foodManager = new FoodManager();
        // DataManager 적용해 볼 것
        foodList = foodManager.getFoodList();
        // Food 객체의 toString() 메소드가 호출되어 하나의 문자열로 처리됨
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, foodList);
        listView.setAdapter(adapter);
        // listView 롱클릭 이벤트 추가
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                String msg = foodList.get(position).getFood() + "을(를) 삭제하시겠습니끼?";
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("음식 삭제");
                builder.setMessage(msg);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //원본 데이터 삭제 - 푸드 매니저에서 삭제
                        foodManager.deleteFood(pos);
                        //원본 갱신
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("취소",null);
                builder.setCancelable(false);
                builder.show();
                return true;
            }
        });
    }
    public void onClick(View v){
        final ConstraintLayout orderLayout = (ConstraintLayout) View.inflate(this,R.layout.order_layout,null);
        final EditText etFoodName = orderLayout.findViewById(R.id.etFoodName);
        final EditText etCountryName = orderLayout.findViewById(R.id.etCountryName);
        switch(v.getId()){
            case R.id.button:
                etFoodName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etFoodName.setText("");
                    }
                });
                etCountryName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etCountryName.setText("");
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("음식 추가");
                builder.setView(orderLayout);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //커스텀 대화상자의 내용을 읽어옴
                        String foodName = etFoodName.getText().toString();
                        String countryName = etCountryName.getText().toString();
                        //원본데이터 추가
                        foodManager.addFood(new Food(foodName,countryName));
                        //화면갱신
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                break;
        }
    }

}
