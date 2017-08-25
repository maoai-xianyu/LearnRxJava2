package com.mao.cn.learnRxJava2.wedget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mao.cn.learnRxJava2.R;
import com.mao.cn.learnRxJava2.contants.ValueMaps;
import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.BaseEffects;
import com.mao.cn.learnRxJava2.wedget.animation.animationeffects.Effectstype;

public class DefineTwoBottomDialog extends Dialog implements DialogInterface {


    Dialog dlg;
    Context context;

    private Effectstype type = null;

    private LinearLayout mLinearLayoutView;

    private LinearLayout minsideLinearLayout;

    private FrameLayout mFrameLayoutCustomView;

    private View mDialogView;

    private TextView mTitle;

    private TextView mMessage;

    private SimpleDraweeView mIcon;

    private Button bt_dlg_left;

    private Button bt_dlg_right;

    private int mDuration = -1;

    private static int mOrientation = 1;

    private boolean isCancelable = false;

    private static DefineTwoBottomDialog instance;
    private TextView mMessage2;
    private TouchOutsideListener touchOutSideListener;

    public DefineTwoBottomDialog(Context context) {
        super(context, R.style.dialog_untran);
        init(context);
    }

    public DefineTwoBottomDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

    }


    private void init(Context context) {
        dlg = this;
        this.context = context;
        mDialogView = View.inflate(context, R.layout.dialog_custom, null);

        mLinearLayoutView = (LinearLayout) mDialogView.findViewById(R.id.ll_dlg);
        minsideLinearLayout = (LinearLayout) mDialogView.findViewById(R.id.rl_dlg);
        // mFrameLayoutCustomView = (FrameLayout) mDialogView.findViewById(R.id.fl_dlg_custom);

        mTitle = (TextView) mDialogView.findViewById(R.id.tv_dlg_title);
        mMessage = (TextView) mDialogView.findViewById(R.id.tv_dlg_message);
        mMessage2 = (TextView) mDialogView.findViewById(R.id.tv_dlg_message2);
        mIcon = (SimpleDraweeView) mDialogView.findViewById(R.id.iv_dlg_icon);
        bt_dlg_left = (Button) mDialogView.findViewById(R.id.bt_left);
        bt_dlg_right = (Button) mDialogView.findViewById(R.id.bt_right);

        setContentView(mDialogView);

        this.setOnShowListener(dialogInterface -> {

            mLinearLayoutView.setVisibility(View.VISIBLE);
            if (type == null) {
                type = Effectstype.Slidetop;
            }
            start(type);
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void toDefault() {
        mTitle.setTextColor(getColor(R.color.defBlack));
        mMessage.setTextColor(getColor(R.color.defBlack));
        mLinearLayoutView.setBackgroundColor(getColor(R.color.defDialogColor));
    }

    private int getColor(int colorResId) {
        return context.getResources().getColor(colorResId);
    }


    public DefineTwoBottomDialog withTitle(CharSequence title) {
        if (title == null) {
            title = context.getResources().getString(R.string.tips);
        }
        if (title.toString().length() > 5) {
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.text_size_18));
        }
        if (title.toString().length() > 8) {
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.text_size_15));
        }
        mTitle.setText(title);
        return this;
    }


    public DefineTwoBottomDialog withTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        mTitle.setTextColor(Color.parseColor("#000000"));
        return this;
    }

    public DefineTwoBottomDialog withTitleColor(int colorString) {
        mTitle.setTextColor(context.getResources().getColor(colorString));
        return this;
    }

    public DefineTwoBottomDialog withMessage(int textResId) {
        mMessage.setText(textResId);
        return this;
    }

    public DefineTwoBottomDialog withMessage(CharSequence msg) {
        mMessage.setText(msg);
        mMessage2.setVisibility(View.GONE);
        return this;
    }

    public DefineTwoBottomDialog withMessageGravity(int gravity) {
        if (gravity != 0) {
            mMessage.setGravity(gravity);
        }
        return this;
    }

    public DefineTwoBottomDialog withMessageColor(String colorString) {
        mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public DefineTwoBottomDialog withMessage2(int textResId) {
        mMessage2.setVisibility(View.VISIBLE);
        mMessage2.setText(textResId);
        return this;
    }

    public DefineTwoBottomDialog withMessage2(CharSequence msg) {
        mMessage2.setVisibility(View.VISIBLE);
        mMessage2.setText(msg);
        return this;
    }


    public DefineTwoBottomDialog seTouchViewtCancel(boolean flag) {
        if (flag) {
            minsideLinearLayout.setOnClickListener(defaultLsn);
        } else {
            minsideLinearLayout.setOnClickListener(null);
        }
        return this;
    }

    public DefineTwoBottomDialog withIcon(int drawableResId) {
        mIcon.setImageURI(Uri.parse(ValueMaps.ImagePath.IMAGE_RES + drawableResId));
        return this;
    }

    private DefineTwoBottomDialog withIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    public DefineTwoBottomDialog withDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public DefineTwoBottomDialog withEffect(Effectstype type) {
        this.type = type;
        return this;
    }

    public DefineTwoBottomDialog withButtonDrawable(int resid) {
        bt_dlg_left.setBackgroundResource(resid);
        bt_dlg_right.setBackgroundResource(resid);
        return this;
    }

    public DefineTwoBottomDialog withButtonDrawable(int leftresid, int rightresid) {
        bt_dlg_left.setBackgroundResource(leftresid);
        bt_dlg_right.setBackgroundResource(rightresid);
        return this;
    }

    public DefineTwoBottomDialog withButtonLeftText(CharSequence text) {
        if (text == null) {
            text = context.getResources().getString(R.string.cancel);
        }
        bt_dlg_left.setVisibility(View.VISIBLE);
        bt_dlg_left.setText(text);
        return this;
    }

    public DefineTwoBottomDialog withButtonLeftTextColor(int color) {
        if (color == 0) {
            return this;
        } else {
            bt_dlg_left.setTextColor(context.getResources().getColor(color));
        }
        return this;
    }

    public DefineTwoBottomDialog withButtonRightTextColor(int color) {
        if (color == 0) {
            return this;
        } else {
            bt_dlg_right.setTextColor(context.getResources().getColor(color));
        }
        return this;
    }


    public DefineTwoBottomDialog withButtonRightText(CharSequence text) {
        if (text == null) {
            text = context.getResources().getString(R.string.confirm);
        }
        bt_dlg_right.setVisibility(View.VISIBLE);
        bt_dlg_right.setText(text);
        return this;
    }

    public DefineTwoBottomDialog setButtonLeftClick(View.OnClickListener click) {
        if (click == null) {
            click = defaultLsn;
        }
        bt_dlg_left.setOnClickListener(click);
        return this;
    }

    public DefineTwoBottomDialog setButtonRightClick(View.OnClickListener click) {
        if (click == null) {
            click = defaultLsn;
        }
        bt_dlg_right.setOnClickListener(click);
        return this;
    }


    public DefineTwoBottomDialog setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        if (mFrameLayoutCustomView.getChildCount() > 0) {
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(customView);
        return this;
    }

    public DefineTwoBottomDialog setCustomView(View view, Context context) {
        if (mFrameLayoutCustomView.getChildCount() > 0) {
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(view);

        return this;
    }

    public DefineTwoBottomDialog isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public DefineTwoBottomDialog isCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCancelable(cancelable);
        return this;
    }

    private void toggleView(View view, Object obj) {
        if (obj == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener defaultLsn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (dlg != null && dlg.isShowing()) {
                dlg.dismiss();
            }
            if (touchOutSideListener != null) {
                touchOutSideListener.onTouchOutSide();
            }
        }
    };

    public interface TouchOutsideListener {
        void onTouchOutSide();
    }

    public DefineTwoBottomDialog setOnTouchOutsideListener(TouchOutsideListener listener) {
        this.touchOutSideListener = listener;
        return this;
    }

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
            super.show();
    }

    @Override
    public void onBackPressed() {

    }
}