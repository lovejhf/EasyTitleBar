package com.next.easytitlebardemo.ui.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.next.easytitlebar.view.EasyTitleBar;
import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;
import com.next.easytitlebardemo.util.StatusBarUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/30.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.titleBar)
    EasyTitleBar titleBar;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onViewCreated() {

    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void lazyLoad() {
        super.lazyLoad();
       // titleBar.setTitleStyle(EasyTitleBar.TITLE_STYLE_LEFT);
       // titleBar.getBackLayout().setVisibility(View.GONE);
    }

}
