package com.example.framework.di.module;

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

import androidx.room.Room;

import com.example.app.RecipeApplication;
import com.example.lib.localstorage.AppDatabase;
import com.example.lib.localstorage.DisposableManager;
import com.example.lib.localstorage.RoomUIManager;
import com.example.lib.localstorage.dao.RecipeDAO;
import com.example.lib.localstorage.dao.RecipeTypeDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lorence on 27/November/2020.
 */
@Module
public class RoomModule {

    private AppDatabase mAppDatabase;

    public RoomModule(RecipeApplication application) {
        mAppDatabase = Room.databaseBuilder(application, AppDatabase.class, AppDatabase.DB_NAME).build();
    }

    /*
     * @return a DisposableManager that can be used everywhere in Recipe application by annotation @Inject
     */
    @Provides
    @Singleton
    DisposableManager provideDisposableManager() {
        return new DisposableManager();
    }

    /*
     * @return a RecipeTypeDAO that can be used everywhere in Recipe application by annotation @Inject
     */
    @Provides
    @Singleton
    RecipeTypeDAO provideRecipeTypeDAO() {
        return mAppDatabase.getRecipeTypeDAO();
    }

    /*
     * @return a RecipeDAO that can be used everywhere in Recipe application by annotation @Inject
     */
    @Provides
    @Singleton
    RecipeDAO provideRecipeDAO() {
        return mAppDatabase.getRecipeDAO();
    }

    /*
     * @return a RoomUIManager that can be used everywhere in Recipe application by annotation @Inject
     */
    @Provides
    @Singleton
    RoomUIManager provideRoomUIManager(RecipeTypeDAO recipeTypeDAO, RecipeDAO recipeDAO) { return new RoomUIManager(recipeTypeDAO, recipeDAO); }
}
