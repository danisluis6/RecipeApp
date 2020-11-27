package com.example.lib.localstorage;

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

import android.os.AsyncTask;

import com.example.lib.localstorage.dao.RecipeDAO;
import com.example.lib.localstorage.dao.RecipeTypeDAO;
import com.example.lib.localstorage.entities.Recipe;
import com.example.lib.localstorage.entities.RecipeType;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lorence on 27/November/2020.
 */
public class RoomUIManager {

    private RecipeDAO mRecipeDAO;
    private RecipeTypeDAO mRecipeTypeDAO;

    /*
     * This is constructor of RoomUIManager. Use it constructor to argument RecipeTypeDAO
     * @param recipeTypeDAO
     */
    public RoomUIManager(RecipeTypeDAO recipeTypeDAO, RecipeDAO recipeDAO) {
        mRecipeTypeDAO = recipeTypeDAO;
        mRecipeDAO = recipeDAO;
    }

    /*
     * This method to check recipe type is exist or not and conduct to save or update it.
     * @param recipeTypes
     */
    public void saveRecipeTypes(List<RecipeType> recipeTypes) {
        for (int index = 0; index < recipeTypes.size(); index++) {
            final int indexTemp = index;
            AsyncTask.execute(() -> mRecipeTypeDAO.getById(recipeTypes.get(indexTemp).getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(recipeTypeList -> {
                if (recipeTypeList.size() == 0) {
                    AsyncTask.execute(() -> mRecipeTypeDAO.save(recipeTypes.get(indexTemp)));
                } else {
                    AsyncTask.execute(() -> mRecipeTypeDAO.update(recipeTypes.get(indexTemp)));
                }
            }));
        }
    }

    /*
     * This method to check recipe is exist or not and conduct to save or update it.
     * @param recipeTypes
     */
    public void saveRecipe(List<Recipe> recipes) {
        for (int index = 0; index < recipes.size(); index++) {
            final int indexTemp = index;
            AsyncTask.execute(() -> mRecipeDAO.getById(recipes.get(indexTemp).getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(recipeTypeList -> {
                if (recipeTypeList.size() == 0) {
                    AsyncTask.execute(() -> mRecipeDAO.save(recipes.get(indexTemp)));
                } else {
                    AsyncTask.execute(() -> mRecipeDAO.update(recipes.get(indexTemp)));
                }
            }));
        }
    }

    /*
     * This method can be used to get all recipe types.
     * @param _interface
     */
    public void getAllRecipeTypes(final IRoomListener<RecipeType> _interface) {
        AsyncTask.execute(() -> mRecipeTypeDAO.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(_interface::showListData));
    }

    /*
     * This method can be used to get all recipes.
     * @param _interface
     */
    public void getAllRecipes(final IRoomListener<Recipe> _interface) {
        AsyncTask.execute(() -> mRecipeDAO.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(_interface::showListData));
    }

    /*
     * This method can be used to get all recipes.
     * @param _interface
     */
    public void getAllRecipesByType(String type, final IRoomListener<Recipe> _interface) {
        AsyncTask.execute(() -> mRecipeDAO.getByType(type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(_interface::showListData));
    }

    /*
     * This method can be used to get recipe type.
     * @param _interface
     */
    public void getRecipeTypeByName(String name, final IRoomListener<RecipeType> _interface) {
        AsyncTask.execute(() -> mRecipeTypeDAO.getByName(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(_interface::showListData));
    }

    /*
     * This method can be used to get recipe.
     * @param _interface
     */
    public void getRecipeByName(String name, final IRoomListener<Recipe> _interface) {
        AsyncTask.execute(() -> mRecipeDAO.getByName(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(_interface::showListData));
    }

    /*
     * This method is used to delete recipe or list of recipes
     * @param recipe
     */
    public void deleteRecipe(Recipe recipe) {
        Completable.fromAction(() -> mRecipeDAO.delete(recipe))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
