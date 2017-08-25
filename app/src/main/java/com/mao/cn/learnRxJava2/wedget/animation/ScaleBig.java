package com.mao.cn.learnRxJava2.wedget.animation;

import android.view.View;

import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.nineoldandroids.animation.ObjectAnimator;


public class ScaleBig extends BaseEffects {
    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.0f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view,"scaleY",0.8f,1.0f,1).setDuration(mDuration)
        );
    }
}
