package com.next.easytitlebardemo.ui.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.next.easytitlebardemo.R;
import com.next.easytitlebardemo.base.BaseFragment;
import com.next.easytitlebardemo.util.StatusBarUtil;

/**
 * Created by Administrator on 2018/7/30.
 */

public class DiscoverFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
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
        StatusBarUtil.StatusBarLightMode(getActivity());
    }

}
