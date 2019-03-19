package com.licun.storyme.storyme_phone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class UploadStatePagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> _mFragment  = new ArrayList<>();

    public UploadStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add_fragment(Fragment fragment){
        this._mFragment.add(fragment);
    }

    @Override

    public Fragment getItem(int i) {
        return _mFragment.get(i);
    }

    @Override
    public int getCount() {
        return _mFragment.size();
    }
}
