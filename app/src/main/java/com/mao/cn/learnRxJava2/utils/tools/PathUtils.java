//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools;

import android.content.Context;
import android.os.Environment;

import com.mao.cn.learnRxJava2.utils.tools.utils.EnvironmentU;

import java.io.File;

public class PathUtils {
    private static PathUtils ourInstance = new PathUtils();
    private static File storageDir = null;

    public static PathUtils getInstance() {
        return ourInstance;
    }

    private PathUtils() {
    }

    public File generatePath(String pathPrefix, String module, Context var2) {
        String var3 = null;
        if(StringU.isNotEmpty(pathPrefix)) {
            var3 = pathPrefix + "/" + module + "/";
        } else {
            var3 = pathPrefix + "/" + module + "/";
        }

        File file = new File(getStorageDir(var2), var3);

        try {
            FileU.forceMkdir(file);
        } catch (Exception var7) {
        }

        return file;
    }

    private static File getStorageDir(Context var0) {
        if(storageDir == null) {
            if(EnvironmentU.isExitsSdcard()) {
                File cacheDir = var0.getExternalCacheDir();
                if(cacheDir != null) {
                    File fileCacheDir = cacheDir.getParentFile();
                    if(fileCacheDir.exists() && fileCacheDir.canWrite()) {
                        return fileCacheDir;
                    }
                }

                String fileCacheDir1 = "/Android/data/" + var0.getPackageName() + "/";
                fileCacheDir1 = Environment.getExternalStorageDirectory().getPath() + fileCacheDir1;
                FileU.ifNotExistCreateDir(fileCacheDir1);
                return new File(fileCacheDir1);
            }

            storageDir = var0.getDir("learnDevelop", 0);
        }

        return storageDir;
    }
}
