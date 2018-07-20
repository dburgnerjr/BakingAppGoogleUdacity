package com.danielburgnerjr.bakingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.danielburgnerjr.bakingapp.IdlingResource.SimpleIdlingResource;
import com.danielburgnerjr.bakingapp.model.Recipe;
import com.danielburgnerjr.bakingapp.utils.RecipeClient;
import com.danielburgnerjr.bakingapp.utils.RecipeMenuAdapter;
import com.danielburgnerjr.bakingapp.utils.RetrofitClient;
import com.danielburgnerjr.bakingapp.widget.WidgetUpdateService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static private String Tag = MainActivity.class.getSimpleName();
    public static String RECIPES_EXTRA = "recipes_extra";

    @BindView(R.id.recipe_grid_view)
    GridView recipeGv;
    RecipeMenuAdapter mRecipeMenuAdapter;
    List<Recipe> mRecipes = new ArrayList<>();
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getIdlingResource();

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_EXTRA);
            populateUI();
        }
        else {
            loadRecipesData();
        }
        recipeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Item is clicked at "+ position,Toast.LENGTH_SHORT).show();
                Recipe item = (Recipe) parent.getItemAtPosition(position);
                Intent detailRecipeListIntent = new Intent(MainActivity.this,DetailRecipeListActivity.class);
                detailRecipeListIntent.putExtra(DetailRecipeListActivity.RECIPE_EXTRA, item);
                WidgetUpdateService.startActionUpdateListView(getApplicationContext(), item);
                startActivity(detailRecipeListIntent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(RECIPES_EXTRA,(ArrayList<Recipe>)mRecipes);
    }

    public void populateUI () {
        //test idling resource
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
        mRecipeMenuAdapter = new RecipeMenuAdapter(MainActivity.this, R.layout.recipe_list_item, mRecipes);
        recipeGv.setAdapter(mRecipeMenuAdapter);
    }

    public void loadRecipesData () {
        RecipeClient client = new RetrofitClient().getClient().create(RecipeClient.class);
        Call<List<Recipe>> call = client.get_recipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipes = response.body();
                populateUI();
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                //Show alert dialog
                Log.e("Error", "Error in retrofit");
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(getString(R.string.connection_error_title));
                dialog.setMessage(getString(R.string.connection_error));
                dialog.setPositiveButton(getString(R.string.reload_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        loadRecipesData();
                    }
                });
                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });
    }
}
