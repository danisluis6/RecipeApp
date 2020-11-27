package com.example.framework.presenter;

/*
 *
 * +--------------------------------------------------------------------------
 * |
 * | WARNING: REMOVING THIS COPYRIGHT HEADER IS EXPRESSLY FORBIDDEN
 * |
 * | RECIPE APPLICATION
 * | ========================================
 * | by ENCLAVE, INC.
 * | (c) 2012-2013 ENCLAVEIT.COM - All right reserved
 * | Website: http://www.enclaveit.com [^]
 * | Email : engineering@enclave.vn
 * | ========================================
 * |
 * | WARNING //--------------------------
 * |
 * | Selling the code for this program without prior written consent is expressly
 * |forbidden.
 * | This computer program is protected by copyright law.
 * | Unauthorized reproduction or distribution of this program, or any portion of
 * | if, may result in severe civil and criminal penalties and will be prosecuted
 * |to the maximum extent possible under the law.
 * +--------------------------------------------------------------------------
 */

import android.os.Handler;

import com.example.callback.RecipeView;
import com.example.lib.localstorage.RoomUIManager;
import com.example.lib.localstorage.entities.Recipe;
import com.example.lib.localstorage.entities.RecipeType;
import com.example.view.activity.RecipeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lorence on 27/November/2020.
 */
public class RecipePresenter {

    private RecipeView mRecipeView;
    private RoomUIManager mRoomUIManager;
    private RecipeActivity mActivity;

    /* This is constructor of MainPresenter. Use it constructor to argument MainView
     * @param view
     */
    public RecipePresenter(RecipeActivity activity, RecipeView view, RoomUIManager roomUIManager) {
        mRecipeView = view;
        mActivity = activity;
        mRoomUIManager = roomUIManager;
    }

    /*
     * Get Recipe Types from file xml.
     */
    public void getRecipeTypes() {
        mRecipeView.showDialogProgressBar(true);
        mActivity.runOnUiThread(() -> {
            mRoomUIManager.getAllRecipeTypes(recipeTypes -> {
                mRecipeView.showDialogProgressBar(false);
                mRecipeView.showTypeRecipeList((ArrayList<RecipeType>) recipeTypes);
            });
        });
    }

    /*
     * This method can be used to save recipe to DB
     * @param name
     * @param path
     * @param ingredientList
     * @param stepList
     */
    public void saveRecipe(int id, String name, String type, String path, List<String> ingredientList, List<String> stepList) {
        Recipe recipe = new Recipe(id, name, type, path, ingredientList, stepList);
        List<Recipe> mListRecipes = new ArrayList<>();
        mListRecipes.add(recipe);
        mRoomUIManager.saveRecipe(mListRecipes);
        mRoomUIManager.getRecipeByName(name, recipes -> {
            if (recipes.size() > 0) {
                mActivity.runOnUiThread(() -> new Handler().postDelayed(() -> {
                    recipe.setId(recipes.get(0).getId());
                    mRecipeView.saveRecipeSuccessfully(recipe);
                }, 500));
            } else {
                mActivity.runOnUiThread(() -> new Handler().postDelayed(() -> {
                    recipe.setId(0);
                    mRecipeView.saveRecipeSuccessfully(recipe);
                }, 500));
            }
        });
    }

    /*
     * This method is used to query to get RecipeType
     * @param name
     */
    public void getRecipeByName(String name) {
        mRoomUIManager.getRecipeTypeByName(name, recipeTypes -> {
            if (recipeTypes.size() > 0)
                mRecipeView.showRecipeType(recipeTypes.get(0));
        });
    }
}
