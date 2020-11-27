package com.example.app;

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

import android.app.Application;
import android.content.Context;

import com.example.framework.di.component.AppComponent;
import com.example.framework.di.component.DaggerAppComponent;
import com.example.framework.di.module.AppModule;
import com.example.framework.di.module.RoomModule;

/**
 * Created by lorence on 27/November/2020.
 */
public class RecipeApplication extends Application {

    private Context mContext;
    protected static RecipeApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        sInstance = this;
        getAppComponent();
    }

    /*
     * This method will be used to create an object by singleton pattern.
     * @return a RecipeApplication
     */
    public static synchronized RecipeApplication getInstance() {
        if (sInstance == null) {
            sInstance = new RecipeApplication();
        }
        return sInstance;
    }

    /*
     * Initialize Dagger and implement AppModule which is highest level with some
     * common method, object for whole application.
     * @return
     */
    public AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this, mContext))
                .roomModule(new RoomModule(this))
                .build();
    }

}
