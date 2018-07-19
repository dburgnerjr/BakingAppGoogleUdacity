package com.danielburgnerjr.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.danielburgnerjr.bakingapp.R;
import com.danielburgnerjr.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AppWidgetListView(getApplicationContext());
    }
}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    String recipe_name = "Recipe_Name";
    List<Ingredient> ingredients = new ArrayList<>();

    public AppWidgetListView(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        ingredients.add(new Ingredient(20.0, "pb", "ingredient0"));
        ingredients.add(new Ingredient(21.0, "pl", "ingredient1"));
        ingredients.add(new Ingredient(22.0, "pa", "ingredient2"));
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_item_widget);

        remoteViews.setTextViewText(R.id.ingredient_name_widget_tv, ingredients.get(position).getIngredient());
        remoteViews.setTextViewText(R.id.ingredient_amount_widget_tv, ingredients.get(position).getMeasure());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
