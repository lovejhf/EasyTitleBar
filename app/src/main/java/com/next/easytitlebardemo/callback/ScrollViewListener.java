package com.next.easytitlebardemo.callback;

import com.next.easytitlebardemo.view.ObservableScrollView;

public interface ScrollViewListener {

    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

}
