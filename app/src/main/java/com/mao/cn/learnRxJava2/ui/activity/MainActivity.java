// +----------------------------------------------------------------------
// | Project:   MvpProject
// +----------------------------------------------------------------------
// | CreateTime: 06/09/2017 11:17 上午
// +----------------------------------------------------------------------
// | Author:     xab(xab@xabad.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.component.AppComponent;
import com.mao.cn.learnRxJava2.component.DaggerMainComponent;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.modules.MainModule;
import com.mao.cn.learnRxJava2.ui.commons.BaseActivity;
import com.mao.cn.learnRxJava2.ui.features.IMain;
import com.mao.cn.learnRxJava2.ui.presenter.MainPresenter;
import com.mao.cn.learnRxJava2.utils.tools.LogU;
import com.mao.cn.learnRxJava2.utils.tools.StringU;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * DESC   :
 * AUTHOR : Xabad
 */
public class MainActivity extends BaseActivity implements IMain {

    @Inject
    MainPresenter presenter;

    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.btn_desc_net)
    Button btnDescNet;
    @BindView(R.id.btn_desc_rxjava)
    Button btnDescRxjava;
    @BindView(R.id.btn_desc_image)
    Button btnDescImage;
    @BindView(R.id.image)
    ImageView mImage;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            mImage.setImageBitmap(bitmap);
        }
    };


    @Override
    public void getArgs(Bundle bundle) {

    }

    @Override
    public int setView() {
        return R.layout.aty_main;
    }

    @Override
    public void initView() {
        tvHeaderTitle.setText(getString(R.string.header));
        tvHeaderTitle.setVisibility(View.VISIBLE);
        requestPermission();


        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://img.taopic.com/uploads/allimg/130331/240460-13033106243430.jpg");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    // 加一个水印
                    bitmap = createWatermark(bitmap,"RxJava2.0");
                    // 显示到图片
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

        Observable.just("http://img.taopic.com/uploads/allimg/130331/240460-13033106243430.jpg")
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        URL url = new URL("http://img.taopic.com/uploads/allimg/130331/240460-13033106243430.jpg");
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        return bitmap;
                    }
                })
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        return createWatermark(bitmap, "RxJava2.0");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void setListener() {

        RxView.clicks(btnDescNet).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
                .MILLISECONDS).subscribe(aVoid -> {
            startActivity(NetWorkRequestActivity.class);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });


        RxView.clicks(btnDescRxjava).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
                .MILLISECONDS).subscribe(aVoid -> {
            startActivity(RxJavaLearnActivity.class);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });

        RxView.clicks(btnDescImage).throttleFirst(ValueMaps.ClickTime.BREAK_TIME_MILLISECOND, TimeUnit
                .MILLISECONDS).subscribe(aVoid -> {
            startActivity(RetrofitShowContentActivity.class);
        }, throwable -> {
            LogU.e(throwable.getMessage());
        });
    }


    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build().inject(this);
    }

    /**
     * 获取需要操作日历的权限
     */
    private void requestPermission() {

        new RxPermissions(MainActivity.this)
                .requestEach(Manifest.permission.WRITE_CALENDAR,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.RECORD_AUDIO)
                .subscribe(permission -> {
                    if (permission.granted) {

                    } else {
                        if (StringU.equals(permission.name, Manifest.permission.RECORD_AUDIO)) {
                            onTip("未授予录音权限,将会影响语音朗读");
                        }
                    }

                }, throwable -> LogU.e("异常"));
    }


    private Bitmap createWatermark(Bitmap bitmap, String mark) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        // 水印颜色
        p.setColor(Color.parseColor("#C5FF0000"));
        // 水印字体大小
        p.setTextSize(150);
        //抗锯齿
        p.setAntiAlias(true);
        //绘制图像
        canvas.drawBitmap(bitmap, 0, 0, p);
        //绘制文字
        canvas.drawText(mark, 0, h / 2, p);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bmp;
    }


}
