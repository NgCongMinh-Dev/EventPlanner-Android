package com.htw.project.eventplanner.ViewController.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.htw.project.eventplanner.ViewController.ActionbarProvider;
import com.htw.project.eventplanner.ViewController.Controller.ActionBarController;
import com.htw.project.eventplanner.ViewController.FragmentChangeListener;

public abstract class AbstractFragment extends Fragment {

    private FragmentChangeListener fragmentChangeListener;
    protected ActionBarController actionBarController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);
        return view;
    }

    protected abstract int getLayoutResourceId();

    @Override
    public void onStart() {
        super.onStart();

        fragmentChangeListener = (FragmentChangeListener) getActivity();
        actionBarController = ((ActionbarProvider) getActivity()).getActionBarController();
    }

    @Override
    public void onStop() {
        super.onStop();

        fragmentChangeListener = null;
    }

    protected void changeFragment(AbstractFragment fragment) {
        fragmentChangeListener.pushFragment(fragment);
    }

    protected <T> T getViewElement(final View parentView, final int childId) {
        return (T) parentView.findViewById(childId);
    }

}
