package com.xqd.meizhi.view;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.xqd.meizhi.R;

import java.util.ArrayList;
import java.util.List;


public class TitleBar extends FrameLayout {

    @Bind(R.id.zroot)
    View root;
    @Bind(R.id.ibtn_left)
    ImageButton ibLeft;
    @Bind(R.id.tv_titlebar_left)
    TextView tvTitleLeft;
    @Bind(R.id.tv_titlebar_title)
    TextView tvTitle;
    @Bind(R.id.tv_titlebar_right)
    TextView tvTitleRight;
    @Bind(R.id.ibtn_right)
    ImageButton ibRight;
    private Context mContext = null;
    // 背景

    Drawable left;
    Drawable right;
    Drawable background;
    String text;
    String textRight;
    String textLeft;
    int textColor;
    ColorStateList textColorRight;
    ColorStateList textColorLeft;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setBackgroundColor(Color.TRANSPARENT);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, 0, 0);
        left = a.getDrawable(R.styleable.TitleBar_title_ic_left);
        right = a.getDrawable(R.styleable.TitleBar_title_ic_right);
        background = a.getDrawable(R.styleable.TitleBar_title_background);
        text = a.getString(R.styleable.TitleBar_title_text);
        textColor = a.getColor(R.styleable.TitleBar_title_text_color, getResources().getColor(R.color.titlebar_tv_title));
        textRight = a.getString(R.styleable.TitleBar_title_text_right);
        textColorRight = a.getColorStateList(R.styleable.TitleBar_title_text_color_right);
        textLeft = a.getString(R.styleable.TitleBar_title_text_left);
        textColorLeft = a.getColorStateList(R.styleable.TitleBar_title_text_color_left);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        List<View> views = new ArrayList<>();
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                views.add(getChildAt(i));
            }
        }
        LayoutInflater.from(getContext()).inflate(R.layout.titlebar, this, true);
        for (View view : views) {
            view.bringToFront();
        }
        ButterKnife.bind(this, this);
        setLeftDrawable(left);
        setRightDrawable(right);
        setBackground(background);
        setText(text);
        tvTitle.setTextColor(textColor);
        if (textColorRight != null) {
            tvTitleRight.setTextColor(textColorRight);
        }
        setTextRight(textRight);

        if (textColorLeft != null) {
            tvTitleLeft.setTextColor(textColorLeft);
        }
        setTextLeft(textLeft);

    }

    public void setLeftDrawable(int res) {
        setLeftDrawable(getResources().getDrawable(res));
    }

    public void setLeftDrawable(Drawable d) {
        setImageButtonDrawable(ibLeft, d);
    }

    public void setRightDrawable(int res) {
        setRightDrawable(getResources().getDrawable(res));
    }

    public void setRightDrawable(Drawable d) {
        setImageButtonDrawable(ibRight, d);
    }

    private void setImageButtonDrawable(ImageButton ib, Drawable d) {
        if (d == null) {
            ib.setVisibility(View.GONE);
        } else {
            ib.setVisibility(View.VISIBLE);
            ib.setImageDrawable(d);
        }
    }

    public void setBackground(int res) {
        setBackground(getResources().getDrawable(res));
    }

    public void setBackground(Drawable d) {
        if (d != null && root != null) {
            root.setBackgroundDrawable(d);
        } else if (d == null) {
            root.setBackgroundResource(R.color.titlebar_bg_black);
        }
    }

    public void setText(CharSequence s) {
        if (TextUtils.isEmpty(s)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(s);
        }
    }

    public void setTextRight(CharSequence s) {
        if (TextUtils.isEmpty(s)) {
            tvTitleRight.setVisibility(View.GONE);
        } else {
            tvTitleRight.setVisibility(View.VISIBLE);
            tvTitleRight.setText(s);
        }
    }

    public void setTextLeft(CharSequence s) {
        if (TextUtils.isEmpty(s)) {
            tvTitleLeft.setVisibility(View.GONE);
        } else {
            tvTitleLeft.setVisibility(View.VISIBLE);
            tvTitleLeft.setText(s);
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        ibLeft.setOnClickListener(l);
        tvTitleRight.setOnClickListener(l);
        tvTitleLeft.setOnClickListener(l);
        ibRight.setOnClickListener(l);
    }

}
