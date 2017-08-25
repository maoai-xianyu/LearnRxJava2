// +----------------------------------------------------------------------
// | Project:   Xabad  
// +----------------------------------------------------------------------
// | CreateTime: 15/11/20  下午2:42
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.utils.tools;

import android.content.Context;
import android.net.Uri;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.mao.cn.learnRxJava2.wedget.frescoblur.BlurPostprocessor;


/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class FrescoFactoryU {

    private static ImageRequest createRequest(String imgUrl, int width, int height) {
        Uri uri = Uri.parse(imgUrl);
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(
                uri);
        if (width != 0 && height != 0) {
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        builder.setAutoRotateEnabled(true);
        return builder.build();
    }

    private static ImageRequest createRequest(int resID, int width, int height) {
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(
                drawable(resID));
        if (width != 0 && height != 0) {
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        builder.setAutoRotateEnabled(true);
        return builder.build();
    }

    private static ImageRequest createRequest(String imgUrl, int width, int height, Postprocessor postprocessor) {
        Uri uri = Uri.parse(imgUrl);
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(
                uri);
        if (width != 0 && height != 0) {
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        if (postprocessor != null)
            builder.setPostprocessor(postprocessor);
        builder.setAutoRotateEnabled(true);
        return builder.build();
    }


    public static Uri http(String path) {
        if (StringU.isNotEmpty(path))
            return new Uri.Builder()
                    .path(String.valueOf(path))
                    .build();
        return null;
    }

    public static Uri file(String path) {
        if (StringU.isNotEmpty(path))
            return new Uri.Builder()
                    .scheme(UriUtil.LOCAL_FILE_SCHEME)
                    .path(String.valueOf(path))
                    .build();
        return null;
    }

    public static Uri asset(String path) {
        if (StringU.isNotEmpty(path))
            return new Uri.Builder()
                    .scheme(UriUtil.LOCAL_ASSET_SCHEME)
                    .path(String.valueOf(path))
                    .build();
        return null;
    }

    public static Uri drawable(int resID) {
        if (resID != 0)
            return new Uri.Builder()
                    .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                    .path(String.valueOf(resID))
                    .build();
        return null;
    }

    /**
     * 自定义大小
     *
     * @param imgUrl
     * @param controller
     * @return
     */
    public static DraweeController resize(String imgUrl, int width, int height, DraweeController controller) {
        return Fresco.newDraweeControllerBuilder()
                .setOldController(controller)
                .setAutoPlayAnimations(true)
                .setImageRequest(createRequest(imgUrl, width, height))
                .build();
    }

    /**
     * 自定义大小
     *
     * @param imgUrl
     * @return
     */
    public static DraweeController resize(String imgUrl, int width, int height) {
        return Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setImageRequest(createRequest(imgUrl, width, height))
                .build();
    }

    public static DraweeController resize(int resID, int width, int height) {
        return Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setImageRequest(createRequest(resID, width, height))
                .build();
    }


    public static DraweeController newControler(String imgUrl) {
        return Fresco.newDraweeControllerBuilder()
                .setUri(imgUrl)
                .setAutoPlayAnimations(true)
                .build();
    }


    //虚化图片
    public static DraweeController blur(String imgUrl) {
        return Fresco.newDraweeControllerBuilder()
                    .setImageRequest(createRequest(imgUrl, 0, 0, new BlurPostprocessor()))
                .build();
    }

    public static DraweeController blur(String imgUrl, BlurPostprocessor postprocessor) {
        return Fresco.newDraweeControllerBuilder()
                .setImageRequest(createRequest(imgUrl, 0, 0, postprocessor))
                .build();
    }

    public static DraweeController blur(String imgUrl, DraweeController controller) {
        return Fresco.newDraweeControllerBuilder()
                .setOldController(controller)
                .setImageRequest(createRequest(imgUrl, 0, 0, new BlurPostprocessor()))
                .build();
    }

    public static DraweeController blur(String imgUrl, int width, int height) {
        return Fresco.newDraweeControllerBuilder()
                .setImageRequest(createRequest(imgUrl, width, height, new BlurPostprocessor()))
                .build();
    }

    public static DraweeController blur(String imgUrl, int width, int height, DraweeController controller) {
        return Fresco.newDraweeControllerBuilder()
                .setOldController(controller)
                .setImageRequest(createRequest(imgUrl, width, height, new BlurPostprocessor()))
                .build();
    }

    /**
     * 获取bigmap 对象
     *
     * @param context
     * @param imgUrl
     * @param width
     * @param height
     * @param dataSubscriber
     */
    public static void get(Context context, String imgUrl, int width, int height, BaseBitmapDataSubscriber dataSubscriber) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(
                createRequest(imgUrl, width, height), context);
        dataSource.subscribe(dataSubscriber,
                CallerThreadExecutor.getInstance());

    }

    /**
     * 删除图片缓存(内存和硬盘都删除)
     *
     * @param imgPath
     */
    public static void clearCache(String imgPath) {
        if (StringU.isNotEmpty(imgPath))
            clearCache(Uri.parse(imgPath));
    }

    public static void clearCache(Uri uri) {
        if (uri != null) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.evictFromCache(uri);
        }
    }

    /**
     * 删除图片内存缓存
     *
     * @param imgPath
     */
    public static void clearMemoryCache(String imgPath) {
        if (StringU.isNotEmpty(imgPath))
            clearMemoryCache(Uri.parse(imgPath));
    }

    public static void clearMemoryCache(Uri uri) {
        if (uri != null) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.evictFromMemoryCache(uri);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        public Builder() {
        }


    }
}
