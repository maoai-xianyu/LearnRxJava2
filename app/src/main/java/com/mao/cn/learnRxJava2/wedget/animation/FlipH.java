package com.mao.cn.learnRxJava2.wedget.animation;

import android.view.View;

import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.nineoldandroids.animation.ObjectAnimator;

public class FlipH  extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "rotationY", -90, 0).setDuration(mDuration)

        );
    }
}
