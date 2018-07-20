package com.danielburgnerjr.bakingapp.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danielburgnerjr.bakingapp.R;
import com.danielburgnerjr.bakingapp.model.Ingredient;
import com.danielburgnerjr.bakingapp.utils.IngredientAdapter;

import java.util.List;

public class IngredientFragment extends Fragment {

    public static String INGREDIENTS_EXTRA = "i_extra";
    List<Ingredient> mIngredients = null;

    public IngredientFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ingredients_fragment, container, false);

        mIngredients = getArguments().getParcelableArrayList(INGREDIENTS_EXTRA);

        RecyclerView rv = view.findViewById(R.id.ingredients_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        IngredientAdapter ingredientsAdapter = new IngredientAdapter(getActivity(), mIngredients);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(ingredientsAdapter);

        return view;
    }
}