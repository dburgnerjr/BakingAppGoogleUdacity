package com.danielburgnerjr.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielburgnerjr.bakingapp.model.Ingredient;
import com.danielburgnerjr.bakingapp.model.Recipe;
import com.danielburgnerjr.bakingapp.utils.IngredientAdapter;
import com.danielburgnerjr.bakingapp.utils.IngredientFragment;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {
    private static final String INGREDIENT_FRAGMENT ="ingredient_fragment" ;
    private static final String PROCESS_FRAGMENT="process_fragment";
    private Recipe detailRecipe;
    private ArrayList<Ingredient> mIngredientsList;
    private ArrayList<Process> mProcessList;
    private IngredientAdapter mIngredientAdapter;

    Recipe selectedRecipe;
    RecyclerView rv_ingred;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_layout);

        detailRecipe= getIntent().getExtras().getParcelable(MainActivity.RECIPE_KEY);

        Bundle ingredientBundle=new Bundle();
        ingredientBundle.putParcelable("ingredientBundle", detailRecipe);
        ingredientBundle.putString("phone_or_tablet", "phone");

        ImageView recipe_image_ing_act = findViewById(R.id.ingredient_detail_imageView);
        TextView recipe_textView_ing_act = findViewById(R.id.textView_recipe);

        IngredientFragment ingredientFragment=new IngredientFragment();
        ingredientFragment.setArguments(ingredientBundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.ingredient_frameLayout,ingredientFragment,INGREDIENT_FRAGMENT).commit();

        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(ingredientBundle);

        getSupportFragmentManager().beginTransaction().add(R.id.framelayout_steps, stepFragment, PROCESS_FRAGMENT).commit();

        selectedRecipe = detailRecipe;

        int id = detailRecipe.getId();
        String name = detailRecipe.getName();

        switch(id){
            case 1:
                recipe_image_ing_act.setImageResource(R.drawable.n_pie);
                recipe_textView_ing_act.setText(name);
                break;
            case 2:
                recipe_image_ing_act.setImageResource(R.drawable.brownies);
                recipe_textView_ing_act.setText(name);
                break;

            case 3:
                recipe_image_ing_act.setImageResource(R.drawable.yellow_cake);
                recipe_textView_ing_act.setText(name);
                break;

            case 4:
                recipe_image_ing_act.setImageResource(R.drawable.cheese_cake);
                recipe_textView_ing_act.setText(name);
                break;
        }
    }
}
