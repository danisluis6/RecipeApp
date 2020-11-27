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
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lib.localstorage.DatabaseInfo;
import com.example.lib.localstorage.entities.RecipeType;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by lorence on 27/November/2020.
 */

@Dao
public interface RecipeTypeDAO {

    /* This method can be used to save one or more object RecipeType.
     * @param list
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(RecipeType... list);

    /* Return array of Recipe Types
     * @param id
     * @return
     */
    @Query("SELECT * FROM " + DatabaseInfo.Tables.RecipeType + " WHERE id = :id")
    Maybe<List<RecipeType>> getById(int id);

    /*
     * Update recipe type
     * @param recipeTypes
     * @return
     */
    @Update
    int update(RecipeType... recipeTypes);

    /*
     * Get all Recipe Types
     * @return
     */
    @Query("SELECT * FROM " + DatabaseInfo.Tables.RecipeType)
    Maybe<List<RecipeType>> getAll();

    /* Return array of Recipe Types
     * @param name
     * @return
     */
    @Query("SELECT * FROM " + DatabaseInfo.Tables.RecipeType + " WHERE name = :name")
    Maybe<List<RecipeType>> getByName(String name);

}
