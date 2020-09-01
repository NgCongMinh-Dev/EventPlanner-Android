package com.htw.project.eventplanner.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.htw.project.eventplanner.BuildConfig;

public class FontManager {

    /**
     * Returns the {@link Typeface} for FontAwesome.
     *
     * @param context context
     * @return typeface
     */
    public static Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), BuildConfig.FONTAWESOME_LOCATION);
    }

    /**
     * Marks the given {@link TextView} as a container for FontAwesome icon.
     *
     * @param context  context
     * @param textView textView should be marked as
     */
    public static void markAsIconContainer(Context context, TextView textView) {
        Typeface typeface = getTypeface(context);
        textView.setTypeface(typeface);
    }

}