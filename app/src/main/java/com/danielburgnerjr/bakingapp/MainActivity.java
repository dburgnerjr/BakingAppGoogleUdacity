package com.danielburgnerjr.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.danielburgnerjr.bakingapp.IdlingResource.SimpleIdlingResource;
import com.danielburgnerjr.bakingapp.model.Recipe;
import com.danielburgnerjr.bakingapp.utils.RecipeClient;
import com.danielburgnerjr.bakingapp.utils.RecipesAdapter;
import com.danielburgnerjr.bakingapp.utils.RetrofitClient;
import com.danielburgnerjr.bakingapp.widget.WidgetUpdateService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ItemListener {

    public static String RECIPES_EXTRA = "recipes_extra";

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
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        getIdlingResource();

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_EXTRA);
            populateUI();
        }
        else {
            loadRecipesData();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPES_EXTRA, (ArrayList<Recipe>)mRecipes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Respond to the action bar's Up/Home button
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateUI () {
        //test idling resource
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecipesAdapter recipesAdapter = new RecipesAdapter(this, this, mRecipes);
        RecyclerView recyclerView = findViewById(R.id.recipe_rv);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recipesAdapter);
    }

    public void loadRecipesData () {
        RecipeClient client = RetrofitClient.getClient().create(RecipeClient.class);
        Call<List<Recipe>> call = client.get_recipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipes = response.body();
                populateUI();
            }
            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                //Show alert dialog
                Log.e("Error", "Error in retrofit");
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(getString(R.string.connection_error_title));
                dialog.setMessage(getString(R.string.connection_error));
                dialog.setPositiveButton(getString(R.string.reload_button), (dialog1, id) -> loadRecipesData());
                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });
    }

    @Override
    public void onRecipeClicked(int position) {
        Toast.makeText(getApplicationContext(),"Item is clicked at "+ position,Toast.LENGTH_SHORT).show();
        Recipe item = mRecipes.get(position);
        Intent detailRecipeListIntent = new Intent(MainActivity.this, DetailRecipeListActivity.class);
        detailRecipeListIntent.putExtra(DetailRecipeListActivity.RECIPE_EXTRA, item);

        WidgetUpdateService.startActionUpdateListView(getApplicationContext(), item);
        startActivity(detailRecipeListIntent);
    }
}
