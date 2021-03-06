package com.example.callback;

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

import com.example.lib.localstorage.entities.Recipe;
import com.example.lib.localstorage.entities.RecipeType;

import java.util.ArrayList;

/**
 * Created by lorence on 27/November/2020.
 */
public interface MainView extends BaseView {
    void showRecipeList(ArrayList<Recipe> t);
    void showRecipeTypeList(ArrayList<RecipeType> recipeTypes);
}
