package com.danielburgnerjr.bakingapp.utils;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AndroidException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.danielburgnerjr.bakingapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailRecipeListFragment extends Fragment {

    public DetailRecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail_recipe_list,container,false);

        ListView listView = (ListView)rootView.findViewById(R.id.lv);
        // Initializing a new String Array
        String[] fruits = new String[] {
                "Cattley Guava",
                "Cawesh",
                "Cedar Bay cherry ",
                "Chayote",
                "Carambola",
                "Cantaloupe"
        };

        // Create a List from String Array elements
        List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));

        // Create a ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, fruits_list);
        listView.setAdapter(arrayAdapter);

        return rootView;
    }
}
