package com.htw.project.eventplanner.View;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.htw.project.eventplanner.Utils.FontManager;

public class FontAwesome extends AppCompatTextView {

    public FontAwesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesome(Context context) {
        super(context);
        init();
    }

    private void init() {
        FontManager.markAsIconContainer(getContext(), this);
    }

}
