package ddwucom.mobile.week06.exam02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MyView extends View {

    float posX;
    float posY;
    float r;
    int paintColor = Color.CYAN;

    private void init(){
        posX = 100;
        posY = 100;
        r = 100;
    }
    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public float getCircleX() { return posX; }
    public float getPosY(){ return posY; }
    public void setPosX(float posX){ this.posX = posX; }
    public void setPosY(float posY){ this.posY = posY; }
    public void setColor(int paintColor) { this.paintColor = paintColor; }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);

        Paint paint = new Paint();
        paint.setColor(paintColor);

        canvas.drawCircle(posX, posY, r, paint);
    }

    @Override
    //뷰에는 이미 onTouchEvent가 존재한다!
    //이벤트 핸들러 / 개발자가 CustomView 를 작성할 때 사용할 때만 가능
    //상속 메소드 재정의

    //View안의 onTouchEvent를 재정의 
    //뷰 자체의 콜백 메소드 구현
    public boolean onTouchEvent(MotionEvent event) {
        setPosX(event.getX());
        setPosY(event.getY());

        invalidate();
        return true;
    }

}
