package com.next.easytitlebardemo.ui.demo;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Toast;

import com.next.easytitlebar.utils.EasyUtil;
import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;

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
        return R.layout.fragment_me;
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
                Toast.makeText(getContext(), "你总点我干嘛", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
