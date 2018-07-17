package com.danielburgnerjr.bakingapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AndroidException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.danielburgnerjr.bakingapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailRecipeListFragment extends Fragment {

    public static String LIST_NAMES_EXTRA = "names_extra";

    private ArrayList<String> mNameList;

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnItemClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnItemClickListener {
        void onItemSelected(int position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public DetailRecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mNameList = savedInstanceState.getStringArrayList(LIST_NAMES_EXTRA);
        } else {
            mNameList = getArguments().getStringArrayList(LIST_NAMES_EXTRA);
        }

        final View rootView = inflater.inflate(R.layout.fragment_detail_recipe_list,container,false);

        ListView listView = (ListView)rootView.findViewById(R.id.lv);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, mNameList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),mNameList.get(position) + "is clicked.",Toast.LENGTH_SHORT).show();
                mCallback.onItemSelected(position);
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(LIST_NAMES_EXTRA,mNameList);
    }
}
