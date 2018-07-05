package com.danielburgnerjr.bakingapp.utils;

import com.danielburgnerjr.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeClient {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> get_recipes();
}