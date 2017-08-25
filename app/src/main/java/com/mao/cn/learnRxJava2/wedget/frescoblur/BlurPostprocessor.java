//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.wedget.frescoblur;

import android.graphics.Bitmap;

import com.facebook.imagepipeline.request.BasePostprocessor;

public class BlurPostprocessor extends BasePostprocessor {
    private int radius = 18;

    public BlurPostprocessor() {
    }

    public BlurPostprocessor(int radius) {
        this.radius = radius;
    }

    public void process(Bitmap bitmap) {
        try {
            Blur.convert(bitmap, this.radius);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
}
