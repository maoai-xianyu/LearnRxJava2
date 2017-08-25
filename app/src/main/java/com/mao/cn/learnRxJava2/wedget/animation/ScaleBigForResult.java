package com.mao.cn.learnRxJava2.wedget.animation;

import android.view.View;

import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.nineoldandroids.animation.ObjectAnimator;

public class ScaleBigForResult extends BaseEffects {
    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 3f,1f).setDuration(1300),
                ObjectAnimator.ofFloat(view,"scaleY",3f,1f).setDuration(1300),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(1300)

        );
    }
}
