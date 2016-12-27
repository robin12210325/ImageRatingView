package com.hard.imageratingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 1 on 2016/12/25.
 */

public class ImageRatingView extends View {
    /**
     * 默认评分最大值
     */
    private final static int DEFAULT_MAX_COUNT = 8;
    /**
     * 默认评分最小跨度
     */
    private final static float DEFAULT_MIN_STEP = 2f;
    /**
     * 默认图片间隔(dp)
     */
    private final static int DEFAULT_SPAN_SIZE = 0;
    /**
     * 默认分数
     */
    private final static float DEFAULT_RATING =5.1f;

    /**
     * 评分最大值
     */
    private int mMaxCount;
    /**
     * 已评分的图片
     */
    private Bitmap mFront;
    /**
     * 未评分的图片
     */
    private Bitmap mBack;
    /**
     * 评分最小间隔
     */
    private float mMinStep;
    /**
     * 各个图片之间的间距
     */
    private int mSpanSize;
    /**
     * 评分
     */
    private float mRating;
    /**
     * ImageRatingView的宽度
     */
    private int mViewWidth;
    /**
     * ImageRatingView的高度
     */
    private int mViewHeight;
    /**
     * 图片展示出来的宽度
     */
    private int mBitmapDstWidth;
    /**
     * 图片展示出来的高度
     */
    private int mBitmapDstHeight;
    /**
     * 是否可触摸操作
     */
    private boolean mTouchable;

    public ImageRatingView(Context context) {
        this(context, null);
    }

    public ImageRatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        correctedRatingValue();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageRatingView);
        mMinStep = a.getFloat(R.styleable.ImageRatingView_minStep, DEFAULT_MIN_STEP);
        mMaxCount = a.getInt(R.styleable.ImageRatingView_starsCount, DEFAULT_MAX_COUNT);
        mFront = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.ImageRatingView_starsImage, R.drawable.ic_star_amber_a200_24dp));
        mBack = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.ImageRatingView_starsBackImage, R.drawable.ic_star_white_24dp));
        mSpanSize = a.getDimensionPixelSize(R.styleable.ImageRatingView_spanSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        DEFAULT_SPAN_SIZE, getResources().getDisplayMetrics()));
        mRating = a.getFloat(R.styleable.ImageRatingView_rating, DEFAULT_RATING);
        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mFront.getWidth()!= mBack.getWidth()|| mFront.getHeight()!= mBack.getHeight()){
            new Throwable("the front and back bitmap's width or height must be equal");
            return;
        }

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
            mBitmapDstWidth =(mViewWidth-mSpanSize*(mMaxCount-1))/ mMaxCount;
        } else {
            //如果是wrap_content则等于bitmap数量*图片宽度+间隔宽度*（bitmap数量-1）
            mViewWidth = mMaxCount * mFront.getWidth()+mSpanSize*(mMaxCount-1);
            mBitmapDstWidth = mFront.getWidth();
        }

        specMode =MeasureSpec.getMode(heightMeasureSpec);
        if(specMode==MeasureSpec.EXACTLY){
            mViewHeight=MeasureSpec.getSize(heightMeasureSpec);
            mBitmapDstHeight=mViewHeight>mFront.getHeight()?mFront.getHeight():mViewHeight;
        }else{
            mViewHeight= mFront.getHeight();
            mBitmapDstHeight = mFront.getHeight();
        }
        setMeasuredDimension(mViewWidth, mViewHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect src=new Rect(0,0, mFront.getWidth(), mFront.getHeight());
        drawFront(canvas,src);
        drawBack(canvas,src);
        if(mRating!=mMaxCount){
            drawLastFront(canvas);
        }
    }



    /**
     * 画出已打分的部分,(除了最后一个)
     * @param canvas
     * @param src
     */
    private void drawFront(Canvas canvas,Rect src) {
        for (int i=0; i<(int)mRating;i++){
            int left=i*(mBitmapDstWidth +mSpanSize);
            int right=(i+1)* mBitmapDstWidth +i*mSpanSize;
            Rect dst=new Rect(left,0,right, mBitmapDstHeight);
            canvas.drawBitmap(mFront,src,dst,null);
        }
    }

    /**
     * 画出未打分的部分
     * @param canvas
     * @param src
     */
    private void drawBack(Canvas canvas,Rect src) {
        for (int i = (int)(mRating+1); i< mMaxCount; i++){
            int left=i*(mBitmapDstWidth +mSpanSize);
            int right=(i+1)* mBitmapDstWidth +i*mSpanSize;
            Rect dst=new Rect(left,0,right, mBitmapDstHeight);
            canvas.drawBitmap(mBack,src,dst,null);
        }
    }

    /**
     * 打分的最后那个特殊处理
     * @param canvas
     */
    private void drawLastFront(Canvas canvas) {
        //先画front部分
        //rating小数点前的值。
        int rating=(int)mRating;
        float frontPart=mRating-rating;
        int frontWidth= (int) (frontPart* mFront.getWidth());
        Rect srcFront=new Rect(0,0,frontWidth,  mFront.getHeight());
        int leftFront=rating*(mBitmapDstWidth +mSpanSize);
        int rightFront= (int) ((rating+frontPart)* mBitmapDstWidth +rating*mSpanSize);
        Rect dstFront=new Rect(leftFront,0,rightFront, mBitmapDstHeight);
        canvas.drawBitmap(mFront,srcFront,dstFront,null);

        //back部分
        int backWidth= (int) (frontPart* mFront.getWidth());
        Rect srcBack=new Rect(backWidth,0, mFront.getWidth(), mFront.getHeight());
        int leftBack= (int) ((rating+frontPart)* mBitmapDstWidth +rating*mSpanSize);
        int rightBack= (rating+1)* mBitmapDstWidth +rating*mSpanSize;
        Rect dstBack=new Rect(leftBack,0,rightBack, mBitmapDstHeight);
        canvas.drawBitmap(mBack,srcBack,dstBack,null);
    }


    /**
     * 修正rating值
     */
    private void correctedRatingValue() {
        if(mRating>mMaxCount){
            mRating=mMaxCount;
        }else if(mRating<0){
            mRating=0;
        }
        float pointAfter=mRating%mMinStep>mMinStep/2?mMinStep:0;
        float pointFront=mRating-mRating%mMinStep;
        mRating = pointAfter+pointFront;
    }

    public void setRating(float rating){
        this.mRating=rating;
        correctedRatingValue();
        invalidate();
    }
}

