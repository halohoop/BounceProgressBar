package com.halohoop.bounceprogressbar.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.DecelerateInterpolator;

import com.halohoop.bounceprogressbar.R;
import com.halohoop.bounceprogressbar.interpolators.DampingInterpolator;
import com.halohoop.bounceprogressbar.interpolators.DecelerateAccelerateInterpolator;
import com.halohoop.bounceprogressbar.utils.GeometryUtil;

/**
 * Created by Pooholah on 2016/8/27.
 */

public class BounceProgressBar extends SurfaceView implements SurfaceHolder.Callback {

    private Paint mPaint;
    private float mStrokeWidth;
    private int mLineColor;
    private float mFixBallRadius;
    private int mFixBallColor;
    private float mBouncingBallRadius;
    private int mBouncingBallColor;
    private float mLineWidth;

    private int mState;
    private final int STATE_DOWN = 1;
    private final int STATE_UP = 2;

    private float mDownDistance;
    private float mUpDistance;
    private float mFreeDistance;
    private Path mPath;
    private ValueAnimator mDownControllor;
    private ValueAnimator mUpControllor;
    private ValueAnimator mFreeControllor;
    private AnimatorSet mAnimatorSet;
    private boolean mIsBouncing = false;
    private boolean mNeedUseFreeData = false;
    private boolean mIsAnimationShowing = false;
    private boolean mIsUpControllorDied = false;
    private final float MAX_DISTANCE = 50;

    public BounceProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public BounceProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public BounceProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        getHolder().addCallback(this);
        initAttrs(context, attrs);
        initPaintTool();
        initControllor();
    }

    public void startTotalAnimation() {
        if (mIsAnimationShowing) {
            return;
        }
        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.end();
            mAnimatorSet.cancel();
        }
        mState = -1;
        mIsBouncing = false;
        mIsAnimationShowing = false;
        mNeedUseFreeData = false;
        mIsUpControllorDied = false;

        mAnimatorSet.start();
    }

    public void setBouncingBallColor(int mBouncingBallColor) {
        this.mBouncingBallColor = mBouncingBallColor;
    }

    public void setBouncingBallRadius(float mBouncingBallRadius) {
        this.mBouncingBallRadius = mBouncingBallRadius;
    }

    public void setFixBallColor(int mFixBallColor) {
        this.mFixBallColor = mFixBallColor;
    }

    public void setFixBallRadius(float mFixBallRadius) {
        this.mFixBallRadius = mFixBallRadius;
    }

    public void setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    public void setLineWidth(float mLineWidth) {
        this.mLineWidth = mLineWidth;
    }

    private void initControllor() {
        mDownControllor = ValueAnimator.ofFloat(0, 1);
        mDownControllor.setDuration(500);
        mDownControllor.setInterpolator(new DecelerateInterpolator(1.5f));
        mDownControllor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float downPercent = (float) valueAnimator.getAnimatedValue();
                mDownDistance = MAX_DISTANCE * downPercent;
                postInvalidate();
            }
        });
        mDownControllor.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mState = STATE_DOWN;
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mUpControllor = ValueAnimator.ofFloat(0, 1);
        mUpControllor.setDuration(800);
        mUpControllor.setInterpolator(new DampingInterpolator());
        mUpControllor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float upPercent = (float) valueAnimator.getAnimatedValue();
                mUpDistance = MAX_DISTANCE * upPercent;
                if (mUpDistance >= MAX_DISTANCE) {
                    if (!mFreeControllor.isRunning()) {
                        mFreeControllor.start();
                    }
                }
                postInvalidate();
            }
        });
        mUpControllor.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mState = STATE_UP;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsBouncing = true;
                mIsUpControllorDied = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mFreeControllor = ValueAnimator.ofFloat(0, 6.8f);
        mFreeControllor.setDuration(380);
        mFreeControllor.setInterpolator(new DecelerateAccelerateInterpolator());
        mFreeControllor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float freeValue = (float) valueAnimator.getAnimatedValue();
                mFreeDistance = (34 * freeValue - 5 * freeValue * freeValue) * 1;
                //if freeValue == 0 or freeValue == 6.8,mFreeDistance == 0,
                float percent = mFreeDistance / 57.361153f;
                mFreeDistance = GeometryUtil.evaluateValue(percent,0,MAX_DISTANCE);
                if (mIsUpControllorDied) {
                    //因为还需要绳子有弹性的效果，所以不能立刻就重绘界面
                    postInvalidate();
                }
            }
        });
        mFreeControllor.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                mNeedUseFreeData = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsBouncing = false;
                mIsAnimationShowing = false;
                //循环
                startTotalAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(mDownControllor).before(mUpControllor);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsAnimationShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BounceProgressBar);
        //bouncing line stroke width and color
        mLineColor = typedArray.getColor(R.styleable.BounceProgressBar_LineColor, Color.WHITE);
        mStrokeWidth = typedArray.getFloat(R.styleable.BounceProgressBar_StrokeWidth, 10);
        mLineWidth = typedArray.getFloat(R.styleable.BounceProgressBar_LineWidth, 150);
        //two fixed point
        mFixBallRadius = typedArray.getFloat(R.styleable.BounceProgressBar_FixRadius, 10);
        mFixBallColor = typedArray.getColor(R.styleable.BounceProgressBar_FixBallColor, Color.BLACK);
        //bouncing ball
        mBouncingBallRadius = typedArray.getFloat(R.styleable.BounceProgressBar_BouncingBallRadius, 15);
        mBouncingBallColor = typedArray.getColor(R.styleable.BounceProgressBar_BouncingBallColor, Color.BLACK);
    }

    private void initPaintTool() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        //线帽,圆形
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //draw balls and lines
        mPath.reset();
        if (mState == STATE_DOWN) {
            //draw lines
            mPaint.setColor(mLineColor);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            mPath.moveTo(getWidth() / 2 - mLineWidth / 2, getHeight() / 2);
            mPath.quadTo(
                    (float) (getWidth() / 2 - mLineWidth / 2 + mLineWidth * 0.375), getHeight() / 2 + mDownDistance,
                    getWidth() / 2, getHeight() / 2 + mDownDistance
            );
            mPath.quadTo(
                    (float) (getWidth() / 2 + mLineWidth / 2 - mLineWidth * 0.375), getHeight() / 2 + mDownDistance,
                    getWidth() / 2 + mLineWidth / 2, getHeight() / 2
            );
            canvas.drawPath(mPath, mPaint);
            //draw bounce ball
            mPaint.setColor(mBouncingBallColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2 + mDownDistance - mBouncingBallRadius, mBouncingBallRadius, mPaint);
        } else if (mState == STATE_UP) {
            //draw lines
            mPaint.setColor(mLineColor);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            mPath.moveTo(getWidth() / 2 - mLineWidth / 2, getHeight() / 2);
            mPath.quadTo(
                    (float) (getWidth() / 2 - mLineWidth / 2 + mLineWidth * 0.375), getHeight() / 2 + (MAX_DISTANCE - mUpDistance),
                    getWidth() / 2, getHeight() / 2 + (MAX_DISTANCE - mUpDistance)
            );
            mPath.quadTo(
                    (float) (getWidth() / 2 + mLineWidth / 2 - mLineWidth * 0.375), getHeight() / 2 + (MAX_DISTANCE - mUpDistance),
                    getWidth() / 2 + mLineWidth / 2, getHeight() / 2
            );
            canvas.drawPath(mPath, mPaint);
            if (!mIsBouncing) {
                //draw bounce ball
                if (!mNeedUseFreeData) {
                    mPaint.setColor(mBouncingBallColor);
                    mPaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2 + (MAX_DISTANCE - mUpDistance) - mBouncingBallRadius,
                            mBouncingBallRadius, mPaint);
                } else {
                    mPaint.setColor(mBouncingBallColor);
                    mPaint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2 - mFreeDistance - mBouncingBallRadius,
                            mBouncingBallRadius, mPaint);
                }
            } else {
                //use field mFreeDistance to draw the ball
                //draw bounce ball
                mPaint.setColor(mBouncingBallColor);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2 - mFreeDistance - mBouncingBallRadius,
                        mBouncingBallRadius, mPaint);
            }
        }
        //draw two fixed balls
        mPaint.setColor(mFixBallColor);
        canvas.drawCircle(getWidth() / 2 - mLineWidth / 2, getHeight() / 2, mFixBallRadius, mPaint);
        canvas.drawCircle(getWidth() / 2 + mLineWidth / 2, getHeight() / 2, mFixBallRadius, mPaint);

        super.onDraw(canvas);//bg maybe
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //surfaceview高效绘制套路
        Canvas canvas = surfaceHolder.lockCanvas();
        draw(canvas);//最终会调用到onDraw
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
