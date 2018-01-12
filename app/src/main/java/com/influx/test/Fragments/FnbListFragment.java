package com.influx.test.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.influx.test.Adapters.FooditemListAdapter;
import com.influx.test.Interface.OnItemCount;
import com.influx.test.Models.MainItemModel;
import com.influx.test.R;

import java.util.ArrayList;

public class FnbListFragment extends Fragment {

    View view;

    RecyclerView fnbListRCView;

    ArrayList<MainItemModel> mainItemModel;

    FooditemListAdapter fooditemListAdapter;

    OnItemCount onItemCount;

    public FnbListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fnblist_frag, container, false);

        fnbListRCView = (RecyclerView)view.findViewById(R.id.fnbListRCView);

        Bundle bundle = getArguments();
        assert bundle != null;
        mainItemModel = (ArrayList<MainItemModel>) bundle.getSerializable("MainItemModel");

        fooditemListAdapter = new FooditemListAdapter(getContext());
        fooditemListAdapter.setData(mainItemModel);
        fooditemListAdapter.setInterface(onItemCount);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        fnbListRCView.setLayoutManager(mLayoutManager);
        fnbListRCView.setItemAnimator(new DefaultItemAnimator());
        fnbListRCView.setAdapter(fooditemListAdapter);

        return view;
    }

    public void setInterfaceObject(OnItemCount onItemCount) {
        this.onItemCount = onItemCount;
    }

}
