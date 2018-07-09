package com.next.easytitlebar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.next.easytitlebar.R;
import com.next.easytitlebar.utils.EasyUtil;

import java.util.logging.Logger;


/**
 * 通用标题栏
 */
public class EasyTitleBar extends RelativeLayout {

    private static TitleBarSetting titleBarSetting;

    private RelativeLayout leftLayout;


    private ImageView leftImage;
    private TextView leftText;
    private ImageView rightImage;
    private TextView rightText;


    //右侧viewgroup
    private RelativeLayout rightLayout;
    private TextView title_tv;
    private LinearLayout titleLayout;
    private ConstraintLayout fit_cl;


    private ViewGroup right_vg;
    private ViewGroup left_vg;
    private ViewGroup title_vg;

    //标题栏高度
    private float titleBarHeight = EasyUtil.dip2px(getContext(), 48);
    //标题栏背景
    private int titleBarBackGround = Color.parseColor("#FFFFFF");

    //分割线
    private View titleLine;

    //左边的图标（一般为返回箭头）
    private int backRes = R.drawable.tab_icon_back_black_default;

    //标题字体大小
    private float titleTextSize = 18;
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

    private OnDoubleClickListener onDoubleClickListener;

    //右侧图片大小
    private int leftImgSize = (int) getResources().getDimension(R.dimen.default_image_size);
    //右侧字体颜色
    private int leftTextColor = ContextCompat.getColor(getContext(), R.color.white);
    //右侧字体大小
    private int leftTextSize = (int) getResources().getDimension(R.dimen.default_subTilte_size);

    //右侧图片大小
    private int rightImgSize = (int) getResources().getDimension(R.dimen.default_image_size);
    //右侧字体颜色
    private int rightTextColor = ContextCompat.getColor(getContext(), R.color.white);
    //右侧字体大小
    private int rightTextSize = (int) getResources().getDimension(R.dimen.default_subTilte_size);

    private ConstraintSet leftConstraintSet = new ConstraintSet();
    private ConstraintSet centerConstraintSet = new ConstraintSet();
    private String lineState;
    private GestureDetector detector;

    public EasyTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public EasyTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs, 0);
    }

    public EasyTitleBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(R.layout.easy_titlebar, this);

        fit_cl = findViewById(R.id.fit_cl);

        leftLayout = findViewById(R.id.left_layout);

        leftText = findViewById(R.id.left_text);
        leftImage = findViewById(R.id.left_image);
        rightImage = findViewById(R.id.right_image);
        rightText = findViewById(R.id.right_text);

        rightLayout = findViewById(R.id.right_layout);
        right_vg = findViewById(R.id.right_vg);
        left_vg = findViewById(R.id.left_vg);

        title_tv = findViewById(R.id.title_tv);
        titleLayout = findViewById(R.id.root);
        titleLine = findViewById(R.id.line);


        leftConstraintSet.clone(fit_cl);
        centerConstraintSet.clone(fit_cl);

        initEvent();

        initSetting();

        parseStyle(context, attrs);
    }

    private void initEvent() {
        detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (onDoubleClickListener != null)
                    onDoubleClickListener.onDoubleEvent(title_tv);
                return super.onDoubleTap(e);
            }
        });

        titleLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
    }

    private void initSetting() {
        if (titleBarSetting == null)
            return;
        titleBarBackGround = titleBarSetting.getBackgroud();
        backRes = titleBarSetting.getBack_icon();
        titleTextSize = titleBarSetting.getTitleSize();
        titleColor = titleBarSetting.getTitleColor();
        titleBarHeight = EasyUtil.dip2px(getContext(), titleBarSetting.getTitleBarHeight());
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyTitleBar);

            boolean fitSystemWindow = ta.getBoolean(R.styleable.EasyTitleBar_Easy_fitsSystemWindows, false);
            titleLayout.setFitsSystemWindows(fitSystemWindow);

            //标题栏
            titleBarHeight = ta.getDimension(R.styleable.EasyTitleBar_Easy_titleBarHeight, titleBarHeight);
            titleBarBackGround = ta.getColor(R.styleable.EasyTitleBar_Easy_titleBarBackground, titleBarBackGround);
            titleLayout.setBackgroundColor(titleBarBackGround);
            LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) fit_cl.getLayoutParams();
            titleParams.height = (int) titleBarHeight;
            fit_cl.setLayoutParams(titleParams);

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
                leftImage.setImageDrawable(leftDrawable);
            } else {
                leftLayout.setVisibility(VISIBLE);
                leftImage.setImageResource(backRes);
            }
            //左侧文字
            String leftTextStr = ta.getString(R.styleable.EasyTitleBar_Easy_leftText);
            if (null != leftTextStr) {
                leftImage.setVisibility(GONE);
                leftText.setVisibility(VISIBLE);
                leftText.setText(leftTextStr);
            }

            //右侧图标
            Drawable rightDrawable = ta.getDrawable(R.styleable.EasyTitleBar_Easy_rightImage);
            if (null != rightDrawable) {
                rightImage.setVisibility(VISIBLE);
                rightText.setVisibility(GONE);
                rightImage.setImageDrawable(rightDrawable);
            }

            //右侧文字
            String rightTxt = ta.getString(R.styleable.EasyTitleBar_Easy_rightText);
            if (null != rightTxt) {
                rightImage.setVisibility(GONE);
                rightText.setVisibility(VISIBLE);
                rightText.setText(rightTxt);
            }
            String rightImageState = ta.getString(R.styleable.EasyTitleBar_Easy_rightLayoutState);
            if (null != rightImageState && rightImageState.equals("gone")) {
                rightLayout.setVisibility(GONE);
            }

            String leftImageState = ta.getString(R.styleable.EasyTitleBar_Easy_leftLayoutState);
            if (null != leftImageState && leftImageState.equals("gone")) {
                leftLayout.setVisibility(GONE);
            }

            titleLineHeight = ta.getDimension(R.styleable.EasyTitleBar_Easy_lineHeight, 1);
            titleLineColor = ta.getColor(R.styleable.EasyTitleBar_Easy_lineColor, Color.parseColor("#cccccc"));
            ConstraintLayout.LayoutParams lineParams = (ConstraintLayout.LayoutParams) titleLine.getLayoutParams();
            lineParams.height = (int) titleLineHeight;
            titleLine.setBackgroundColor(titleLineColor);
            titleLine.setLayoutParams(lineParams);

            //分割线
            lineState = ta.getString(R.styleable.EasyTitleBar_Easy_lineState);
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

    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
    }

    public void setEasyFitsWindows(boolean fitSystem) {
        titleLayout.setFitsSystemWindows(fitSystem);
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


    /**
     * 获取标题
     *
     * @return
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
            centerConstraintSet.applyTo(fit_cl);
        } else if (style == TITLE_STYLE_LEFT) {
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.LEFT, leftLayout.getId(), ConstraintSet.RIGHT, 0);
            leftConstraintSet.setGoneMargin(title_tv.getId(), ConstraintSet.LEFT, dip2px(100));
            leftConstraintSet.applyTo(fit_cl);
        }
        //会改变分割线的状态
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
    }


    /**
     * 获取标题分割线
     *
     * @return
     */
    public View getTitleLine() {
        return titleLine;
    }


    //* 左侧添加view

    public void setLeftText(String leftTex) {
        leftImage.setVisibility(GONE);
        leftText.setVisibility(VISIBLE);
        leftText.setText(leftTex);
    }

    public void setRightText(String rightTex) {
        rightImage.setVisibility(GONE);
        rightText.setVisibility(VISIBLE);
        rightText.setText(rightTex);
    }

    public void setLeftImageResource(int resId) {
        leftText.setVisibility(GONE);
        leftImage.setVisibility(VISIBLE);
        leftImage.setImageResource(resId);
    }

    public void setRightImageResource(int resId) {
        rightImage.setVisibility(VISIBLE);
        rightText.setVisibility(GONE);
        rightImage.setImageResource(resId);
    }


    /**
     * 向右侧添加文字（默认距左15dp,距右5dp,字体10sp）
     *
     * @param text                文字
     * @param paddingleft         文字距左边的距离
     * @param paddingright        文字距右边的距离
     * @param textSize            文字大小
     * @param onItemClickListener 点击事件
     */
    public TextView addLeftText(String text, int paddingleft, int paddingright, int textSize, final OnItemClickListener onItemClickListener) {
        leftImage.setVisibility(GONE);
        leftText.setVisibility(GONE);

        LinearLayout textLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textLayout.setPadding(EasyUtil.dip2px(getContext(), paddingleft), 0, EasyUtil.dip2px(getContext(), paddingright), 0);
        textLayout.setGravity(Gravity.CENTER);
        textLayout.setLayoutParams(layoutParams);

        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(leftTextColor);

        textLayout.addView(textView);

        left_vg.addView(textLayout);


        textLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.OnItemEvent();
            }
        });

        return textView;
    }

    public TextView addLeftText(String text, int textSize, final OnItemClickListener onItemClickListener) {
        return addLeftText(text, 15, 5, textSize, onItemClickListener);
    }

    public TextView addLeftText(String text, final OnItemClickListener onItemClickListener) {
        return addLeftText(text, 15, 5, leftTextSize, onItemClickListener);
    }

    public TextView addLeftText(String text, int paddingleft, int paddingright, final OnItemClickListener onItemClickListener) {
        return addLeftText(text, paddingleft, paddingright, leftTextSize, onItemClickListener);
    }


    /**
     * 向左侧添加图片（默认距左15dp,距右5dp）
     *
     * @param icon                图片
     * @param paddingleft         文字距左边的距离
     * @param paddingright        文字距右边的距离
     * @param imgSize             图片大小
     * @param onItemClickListener 点击事件
     */
    public ImageView addLeftImg(int icon, int paddingleft, int paddingright, int imgSize, final OnItemClickListener onItemClickListener) {
        leftImage.setVisibility(GONE);
        leftText.setVisibility(GONE);

        LinearLayout imageLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageLayout.setPadding(EasyUtil.dip2px(getContext(), paddingleft), 0, EasyUtil.dip2px(getContext(), paddingright), 0);

        imageLayout.setGravity(Gravity.CENTER_VERTICAL);
        imageLayout.setLayoutParams(layoutParams);

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(icon);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageParams.width = imgSize;
        imageParams.height = imgSize;
        imageView.setLayoutParams(imageParams);

        imageLayout.addView(imageView);

        left_vg.addView(imageLayout);


        imageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.OnItemEvent();
            }
        });

        return imageView;
    }

    public ImageView addLeftImg(int icon, int imgSize, final OnItemClickListener onItemClickListener) {
        return addLeftImg(icon, 15, 5, imgSize, onItemClickListener);
    }

    public ImageView addLeftImg(int icon, final OnItemClickListener onItemClickListener) {
        return addLeftImg(icon, 15, 5, leftImgSize, onItemClickListener);
    }

    public ImageView addLeftImg(int icon, int paddingleft, int paddingright, final OnItemClickListener onItemClickListener) {
        return addLeftImg(icon, paddingleft, paddingright, leftImgSize, onItemClickListener);
    }

    //--------------  右侧


    /**
     * 向右侧添加图片（默认距左5dp,距右15dp）
     *
     * @param icon                图片
     * @param paddingleft         文字距左边的距离
     * @param paddingright        文字距右边的距离
     * @param imgSize             图片大小
     * @param onItemClickListener 点击事件
     */
    public ImageView addRightImg(int icon, int paddingleft, int paddingright, int imgSize, final OnItemClickListener onItemClickListener) {
        rightImage.setVisibility(GONE);
        rightText.setVisibility(GONE);

        LinearLayout imageLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageLayout.setPadding(EasyUtil.dip2px(getContext(), paddingleft), 0, EasyUtil.dip2px(getContext(), paddingright), 0);

        imageLayout.setGravity(Gravity.CENTER_VERTICAL);
        imageLayout.setLayoutParams(layoutParams);

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(icon);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageParams.width = imgSize;
        imageParams.height = imgSize;
        imageView.setLayoutParams(imageParams);

        imageLayout.addView(imageView);

        right_vg.addView(imageLayout);


        imageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.OnItemEvent();
            }
        });

        return imageView;
    }

    public ImageView addRightImg(int icon, int imgSize, final OnItemClickListener onItemClickListener) {
        return addRightImg(icon, 5, 15, imgSize, onItemClickListener);
    }

    public ImageView addRightImg(int icon, final OnItemClickListener onItemClickListener) {
        return addRightImg(icon, 5, 15, rightImgSize, onItemClickListener);
    }

    public ImageView addRightImg(int icon, int paddingleft, int paddingright, final OnItemClickListener onItemClickListener) {
        return addRightImg(icon, paddingleft, paddingright, rightImgSize, onItemClickListener);
    }


    /**
     * 向右侧添加文字（默认距左5dp,距右15dp,字体10sp）
     *
     * @param text                文字
     * @param paddingleft         文字距左边的距离
     * @param paddingright        文字距右边的距离
     * @param textSize            文字大小
     * @param onItemClickListener 点击事件
     */
    public TextView addRightText(String text, int paddingleft, int paddingright, int textSize, final OnItemClickListener onItemClickListener) {
        rightImage.setVisibility(GONE);
        rightText.setVisibility(GONE);

        LinearLayout textLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textLayout.setPadding(EasyUtil.dip2px(getContext(), paddingleft), 0, EasyUtil.dip2px(getContext(), paddingright), 0);
        textLayout.setGravity(Gravity.CENTER);
        textLayout.setLayoutParams(layoutParams);

        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(rightTextColor);

        textLayout.addView(textView);

        right_vg.addView(textLayout);


        textLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.OnItemEvent();
            }
        });
        return textView;
    }

    public TextView addRightText(String text, int textSize, final OnItemClickListener onItemClickListener) {
        return addRightText(text, 5, 15, textSize, onItemClickListener);
    }

    public TextView addRightText(String text, final OnItemClickListener onItemClickListener) {
        return addRightText(text, 5, 15, rightTextSize, onItemClickListener);
    }

    public TextView addRightText(String text, int paddingleft, int paddingright, final OnItemClickListener onItemClickListener) {
        return addRightText(text, paddingleft, paddingright, rightTextSize, onItemClickListener);
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

    //双击事件
    public interface OnDoubleClickListener {
        public void onDoubleEvent(View view);
    }

}
