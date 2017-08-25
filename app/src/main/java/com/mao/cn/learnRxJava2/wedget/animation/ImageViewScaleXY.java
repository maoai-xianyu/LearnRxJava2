package com.mao.cn.learnRxJava2.wedget.animation;

import android.view.View;

import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.nineoldandroids.animation.ObjectAnimator;


public class ImageViewScaleXY extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1, 0.95f),
                ObjectAnimator.ofFloat(view, "scaleY", 1, 0.95f),
                ObjectAnimator.ofFloat(view, "scaleX", 0.95f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0.95f, 1f)
        );
    }
}
