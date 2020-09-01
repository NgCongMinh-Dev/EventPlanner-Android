package com.htw.project.eventplanner.Utils;

import android.content.Context;

public final class ViewScaleConverter {

    private ViewScaleConverter() {
    }

    public static int toDP(Context context, int number) {
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (number * scale + 0.5f);
        return dpAsPixels;
    }

}
