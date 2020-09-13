package com.htw.project.eventplanner.ViewController;

import com.htw.project.eventplanner.ViewController.Fragment.AbstractFragment;

public interface FragmentChangeListener {

    void pushFragment(AbstractFragment fragment);

    void popCurrentFragment();

}
