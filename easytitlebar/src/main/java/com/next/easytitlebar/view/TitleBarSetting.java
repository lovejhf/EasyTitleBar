package com.next.easytitlebar.view;

import android.content.Context;
import android.graphics.Color;

import com.next.easytitlebar.R;
import com.next.easytitlebar.utils.SmallUtil;

/**
 * Created by Administrator on 2018/6/23.
 */

public class TitleBarSetting {


    private Context mContext;

    private static TitleBarSetting setting;

    //默认返回图标
    private int back_icon = R.drawable.tab_icon_back_black_default;

    //默认背景颜色
    private int backgroud = Color.parseColor("#ffffff");

    //默认标题大小
    private int titleSize = 17;

    //默认标题颜色
    private int titleColor = Color.parseColor("#ffffff");

    //标题栏分割线的有无
    private boolean lineShow = true;

    //默认标题栏高度
    private int titleBarHeight = 48;

    private TitleBarSetting() {
    }

    public static TitleBarSetting getInstance() {
        if (setting == null) {
            setting = new TitleBarSetting();
        }
        return setting;
    }

    public TitleBarSetting backIconRes(int back_icon) {
        this.back_icon = back_icon;
        return this;
    }

    public TitleBarSetting backgroud(int backgroud) {
        this.backgroud = backgroud;
        return this;
    }

    public TitleBarSetting titleSize(int titleSize) {
        this.titleSize = titleSize;
        return this;
    }

    public TitleBarSetting titleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public TitleBarSetting showLine(boolean lineShow) {
        this.lineShow = lineShow;
        return this;
    }

    public TitleBarSetting titleBarHeight(int height) {
        this.titleBarHeight = height;
        return this;
    }


    public int getBack_icon() {
        return back_icon;
    }

    public int getBackgroud() {
        return backgroud;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public boolean getShowLine() {
        return lineShow;
    }

    public int getTitleBarHeight() {
        return titleBarHeight;
    }

    public void setTitleBarHeight(int height) {
        this.titleBarHeight = height;
    }

    public void reset() {
        back_icon = R.drawable.tab_icon_back_black_default;
        backgroud = Color.parseColor("#ffffff");
        titleSize = 17;
        titleColor = Color.parseColor("#ffffff");
        titleBarHeight = 48;
        lineShow = true;
    }

}
