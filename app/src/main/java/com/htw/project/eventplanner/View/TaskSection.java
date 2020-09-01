package com.htw.project.eventplanner.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.Utils.ViewScaleConverter;

public class TaskSection extends LinearLayout {

    private Bitmap iconExpandLess;

    private Bitmap iconExpandMore;

    private TextView headerTitle;

    private RecyclerView taskContainer;

    public TaskSection(Context context) {
        super(context);
        init();
    }

    public TaskSection(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TaskSection(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setBackgroundColor(getResources().getColor(R.color.grey));
        setOrientation(LinearLayout.VERTICAL);

        // section header
        LinearLayout headerContainer = new LinearLayout(getContext());
        headerContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerContainer.setGravity(Gravity.CENTER_VERTICAL);
        headerContainer.setOrientation(HORIZONTAL);
        headerContainer.setPadding(
                ViewScaleConverter.toDP(getContext(), 10),
                ViewScaleConverter.toDP(getContext(), 10),
                ViewScaleConverter.toDP(getContext(), 10),
                ViewScaleConverter.toDP(getContext(), 10));
        addView(headerContainer);

        // section header - title
        headerTitle = new TextView(headerContainer.getContext());
        headerTitle.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        headerTitle.setText("Section");
        headerTitle.setTextColor(getResources().getColor(R.color.black));
        headerTitle.setTextSize(20);
        headerTitle.setTypeface(headerTitle.getTypeface(), Typeface.BOLD);
        headerContainer.addView(headerTitle);

        // section header - expand icon
        iconExpandLess = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_expand_less);
        iconExpandMore = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_expand_more);

        ImageButton headerExpandIcon = new ImageButton(headerContainer.getContext());
        headerExpandIcon.setLayoutParams(new LayoutParams(ViewScaleConverter.toDP(getContext(), 30), ViewScaleConverter.toDP(getContext(), 30)));
        headerExpandIcon.setLeft(ViewScaleConverter.toDP(getContext(), 10));
        headerExpandIcon.setBackgroundColor(getResources().getColor(R.color.grey));
        headerExpandIcon.setImageBitmap(iconExpandMore);
        headerExpandIcon.setOnClickListener(view -> {
            if (taskContainer.getVisibility() == VISIBLE) {
                taskContainer.setVisibility(GONE);
                headerExpandIcon.setImageBitmap(iconExpandMore);
            } else {
                taskContainer.setVisibility(VISIBLE);
                headerExpandIcon.setImageBitmap(iconExpandLess);
            }
        });
        headerContainer.addView(headerExpandIcon);

        // task container
        taskContainer = new RecyclerView(getContext());
        RecyclerView.LayoutParams taskContainerLayoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        taskContainerLayoutParams.setMargins(
                ViewScaleConverter.toDP(getContext(), 10),
                0,
                ViewScaleConverter.toDP(getContext(), 10),
                0);
        taskContainer.setLayoutParams(taskContainerLayoutParams);
        taskContainer.setVisibility(GONE);
        taskContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        taskContainer.setItemAnimator(new DefaultItemAnimator());
        taskContainer.setNestedScrollingEnabled(false);
        addView(taskContainer);
    }

    public RecyclerView getTaskContainer() {
        return taskContainer;
    }

    public void setSectionTitle(int resId) {
        headerTitle.setText(resId);
    }

}
