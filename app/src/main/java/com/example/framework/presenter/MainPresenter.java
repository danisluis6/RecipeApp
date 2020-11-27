package com.example.framework.presenter;

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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.example.callback.MainView;
import com.example.lib.localstorage.RoomUIManager;
import com.example.lib.localstorage.entities.Recipe;
import com.example.lib.localstorage.entities.RecipeType;
import com.example.lib.xml.XmlParserUtils;
import com.example.recipe.R;
import com.example.view.activity.MainActivity;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by lorence on 27/November/2020.
 */
public class MainPresenter {

    private MainView mMainView;
    private Context mContext;
    private RoomUIManager mRoomUIManager;
    private MainActivity mActivity;
    private XmlParserUtils mXmlParserUtils;

    /* This is constructor of MainPresenter. Use it constructor to argument MainView
     * @param view
     */
    public MainPresenter(Context context, MainActivity activity, MainView view, XmlParserUtils xmlParserUtils, RoomUIManager roomUIManager) {
        mMainView = view;
        mContext = context;
        mActivity = activity;
        mRoomUIManager = roomUIManager;
        mXmlParserUtils = xmlParserUtils;
    }

    /*
     * Get Recipes from file xml.
     */
    public void getRecipes() {
        mMainView.showDialogProgressBar(true);
        XmlPullParser xmlPullParser = mXmlParserUtils.getXmlPullParser(R.raw.recipetypes);
        // Save recipe types into Database.
        mActivity.runOnUiThread(() -> new SynchronizeDatabase().execute(xmlPullParser));
        // Get Recipes
        mRoomUIManager.getAllRecipes(recipes -> mMainView.showRecipeList((ArrayList<Recipe>) recipes));
    }

    /*
     * This method can be used to delete all recipes
     * @param recipe
     */
    public void delete(Recipe recipe) {
        mRoomUIManager.deleteRecipe(recipe);
    }

    public void filterRecipeList(String type) {
        if (TextUtils.equals(mContext.getString(R.string.all), type)) {
            mRoomUIManager.getAllRecipes(recipes -> mMainView.showRecipeList((ArrayList<Recipe>) recipes));
        } else {
            mRoomUIManager.getAllRecipesByType(type, recipes -> mMainView.showRecipeList((ArrayList<Recipe>) recipes));
        }
    }

    @SuppressLint("StaticFieldLeak")
    class SynchronizeDatabase extends AsyncTask<XmlPullParser, XmlPullParser, Boolean> {

        @Override
        protected Boolean doInBackground(XmlPullParser... xmlPullParser) {
            ArrayList<RecipeType> recipeTypeList = mXmlParserUtils.processParsing(xmlPullParser[0]);
            mRoomUIManager.saveRecipeTypes(recipeTypeList);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) mActivity.runOnUiThread(() -> {
                new Handler().postDelayed(() -> {
                    mRoomUIManager.getAllRecipeTypes(recipeTypes -> mMainView.showRecipeTypeList((ArrayList<RecipeType>) recipeTypes));
                }, 1000);
            });
        }
    }
}
