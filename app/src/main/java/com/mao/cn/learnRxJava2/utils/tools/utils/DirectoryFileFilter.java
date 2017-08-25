//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools.utils;

import java.io.File;
import java.io.Serializable;

public class DirectoryFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter DIRECTORY = new DirectoryFileFilter();
    public static final IOFileFilter INSTANCE;

    protected DirectoryFileFilter() {
    }

    public boolean accept(File file) {
        return file.isDirectory();
    }

    static {
        INSTANCE = DIRECTORY;
    }
}
