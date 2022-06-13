package ddwucom.mobile.week05.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCustomView extends View{

    int y = 100;

    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCircleY(int y) {
        this.y = y;
    }
    public int getCircleY(){
        return y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);

        Paint pnt = new Paint();
        pnt.setColor(Color.BLUE);

        canvas.drawCircle(100,y,80,pnt);
    }
}
