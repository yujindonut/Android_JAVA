package ddwucom.mobile.week05.exam02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {

    int circleX;
    int circleY;
    int circleR;
    boolean flag = false;
    
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

    //초기화하는 부분
    private void init(){
        circleX = 100;
        circleY = 100;
        circleR = 80;
    }

    public int getCircleX() {
        return circleX;
    }

    public void setCircleX(int circleX) {
        this.circleX = circleX;
    }

    public int getCircleY() {
        return circleY;
    }

    public void setCircleY(int circleY) {
        this.circleY = circleY;
    }

    public int getCircleR() { return circleR; }

    public void setCircleR(int circleR) { this.circleR = circleR; }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.YELLOW);

        Paint pnt = new Paint();
        pnt.setColor(Color.BLUE);

        //버튼을 눌러야지 원이 생기게끔 설정해야함
        if(flag == true){
            canvas.drawCircle(circleX,circleY,circleR,pnt);
        }
        flag = true;
    }
}
