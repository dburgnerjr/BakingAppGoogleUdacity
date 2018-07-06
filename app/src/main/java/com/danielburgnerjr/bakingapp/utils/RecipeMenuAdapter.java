package com.danielburgnerjr.bakingapp.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielburgnerjr.bakingapp.R;
import com.danielburgnerjr.bakingapp.model.Recipe;

import java.util.List;
import java.util.ArrayList;

public class RecipeMenuAdapter extends ArrayAdapter {
    private Context mContext;
    private int mLayoutResourceId;
    public List<Recipe> mRecipeData = new ArrayList<>();

    public RecipeMenuAdapter(Context context, int layoutResourceId, List<Recipe> data) {
        super(context, layoutResourceId, data);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mRecipeData = data;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = null;
        Recipe currentRecipe = (Recipe) getItem(position);

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) convertView.findViewById(R.id.recipe_grid_view);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageTitle.setText(currentRecipe.getName());
        holder.image.setImageResource(R.drawable.honey_lemon_tea);

        return convertView;
    }
}