package com.mao.cn.learnRxJava2.wedget.animation;

import android.view.View;

import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.nineoldandroids.animation.ObjectAnimator;


public class SlideBottomIn extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 360f, 0f).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration*3/2)

        );
    }
}
