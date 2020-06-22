package com.example.democreatefolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;

@SuppressLint("AppCompatCustomView")
public class AddImage extends ImageView {

    private Paint mPaint;
    private Bitmap flip_Bitmap;
    private Bitmap delete_Bitmap;
    private Bitmap opacity_Bitmap;
    private Bitmap resize_Bitmap;
    private DisplayMetrics displayMetrics;
    private int mScrreenHeight, mScreenWidth;
    private Rect rect_flip;
    private Rect rect_delete;
    private Rect rect_opacity;
    private Rect rect_resize;
    private Matrix matrix;
    private Bitmap mBitmap;

    public AddImage(Context context) {
        super(context);
        init();
    }

    public AddImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2.0f);
        displayMetrics = getResources().getDisplayMetrics();
        mScrreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        rect_flip = new Rect();
        rect_delete = new Rect();
        rect_resize = new Rect();
        rect_opacity = new Rect();
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        flip_Bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_flip);
        delete_Bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_delete);
        opacity_Bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_color);
        resize_Bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_color);

        rect_flip.left = 0;
        rect_flip.right = flip_Bitmap.getWidth();
        rect_flip.top = 0;
        rect_flip.bottom = flip_Bitmap.getHeight();

        rect_delete.left = this.getWidth() - delete_Bitmap.getWidth();
        rect_delete.right = rect_delete.left + delete_Bitmap.getWidth();
        rect_delete.top = 0;
        rect_delete.bottom = delete_Bitmap.getHeight();

        rect_opacity.left = 0;
        rect_opacity.right = opacity_Bitmap.getWidth();
        rect_opacity.top = this.getHeight() - opacity_Bitmap.getHeight();
        rect_opacity.bottom = rect_opacity.top + opacity_Bitmap.getHeight();

        rect_resize.left = this.getWidth() - resize_Bitmap.getWidth();
        rect_resize.right = rect_resize.left + resize_Bitmap.getWidth();
        rect_resize.top = this.getHeight() - resize_Bitmap.getHeight();
        rect_resize.bottom = rect_resize.top + resize_Bitmap.getHeight();

        float startX_lineTop = rect_flip.left + rect_flip.width() / 2;
        float startY_lineTop = rect_flip.top + rect_flip.height() / 2;
        float stopX_lineTop = rect_delete.left + rect_delete.width() / 2;
        float stopY_lineTop = rect_delete.top + rect_delete.height() / 2;

        float startX_lineLeft = rect_flip.left + rect_flip.width() / 2;
        float startY_lineLeft = rect_flip.top + rect_flip.height() / 2;
        float stopX_lineLeft = rect_opacity.left + rect_opacity.width() / 2;
        float stopY_lineLeft = rect_opacity.top + rect_opacity.height() / 2;

        float startX_lineRight = rect_delete.left + rect_delete.width() / 2;
        float startY_lineRight = rect_delete.top + rect_delete.height() / 2;
        float stopX_lineRight = rect_resize.left + rect_resize.width() / 2;
        float stopY_lineRight = rect_resize.top + rect_resize.height() / 2;

        canvas.drawLine(startX_lineTop, startY_lineTop, stopX_lineTop, stopY_lineTop, mPaint);
        canvas.drawLine(startX_lineLeft, startY_lineLeft, stopX_lineLeft, stopY_lineLeft, mPaint);
        canvas.drawLine(startX_lineRight, startY_lineRight, stopX_lineRight, stopY_lineRight, mPaint);
        canvas.drawLine(rect_opacity.left + rect_opacity.width() / 2, rect_opacity.top + rect_opacity.height() / 2,
                rect_resize.left + rect_resize.width() / 2, rect_resize.top + rect_resize.height() / 2,
                mPaint);

        canvas.drawBitmap(delete_Bitmap, null, rect_delete, null);
        canvas.drawBitmap(flip_Bitmap, null, rect_flip, null);
        canvas.drawBitmap(opacity_Bitmap, null, rect_opacity, null);
        canvas.drawBitmap(resize_Bitmap, null, rect_resize, null);

    }

    private Bitmap rotateBitmap(Bitmap source) {
        matrix.postScale(-1, 1, source.getWidth()/2f, source.getHeight()/2f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:

                break;
        }
        return false;
    }

    public boolean isCheckedButton(Rect rect, MotionEvent event){
        int left = rect.left;
        int righr = rect.right;
        int top = rect.top;
        int bottom = rect.bottom;
        return false;
    }

    public void setBitmap(Bitmap bitmap){
        matrix.reset();
        mBitmap = bitmap;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float initScale = (0.5f + 1.2f) / 2;
        matrix.postScale(initScale, initScale, w / 2, h / 2);
        matrix.postTranslate(displayMetrics.widthPixels / 2 - w / 2, displayMetrics.heightPixels / 2 - h / 2);
        setImageBitmap(mBitmap);
        invalidate();
    }

    @Override
    public void setImageResource(int resId) {
        setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.youtube));
    }
}
