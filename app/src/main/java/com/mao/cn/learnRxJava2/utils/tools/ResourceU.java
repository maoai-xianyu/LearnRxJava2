package com.mao.cn.learnRxJava2.utils.tools;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mao.cn.learnRxJava2.LearnRxJava2Application;
import com.mao.cn.learnRxJava2.contants.KeyMaps;
import com.mao.cn.learnRxJava2.utils.tools.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * author:  zhangkun .
 * date:    on 2017/8/10.
 */

public class ResourceU {

    public static String getDefaultCover() {
        String prex = "files" + File.separator + "image_default_bg.jpg";
        if (hasSdFilesFile(File.separator + prex)) {
            return KeyMaps.ImagesFresco.FRESCO_FILE + PathU.getInstance().getFilesPath() + File.separator + prex;
        } else {
            return KeyMaps.ImagesFresco.FRESCO_ASSET + prex;
        }
    }

    public static Bitmap getImageBitmap(String imageName) {
        AssetManager assetManager = LearnRxJava2Application.context().getAssets();
        String filename = "files/image_icons/" + imageName;
        try {
            if (hasSdFilesFile(File.separator + filename)) {
                return BitmapFactory.decodeStream(getDataInputStream(filename));
            } else {
                return BitmapFactory.decodeStream(assetManager.open(filename));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmap(String imageName) {
        AssetManager assetManager = LearnRxJava2Application.context().getAssets();
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = assetManager.open(imageName);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 用于获取一些读取静态资源信息的方法
     *
     * @param fileName
     * @return
     */
    public static String getCommonDataFromJsonFile(String fileName) {
        AssetManager assetManager = LearnRxJava2Application.context().getAssets();
        fileName = "files" + File.separator + fileName;
        String dataStr = "";
        InputStream in = null;
        try {
            if (hasSdFilesFile(File.separator + fileName)) {
                dataStr = getDataString(File.separator + fileName);
            } else {
                in = assetManager.open(fileName);
                dataStr = IOUtils.toString(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        return dataStr;
    }

    public static boolean hasSdFilesFile(String filename) {
        File file;
        try {
            file = new File(PathU.getInstance().getFilesPath() + filename);
        } catch (Exception e) {
            return false;
        }
        return file.exists();
    }

    public static InputStream getDataInputStream(String filename) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(PathU.getInstance().getFilesPath() + File.separator + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static String getDataString(String filename) {
        String data = "";
        try {
            File file = new File(PathU.getInstance().getFilesPath() + File.separator + filename);
            data = FileU.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * 获取内部的 asset
     * fileName 为空 返回  不为空返回
     *
     * @param fileName
     * @return
     */
    public static String[] getAssetsFileNames(String fileName) {

        AssetManager manager = LearnRxJava2Application.context().getAssets();

        String[] list;
        try {
            list = manager.list(fileName);
        } catch (IOException e) {
            list = null;
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 获取内部的 asset
     * fileName 为空 返回  不为空返回
     *
     * @param fileName
     * @return
     */
    public static String[] getAssetsFileNamesOut(String fileName) {

        AssetManager manager = LearnRxJava2Application.context().getAssets();
        fileName = "files" + File.separator + fileName;
        String[] list;
        try {
            if (hasSdFilesFile(fileName)) {
                File file = new File(PathU.getInstance().getFilesPath() + fileName);
                list = file.list();
            } else {
                list = manager.list(fileName);
            }
        } catch (IOException e) {
            list = null;
            e.printStackTrace();
        }
        return list;
    }


}
