// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 08/04/2017 16:53 下午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.presenterimp;

import com.google.gson.JsonSyntaxException;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.interactors.OkhttpShowContentInteractor;
import com.mao.cn.learnRxJava2.model.Movie;
import com.mao.cn.learnRxJava2.ui.commons.BasePresenterImp;
import com.mao.cn.learnRxJava2.ui.features.IOkhttpShowContent;
import com.mao.cn.learnRxJava2.ui.presenter.OkhttpShowContentPresenter;
import com.mao.cn.learnRxJava2.utils.network.NetworkUtils;
import com.mao.cn.learnRxJava2.utils.network.OKHttpClientFactory;
import com.mao.cn.learnRxJava2.utils.tools.GsonU;
import com.mao.cn.learnRxJava2.utils.tools.ListU;
import com.mao.cn.learnRxJava2.utils.tools.StringU;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class OkhttpShowContentPresenterImp extends BasePresenterImp implements OkhttpShowContentPresenter {
    OkhttpShowContentInteractor interactor;
    IOkhttpShowContent viewInterface;

    public OkhttpShowContentPresenterImp(IOkhttpShowContent viewInterface, OkhttpShowContentInteractor okhttpShowContentInteractor) {
        super();
        this.viewInterface = viewInterface;
        this.interactor = okhttpShowContentInteractor;
    }

    @Override
    public void getMovieTop(int start, int count) {
        if (!NetworkUtils.isConnected(context)) {
            viewInterface.onTip(context.getString(R.string.no_connect_net));
            return;
        }
        viewInterface.showLoadingDialog("");
        OKHttpClientFactory clientFactory = OKHttpClientFactory.getInStance();
        Map<String, String> params = new HashMap<>();
        params.put("start", start + "");
        params.put("count", count + "");
        clientFactory.requestGetBySyn("v2/movie/top250", params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                viewInterface.hideLoadingDialog();
                viewInterface.interError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                viewInterface.hideLoadingDialog();
                String res = response.body().string();
                Movie convert = null;
                try {
                    convert = GsonU.convert(res, Movie.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (convert != null && StringU.isNotEmpty(convert.getTitle()) && ListU.notEmpty(convert.getSubjects())) {
                    viewInterface.showTopMovie(convert.getSubjects(), convert.getTitle());
                } else {
                    viewInterface.showTopMovie(null, "");
                }

            }
        });

    }
}
