package com.next.easytitlebar.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.next.easytitlebar.R;
import com.next.easytitlebar.utils.EasyUtil;

import java.util.logging.Logger;


/**
 * 通用标题栏
 */
public class EasyTitleBar extends RelativeLayout {

    private static TitleBarSetting titleBarSetting;

    private TextView title_tv;
    private LinearLayout titleLayout;
    private ConstraintLayout fit_cl;
    //返回箭头图片
    private ImageView backImage;
    //返回箭头的父布局
    private LinearLayout backLayout;
    private ViewGroup rightLayout;
    private ViewGroup leftLayout;
    private ViewGroup title_vg;
    //分割线
    private View titleLine;


    //menu图片大小
    private static float menuImgSize;
    //menu文字大小
    private static float menuTextSize;
    //menu文字颜色
    private static int menuTextColor;

    //标题栏高度
    private float titleBarHeight;
    //标题栏背景
    private int titleBarBackGround;

    //左边的图标（一般为返回箭头）
    private int backRes;

    //返回箭头、左右viewgroup距两边的距离
    private float parentPadding;
    //左右viewgroup之间的距离
    private static float viewPadding;

    //标题字体大小
    private float titleTextSize;
    //标题字体颜色
    private int titleColor;
    //标题字排列风格  居中或是居左
    private int titleStyle;

    public static final int TITLE_STYLE_LEFT = 0;
    public static final int TITLE_STYLE_CENTER = 1;

    //分割线高度
    private float lineHeight;
    //分割线颜色
    private int lineColor;
    private int backImageSize;

    private OnDoubleClickListener onDoubleClickListener;

    private ConstraintSet leftConstraintSet = new ConstraintSet();
    private ConstraintSet centerConstraintSet = new ConstraintSet();
    private String lineState;
    private GestureDetector detector;
    private String title;

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

        backImage = findViewById(R.id.left_image);

        rightLayout = findViewById(R.id.right_layout);
        leftLayout = findViewById(R.id.left_layout);

        backLayout = findViewById(R.id.back_layout);

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


    //初始化
    private void initSetting() {
        if (titleBarSetting == null)
            return;
        titleBarBackGround = titleBarSetting.getBackgroud();

        backRes = titleBarSetting.getBack_icon();
        titleTextSize = titleBarSetting.getTitleSize();
        titleColor = titleBarSetting.getTitleColor();
        titleBarHeight = EasyUtil.dip2px(getContext(), titleBarSetting.getTitleBarHeight());

        parentPadding = EasyUtil.dip2px(getContext(), titleBarSetting.getParentPadding());
        viewPadding = EasyUtil.dip2px(getContext(), titleBarSetting.getViewPadding());

        backImageSize = EasyUtil.dip2px(getContext(), titleBarSetting.getBackImageSize());
        menuImgSize = EasyUtil.dip2px(getContext(), titleBarSetting.getMenuImgSize());
        menuTextColor = titleBarSetting.getMenuTextColor();
        menuTextSize = titleBarSetting.getMenuTextSize();
        titleStyle = titleBarSetting.getTitleStyle();
        lineHeight = titleBarSetting.getLineHeight();
        lineColor = titleBarSetting.getLineColor();
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyTitleBar);

            boolean fitSystemWindow = ta.getBoolean(R.styleable.EasyTitleBar_Easy_fitsSystemWindows, false);
            titleLayout.setFitsSystemWindows(fitSystemWindow);

            //返回箭头
            Drawable backDrawable = ta.getDrawable(R.styleable.EasyTitleBar_Easy_backRes);
            if (backDrawable != null) {
                backImage.setImageDrawable(backDrawable);
            } else {
                backImage.setImageResource(backRes);
            }
            int backLayoutState = ta.getInt(R.styleable.EasyTitleBar_Easy_backLayoutState, 1);
            if (backLayoutState == 1) {
                backLayout.setVisibility(VISIBLE);
                backImage.setVisibility(VISIBLE);
            } else {
                backImage.setVisibility(GONE);
                backLayout.setVisibility(GONE);
            }

            //标题栏
            titleBarHeight = ta.getDimension(R.styleable.EasyTitleBar_Easy_titleBarHeight, titleBarHeight);
            titleBarBackGround = ta.getColor(R.styleable.EasyTitleBar_Easy_titleBarBackground, titleBarBackGround);
            titleLayout.setBackgroundColor(titleBarBackGround);
            LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) fit_cl.getLayoutParams();
            titleParams.height = (int) titleBarHeight;
            fit_cl.setLayoutParams(titleParams);

            //标题
            title = ta.getString(R.styleable.EasyTitleBar_Easy_title);
            if (null != title) {
                title_tv.setText(title);
            } else {
                if (context instanceof Activity) {
                    PackageManager pm = context.getPackageManager();
                    try {
                        ActivityInfo activityInfo = pm.getActivityInfo((((Activity) context).getComponentName()), 0);
                        setTitle(activityInfo.loadLabel(pm).toString());
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            titleTextSize = ta.getDimension(R.styleable.EasyTitleBar_Easy_titleSize, titleTextSize);
            title_tv.setTextSize(titleTextSize);
            titleColor = ta.getColor(R.styleable.EasyTitleBar_Easy_titleColor, titleColor);
            title_tv.setTextColor(titleColor);

            titleStyle = ta.getInt(R.styleable.EasyTitleBar_Easy_titleStyle, titleStyle);
            if (titleStyle == 0) {
                setTitleStyle(TITLE_STYLE_CENTER);
            } else {
                setTitleStyle(TITLE_STYLE_LEFT);
            }


            lineHeight = ta.getDimension(R.styleable.EasyTitleBar_Easy_lineHeight, 1);
            lineColor = ta.getColor(R.styleable.EasyTitleBar_Easy_lineColor, Color.parseColor("#cccccc"));
            ConstraintLayout.LayoutParams lineParams = (ConstraintLayout.LayoutParams) titleLine.getLayoutParams();
            lineParams.height = (int) lineHeight;
            titleLine.setBackgroundColor(lineColor);
            titleLine.setLayoutParams(lineParams);

            //间距
            viewPadding = ta.getDimension(R.styleable.EasyTitleBar_Easy_viewPadding, viewPadding);
            parentPadding = ta.getDimension(R.styleable.EasyTitleBar_Easy_parentPadding, parentPadding);
            ConstraintLayout.LayoutParams backLayoutParams = (ConstraintLayout.LayoutParams) backLayout.getLayoutParams();
            backLayoutParams.width = (int) (backImageSize + parentPadding * 2);
            backLayout.setLayoutParams(backLayoutParams);

            leftLayout.setPadding((int) (parentPadding - viewPadding / 2), 0, 0, 0);

            rightLayout.setPadding(0, 0, (int) (parentPadding - (viewPadding / 2)), 0);


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

            lineState = ta.getString(R.styleable.EasyTitleBar_Easy_lineState);


            //菜单图标大小
            menuImgSize = ta.getDimension(R.styleable.EasyTitleBar_Easy_menuImgSize, menuImgSize);
            //菜单文字大小
            menuTextSize = ta.getDimension(R.styleable.EasyTitleBar_Easy_menuTextSize, menuTextSize);
            //菜单文字颜色
            menuTextColor = (int) ta.getDimension(R.styleable.EasyTitleBar_Easy_menuTextColor, menuTextColor);

            //左边xml添加View

            String leftImageState = ta.getString(R.styleable.EasyTitleBar_Easy_leftLayoutState);
            if (null != leftImageState && leftImageState.equals("gone")) {
                leftLayout.setVisibility(GONE);
            }

            //One
            String leftOneText = ta.getString(R.styleable.EasyTitleBar_Easy_leftOneText);
            if (!TextUtils.isEmpty(leftOneText)) {
                addLeftView(new LayoutBuilder(getContext())
                        .text(leftOneText)
                        .createText());
            }
            Drawable leftOneImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_leftOneImage);
            if (leftOneImage != null) {
                addLeftView(new LayoutBuilder(getContext())
                        .drawable(leftOneImage)
                        .createImage());
            }
            //Two
            String leftTwoText = ta.getString(R.styleable.EasyTitleBar_Easy_leftTwoText);
            if (!TextUtils.isEmpty(leftTwoText)) {
                addLeftView(new LayoutBuilder(getContext())
                        .text(leftTwoText)
                        .createText());
            }
            Drawable leftTwoImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_leftTwoImage);
            if (leftTwoImage != null) {
                addLeftView(new LayoutBuilder(getContext())
                        .drawable(leftTwoImage)
                        .createImage());
            }
            //Three
            String leftThreeText = ta.getString(R.styleable.EasyTitleBar_Easy_leftThreeText);
            if (!TextUtils.isEmpty(leftThreeText)) {
                addLeftView(new LayoutBuilder(getContext())
                        .text(leftThreeText)
                        .createText());
            }
            Drawable leftThreeImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_leftThreeImage);
            if (leftThreeImage != null) {
                addLeftView(new LayoutBuilder(getContext())
                        .drawable(leftThreeImage)
                        .createImage());
            }

            //右侧xml添加View
            String rightImageState = ta.getString(R.styleable.EasyTitleBar_Easy_rightLayoutState);
            if (null != rightImageState && rightImageState.equals("gone")) {
                rightLayout.setVisibility(GONE);
            }
            //One
            String rightOneText = ta.getString(R.styleable.EasyTitleBar_Easy_rightOneText);
            if (!TextUtils.isEmpty(rightOneText)) {
                addRightView(new LayoutBuilder(getContext())
                        .text(rightOneText)
                        .createText());
            }
            Drawable rightOneImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_rightOneImage);
            if (rightOneImage != null) {
                addRightView(new LayoutBuilder(getContext())
                        .drawable(rightOneImage)
                        .createImage());
            }
            //Two
            String rightTwoText = ta.getString(R.styleable.EasyTitleBar_Easy_rightTwoText);
            if (!TextUtils.isEmpty(rightTwoText)) {
                addRightView(new LayoutBuilder(getContext())
                        .text(rightTwoText)
                        .createText());
            }
            Drawable rightTwoImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_rightTwoImage);
            if (rightTwoImage != null) {
                addRightView(new LayoutBuilder(getContext())
                        .drawable(rightTwoImage)
                        .createImage());
            }
            //Three
            String rightThreeText = ta.getString(R.styleable.EasyTitleBar_Easy_rightThreeText);
            if (!TextUtils.isEmpty(rightThreeText)) {
                addRightView(new LayoutBuilder(getContext())
                        .text(rightThreeText)
                        .createText());
            }
            Drawable rightThreeImage = ta.getDrawable(R.styleable.EasyTitleBar_Easy_rightThreeImage);
            if (rightThreeImage != null) {
                addRightView(new LayoutBuilder(getContext())
                        .drawable(rightThreeImage)
                        .createImage());
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

    public ViewGroup getLeftLayout() {
        return leftLayout;
    }

    public ViewGroup getRightLayout() {
        return rightLayout;
    }


    /**
     * 返回箭头的父布局
     *
     * @return
     */
    public LinearLayout getBackLayout() {
        return backLayout;
    }


    /**
     * 获取标题
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * 获取标题View
     *
     * @return
     */
    public TextView getTitleView() {
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
            leftConstraintSet.connect(title_tv.getId(), ConstraintSet.LEFT, backLayout.getId(), ConstraintSet.RIGHT, 0);
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

    public void attachScrollView(View view, final int color, final int height, final OnSrollAlphaListener onSrollAlphaListener) {
        EasyUtil.addOnSrollListener(view, new EasyUtil.OnSrollListener() {
            @Override
            public void onSrollEvent(int scrollY) {
                int baseColor = getResources().getColor(color);
                float alpha = Math.min(1, (float) scrollY / EasyUtil.dip2px(getContext(), height));
                setBackgroundColor(EasyUtil.getColorWithAlpha(alpha, baseColor));
                if (onSrollAlphaListener != null)
                    onSrollAlphaListener.OnSrollAlphaEvent(alpha);
            }
        });
    }

    //双击事件
    public interface OnDoubleClickListener {
        public void onDoubleEvent(View view);
    }

    public interface OnSrollAlphaListener {
        void OnSrollAlphaEvent(float alpha);
    }


    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static TitleBarSetting init() {
        titleBarSetting = TitleBarSetting.getInstance();
        return titleBarSetting;
    }

    public View getRightLayout(int position) {
        return rightLayout.getChildAt(position);
    }

    public View getLeftLayout(int position) {
        return leftLayout.getChildAt(position);
    }


    public static class LayoutBuilder {

        private Context context;

        private OnMenuClickListener onMenuClickListener;

        private String text;

        private int icon;
        private Drawable drawable;

        private int paddingleft;
        private int paddingright;

        private float menuImgSize;
        private float menuTextSize;
        private int menuTextColor;

        public LayoutBuilder(Context context) {
            this.context = context;
            paddingleft = (int) (viewPadding / 2);
            paddingright = (int) (viewPadding / 2);
            menuImgSize = EasyTitleBar.menuImgSize;
            menuTextSize = EasyTitleBar.menuTextSize;
            menuTextColor = EasyTitleBar.menuTextColor;
        }

        public LayoutBuilder text(String text) {
            this.text = text;
            return this;
        }

        public LayoutBuilder textSize(float textSize) {
            this.menuTextSize = textSize;
            return this;
        }

        public LayoutBuilder listener(OnMenuClickListener onMenuClickListener) {
            this.onMenuClickListener = onMenuClickListener;
            return this;
        }


        public LayoutBuilder paddingleft(int paddingleft) {
            this.paddingleft = paddingleft;
            return this;
        }

        public LayoutBuilder paddingright(int paddingright) {
            this.paddingright = paddingright;
            return this;
        }


        public LayoutBuilder icon(int icon) {
            this.icon = icon;
            return this;
        }

        public LayoutBuilder menuImgSize(int menuImgSize) {
            this.menuImgSize = menuImgSize;
            return this;
        }

        public LayoutBuilder drawable(Drawable drawable) {
            this.drawable = drawable;
            return this;
        }

        public LayoutBuilder menuTextColor(int menuTextColor) {
            this.menuTextColor = menuTextColor;
            return this;
        }

        public LayoutBuilder onItemClickListener(LayoutBuilder.OnMenuClickListener onMenuClickListener) {
            this.onMenuClickListener = onMenuClickListener;
            return this;
        }

        public View createText() {

            TextView textView = new TextView(context);
            textView.setText(text);
            textView.setTextSize(menuTextSize);
            textView.setTextColor(menuTextColor);
            textView.setPadding(paddingleft, 0, paddingright, 0);
            textView.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(textParams);

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null)
                        onMenuClickListener.OnMenuEvent();
                }
            });
            return textView;
        }

        public View createImage() {
            ImageView imageView = new ImageView(context);
            if (drawable != null)
                imageView.setImageDrawable(drawable);
            else if (icon != 0) {
                imageView.setImageResource(icon);
            } else {
                imageView.setImageBitmap(null);
            }
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageParams.width = (int) (menuImgSize + paddingleft + paddingright);
            imageView.setLayoutParams(imageParams);
            imageView.setPadding(paddingleft, 0, paddingright, 0);

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMenuClickListener != null)
                        onMenuClickListener.OnMenuEvent();
                }
            });

            return imageView;
        }


        public interface OnMenuClickListener {
            void OnMenuEvent();
        }

    }

    public void addRightView(View view) {
        rightLayout.addView(view);
    }

    public void addLeftView(View view) {
        leftLayout.addView(view);
    }

    public ImageView addRightImg(int res) {
        return addRightImg(res, null);
    }

    public TextView addRightText(String str) {
        return addRightText(str, null);
    }

    public ImageView addLeftImg(int res) {
        return addLeftImg(res, null);
    }

    public TextView addLeftText(String str) {
        return addLeftText(str, null);
    }

    public ImageView addRightImg(int res, LayoutBuilder.OnMenuClickListener onMenuClickListener) {
        ImageView imageView = (ImageView) new LayoutBuilder(getContext())
                .icon(res)
                .listener(onMenuClickListener)
                .createImage();
        addRightView(imageView);
        return imageView;
    }

    public TextView addRightText(String str, LayoutBuilder.OnMenuClickListener onMenuClickListener) {
        TextView textView = (TextView) new LayoutBuilder(getContext())
                .text(str)
                .listener(onMenuClickListener)
                .createText();
        addRightView(textView);
        return textView;
    }

    public ImageView addLeftImg(int res, LayoutBuilder.OnMenuClickListener onMenuClickListener) {
        ImageView imageView = (ImageView) new LayoutBuilder(getContext())
                .icon(res)
                .listener(onMenuClickListener)
                .createImage();
        addLeftView(imageView);
        return imageView;
    }

    public TextView addLeftText(String str, LayoutBuilder.OnMenuClickListener onMenuClickListener) {
        TextView textView = (TextView) new LayoutBuilder(getContext())
                .text(str)
                .listener(onMenuClickListener)
                .createText();
        addLeftView(textView);
        return textView;
    }

}
