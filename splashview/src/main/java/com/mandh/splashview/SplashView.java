package com.mandh.splashview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.mandh.splashview.interfaces.SplashListener;

public class SplashView extends FrameLayout {
    Context context;
    ConstraintLayout container;
    SplashListener splashListener;
    String text;
    TextView textView;
    int backgroundColor, textColor;

    int duration = 2000;

    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        setAttrs(attrs);
        initView();
        setDefinitions();
    }

    /**
     * This method parse custom attributes
     *
     * @param attrs AttributeSet of view
     */
    private void setAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MandhSplashView,
                0, 0);

        try {
            duration = typedArray.getInt(R.styleable.MandhSplashView_duration, 2000);
            backgroundColor = typedArray.getColor(R.styleable.MandhSplashView_backgroundColor, getResources().getColor(R.color.background));
            textColor = typedArray.getColor(R.styleable.MandhSplashView_color, getResources().getColor(R.color.white));
            text = typedArray.getString(R.styleable.MandhSplashView_text);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * This method prepare view and definitions.
     */
    private void initView() {
        inflate(getContext(), R.layout.splash_layout, this);

        setDefinitions();
        setValues();
    }

    /**
     * This method set view variables.
     */
    private void setDefinitions() {
        container = findViewById(R.id.root_view);
        textView = findViewById(R.id.splash_text);
    }

    private void setValues() {
        container.setBackgroundColor(backgroundColor);
        textView.setTextColor(textColor);

        if (text != null) {
            textView.setText(text);
        }
    }

    public void show() {
        container.setVisibility(VISIBLE);
        setElevation(Float.MAX_VALUE);

        startAnimation();

        Runnable runnable = () -> {
            if (splashListener != null) {
                splashListener.onDurationFinish(this);
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, duration);
    }

    public void hide() {
        container.setVisibility(GONE);
    }

    public void startAnimation() {
        ImageView image = findViewById(R.id.mandh_logo_shine);

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());

        image.startAnimation(rotate);
    }

    public void setOnSplashListener(SplashListener splashListener) {
        this.splashListener = splashListener;
    }
}
