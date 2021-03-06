package com.danielburgnerjr.bakingapp.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielburgnerjr.bakingapp.model.Recipe;
import com.danielburgnerjr.bakingapp.R;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeVH> {

    private Context mContext;
    private static ItemListener mItemListener;
    private List<Recipe> mData;
    private int[] drawableIds = {R.drawable.n_pie, R.drawable.brownies, R.drawable.yellow_cake, R.drawable.cheese_cake};

    public RecipesAdapter(Context mContext, ItemListener mItemListener, List<Recipe> mData) {
        this.mContext = mContext;
        RecipesAdapter.mItemListener = mItemListener;
        this.mData = mData;
    }

    public interface ItemListener {
        void onRecipeClicked(int position);
    }

    @NonNull
    @Override
    public RecipeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeVH holder, int position) {
        mData.get(position).setImageId(drawableIds[position]);
        holder.setData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class RecipeVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recipeIv;
        TextView recipeTv;
        int posVh;

        RecipeVH(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            recipeIv = itemView.findViewById(R.id.recipe_iv);
            recipeTv = itemView.findViewById(R.id.recipe_tv);
        }

        void setData(Recipe data, int pos) {
            recipeTv.setText(data.getName());
            recipeIv.setImageResource(data.getImageId());
            posVh = pos;
        }

        @Override
        public void onClick(View v) {
            RecipesAdapter.mItemListener.onRecipeClicked(posVh);
        }
    }
}
