package com.mao.cn.learnRxJava2.wedget.frescoblur;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;

public interface Postprocessor {
    CloseableReference<Bitmap> process(Bitmap var1, PlatformBitmapFactory var2);

    String getName();

    @Nullable
    CacheKey getPostprocessorCacheKey();
}