package ddwucom.mobile.finalreport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class updateActivity extends AppCompatActivity {
    MovieData movieData;
    EditText etTitle;
    EditText etActor;
    EditText etDirectory;
    EditText etStory;
    EditText etReleaseDate;
    ImageView imageView;

    MovieDBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);

        //넘긴 인텐트 받기
        movieData = (MovieData) getIntent().getSerializableExtra("movie");

        etTitle = findViewById(R.id.etTitle);
        etActor = findViewById(R.id.etActor);
        etDirectory = findViewById(R.id.etDirectory);
        etStory = findViewById(R.id.etStory);
        etReleaseDate = findViewById(R.id.etReleaseDate);
        imageView = findViewById(R.id.imageView2);

        //클릭했을때 클릭한 부분의 값들이 나오게 하는 방법
        int pos = (int) (movieData.get_id() - 1);
        if(pos == 0) {
            imageView.setImageResource(R.drawable.m1);
        }if(pos == 1) {
            imageView.setImageResource(R.drawable.m2);
        }if(pos == 2) {
            imageView.setImageResource(R.drawable.m3);
        }if(pos == 3) {
            imageView.setImageResource(R.drawable.m4);
        }if(pos == 4) {
            imageView.setImageResource(R.drawable.m5);
        }if(pos == 5) {
            imageView.setImageResource(R.drawable.m6);
        }if(pos > 5) {
            imageView.setImageResource(R.drawable.newmovie);
        }

        etTitle.setHint(movieData.getTitle());
        etActor.setHint(movieData.getActor());
        etDirectory.setHint(movieData.getDirector());
        etStory.setHint(movieData.getStory());
        etReleaseDate.setHint(movieData.getReleaseDate());

        manager = new MovieDBManager(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdate:
                if(etTitle.getText().toString().equals("") || etTitle.getText().toString() == null) {
                    Toast.makeText(updateActivity.this, "영화 제목은 필수 항목입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    String movieName = String.valueOf(etTitle.getText());
                    String actor = etActor.getText().toString();
                    String directory = etDirectory.getText().toString();
                    String story = etStory.getText().toString();
                    String releaseDate = etReleaseDate.getText().toString();


//                FoodDBHelper foodDBHelper = new FoodDBHelper(this);
//                SQLiteDatabase sqLiteDatabase = foodDBHelper.getWritableDatabase();
//
//                ContentValues row = new ContentValues();
//                row.put(FoodDBHelper.COL_FOOD, foodName);
//                row.put(FoodDBHelper.COL_NATION, nation);
//
//                String whereClause = FoodDBHelper.COL_ID + "=?";
//                String[] whereArgs = new String[] {String.valueOf(food.get_id())};
//
//                int result = sqLiteDatabase.update(foodDBHelper.TABLE_NAME, row, whereClause, whereArgs);

                    movieData.setTitle(movieName);
                    movieData.setActor(actor);
                    movieData.setDirector(directory);
                    movieData.setStory(story);
                    movieData.setReleaseDate(releaseDate);

                    boolean result = manager.modifyMovie(movieData);
                    if(result) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("movie", movieData);
                        setResult(RESULT_OK, resultIntent);
                    }
                    finish();
                }
                break;
            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
//        finish();
    }
}
