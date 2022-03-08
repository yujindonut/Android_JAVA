package ddwucom.mobile.finalreport;
//과제명: 맛집 정보 관리 앱
//분반 : 02분반
//학번: 20181030 성명 : 한유진
//제출일: 2021.06.24

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    final int ADD_CODE = 100;
    final int UPDATE_CODE = 200;
    final int SEARCH_CODE = 300;
    ArrayList<Restaurant> myRestaurantList = null;
    ListView listView;
    MyAdapter myAdapter;
    RestaurantDBManager restaurantDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.customListView);
        myRestaurantList = new ArrayList<>();
       
        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, myRestaurantList);
        listView.setAdapter(myAdapter);
       
        restaurantDBManager = new RestaurantDBManager(this);

        //클릭 구현 - 수정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = myRestaurantList.get(position);
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivityForResult(intent, UPDATE_CODE);
            }
        });
        //롱클릭 구현 - 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_title)
                        .setMessage( myRestaurantList.get(pos).restaurantName + "식당을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean result = restaurantDBManager.removeRestaurant(myRestaurantList.get(pos).get_id());
                                Log.d("MainActivity", String.valueOf(result));
                                if(result) {
                                    Toast.makeText(MainActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                    myRestaurantList.clear();
                                    myRestaurantList.addAll(restaurantDBManager.getAllRestaurant());
                                    myAdapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();

                return true;
            }
        });
    }
    @Override
    protected void onResume() { //MainActivity에 돌아오면 자동으로 onResume시작
        super.onResume();
        myRestaurantList.clear();
        myRestaurantList.addAll(restaurantDBManager.getAllRestaurant());
        myAdapter.notifyDataSetChanged();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_add:
                intent = new Intent(this, AddActivity.class);
                startActivityForResult(intent, ADD_CODE);
                break;
            case R.id.menu_introduce:
                intent = new Intent(this, IntroduceMyself_Activity.class);
                startActivity(intent);
                break;
            case R.id.menu_search:
                intent = new Intent(this, SearchActivity.class);
                startActivityForResult(intent,SEARCH_CODE);
                break;
            case R.id.menu_close:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.close_dialog_title)
                        .setMessage(R.string.close_dialog_message)
                        .setPositiveButton(R.string.close_dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel,null)
                        .setCancelable(false) // 무조건 취소를 누르게
                        .show();
                break;
        }
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CODE) {  // AddActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
                    String Restaurant = data.getStringExtra("Restaurant");
                    Toast.makeText(this, Restaurant + " 추가 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "추가 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (requestCode == UPDATE_CODE) {    // UpdateActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        else if(requestCode == SEARCH_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "검색 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "검색 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}