package com.example.alexander.extrabraintesting.Fragment.Content;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexander.extrabraintesting.R;

/**
 * Created by Alexander on 2014-10-17.
 */
public class TimeFragment extends ListFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_time,container,false);
    }
}
