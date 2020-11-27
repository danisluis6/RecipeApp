package com.example.lib.localstorage.dao;

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

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lib.localstorage.DatabaseInfo;
import com.example.lib.localstorage.entities.Recipe;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by lorence on 27/November/2020.
 */

@Dao
public interface RecipeDAO {

    /* This method can be used to save one or more object Recipe.
     * @param list
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Recipe... list);

    /* Return array of Recipe
     * @param id
     * @return
     */
    @Query("SELECT * FROM " + DatabaseInfo.Tables.Recipe + " WHERE id = :id")
    Maybe<List<Recipe>> getById(int id);

    /* Return array of Recipe
     * @param name
     * @return
     */
    @Query("SELECT * FROM " + DatabaseInfo.Tables.Recipe + " WHERE name = :name")
    Maybe<List<Recipe>> getByName(String name);

    /*
     * Update recipe
     * @param recipes
     * @return
     */
    @Update
    int update(Recipe... recipes);

    /*
     * Get all Recipes
     * @return
     */
    @Query("SELECT * FROM " + DatabaseInfo.Tables.Recipe)
    Maybe<List<Recipe>> getAll();

    /*
     * Delete recipe or list of recipes
     * @param recipes
     * @return
     */
    @Delete
    int delete(Recipe... recipes);

    /* Return array of Recipe
     * @param id
     * @return
     */
    @Query("SELECT * FROM " + DatabaseInfo.Tables.Recipe + " WHERE type = :type")
    Maybe<List<Recipe>> getByType(String type);

}
