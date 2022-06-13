package ddwucom.mobileapplication.breadtrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()) {
            case R.id.btnOpenAllBakery:
                intent = new Intent(this,AllBakeryActivity.class);
                break;
            case R.id.btnAddNewBakery:
                intent = new Intent(this, InsertBakeryActivity.class);
                break;
            case R.id.btnSearchBakery:
                intent = new Intent(this,SearchBakeryActivity.class);
                break;
        }
        if(intent != null)
            startActivity(intent);
    }
}