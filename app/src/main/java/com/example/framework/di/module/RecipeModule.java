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

import com.example.callback.RecipeView;
import com.example.framework.adapter.IngredientAdapter;
import com.example.framework.adapter.StepsAdapter;
import com.example.framework.di.scope.PerActivity;
import com.example.framework.presenter.RecipePresenter;
import com.example.lib.customui.RecipeLoadingDialog;
import com.example.lib.localstorage.RoomUIManager;
import com.example.view.activity.RecipeActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lorence on 27/November/2020.
 */
@Module
public class RecipeModule {

    private RecipeActivity activity;
    private RecipeView view;

    /* This is constructor of RecipeModule. Use it constructor to pass two arguments RecipeActivity and RecipeView
     * @param application
     * @param context
     */
    public RecipeModule(RecipeActivity activity, RecipeView view) {
        this.activity = activity;
        this.view = view;
    }

    /*
     * @return a RecipeActivity that can be used in scope of RecipeActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    RecipeActivity provideRecipeActivity() { return activity; }

    /*
     * @return a RecipeView that can be used in scope of RecipeActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    RecipeView provideRecipeView() { return view; }

    /*
     * @return a RecipePresenter that can be used in scope of RecipeActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    RecipePresenter provideRecipePresenter(RecipeActivity activity, RoomUIManager roomUIManager) { return new RecipePresenter(activity, view, roomUIManager); }

    /*
     * @return a IngredientAdapter that can be used in scope of RecipeActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    IngredientAdapter provideIngredientAdapter() {
        return new IngredientAdapter(new ArrayList<>());
    }

    /*
     * @return a StepsAdapter that can be used in scope of RecipeActivity by annotation @Inject
     */
    @Provides
    @PerActivity
    StepsAdapter provideStepsAdapter() {
        return new StepsAdapter(new ArrayList<>());
    }


    /*
     * This method can be used to provide RecipeLoadingDialog
     * @param activity
     * @return
     */
    @Provides
    @PerActivity
    RecipeLoadingDialog provideRecipeLoadingDialog(RecipeActivity activity) {
        return new RecipeLoadingDialog(activity);
    }
}
