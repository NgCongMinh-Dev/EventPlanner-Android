package com.htw.project.eventplanner.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.ViewScaleConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TabBar extends LinearLayout {

    private static final int TAB_BAR_HEIGHT = 50;

    private static final float ALPHA_NON_SELECTED_STATE = 0.3f;

    private static final float ALPHA_SELECTED_STATE = 1f;

    private static final Integer START_TAB_INDEX = new Integer(0);

    private Map<Integer, TabBarElement> elements;

    private Integer activeTabIndex;

    public TabBar(Context context) {
        super(context);
        init();
    }

    public TabBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        elements = new HashMap<>();
        activeTabIndex = 0;

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewScaleConverter.toDP(getContext(), TAB_BAR_HEIGHT)));
        setBackgroundColor(getResources().getColor(R.color.grey));
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void addView(View child) {
        if (!(child instanceof TabBarElement)) {
            return;
        }
        Integer index = new Integer(elements.size());

        TabBarElement element = (TabBarElement) child;
        element.index = index;
        element.tabBar = this;

        elements.put(index, element);

        if (index.equals(START_TAB_INDEX)) {
            element.activate();
        } else {
            element.deactivate();
        }

        super.addView(element);
    }

    private void notify(Integer index) {
        TabBarElement selectedElement = elements.get(index);
        selectedElement.activate();

        List<TabBarElement> nonSelectedElements = elements.entrySet().stream()
                .filter(x -> x.getKey() != index)
                .map(x -> x.getValue())
                .collect(Collectors.toList());

        nonSelectedElements.stream().forEach(x -> x.deactivate());
        activeTabIndex = index;
    }

    @SuppressLint("AppCompatCustomView")
    public static class TabBarElement extends ImageButton {

        private static final float LAYOUT_WEIGHT = 1f;

        private Integer index;

        private TabBar tabBar;

        public TabBarElement(Context context) {
            super(context);
            init();
        }

        public TabBarElement(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public TabBarElement(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, LAYOUT_WEIGHT));
            setBackgroundColor(getResources().getColor(R.color.grey));
        }

        private void activate() {
            setAlpha(ALPHA_SELECTED_STATE);
        }

        private void deactivate() {
            setAlpha(ALPHA_NON_SELECTED_STATE);
        }

    }

    public static abstract class TabBarElementClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (!(view instanceof TabBarElement)) {
                return;
            }

            TabBarElement element = (TabBarElement) view;
            Integer index = element.index;

            TabBar tabBar = element.tabBar;
            if (tabBar.activeTabIndex == index) {
                return;
            }
            tabBar.notify(index);

            execute(view);
        }

        protected abstract void execute(View view);

    }


}
