package com.example.framework.di.component;

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

import com.example.framework.di.module.AppModule;
import com.example.framework.di.module.MainModule;
import com.example.framework.di.module.RecipeModule;
import com.example.framework.di.module.RoomModule;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by lorence on 27/November/2020.
 */
@Singleton
@Component(
        modules = {
                AppModule.class, RoomModule.class
        }
)
public interface AppComponent {
        MainComponent plus(MainModule module);
        RecipeComponent plus(RecipeModule module);
}
