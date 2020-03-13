package com.danielburgnerjr.bakingapp.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danielburgnerjr.bakingapp.R;

import java.util.ArrayList;

public class DetailRecipeListFragment extends Fragment {

    public static String LIST_NAMES_EXTRA = "names_extra";

    private ArrayList<String> mNameList;

    public DetailRecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mNameList = savedInstanceState.getStringArrayList(LIST_NAMES_EXTRA);
        } else {
            if (getArguments() != null)
                mNameList = getArguments().getStringArrayList(LIST_NAMES_EXTRA);
        }

        final View rootView = inflater.inflate(R.layout.fragment_detail_recipe_list,container,false);

        RecyclerView recyclerView = rootView.findViewById(R.id.lv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ListAdapter listAdapter = new ListAdapter(getContext(),(ListAdapter.ItemListener) getContext(), mNameList);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(LIST_NAMES_EXTRA,mNameList);
    }
}
