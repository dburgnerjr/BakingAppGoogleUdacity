package com.danielburgnerjr.bakingapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielburgnerjr.bakingapp.R;
import com.danielburgnerjr.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    Context mContext;
    List<Ingredient> mItems;

    public IngredientAdapter(Context mContext, List<Ingredient> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView recipeAmount;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.ingredient_name_tv);
            recipeAmount = itemView.findViewById(R.id.ingredient_amount_tv);
        }

        public void setData(Ingredient data) {
            recipeName.setText(data.getIngredient());
            recipeAmount.setText(mContext.getResources().getString(R.string.ingredient_amount, data.getQuantity(), data.getMeasure()));
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.setData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
