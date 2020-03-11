package com.danielburgnerjr.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.danielburgnerjr.bakingapp.model.Ingredient;
import com.danielburgnerjr.bakingapp.utils.IngredientFragment;

import java.util.List;
import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {
    public static String INGREDIENTS_EXTRA = "i_extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Intent intent =  getIntent();
        if (intent == null) {
            closeOnError();
        }

        List<Ingredient> mIngredients = intent.getParcelableArrayListExtra(INGREDIENTS_EXTRA);
        FragmentManager fragmentManager = getSupportFragmentManager();
        IngredientFragment ingredientFragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IngredientFragment.INGREDIENTS_EXTRA, (ArrayList<? extends Parcelable>) mIngredients);
        ingredientFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_fragment,ingredientFragment)
                .commit();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.close_on_intent_error), Toast.LENGTH_SHORT).show();
    }

}
