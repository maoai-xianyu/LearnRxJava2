// +----------------------------------------------------------------------
// | Project:   LearnMyDevelopProject
// +----------------------------------------------------------------------
// | CreateTime: 08/08/2017 16:39 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.features;

import com.mao.cn.learnRxJava2.model.MovieDetail;
import com.mao.cn.learnRxJava2.ui.commons.BaseViewInferface;

import java.util.List;

/**
* DESC   :
* AUTHOR : Xabad
*/
public interface IRetrofitShowContent extends BaseViewInferface{
    void showTopMovie(List<MovieDetail> subjects, String title);
}
