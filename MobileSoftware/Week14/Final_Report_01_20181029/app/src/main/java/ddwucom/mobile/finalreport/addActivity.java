package ddwucom.mobile.finalreport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class addActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etActor;
    EditText etDirectory;
    EditText etStory;
    EditText etReleaseDate;
    MovieDBManager movieManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        etTitle = findViewById(R.id.etTitle);
        etActor = findViewById(R.id.etActor);
        etDirectory = findViewById(R.id.etDirectory);
        etStory = findViewById(R.id.etStory);
        etReleaseDate = findViewById(R.id.etReleaseDate);

        movieManager = new MovieDBManager(this);

        RatingBar rb = (RatingBar)findViewById(R.id.ratingBar);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(addActivity.this, "이 영화의 별점 : " + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onClick(View v) {
//        버튼의 종류에 따라 결과 설정 후 finish()
        switch (v.getId()) {
            case R.id.btnUpdate:
                if(etTitle.getText().toString().equals("") || etTitle.getText().toString() == null) {
                    Toast.makeText(addActivity.this, "영화 제목은 필수 항목입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    MovieData newMovie = new MovieData(etTitle.getText().toString(),
                            etActor.getText().toString(), etDirectory.getText().toString(),
                            etStory.getText().toString(), etReleaseDate.getText().toString());
                    boolean result = movieManager.addMovie(newMovie);

                    if (result) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("movie", newMovie);
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
    }


}
