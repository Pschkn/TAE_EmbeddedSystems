package smartcart.activities;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import smartcart.abstractclasses.CustomPagerAdapter;
import smartcart.model.shoppingList;

/**
 * Created by admin on 25.03.2017.
 */

public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
    private ViewPager viewPager;
    private CustomPagerAdapter mAdapter;
    private ActionBar actionBar;
    ArrayList values;
    ArrayAdapter<String> adapter;
    // Tab titles
    private String[] tabs = { "Manage List", "Go Shopping"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shoppingList.InitShoppingList();
        setContentView(R.layout.starting_page);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new CustomPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    @Override
    protected void onResume(){
        super.onResume();

        if(viewPager.getCurrentItem() == 0){
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, shoppingList.GetShoppingList());
            manageListDefaultFragment current = (manageListDefaultFragment)mAdapter.getItem(0);
            current.SetAdapter(adapter);
        }
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
