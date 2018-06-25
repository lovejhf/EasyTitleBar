package com.next.easytitlebardemo;

import android.app.Application;
import android.graphics.Color;

import com.next.easytitlebar.view.EasyTitleBar;

/**
 * Created by Administrator on 2018/6/25.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initTitleBar();
    }

    private void initTitleBar() {
        EasyTitleBar.init()
                .backIconRes(R.mipmap.icon_l)
                .backgroud(Color.parseColor("#00ffff"))
                .titleSize(12)
                .titleColor(Color.parseColor("#ffffff"))
        .titleBarHeight(48);
    }
}
