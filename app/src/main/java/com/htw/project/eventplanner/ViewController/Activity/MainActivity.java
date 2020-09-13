package com.htw.project.eventplanner.ViewController.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.htw.project.eventplanner.R;
import com.htw.project.eventplanner.ViewController.ActionbarProvider;
import com.htw.project.eventplanner.ViewController.Controller.ActionBarController;
import com.htw.project.eventplanner.ViewController.Fragment.AbstractFragment;
import com.htw.project.eventplanner.ViewController.Fragment.GroupConversationFragment;
import com.htw.project.eventplanner.ViewController.FragmentChangeListener;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener, ActionbarProvider {

    private ActionBarController actionBarController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        openInitFragment();
    }

    private void initComponents(){
        // actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBarController = new ActionBarController(toolbar);
    }

    private void openInitFragment(){
        pushFragment(GroupConversationFragment.newInstance());
    }

    // ##### Interface Implementation #####

    @Override
    public void pushFragment(AbstractFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    @Override
    public void popCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public ActionBarController getActionBarController() {
        return actionBarController;
    }
}