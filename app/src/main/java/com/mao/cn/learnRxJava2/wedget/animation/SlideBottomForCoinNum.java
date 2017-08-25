package com.mao.cn.learnRxJava2.wedget.animation;

import android.view.View;

import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.nineoldandroids.animation.ObjectAnimator;

public class SlideBottomForCoinNum extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 0, -20).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(mDuration*3/2)

        );
    }
}
