package com.example.root.curriculum.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * 加载 assets 文件夹 下的字体文件
 */
public class IconFontTextView extends android.support.v7.widget.AppCompatTextView {

    private Context mContext;

    public IconFontTextView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        Typeface iconfont = Typeface.createFromAsset(mContext.getAssets(), "iconfont.ttf");
        setTypeface(iconfont);
    }
}