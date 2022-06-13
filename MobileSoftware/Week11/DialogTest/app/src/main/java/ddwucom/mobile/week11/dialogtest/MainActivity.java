package ddwucom.mobile.week11.dialogtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int selectedIndex = 0;
    boolean[] selectedItems = {false,false,false,false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v){

        final String[] foodsArray = {"라면", "김밥", "떡볶이", "오뎅"};
        final ConstraintLayout orderLayout = (ConstraintLayout) View.inflate(this, R.layout.order_layout, null);
        switch(v.getId()) {

            case R.id.button:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("대화상자 제목")
//                            .setMessage("대화상자 메세지")
//                            .setItems(foodsArray, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
////                                    String[] foods = getResources().getStringArray(R.array.foods);
//                                    Toast.makeText(MainActivity.this,foodsArray[which], Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .setSingleChoiceItems(R.array.foods, selectedIndex, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    selectedIndex = which;
//                                }
//                            })
//                            .setMultiChoiceItems(R.array.foods, selectedItems, new DialogInterface.OnMultiChoiceClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                                    selectedItems[which] = isChecked;
//                                }
//                            })
                            .setView(orderLayout)
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("확인버튼",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){
//                            String[] foods = getResources().getStringArray(R.array.foods);
//                            StAring result = "선택 : ";
//                            for(int i = 0; i < selectedItems.length; i++){
//                                if(selectedItems[i]){//true면 선택이 된 것
//                                    result += foods[i];
//                                }
//                            }
                            EditText etProduct = orderLayout.findViewById(R.id.etProduct);
                            EditText etQuantity = orderLayout.findViewById(R.id.etQuantity);
                            CheckBox cbPayment = orderLayout.findViewById(R.id.cbPayment);

                            String result = etProduct.getText() + ": " + etQuantity.getText() + ":" + cbPayment.isChecked();
                            //그냥 this하면 DialogInterface의 객체가 들어오게 됨
                            Toast.makeText(MainActivity.this, result + "을(를) 선택.", Toast.LENGTH_SHORT).show();
                        }
                    })
                            .setNeutralButton("대기버튼",null)//대화상자가 그냥 닫히게만 하려면 되면 null을 채우면됨
                            .setNegativeButton("취소버튼",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish(); //Activity가 종료됨
                        }
                    });
//                    .setCancelable(false);//백버튼, 외부화면 클릭 해도 안없어짐
                    //실제 builder에게 Dialog를 생성해서 화면에 보여줘라
//                    .show();
                Dialog dlg = builder.create(); //대화상자 생성, 표시X
                dlg.setCanceledOnTouchOutside(false);//외부 화면클릭하면 안꺼지고, back버튼 누르면 없어짐

                dlg.show();
                break;
        }
        finish();
    }
}