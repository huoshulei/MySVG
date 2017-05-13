package o.thor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者： huoshulei
 * 时间：  2017/5/11.
 */

public class PlayView extends View {
    /**
     * view 尺寸
     */
    private final float width = 321;
    private final float height = 216;
    /**
     * 中心点
     */
    private float centerX;
    private float centerY;

    private Paint paint;
    //屏幕密度
    private float density;

    private List<Path> paths = new ArrayList<>();
    private int count = 0;
    private boolean isAnim;
    private float scaleX;
    private float scaleY;

    public PlayView(Context context) {
        super(context);
        init();
    }

    public PlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        density = getResources().getDisplayMetrics().density;
        initPaint();
    }


    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#f18700"));
        paint.setStrokeWidth(dip(4));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        /*
      内容尺寸
     */
        float contentW = w - getPaddingLeft() - getPaddingRight();
        centerX = contentW / 2f + getPaddingLeft();
        float contentH = h - getPaddingTop() - getPaddingBottom();
        centerY = contentH / 2f + getPaddingTop();
        float fw = contentW / width;
        float fh = contentH / height;
        scaleX = fw / fh;
        scaleY = fh / fw;
        scaleX = scaleX > 1 ? 1 : scaleX;
        scaleY = scaleY > 1 ? 1 : scaleY;
        paint.setStrokeWidth(dip(4f) * Math.min(fw, fh));
        initPath();
    }

    private void initPath() {
        paths.clear();
        paths.add(leftPath());
        paths.add(line1());
        paths.add(line2());
        paths.add(line3());
        count = paths.size();
    }

    /**
     * 开始动画
     */
    public void startAnim() {
        if (isAnim) stopAnim();
        else handler.post(runnable);
    }

    public boolean isAnim() {
        return isAnim;
    }

    /**
     * 中止动画
     */
    public void stopAnim() {
        handler.removeCallbacks(runnable);
        isAnim = false;
        count = paths.size();
        invalidate();
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            isAnim = true;
            count++;
            if (count >= paths.size())
                count = 0;
            invalidate();
            handler.postDelayed(this, 250);
        }
    };


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        startAnim();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnim();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i <= count; i++) {
            if (i < paths.size())
                canvas.drawPath(paths.get(i), paint);
        }
    }

    private Path line1() {
        Path path = new Path();
        path.moveTo(centerX + centerX * 0.125f * scaleY, centerY - centerY * scaleX * 0.5f);
        path.quadTo(centerX + centerX * 0.438f * scaleY, centerY, centerX + centerX * scaleY * 0.125f, centerY + centerY * scaleX * 0.5f);
        return path;
    }

    private Path line2() {
        Path path = new Path();
        path.moveTo(centerX + centerX * scaleY * 0.406f, centerY - centerY * scaleX * 0.685f);
        path.quadTo(centerX + centerX * scaleY * 0.78f, centerY, centerX + centerX * scaleY * 0.406f, centerY + centerY * scaleX * 0.685f);
        return path;
    }

    private Path line3() {
        Path path = new Path();
        path.moveTo(centerX + centerX * scaleY * 0.685f, centerY - centerY * scaleX * 0.87f);
        path.quadTo(centerX + centerX * scaleY * 1.18f, centerY, centerX + centerX * scaleY * 0.685f, centerY + centerY * scaleX * 0.87f);
        return path;
    }

    /**
     * w=321 /3=107 /4=80.25
     * h=216 /12=18 /4=54 /2.3=94 /2.4f=90
     * cx=160.5 *0.65=104 *0.8=128
     * cy=108
     */
    private Path leftPath() {
        Path path = new Path();
        path.moveTo(centerX - centerX * scaleY * 0.9f, centerY - centerY * scaleX * 0.23f);
        path.quadTo(centerX - centerX * scaleY * 0.9f, centerY - centerY * scaleX * 0.5f, centerX - centerX * scaleY * 0.75f, centerY - centerY * scaleX * 0.5f);
        path.lineTo(centerX - centerX * scaleY * 0.65f, centerY - centerY * scaleX * 0.5f);
        path.lineTo(centerX - centerX * scaleY * 0.35f, centerY - centerY * scaleX * 0.87f);
        path.quadTo(centerX - centerX * scaleY * 0.2f, centerY - centerY * scaleX, centerX - centerX * scaleY * 0.2f, centerY);
        path.quadTo(centerX - centerX * scaleY * 0.2f, centerY + centerY * scaleX, centerX - centerX * scaleY * 0.35f, centerY + centerY * scaleX * 0.87f);
        path.lineTo(centerX - centerX * scaleY * 0.65f, centerY + centerY * scaleX * 0.5f);
        path.lineTo(centerX - centerX * scaleY * 0.75f, centerY + centerY * scaleX * 0.5f);
        path.quadTo(centerX - centerX * scaleY * 0.9f, centerY + centerY * scaleX * 0.5f, centerX - centerX * scaleY * 0.9f, centerY + centerY * scaleX * 0.23f);
        path.close();
        return path;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float desiredWidth;
        float desiredHeight;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            desiredWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            desiredWidth = Math.min(width, widthSize);
        } else {
            //Be whatever you want
            desiredWidth = width;
        }
        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            desiredHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            desiredHeight = Math.min(height, heightSize);
        } else {
            //Be whatever you want
            desiredHeight = height;
        }
        //MUST CALL THIS
        setMeasuredDimension((int) desiredWidth, (int) desiredHeight);
    }

    public float dip(float dpValue) {
        return (dpValue * density + 0.5f);
    }
}
