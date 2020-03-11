package com.danielburgnerjr.bakingapp.utils;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danielburgnerjr.bakingapp.R;
import com.danielburgnerjr.bakingapp.model.Ingredient;

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