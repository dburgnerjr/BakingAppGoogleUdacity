package com.danielburgnerjr.bakingapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielburgnerjr.bakingapp.R;
import com.danielburgnerjr.bakingapp.model.Recipe;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.RecipesAdapterViewHolder> {

    private Context mContext;

    private ArrayList<Integer> arrayListImages;
    private ArrayList<Recipe> mRecipeArrayListData;
    private RecipesAdapterOnClickHandler recipesAdapterOnClickHandler;

    public interface RecipesAdapterOnClickHandler {
        void onRecipeClick(Recipe recipe,int position);
    }

    public ListAdapter(RecipesAdapterOnClickHandler OnClickHandler){
        recipesAdapterOnClickHandler=OnClickHandler;
    }


    @NonNull
    @Override
    public ListAdapter.RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext=parent.getContext();

        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        int resource= R.layout.adapter_main_design;
        View view=layoutInflater.inflate(resource,parent,false);

        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.RecipesAdapterViewHolder holder, int position) {

        holder.recipeTitle.setText(mRecipeArrayListData.get(position).getName());


        arrayListImages=new ArrayList<>();
        arrayListImages.add(R.drawable.n_pie);
        arrayListImages.add(R.drawable.brownies);
        arrayListImages.add(R.drawable.yellow_cake);
        arrayListImages.add(R.drawable.cheese_cake);

        int imagePosition= arrayListImages.get(position);

        holder.recipePhoto.setImageResource(imagePosition);

    }

    public void setRecipesData(ArrayList<Recipe> recipesData){
        mRecipeArrayListData = recipesData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mRecipeArrayListData == null) {
            return 0;
        } else {
            return mRecipeArrayListData.size();
        }
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recipePhoto;
        TextView recipeTitle;

        public RecipesAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipeTitle = itemView.findViewById(R.id.cardView_textView_recipe_name);
            recipePhoto = itemView.findViewById(R.id.cardview_imageView);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe=mRecipeArrayListData.get(getAdapterPosition());
            recipesAdapterOnClickHandler.onRecipeClick(recipe,getAdapterPosition());
        }
    }
}
