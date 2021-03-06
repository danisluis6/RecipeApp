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

import com.example.framework.di.module.MainModule;
import com.example.framework.di.scope.PerActivity;
import com.example.view.activity.MainActivity;

import dagger.Subcomponent;

/**
 * Created by lorence on 27/November/2020.
 */
@PerActivity
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {
    MainActivity inject(MainActivity activity);
}
