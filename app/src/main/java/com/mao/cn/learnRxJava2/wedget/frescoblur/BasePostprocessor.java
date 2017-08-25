//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.wedget.frescoblur;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.nativecode.Bitmaps;
import com.facebook.imagepipeline.request.Postprocessor;

public abstract class BasePostprocessor implements Postprocessor {
    public BasePostprocessor() {
    }

    public String getName() {
        return "Unknown postprocessor";
    }

    public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
        CloseableReference destBitmapRef = bitmapFactory.createBitmap(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmap.getConfig());

        CloseableReference var4;
        try {
            this.process((Bitmap)destBitmapRef.get(), sourceBitmap);
            var4 = CloseableReference.cloneOrNull(destBitmapRef);
        } finally {
            CloseableReference.closeSafely(destBitmapRef);
        }

        return var4;
    }

    public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
        Bitmaps.copyBitmap(destBitmap, sourceBitmap);
        this.process(destBitmap);
    }

    public void process(Bitmap bitmap) {
    }

    @Nullable
    public CacheKey getPostprocessorCacheKey() {
        return null;
    }
}
