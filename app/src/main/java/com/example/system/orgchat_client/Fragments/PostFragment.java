package com.example.system.orgchat_client.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.system.orgchat_client.Activities.HomeNav;
import com.example.system.orgchat_client.Adapters.SwipeAdapter;
import com.example.system.orgchat_client.R;

public class PostFragment extends Fragment {

    ActionBar actionBar;
    SwipeAdapter swipeAdapter;
    ViewPager pager;
    TabLayout tab;
    Context c;

    String tabs[] = {"Suggestion" , "Compliant"};

    public PostFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public PostFragment(Context c, ActionBar actionBar) {

        this.c = c;
        this.actionBar = actionBar;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, container, false);

        ((HomeNav)getActivity()).setActionBarTitle("Post");

        //actionBar = ((HomeNav) getActivity()).getSupportActionBar();
        swipeAdapter = new SwipeAdapter(getFragmentManager());
        pager = (ViewPager)root.findViewById(R.id.view_pager);
        tab = (TabLayout)root.findViewById(R.id.tabLayout);

        pager.setAdapter(swipeAdapter);
        //tab.setHomeButtonEnabled(false);
        tab.setupWithViewPager(pager);



        /*for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener((ActionBar.TabListener) c.getApplicationContext()));

        }*/

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


        return root;
    }

}