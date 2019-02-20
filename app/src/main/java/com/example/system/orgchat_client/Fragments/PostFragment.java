package com.example.system.orgchat_client.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    String tabs[] = {"Suggestion" , "Compliant"};

    public PostFragment() {
        // Required empty public constructor
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


        return root;
    }

}
