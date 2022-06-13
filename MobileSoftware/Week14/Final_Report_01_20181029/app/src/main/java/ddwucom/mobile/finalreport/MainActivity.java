// 과제명: 영화 정보 관리 앱
// 분반: 01분반
// 학번: 20181029 한세영
// 제출일: 2020년 7월 3일

package ddwucom.mobile.finalreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int REQ_CODE = 100;
    final int UPDATE_CODE = 200;

    ListView listView;
    MyAdapter adapter;
    ArrayList<MovieData> movieList;
    MovieDBHelper dbHelper;
    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.customListView);
        movieList = new ArrayList<>();

        adapter = new MyAdapter(this, R.layout.custom_adapter_view, movieList);
        listView.setAdapter(adapter);

        movieDBManager = new MovieDBManager(this);

        //클릭 구현 - 수정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieData movie = movieList.get(position);
                Intent intent = new Intent(MainActivity.this, updateActivity.class);
                intent.putExtra("movie", movie);
                startActivityForResult(intent, UPDATE_CODE);
//                Toast.makeText(MainActivity.this, movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        //롱클릭 구현 - 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                MovieData movie = movieList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("영화 삭제")
                        .setMessage( movie.getTitle() + " 영화를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean result = movieDBManager.removeMovie(movieList.get(pos).get_id());

                                if(result) {
                                    Toast.makeText(MainActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                    movieList.clear();
                                    movieList.addAll(movieDBManager.getMovieList());
                                    adapter.notifyDataSetChanged();
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
    protected void onResume() {//데이터 읽는 메소드를 실행시키고 수정됬다는 사실을 어뎁터에게 알려주는 작업
//        매니저 사용시
        super.onResume();
        movieList.clear();//계속 새로 덮어 씌우는게 아니라 지워주고 다시 읽는 방법을 반복하게 한다
        //foodDBManager 가 새로만든 db에서 가져온 모든 리스트의 내용을 기존에
        //foodList가 가리키고 있는 곳에 넣어준다
        movieList.addAll(movieDBManager.getMovieList());
        ImageView imageView = findViewById(R.id.imageView);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder;
        switch (item.getItemId()){
            case R.id.menu_addMovie:
                Toast.makeText(this, "영화 추가", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, addActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.menu_close:
                Toast.makeText(this, "앱 종료", Toast.LENGTH_SHORT).show();
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();

                break;
            case R.id.menu_developer:
                Toast.makeText(this, "개발자 정보", Toast.LENGTH_SHORT).show();
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("개발자 소개")
                        .setMessage("모바일 소프트웨어 01분반\n20181029 한세영")
                        .setIcon(R.drawable.im)
                        .setPositiveButton("닫기", null)
                        .setCancelable(false)
                        .show();

                break;
            case R.id.menu_search:
                Toast.makeText(this, "영화 검색", Toast.LENGTH_SHORT).show();
                Intent in_tent = new Intent(this, searchActivity.class);
                startActivity(in_tent);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MovieData movie;
        switch (requestCode) {
            case REQ_CODE:
                if (resultCode == RESULT_OK) {
                    movie = (MovieData) data.getSerializableExtra("movie");
                    Toast.makeText(this, movie.getTitle() + "(이)가 추가 됐습니다.", Toast.LENGTH_SHORT).show();
//                    adapter.notifyDataSetChanged();
                }
                break;
            case UPDATE_CODE:
                if (resultCode == RESULT_OK) {
                    movie = (MovieData) data.getSerializableExtra("movie");
                    Toast.makeText(this, movie.getTitle() + "(이)가 수정 됐습니다.", Toast.LENGTH_SHORT).show();
//                    adapter.notifyDataSetChanged();
                }
        }
    }
}