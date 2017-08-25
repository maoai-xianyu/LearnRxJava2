//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools.utils;

import java.io.File;
import java.io.Serializable;

public class TrueFileFilter implements IOFileFilter, Serializable {
    public static final IOFileFilter TRUE = new TrueFileFilter();
    public static final IOFileFilter INSTANCE;

    protected TrueFileFilter() {
    }

    public boolean accept(File file) {
        return true;
    }

    public boolean accept(File dir, String name) {
        return true;
    }

    static {
        INSTANCE = TRUE;
    }
}
