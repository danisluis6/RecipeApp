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

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lib.localstorage.dao.RecipeDAO;
import com.example.lib.localstorage.dao.RecipeTypeDAO;
import com.example.lib.localstorage.entities.Recipe;
import com.example.lib.localstorage.entities.RecipeType;

/**
 * Created by lorence on 27/November/2020.
 */
@Database(entities = {RecipeType.class, Recipe.class}, version = DatabaseInfo.DB_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = DatabaseInfo.DB_NAME;

    public abstract RecipeTypeDAO getRecipeTypeDAO();

    public abstract RecipeDAO getRecipeDAO();

}
