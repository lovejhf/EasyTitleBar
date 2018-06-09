package com.next.easytitlebar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
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
    protected ImageView left_image;
    //右侧viewgroup
    protected LinearLayout rightLayout;
    protected ImageView rightImage;
    protected TextView title_tv;
    protected LinearLayout titleLayout;
    protected TextView rightText;

    //标题栏高度
    private float titleBarHeight = SmallUtil.dip2px(getContext(), 48);
    //标题栏背景
    private int titleBarBackGround = Color.parseColor("#FFFFFF");

    //分割线
    private View titleLine;


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

    private int layoutSize = SmallUtil.dip2px(getContext(), 40);

    private int textColor = Color.parseColor("#333333");
    private int textSize = SmallUtil.sp2px(getContext(),10);

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
        left_image = findViewById(R.id.left_image);
        rightLayout = findViewById(R.id.right_layout);
        rightImage = findViewById(R.id.right_image);
        title_tv = findViewById(R.id.title_tv);
        titleLayout = findViewById(R.id.root);
        titleLine = findViewById(R.id.titlebar_line);

        parseStyle(context, attrs);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyTitleBar);

            //标题栏
            titleBarHeight = ta.getDimension(R.styleable.EasyTitleBar_titlebar_height, SmallUtil.dip2px(context, 48));
            titleBarBackGround = ta.getColor(R.styleable.EasyTitleBar_titlebar_background, 0xffffff);
            RelativeLayout.LayoutParams titleLayoutParams = (RelativeLayout.LayoutParams) titleLayout.getLayoutParams();
            titleLayoutParams.height = (int) titleBarHeight;
            titleLayout.setBackgroundColor(titleBarBackGround);

            //标题
            String title = ta.getString(R.styleable.EasyTitleBar_title_text);
            if (null != title) {
                title_tv.setText(title);
            }
            titleTextSize = ta.getDimension(R.styleable.EasyTitleBar_title_size, SmallUtil.sp2px(context, 13));
            title_tv.setTextSize(titleTextSize);
            titleColor = ta.getColor(R.styleable.EasyTitleBar_title_color, Color.parseColor("#333333"));
            title_tv.setTextColor(titleColor);
            LayoutParams titleParams = (LayoutParams) title_tv.getLayoutParams();

            titleStyle = ta.getString(R.styleable.EasyTitleBar_title_style);
            if (titleStyle==null||titleStyle.equals("center")) {
                titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            } else {
                titleParams.addRule(RelativeLayout.CENTER_VERTICAL);
            }
            title_tv.setLayoutParams(titleParams);


            //分割线
            titleLineHeight = ta.getDimension(R.styleable.EasyTitleBar_line_height, 1);
            titleLineColor = ta.getColor(R.styleable.EasyTitleBar_line_color, Color.parseColor("#cccccc"));
            LinearLayout.LayoutParams lineParams = (LinearLayout.LayoutParams) titleLine.getLayoutParams();
            lineParams.height = (int) titleLineHeight;
            titleLine.setBackgroundColor(titleLineColor);
            titleLine.setLayoutParams(lineParams);

            //左侧图标
            Drawable leftDrawable = ta.getDrawable(R.styleable.EasyTitleBar_left_image);
            if (null != leftDrawable) {
                leftLayout.setVisibility(VISIBLE);
                left_image.setImageDrawable(leftDrawable);
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


            String leftImageState = ta.getString(R.styleable.EasyTitleBar_titlebarLeftState);
            if (null != leftImageState && leftImageState.equals("gone")) {
                leftLayout.setVisibility(GONE);
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
        LayoutParams titleParams = (LayoutParams) title_tv.getLayoutParams();
        if (style == TITLE_STYLE_LEFT) {
            titleParams.addRule(RelativeLayout.CENTER_VERTICAL);
            titleParams.removeRule(RelativeLayout.CENTER_IN_PARENT);
        } else {
            titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            titleParams.removeRule(RelativeLayout.CENTER_VERTICAL);
        }
        title_tv.setLayoutParams(titleParams);
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
        LinearLayout imageLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.width = layoutSize;
        imageLayout.setGravity(Gravity.CENTER_VERTICAL);
        imageLayout.setLayoutParams(layoutParams);

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(icon);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageParams.width = imageSize;
        imageParams.height = imageSize;
        imageView.setLayoutParams(imageParams);

        imageLayout.addView(imageView);

        rightLayout.addView(imageLayout);


        imageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemEvent();
            }
        });
    }

    public void addItem(String text, final OnItemClickListener onItemClickListener) {
        LinearLayout textLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textLayout.setPadding(SmallUtil.dip2px(getContext(),15),0,SmallUtil.dip2px(getContext(),15),0);
        textLayout.setGravity(Gravity.CENTER);
        textLayout.setLayoutParams(layoutParams);

        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);

        textLayout.addView(textView);

        rightLayout.addView(textLayout);


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
}
