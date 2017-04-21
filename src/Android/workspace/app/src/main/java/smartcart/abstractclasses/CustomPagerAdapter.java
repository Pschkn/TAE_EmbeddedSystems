package smartcart.abstractclasses;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import smartcart.activities.MainActivity;
import smartcart.activities.goShoppingDefaultFragment;
import smartcart.activities.manageListDefaultFragment;

/**
 * Created by admin on 25.03.2017.
 */

public class CustomPagerAdapter extends FragmentPagerAdapter {

    Fragment manageFragment, shoppingFragment;

    public CustomPagerAdapter(FragmentManager fm) {

        super(fm);
        manageFragment = new manageListDefaultFragment();
        shoppingFragment = new goShoppingDefaultFragment();
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return manageFragment;
            case 1:
                return shoppingFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
}