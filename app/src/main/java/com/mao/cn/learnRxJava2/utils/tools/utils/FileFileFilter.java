//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools.utils;

import java.io.File;
import java.io.Serializable;

public class FileFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter FILE = new FileFileFilter();

    protected FileFileFilter() {
    }

    public boolean accept(File file) {
        return file.isFile();
    }
}
