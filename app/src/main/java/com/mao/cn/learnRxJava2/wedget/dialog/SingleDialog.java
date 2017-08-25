package com.mao.cn.learnRxJava2.wedget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.Effectstype;


public class SingleDialog extends Dialog implements DialogInterface {


    Dialog dlg;
    Context context;

    private Effectstype type = null;

    private View mDialogView;

    private TextView mMessage;
    private TextView title;

    private Button btnConfirm;

    private LinearLayout minsideLinearLayout;

    private int mDuration = -1;

    private static int mOrientation = 1;

    private boolean isCancelable = false;

    private static SingleDialog instance;

    public SingleDialog(Context context) {
        super(context, R.style.dialog_single);
        init(context);
    }

    public SingleDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        getWindow().setAttributes(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

//    public static SingleDialog getInstance(Context context) {
//
//        int ort = context.getResources().getConfiguration().orientation;
//        if (mOrientation != ort) {
//            mOrientation = ort;
//            instance = null;
//        }
//
//        synchronized (SingleDialog.class) {
//            instance = new SingleDialog(context, R.style.dialog_single);
//        }
//        return instance;
//
//    }

    private void init(Context context) {
        dlg = this;
        this.context = context;
        mDialogView = View.inflate(context, R.layout.dialog_custom_single_button, null);
        minsideLinearLayout = (LinearLayout) mDialogView.findViewById(R.id.rl_dlg);

        mMessage = (TextView) mDialogView.findViewById(R.id.tv_dlg_message);
        title = (TextView) mDialogView.findViewById(R.id.tv_dlg_title);
        btnConfirm = (Button) mDialogView.findViewById(R.id.btn_confirm);
        setContentView(mDialogView);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                if (type == null) {
                    type = Effectstype.Slidetop;
                }
                start(type);
            }
        });
    }

    public SingleDialog withMessage(CharSequence msg) {
        mMessage.setText(msg);
        return this;
    }

    public SingleDialog withMessageGravity(int gravity) {
        mMessage.setGravity(gravity);
        return this;
    }

    public SingleDialog withTitle(CharSequence msg) {
        title.setText(msg);
        return this;
    }

    public SingleDialog withMessageColor(String colorString) {
        mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public SingleDialog seTouchViewtCancle(boolean flag) {
        if (flag) {
            minsideLinearLayout.setOnClickListener(defaultLsn);
        } else {
            minsideLinearLayout.setOnClickListener(null);
        }
        return this;
    }

    public SingleDialog withDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public SingleDialog withEffect(Effectstype type) {
        this.type = type;
        return this;
    }

    public SingleDialog withButtonText(CharSequence text) {
        if (text == null) {
            text = context.getResources().getString(R.string.confirm);
        }
        btnConfirm.setVisibility(View.VISIBLE);
        btnConfirm.setText(text);
        return this;
    }

    public SingleDialog setButtonClick(View.OnClickListener click) {
        if (click == null) {
            click = defaultLsn;
        }
        btnConfirm.setOnClickListener(click);
        return this;
    }

    public SingleDialog isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public SingleDialog isCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCancelable(cancelable);
        return this;
    }

    private View.OnClickListener defaultLsn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (dlg != null && dlg.isShowing()) {
                dlg.dismiss();
            }
        }
    };

    private void start(Effectstype type) {
        BaseEffects animator = type.getAnimator();
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(minsideLinearLayout);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    @Override
    public void show() {
        if (!isShowing())
            try {
                super.show();
            } catch (Exception ignored) {
            }
    }

    @Override
    public void onBackPressed() {
    }
}
