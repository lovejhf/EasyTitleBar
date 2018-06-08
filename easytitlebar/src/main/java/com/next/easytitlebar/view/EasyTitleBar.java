package com.next.easytitlebar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.next.easytitlebar.R;
import com.next.easytitlebar.utils.SmallUtil;


/**
 * 通用标题栏
 */
public class EasyTitleBar extends RelativeLayout {

    protected RelativeLayout leftLayout;
    protected ImageView leftImage;
    protected RelativeLayout rightLayout;
    protected ImageView rightImage;
    protected TextView title_tv;
    protected LinearLayout titleLayout;
    protected TextView rightText;

    //标题字体大小
    private float titleTextSize =  SmallUtil.dip2px(getContext(),17);

    public EasyTitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public EasyTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EasyTitleBar(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.easy_titlebar, this);
        rightText =  findViewById(R.id.right_text);
        leftLayout =  findViewById(R.id.left_layout);
        leftImage =  findViewById(R.id.left_image);
        rightLayout =  findViewById(R.id.right_layout);
        rightImage =  findViewById(R.id.right_image);
        title_tv =  findViewById(R.id.title_tv);
        titleLayout =  findViewById(R.id.root);

        parseStyle(context, attrs);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar);
            String title = ta.getString(R.styleable.EasyTitleBar_title_text);
            if (null != title) {
                title_tv.setText(title);
            }

            titleTextSize = ta.getDimension(R.styleable.EasyTitleBar_title_size, SmallUtil.dip2px(context,17));

            String rightTxt = ta.getString(R.styleable.EasyTitleBar_titlebarRightText);
            if (null != rightTxt) {
                rightImage.setVisibility(GONE);
                rightText.setVisibility(VISIBLE);
                rightText.setText(rightTxt);
            }

            String leftImageState = ta.getString(R.styleable.EasyTitleBar_titlebarLeftState);
            if (null != leftImageState && leftImageState.equals("gone")) {
                leftLayout.setVisibility(GONE);
            }

            Drawable leftDrawable = ta.getDrawable(R.styleable.EasyTitleBar_titlebarLeftImage);
            if (null != leftDrawable) {
                leftLayout.setVisibility(VISIBLE);
                leftImage.setImageDrawable(leftDrawable);
            }
            Drawable rightDrawable = ta.getDrawable(R.styleable.EasyTitleBar_titlebarRightImage);
            if (null != rightDrawable) {
                rightImage.setVisibility(VISIBLE);
                rightText.setVisibility(GONE);
                rightImage.setImageDrawable(rightDrawable);
            }

            Drawable background = ta.getDrawable(R.styleable.EasyTitleBar_titlebarBackground);
            if (null != background) {
                titleLayout.setBackgroundDrawable(background);
            }

           /* int backgroundColor = ta.getColor(R.styleable.CommonTitleBar_titlebarBackgroundColor, Color.WHITE);
            titleLayout.setBackgroundColor(backgroundColor);*/

            ta.recycle();
        }
    }

    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }

    public void setRightImageResource(int resId) {
        rightImage.setVisibility(VISIBLE);
        rightText.setVisibility(GONE);
        rightImage.setImageResource(resId);
    }

    public void setLeftLayoutClickListener(OnClickListener listener) {
        leftLayout.setOnClickListener(listener);
    }

    public void setRightLayoutClickListener(OnClickListener listener) {
        rightLayout.setOnClickListener(listener);
    }

    public void setLeftLayoutVisibility(int visibility) {
        leftLayout.setVisibility(visibility);
    }

    public void setRightLayoutVisibility(int visibility) {
        rightLayout.setVisibility(visibility);
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title) {
        title_tv.setText(title);
    }

    public void setBackgroundColor(int color) {
        titleLayout.setBackgroundColor(color);
    }

    public RelativeLayout getLeftLayout() {
        return leftLayout;
    }

    public RelativeLayout getRightLayout() {
        return rightLayout;
    }

    public void setRightText(String rightTex) {
        rightImage.setVisibility(GONE);
        rightText.setVisibility(VISIBLE);
        rightText.setText(rightTex);
    }

}
