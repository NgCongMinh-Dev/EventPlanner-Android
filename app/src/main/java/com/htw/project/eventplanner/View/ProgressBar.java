package com.htw.project.eventplanner.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.ViewScaleConverter;

public class ProgressBar extends LinearLayout {

    private static final int PROGRESS_BAR_HEIGHT = 30;

    private TextView progressView;

    private TextView remainingProgressView;

    public ProgressBar(Context context) {
        super(context);
        init();
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewScaleConverter.toDP(getContext(), PROGRESS_BAR_HEIGHT)));
        setOrientation(HORIZONTAL);

        progressView = new TextView(getContext());
        progressView.setLayoutParams(new LayoutParams(ViewScaleConverter.toDP(getContext(), 0), ViewGroup.LayoutParams.MATCH_PARENT));
        progressView.setBackgroundColor(getResources().getColor(R.color.grey_dark));
        progressView.setTextColor(getResources().getColor(R.color.black));
        progressView.setGravity(Gravity.CENTER);
        progressView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        progressView.setTextSize(10);
        progressView.setTypeface(progressView.getTypeface(), Typeface.BOLD);
        addView(progressView);

        remainingProgressView = new TextView(getContext());
        remainingProgressView.setLayoutParams(new LayoutParams(ViewScaleConverter.toDP(getContext(), 0), ViewGroup.LayoutParams.MATCH_PARENT));
        remainingProgressView.setBackgroundColor(getResources().getColor(R.color.grey_light));
        remainingProgressView.setGravity(Gravity.CENTER);
        addView(remainingProgressView);
    }

    public void setProgress(float percentage) {
        float decimalPercentage = percentage / 100f;

        setLayoutWeight(progressView, decimalPercentage);
        setLayoutWeight(remainingProgressView, 1 - decimalPercentage);

        progressView.setText(new Integer(Math.round(percentage)) + "%");
    }

    private void setLayoutWeight(TextView view, float value) {
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        params.weight = value;

        view.setLayoutParams(params);
    }

}
