package com.halohoop.bounceprogressbar.interpolators;


import android.view.animation.Interpolator;

/**
 * Created by Pooholah on 2016/8/27.
 */

public class DampingInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        return (float) (1 - Math.exp(-3 * input) * Math.cos(10 * input));
    }
}
