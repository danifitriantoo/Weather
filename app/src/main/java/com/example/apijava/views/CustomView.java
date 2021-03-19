package com.example.apijava.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class CustomView extends View {

    private int height, width = 0;
    private int paddding = 0;
    private int fontsize = 0;
    private int numeralSpacing = 0;
    private int handTruncation, hourhandTrancation = 0;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;
    private int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12};
    private Rect rect = new Rect();

    public CustomView(Context context) {
        super(context);

    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void iniClock() {
        height = getHeight();
        width = getWidth();
        paddding = numeralSpacing + 50;
        fontsize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,13,
                getResources().getDisplayMetrics());
        int min = Math.min(height,width);
        radius = min / 2 - paddding;
        handTruncation = min / 20;
        paint = new Paint();
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            iniClock();
        }

        canvas.drawColor(Color.BLACK);
        drawCircle(canvas);
        drawCenter(canvas);
        drawaNumeral(canvas);
        drawHands(canvas);

        postInvalidateDelayed(500);
        invalidate();
    }

    private void drawHands(Canvas canvas, double loc, boolean isHour) {
        double angle = Math.PI * loc / 25 - Math.PI / 2;
        int handRadius = isHour ? radius - handTruncation : radius - handTruncation;
        canvas.drawLine(width / 2, height / 2,
                (float) (width / 2 + Math.cos(angle) * handRadius),
                (float) (width / 2 + Math.sin(angle) * handRadius),
                paint);
    }

    private void drawHands(Canvas canvas) {
        Calendar c = Calendar.getInstance();
        float hour = c.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;
        drawHands(canvas, (hour + c.get(Calendar.MINUTE) / 60) * 5f, true);
        drawHands(canvas, c.get(Calendar.MINUTE), false);
        drawHands(canvas, c.get(Calendar.SECOND), false);

    }


    private void drawaNumeral(Canvas canvas) {
        paint.setTextSize(fontsize);
        for(int number : numbers) {
            String tmp = String.valueOf(number);
            paint.getTextBounds(tmp, 0, tmp.length(), rect);
            double angle = Math.PI / 6 * (number - 3);
            int x = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
            int y = (int) (height / 2 + Math.sin(angle) * radius + rect.height() / 2);
            canvas.drawText(tmp, x, y, paint);
            
        }
    }

    private void drawCenter(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, 12 , paint);
    }

    private void drawCircle(Canvas canvas) {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, radius +paddding -10, paint);
    }
}
