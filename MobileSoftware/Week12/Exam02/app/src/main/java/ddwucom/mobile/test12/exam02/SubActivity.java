package ddwucom.mobile.test12.exam02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    EditText etFood;
    EditText etNation;
    Food newFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        etFood = findViewById(R.id.et_food_name);
        etNation = findViewById(R.id.et_nation);
        etFood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                etFood.setText("");
            }
        });
        etNation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNation.setText("");
            }
        });
    }
    public void onClick(View v) {
//        버튼의 종류에 따라 결과 설정 후 finish()
        Intent intent = new Intent();
//        intent.putExtra("food","음식이름");
//        intent.putExtra("nation", "나라이름");
        switch(v.getId()){
            case R.id.btn_add:
                String food = etFood.getText().toString();
                String nation = etNation.getText().toString();
                newFood = new Food(food,nation);
                intent.putExtra("food",newFood);
                setResult(RESULT_OK, intent);
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        finish();
    }
}

