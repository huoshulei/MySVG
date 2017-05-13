package o.thor;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.ExposedPathParser;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿守望先锋的loading加载
 * Created by zhangyu on 2016/11/28.
 */

public class AnimationView extends View {
    private static final String TAG = "OWLoadingView";

    private float space;
    //六边形的半径
    private float hexagonRadius;
    //    private int color = Color.parseColor("#ff9900");//默认橙色
//    private Paint paint;
//    private float sin30 = (float) Math.sin(30f * 2f * Math.PI / 360f);
//    private float cos30 = (float) Math.cos(30f * 2f * Math.PI / 360f);
    private ValueAnimator animator;
    //进行显示动画和进行隐藏动画的标志常量
    private final int ShowAnimatorFlag = 0x1137, HideAnimatorFlag = 0x1139;
    private int nowAnimatorFlag = ShowAnimatorFlag;
    //触发下一个动画开始的缩放临界点值
    private final float scaleCritical = 0.7f;


    private List<Path> paths = new ArrayList<>();
    private Canvas mCanvas;
    private Bitmap bitmap;
    private Paint paint;
    private Matrix matrix;
    private int width;
    private int height;

    public AnimationView(Context context) {
        super(context);
        init();
    }

    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        initAnimator();
        paths.add(ExposedPathParser.createPathFromPathData("M102.24,7.22C108.38,1.96 117.55,-0.86 125.13,2.95C132.73,6.41 136.99,14.84 136.7,22.99C136.69,79.99 136.74,137 136.68,194C136.81,204.15 129.88,214.91 119.19,216L115.9,216C110.78,215.4 105.96,213.23 101.9,210.11C84.65,196.95 67.44,183.76 50.2,170.59C40.83,170.25 31.33,171.47 22.08,169.64C9.56,166.76 0.79,154.74 0,142.2L0,78.25C0.23,62.02 15.01,48.11 31.02,47.76C38.91,47.67 46.8,47.77 54.69,47.74C70.55,34.25 86.35,20.68 102.24,7.22M114.46,13.69C112.46,14.43 110.76,15.75 109.13,17.1C94.12,29.76 79.05,42.36 64.02,55.02C62.4,56.36 60.88,57.86 59.05,58.93C49.41,59.4 39.73,58.74 30.09,59.22C22.27,60.3 15.6,66.21 12.67,73.41C11.15,77.1 11.47,81.13 11.46,85.01C11.47,102.68 11.47,120.36 11.46,138.03C11.24,148.98 21.08,159.08 32.03,159.18C39.69,159.28 47.36,159.13 55.03,159.2C56.26,159.12 57.17,160.08 58.11,160.71C74.96,173.92 91.87,187.04 108.74,200.22C111.64,202.44 115.24,204.67 119.05,203.81C123.18,202.12 125.32,197.31 125.29,193.04C125.28,138.36 124.67,83.68 125.44,29.01C126.13,23.96 125.31,18.32 121.32,14.76C119.53,12.93 116.73,12.75 114.46,13.69Z"));
        paths.add(ExposedPathParser.createPathFromPathData("M183.37,49.49C186.51,48.28 189.41,50.4 191.48,52.54C205.59,67.12 216.22,86.34 216.46,107.03C216.8,129.33 205,150.11 189.41,165.33C185.92,168.23 179.96,165.54 179.78,161.02C179.42,157.69 182.41,155.58 184.32,153.33C195.63,140.96 204.63,125.17 204.48,107.98C204.35,89.17 194.07,71.9 180.9,59.09C177.81,56.25 179.41,50.6 183.37,49.49Z"));
        paths.add(ExposedPathParser.createPathFromPathData("M222.16,25.24C224.08,24.34 226.49,24.55 228.17,25.86C231.4,28.24 234.11,31.24 236.67,34.31C252.61,52.43 264.7,74.83 267.23,99.11C269.35,118.53 264.54,138.29 255.18,155.32C248.36,167.88 239.46,179.27 229.43,189.42C226.18,192.72 219.9,190.47 219.39,185.9C218.75,182.2 222.17,179.84 224.28,177.39C241.31,159 254.7,135.57 255.26,109.96C255.92,80.88 240.3,53.77 219.79,34.16C217.41,31.33 218.78,26.58 222.16,25.24Z"));
        paths.add(ExposedPathParser.createPathFromPathData("M264.6,0L267.29,0C270.15,1.1 272.34,3.31 274.52,5.38C290.92,22.57 304.56,42.65 312.89,64.98C318.06,78.82 320.89,93.53 321,108.3L321,110.45C320.76,118.34 319.92,126.22 318.19,133.93C311.81,162.94 295.07,188.58 275.05,210.09C272.6,212.6 270.09,216.45 266.03,215.64C261.81,215.14 259.38,209.53 262.25,206.27C266.96,200.95 271.92,195.85 276.28,190.23C293.58,168.76 306.64,142.8 308.31,114.9C310.12,88.91 301.52,63.13 287.58,41.43C281.62,31.67 274.23,22.89 266.54,14.46C264.23,11.82 260.12,10.01 260.04,6.03C259.86,3.16 262.15,1.02 264.6,0Z"));
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#f18700"));
        CornerPathEffect corEffect = new CornerPathEffect(hexagonRadius * 0.1f);
        paint.setPathEffect(corEffect);
        matrix = new Matrix();
        width = 321;
        height = 216;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 取得想要缩放的matrix参数
        int scale = Math.min(w / width, h / height);
        matrix.postScale(scale, scale);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        if (bitmap == null) {
//            bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
//            mCanvas = new Canvas(bitmap);
//        }
//        bitmap.eraseColor(0);
//        mCanvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        for (Path path : paths) {
            canvas.drawPath(path, paint);
        }
        canvas.concat(matrix);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredWidth;
        int desiredHeight;

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
        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    private void initAnimator() {
        animator = ObjectAnimator.ofInt(0, 10);
        animator.setDuration(100);
        animator.addUpdateListener(animatorUpdateListener);
        animator.setRepeatCount(-1);
    }

    private void resetHexagons() {

    }


//    private void initPaint() {
//        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(color);
//        CornerPathEffect corEffect = new CornerPathEffect(hexagonRadius * 0.1f);
//        paint.setPathEffect(corEffect);
//    }

//    /**
//     * 设置颜色
//     *
//     * @param color
//     */
//    public void setColor(int color) {
//        this.color = color;
//        paint.setColor(color);
//    }

    /**
     * 开始动画
     */
    public void startAnim() {
        initAnimator();
        animator.start();
    }

    /**
     * 中止动画
     */
    public void stopAnim() {
        if (animator == null) return;
        animator.cancel();
        animator.removeAllListeners();
        animator = null;
        nowAnimatorFlag = ShowAnimatorFlag;

        resetHexagons();
        invalidate();
    }

//    /**
//     * 六边形中心
//     */
//    private void initHexagonCenters() {
//        float bigR = (float) ((1.5 * hexagonRadius + space) / cos30);
//        hexagonCenters[0] = new Point(center.x - bigR * sin30, center.y - bigR * cos30);
//        hexagonCenters[1] = new Point(center.x + bigR * sin30, center.y - bigR * cos30);
//        hexagonCenters[2] = new Point(center.x + bigR, center.y);
//        hexagonCenters[3] = new Point(center.x + bigR * sin30, center.y + bigR * cos30);
//        hexagonCenters[4] = new Point(center.x - bigR * sin30, center.y + bigR * cos30);
//        hexagonCenters[5] = new Point(center.x - bigR, center.y);
//
//        for (int i = 0; i < 6; i++) {
//            hexagons[i] = new Hexagon(hexagonCenters[i], hexagonRadius);
//        }
//        hexagons[6] = new Hexagon(center, hexagonRadius);
//        startAnim();
//    }


    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {

//            if (nowAnimatorFlag == ShowAnimatorFlag) {//逐个显示出来
//                hexagons[0].addScale();
//                hexagons[0].addAlpha();
//                for (int i = 0; i < hexagons.length - 1; i++) {
//                    if (hexagons[i].getScale() >= scaleCritical) {
//                        hexagons[i + 1].addScale();
//                        hexagons[i + 1].addAlpha();
//                    }
//                }
//
//                if (hexagons[6].getScale() == 1) {//当最后一个六边形都完全显示时，切换模式，下一轮逐个消失
//                    nowAnimatorFlag = HideAnimatorFlag;
//                }
//
//            } else {//逐个消失
//                hexagons[0].subScale();
//                hexagons[0].subAlpha();
//                for (int i = 0; i < hexagons.length - 1; i++) {
//                    if (hexagons[i].getScale() <= 1 - scaleCritical) {
//                        hexagons[i + 1].subScale();
//                        hexagons[i + 1].subAlpha();
//                    }
//                }
//                if (hexagons[6].getScale() == 0) {//当最后一个六边形都完全消失时，切换模式，下一轮逐个开始显示
//                    nowAnimatorFlag = ShowAnimatorFlag;
//                }
//            }
            invalidate();
        }
    };

//    /**
//     * 六边形
//     */
//    private class Hexagon {
//
//        //缩放值
//        private float scale = 0;
//        //透明度
//        private int alpha = 0;
//        public Point centerPoint;
//        public float radius;
//        //六个顶点
//        private Point[] vertexs = new Point[6];
//        //缩放程度每次改变量 变化范围为[0,1]
//        private final float scaleChange = 0.06f;
//        //透明度每次改变量 变化范围为[0,255]
//        private final int alpahChange = 15;
//
//        public Hexagon(Point centerPoint, float radius) {
//            this.centerPoint = centerPoint;
//            this.radius = radius;
//            calculatePointsPosition();
//        }
//
//        public void drawHexagon(Canvas canvas, Paint paint) {
//            paint.setAlpha(alpha);
//            canvas.drawPath(getPath(), paint);
//        }
//
//        private int calculatePointsPosition() {
//            if (centerPoint == null) {
//                return -1;
//            }
//            //从最上方顺时针数1-6给各顶点标序号 共6个点
//            vertexs[0] = new Point(centerPoint.x, centerPoint.y - radius * scale);
//            vertexs[1] = new Point(centerPoint.x + radius * cos30 * scale, centerPoint.y - radius * sin30 * scale);
//            vertexs[2] = new Point(centerPoint.x + radius * cos30 * scale, centerPoint.y + radius * sin30 * scale);
//            vertexs[3] = new Point(centerPoint.x, centerPoint.y + radius * scale);
//            vertexs[4] = new Point(centerPoint.x - radius * cos30 * scale, centerPoint.y + radius * sin30 * scale);
//            vertexs[5] = new Point(centerPoint.x - radius * cos30 * scale, centerPoint.y - radius * sin30 * scale);
//            return 1;
//        }
//
//
//        private Path getPath() {
//            Path path = new Path();
//            for (int i = 0; i < 6; i++) {
//                if (i == 0)
//                    path.moveTo(vertexs[i].x, vertexs[i].y);
//                else
//                    path.lineTo(vertexs[i].x, vertexs[i].y);
//            }
//            path.close();
//            return path;
//        }
//
//        /**
//         * 设置透明度
//         *
//         * @param alpha
//         */
//        public void setAlpha(int alpha) {
//            this.alpha = alpha;
//        }
//
//        public int getAlpha() {
//            return alpha;
//        }
//
//        /**
//         * 设置缩放比例
//         *
//         * @param scale
//         */
//        public void setScale(float scale) {
//            this.scale = scale;
//            calculatePointsPosition();
//        }
//
//        public void addScale() {
//            if (scale == 1)
//                return;
//
//            scale += scaleChange;
//            scale = scale > 1 ? 1 : scale;
//            calculatePointsPosition();
//        }
//
//        public void subScale() {
//            if (scale == 0) {
//                return;
//            }
//            scale -= scaleChange;
//            scale = scale < 0 ? 0 : scale;
//            calculatePointsPosition();
//        }
//
//        public void addAlpha() {
//            if (alpha == 255) {
//                return;
//            }
//            alpha += alpahChange;
//            alpha = alpha > 255 ? 255 : alpha;
//        }
//
//        public void subAlpha() {
//            if (alpha == 0) {
//                return;
//            }
//            alpha -= alpahChange;
//            alpha = alpha < 0 ? 0 : alpha;
//        }
//
//        /**
//         * 获取当前缩放比例
//         *
//         * @return
//         */
//        public float getScale() {
//            return scale;
//        }
//    }

//    private class Point {
//        public float x, y;
//
//        public Point(float x, float y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        public Point() {
//        }
//    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnim();
        super.onDetachedFromWindow();
    }

    private class SVG {
        Path path;
        Paint paint;
        private int alpha = 0;

        SVG(Path path, Paint paint) {
            paint.setAlpha(alpha);
            this.path = path;
            this.paint = paint;
        }
    }
}
