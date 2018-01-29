package com.vv.resourcebase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.vv.resourcebase.R;


/**
 * 创建日期: 16/2/16 下午9:38
 * 作者:wanghao
 * 描述: 自定义 带圆角的直线,只可以画横线竖线 原来是 画园+直线+圆
 * xmlns:aa="http://schemas.android.com/apk/res-auto"
 * <p/>
 * <wang.com.myline.myline
 * android:layout_width="10"
 * android:layout_height="100"
 * aa:linecolor="@color/colorPrimaryDark"
 * android:layout_centerVertical="true"
 * android:layout_centerHorizontal="true" />
 * <p/>
 * 添加配置
 * <declare-styleable name="customLine">
 * <attr name="linecolor" format="color"/>
 * </declare-styleable>
 */
public class HorizontalRoundCornerBar extends View {

    private Paint mPaint;
    private int color;//线的颜色
    private int mWidth;
    private int mHeight;
    float density;

    public void setColor(int color) {
        this.color = color;

        invalidate();
    }

    public HorizontalRoundCornerBar(Context context) {
        this(context, null);
    }

    public HorizontalRoundCornerBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalRoundCornerBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        density = getContext().getResources().getDisplayMetrics().density;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.customLine);
        color = a.getColor(R.styleable.customLine_linecolor, 0);
        a.recycle();


        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(color);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(drawlint(), 0, 0, paint);

    }


    public Bitmap drawlint() {

        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas mcanvas = new Canvas(bitmap);
        mcanvas.save();
        if (mWidth > mHeight) {//画横线
            int r = mHeight / 2;
            mcanvas.drawCircle(r, r, r, mPaint);//画圆
            //设置线宽
            mPaint.setStrokeWidth(mHeight * density);
            RectF f = new RectF(r, 0, mWidth - r, mHeight);
            mcanvas.drawRect(f, mPaint);
            mPaint.setStrokeWidth(1);
            mcanvas.drawCircle(mWidth - r, r, r, mPaint);
        } else if (mWidth <= mHeight) {//画竖线
            int r = mWidth / 2;
            mcanvas.drawCircle(r, r, r, mPaint);
            //设置线宽
            mPaint.setStrokeWidth(mWidth * density);
            RectF f = new RectF(0, r, mWidth, mHeight - r);
            mcanvas.drawRect(f, mPaint);
            mPaint.setStrokeWidth(1);
            mcanvas.drawCircle(r, mHeight - r, r, mPaint);
        }
        mcanvas.restore();
        return bitmap;
    }

    /**
     * 计算控件的高度和宽度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) // match_parent , accurate
        {
            mWidth = specSize;
        } else {
            // 由图片决定的宽
            int desireByImg = 1;
            if (specMode == MeasureSpec.AT_MOST) // wrap_content
            {
                mWidth = Math.min(desireByImg, specSize);
            } else {

                mWidth = desireByImg;
            }
        }

        /***
         * 设置高度
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) // match_parent , accurate
        {
            mHeight = specSize;
        } else {

            int desire = 1;

            if (specMode == MeasureSpec.AT_MOST) // wrap_content
            {
                mHeight = Math.min(desire, specSize);
            } else
                mHeight = desire;
        }

        setMeasuredDimension(mWidth, mHeight);

    }

}
