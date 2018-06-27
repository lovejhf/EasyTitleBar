package com.next.easytitlebar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.next.easytitlebar.R;
import com.next.easytitlebar.utils.SmallUtil;

import java.util.logging.Logger;


/**
 * 通用标题栏
 */
public class EasyTitleBar extends RelativeLayout {

    private static TitleBarSetting titleBarSetting;
    protected RelativeLayout leftLayout;
    protected ImageView left_image;
    private TextView left_text;
    //右侧viewgroup
    protected RelativeLayout rightLayout;
    protected ImageView rightImage;
    protected TextView title_tv;
    protected ConstraintLayout titleLayout;
    protected TextView rightText;
    private ViewGroup right_vg;

    //标题栏高度
    private float titleBarHeight = SmallUtil.dip2px(getContext(), 48);
    //标题栏背景
    private int titleBarBackGround = Color.parseColor("#FFFFFF");

    //分割线
    private View titleLine;

    //左边的图标（一般为返回箭头）
    private int backRes = R.drawable.tab_icon_back_black_default;

    //标题字体大小
    private float titleTextSize = SmallUtil.sp2px(getContext(), 13);
    //标题字体颜色
    private int titleColor = Color.parseColor("#333333");
    //标题字排列风格
    private String titleStyle = "center";

    public static final int TITLE_STYLE_LEFT = 0;
    public static final int TITLE_STYLE_CENTER = 1;

    //分割线高度
    private float titleLineHeight = 1;
    //分割线颜色
    private int titleLineColor = Color.parseColor("#cccccc");

    private OnItemClickListener onItemClickListener;

    private int imageSize = SmallUtil.dip2px(getContext(), 18);

    private int layoutSize;

    private int textColor = Color.parseColor("#333333");
    private int textSize = SmallUtil.sp2px(getContext(), 10);

    private ConstraintSet leftConstraintSet = new ConstraintSet();
    private ConstraintSet centerConstraintSet = new ConstraintSet();

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
        rightText = findViewById(R.id.right_text);
        leftLayout = findViewById(R.id.left_layout);
        left_text = findViewById(R.id.left_text);
        left_image = findViewById(R.id.left_image);
        rightLayout = findViewById(R.id.right_layout);
        right_vg = findViewById(R.id.right_vg);
        rightImage = findViewById(R.id.right_image);
        title_tv = findViewById(R.id.title_tv);
        titleLayout = findViewById(R.id.root);
        titleLine = findViewById(R.id.line);

        leftConstraintSet.clone(titleLayout);
        centerConstraintSet.clone(titleLayout);


        initSetting();

        parseStyle(context, attrs);
    }

    private void initSetting() {
        if (titleBarSetting == null)
            return;
        titleBarBackGround = titleBarSetting.getBackgroud();
        backRes = titleBarSetting.getBack_icon();
        titleTextSize = SmallUtil.sp2px(getContext(), titleBarSetting.getTitleSize());
        titleColor = titleBarSetting.getTitleColor();
        titleBarHeight = SmallUtil.dip2px(getContext(), titleBarSetting.getTitleBarHeight());
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyTitleBar);

            //标题栏
            titleBarHeight = ta.getDimension(R.styleable.EasyTitleBar_Easy_titleBarHeight, titleBarHeight);
            titleBarBackGround = ta.getColor(R.styleable.EasyTitleBar_titlebarBackground, titleBarBackGround);
            ConstraintLayout.LayoutParams titleParams = (ConstraintLayout.LayoutParams) title_tv.getLayoutParams();
            titleParams.height = (int) titleBarHeight;
            titleLayout.setBackgroundColor(titleBarBackGround);
            title_tv.setLayoutParams(titleParams);


           /* String titleElevation = ta.getDimension(R.styleable.EasyTitleBar_Easy_titleSize, titleTextSize);
            titleLayout.setElevation();*/

            //标题
            String title = ta.getString(R.styleable.EasyTitleBar_Easy_title);
            if (null != title) {
                title_tv.setText(title);
            }
            titleTextSize = ta.getDimension(R.styleable.EasyTitleBar_Easy_titleSize, titleTextSize);
            title_tv.setTextSize(titleTextSize);
            titleColor = ta.getColor(R.styleable.EasyTitleBar_Easy_titleColor, titleColor);
            title_tv.setTextColor(titleColor);

            titleStyle = ta.getString(R.styleable.EasyTitleBar_Easy_titleStyle);
            if (titleStyle == null || titleStyle.equals("center")) {
                setTitleStyle(TITLE_STYLE_CENTER);
            } else {
                setTitleStyle(TITLE_STYLE_LEFT);
            }

            //左侧图标
            Drawable leftDrawable = ta.getDrawable(R.styleable.EasyTitleBar_Easy_leftImage);
            if (null != leftDrawable) {
                leftLayout.setVisibility(VISIBLE);
                left_image.setImageDrawable(leftDrawable);
            } else {
                leftLayout.setVisibility(VISIBLE);
                left_image.setImageResource(backRes);
            }

            //右侧图标
            Drawable rightDrawable = ta.getDrawable(R.styleable.EasyTitleBar_titlebarRightImage);
            if (null != rightDrawable) {
                rightImage.setVisibility(VISIBLE);
                rightText.setVisibility(GONE);
                rightImage.setImageDrawable(rightDrawable);
            }


            String rightTxt = ta.getString(R.styleable.EasyTitleBar_titlebarRightText);
            if (null != rightTxt) {
                rightImage.setVisibility(GONE);
                rightText.setVisibility(VISIBLE);
                rightText.setText(rightTxt);
            }


            String leftImageState = ta.getString(R.styleable.EasyTitleBar_Easy_leftImageState);
            if (null != leftImageState && leftImageState.equals("gone")) {
                leftLayout.setVisibility(GONE);
            }
            Drawable background = ta.getDrawable(R.styleable.EasyTitleBar_titlebarBackground);
            if (null != background) {
                titleLayout.setBackgroundDrawable(background);
            }


            titleLineHeight = ta.getDimension(R.styleable.EasyTitleBar_Easy_lineHeight, 1);
            titleLineColor = ta.getColor(R.styleable.EasyTitleBar_Easy_lineColor, Color.parseColor("#cccccc"));
            ConstraintLayout.LayoutParams lineParams = (ConstraintLayout.LayoutParams) titleLine.getLayoutParams();
            lineParams.height = (int) titleLineHeight;
            titleLine.setBackgroundColor(titleLineColor);
            titleLine.setLayoutParams(lineParams);

            //分割线
            String lineState = ta.getString(R.styleable.EasyTitleBar_Easy_lineState);
            if (null != lineState && lineState.equals("gone")) {
                titleLine.setVisibility(GONE);
            } else {
                if (titleBarSetting == null)
                    return;
                if (titleBarSetting.getShowLine()) {
                    titleLine.setVisibility(VISIBLE);
                } else {
                    titleLine.setVisibility(GONE);
                }
            }

            ta.recycle();
        }
    }

    public void setLeftImageResource(int resId) {
        left_image.setImageResource(resId);
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

    /**
     * 获取标题
     */
    public TextView getTitle() {
        return title_tv;
    }

    /**
     * 设置标题文字
     */
    public void setTitle(String title) {
        title_tv.setText(title);
    }

    /**
     * 设置标题字体大小
     */
    public void setTitleSize(float textSize) {
        title_tv.setTextSize(textSize);
    }

    /**
     * 设置标题字体颜色
     */
    public void setTitleColor(int textColor) {
        title_tv.setTextColor(textColor);
    }

    /**
     * 设置标题排列方式（一种居中、一种靠左）
     *
     * @param style
     */
    @SuppressLint("NewApi")
    public void setTitleStyle(int style) {
        if (style == TITLE_STYLE_CENTER) {
            centerConstraintSet.connect(title_tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
            centerConstraintSet.connect(title_tv.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
            centerConstraintSet.connect(title_tv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            centerConstraintSet.connect(title_tv.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
            centerConstraintSet.applyTo(titleLayout);
        } else if (style == TITLE_STYLE_LEFT) {
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.LEFT, leftLayout.getId(), ConstraintSet.RIGHT, 0);
            leftConstraintSet.setGoneMargin(title_tv.getId(), ConstraintSet.LEFT, dip2px(100));
            leftConstraintSet.applyTo(titleLayout);
        }
    }


    /**
     * 获取标题分割线
     *
     * @return
     */
    public View getTitleLine() {
        return titleLine;
    }

    public void addItem(int icon, final OnItemClickListener onItemClickListener) {
        rightImage.setVisibility(GONE);
        rightText.setVisibility(GONE);

        LinearLayout imageLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (layoutSize > 0)
            layoutParams.width = layoutSize;
        imageLayout.setGravity(Gravity.CENTER_VERTICAL);
        imageLayout.setLayoutParams(layoutParams);

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(icon);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (layoutSize == 0) {
            imageParams.rightMargin = SmallUtil.dip2px(getContext(), 15);
            imageParams.leftMargin = SmallUtil.dip2px(getContext(), 5);
        }
        imageParams.width = imageSize;
        imageParams.height = imageSize;
        imageView.setLayoutParams(imageParams);

        imageLayout.addView(imageView);

        right_vg.addView(imageLayout);


        imageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemEvent();
            }
        });
    }

    public void addItem(String text, final OnItemClickListener onItemClickListener) {
        rightImage.setVisibility(GONE);
        rightText.setVisibility(GONE);

        LinearLayout textLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textLayout.setPadding(SmallUtil.dip2px(getContext(), 15), 0, SmallUtil.dip2px(getContext(), 15), 0);
        textLayout.setGravity(Gravity.CENTER);
        textLayout.setLayoutParams(layoutParams);

        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);

        textLayout.addView(textView);

        right_vg.addView(textLayout);


        textLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemEvent();
            }
        });
    }

    public interface OnItemClickListener {
        void OnItemEvent();
    }


    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static TitleBarSetting init() {
        titleBarSetting = TitleBarSetting.getInstance();
        return titleBarSetting;
    }
}
