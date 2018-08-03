package com.next.easytitlebardemo.ui.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.next.easytitlebar.utils.EasyUtil;
import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;
import com.next.easytitlebardemo.util.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/30.
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.mSrollView)
    NestedScrollView mSrollView;
    @BindView(R.id.titleBar)
    EasyTitleBar titleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shade;
    }

    @Override
    protected void onViewCreated() {


        titleBar.attachScrollView(mSrollView, R.color.white, EasyUtil.dip2px(getContext(), 250 - 60) - EasyUtil.getStateBarHeight(getActivity()), new EasyTitleBar.OnSrollAlphaListener() {
            @Override
            public void OnSrollAlphaEvent(float alpha) {
                if (alpha > 0.8) {
                    titleBar.setTitle("我的");
                    titleBar.setTitleColor(ContextCompat.getColor(getContext(), R.color.common_text_3));
                }else{
                    titleBar.setTitle("");
                    titleBar.setTitleColor(ContextCompat.getColor(getContext(), R.color.white));
                }
            }
        });
    }

    @Override
    protected void initEventAndData() {
        titleBar.setOnDoubleClickListener(new EasyTitleBar.OnDoubleClickListener() {
            @Override
            public void onDoubleEvent(View view) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        // mSrollView.fullScroll(ScrollView.FOCUS_UP);//滚动到顶部
                    }
                });

            }
        });
    }

}
