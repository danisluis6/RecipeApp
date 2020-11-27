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

import android.content.Context;

import com.example.app.RecipeApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lorence on 27/November/2020.
 */
@Module
public class AppModule {

    private RecipeApplication mApplication;
    private Context mContext;

    /* This is constructor of AppModule. Use it constructor to pass two arguments RecipeApplication and Context
     * @param application
     * @param context
     */
    public AppModule(RecipeApplication application, Context context) {
        this.mApplication = application;
        this.mContext = context;
    }

    /*
     * @return a RecipeApplication that can be used everywhere in Recipe application by annotation @Inject
     */
    @Provides
    @Singleton
    RecipeApplication provideRecipeApplication() {
        return mApplication;
    }

    /*
     * @return a Context that can be used everywhere in Recipe application by annotation @Inject
     */
    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

}
