package com.mao.cn.learnRxJava2.wedget.animation;

import android.view.View;

import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.nineoldandroids.animation.ObjectAnimator;

public class NewsPaper extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "rotation", 1080,720,360,0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration*3/2),
                ObjectAnimator.ofFloat(view, "scaleX", 0.1f, 0.5f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view,"scaleY",0.1f,0.5f,1).setDuration(mDuration)

        );
    }
}
