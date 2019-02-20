package com.example.system.orgchat_client.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.system.orgchat_client.Fragments.CompliantSwipeFragment;
import com.example.system.orgchat_client.Fragments.SuggestionSwipeFragment;

public class SwipeAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"Suggestion", "Compliant"};

    public SwipeAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0:
                return new SuggestionSwipeFragment();
            case 1:
                return new CompliantSwipeFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
