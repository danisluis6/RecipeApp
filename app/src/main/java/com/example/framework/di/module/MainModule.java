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

import com.example.callback.MainView;
import com.example.framework.adapter.RecipeAdapter;
import com.example.framework.di.scope.PerActivity;
import com.example.framework.presenter.MainPresenter;
import com.example.lib.localstorage.RoomUIManager;
import com.example.lib.xml.XmlParserUtils;
import com.example.view.activity.MainActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lorence on 27/November/2020.
 */
@Module
public class MainModule {

    private MainActivity activity;
    private MainView view;

    /* This is constructor of MainModule. Use it constructor to pass two arguments MainActivity and MainView
     * @param application
     * @param context
     */
    public MainModule(MainActivity activity, MainView view) {
        this.activity = activity;
        this.view = view;
    }

    /*
     * @return a MainActivity that can be used in scope of MainActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    MainActivity provideMainActivity() { return activity; }

    /*
     * @return a MainView that can be used in scope of MainActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    MainView provideMainView() { return view; }

    /*
     * @return a XmlParserUtils that can be used in scope of MainActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    XmlParserUtils provideXmlParserUtils(Context context) { return new XmlParserUtils(context); }

    /*
     * @return a MainPresenter that can be used in scope of MainActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    MainPresenter provideMainPresenter(Context context, MainActivity activity, XmlParserUtils xmlParserUtils, RoomUIManager roomUIManager) { return new MainPresenter(context, activity, view, xmlParserUtils, roomUIManager); }

    /*
     * @return a RecipeAdapter that can be used in scope of MainActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    RecipeAdapter provideRecipeTypeAdapter(Context context) {
        return new RecipeAdapter(context, new ArrayList<>());
    }

}
