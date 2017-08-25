//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools.utils;

import java.util.List;

public interface ConditionalFileFilter {
    void addFileFilter(IOFileFilter var1);

    List<IOFileFilter> getFileFilters();

    boolean removeFileFilter(IOFileFilter var1);

    void setFileFilters(List<IOFileFilter> var1);
}
