package com.halohoop.bounceprogressbar.interpolators;

import android.view.animation.Interpolator;

/**
 * Created by Pooholah on 2016/8/27.
 */

public class DecelerateAccelerateInterpolator implements Interpolator {
    private float mFactor = 1;

    public DecelerateAccelerateInterpolator() {
    }

    public DecelerateAccelerateInterpolator(float factor) {
        this.mFactor = factor;
    }

    public float getInterpolation(float t) {
        float x = 2.0f * t - 1.0f;
        return 0.5f * (x * x * x + 1.0f);
    }
//    public float getInterpolation(float t) {
////        y=t^2f
//        if (t >= 0 && t <= 0.5f) {
//            return (float) (Math.pow(t + 0.5, 2*mFactor) / 2);
//        } else {
//            return (float) ((1 - Math.pow(1 - (t + 0.5), 2*mFactor)) / 2);
//        }
//    }
}
