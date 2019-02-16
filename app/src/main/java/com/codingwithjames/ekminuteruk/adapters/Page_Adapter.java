package com.codingwithjames.ekminuteruk.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.codingwithjames.ekminuteruk.fragments.PageFragment;

/**
 * Created by James on 12/31/2018.
 */

public class Page_Adapter extends FragmentStatePagerAdapter {

    public Page_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        PageFragment child = new PageFragment();
        return child;
    }

    @Override
    public int getCount() {
        return 10000;
    }
}
