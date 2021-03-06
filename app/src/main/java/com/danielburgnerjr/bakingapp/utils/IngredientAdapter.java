package com.danielburgnerjr.bakingapp.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielburgnerjr.bakingapp.R;
import com.danielburgnerjr.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context mContext;
    private List<Ingredient> mItems;

    IngredientAdapter(Context mContext, List<Ingredient> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView recipeAmount;

        IngredientViewHolder(View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.ingredient_name_tv);
            recipeAmount = itemView.findViewById(R.id.ingredient_amount_tv);
        }

        void setData(Ingredient data) {
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
