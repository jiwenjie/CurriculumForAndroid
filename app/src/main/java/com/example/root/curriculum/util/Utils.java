package com.example.root.curriculum.util;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * 一些基本的工具方法封装
 */
public class Utils {

    public static void copy(Context context, String content) {
    // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

}
