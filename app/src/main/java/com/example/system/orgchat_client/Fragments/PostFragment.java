package com.example.system.orgchat_client.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.system.orgchat_client.Activities.HomeNav;
import com.example.system.orgchat_client.R;

public class PostFragment extends Fragment {

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
