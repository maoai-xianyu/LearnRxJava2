//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools.utils;

import java.io.File;

public abstract class AbstractFileFilter implements IOFileFilter {
    public AbstractFileFilter() {
    }

    public boolean accept(File file) {
        return this.accept(file.getParentFile(), file.getName());
    }

    public boolean accept(File dir, String name) {
        return this.accept(new File(dir, name));
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
