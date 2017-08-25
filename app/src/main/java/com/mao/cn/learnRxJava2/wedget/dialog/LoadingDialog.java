package com.mao.cn.learnRxJava2.wedget.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mao.cn.learnRxJava2.BuildConfig;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.utils.tools.StringU;
import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.Effectstype;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoadingDialog extends Dialog implements DialogInterface {


    Dialog dlg;
    Context context;
    @BindView(R.id.loadView)
    LinearLayout loadView;
    @BindView(R.id.iv_loading)
    ImageView img;
    @BindView(R.id.tipTextView)
    TextView tipTextView;


    private Effectstype type = null;


    private View mDialogView;

    private int mDuration = -1;

    private static int mOrientation = 1;

    private boolean isCancelable = false;

    private String msg;
    Animation hyperspaceJumpAnimation;


    public LoadingDialog(Context context) {
        super(context, R.style.dialog_tran);
        init(context);

    }
    public LoadingDialog(Context context, String msg) {
        super(context, R.style.dialog_tran);
        init(context,msg);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void init(Context context) {
        dlg = this;
        this.context = context;
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                initView();
            }
        };
        runnable.run();
    }
    private void init(Context context, String msg) {
        dlg = this;
        this.context = context;
        this.msg = msg;
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                initView();
            }
        };
        runnable.run();
    }

    public void initView() {
        mDialogView = View.inflate(context, R.layout.inc_loading, null);
        setContentView(mDialogView);
        ButterKnife.bind(this,mDialogView);
        loadView.setOnClickListener(null);
        loadView.setClickable(false);
        isCancelable(false);
        isCancelableOnTouchOutside(false);
        mDialogView.setOnClickListener(null);
        img.startAnimation(hyperspaceJumpAnimation);
        if (!StringU.isEmpty(msg)){
            tipTextView.setVisibility(View.VISIBLE);
            tipTextView.setText(msg);// 设置加载信息
        }else {
            tipTextView.setVisibility(View.GONE);
        }

        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if (type == null) {
                    type = Effectstype.SlideCenter;
                }
                start(type);
            }
        });
    }

    public void message(String msg){
        if (!StringU.isEmpty(msg)){
            tipTextView.setVisibility(View.VISIBLE);
            tipTextView.setText(msg);// 设置加载信息
        }else {
            tipTextView.setVisibility(View.GONE);
        }
    }
    public LoadingDialog isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        if(BuildConfig.API_DEBUG)
            this.setCanceledOnTouchOutside(true);
        else
            this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public LoadingDialog isCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        if(BuildConfig.API_DEBUG)
            this.setCancelable(true);
        else
            this.setCancelable(cancelable);
        return this;
    }


    @Override
    public void show() {
        if(isShowing())return ;
        try {
            super.show();
            if (img != null && hyperspaceJumpAnimation != null)
                img.startAnimation(hyperspaceJumpAnimation);
        }catch (Exception e){}
    }

    public void show(String message) {
        this.message(message);
        this.show();
    }

    private void start(Effectstype type) {
        BaseEffects animator = type.getAnimator();
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(loadView);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            try{super.dismiss();}catch (Exception e){}
        }
    }
}
