//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools.utils;

import java.io.File;
import java.io.Serializable;

public class FalseFileFilter implements IOFileFilter, Serializable {
    public static final IOFileFilter FALSE = new FalseFileFilter();
    public static final IOFileFilter INSTANCE;

    protected FalseFileFilter() {
    }

    public boolean accept(File file) {
        return false;
    }

    public boolean accept(File dir, String name) {
        return false;
    }

    static {
        INSTANCE = FALSE;
    }
}
