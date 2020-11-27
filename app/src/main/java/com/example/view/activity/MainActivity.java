package com.example.view.activity;

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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.RecipeApplication;
import com.example.callback.MainView;
import com.example.framework.adapter.RecipeAdapter;
import com.example.framework.adapter.RecipeTypeAdapter;
import com.example.framework.di.module.MainModule;
import com.example.framework.presenter.MainPresenter;
import com.example.lib.localstorage.entities.Recipe;
import com.example.lib.localstorage.entities.RecipeType;
import com.example.lib.utils.Constant;
import com.example.lib.utils.Helper;
import com.example.recipe.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lorence on 27/November/2020.
 */
public class MainActivity extends BaseActivity implements MainView, RecipeAdapter.RecipeListener, Spinner.OnItemSelectedListener {

    @Inject
    Context mContext;

    @Inject
    MainActivity mActivity;

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    RecipeAdapter mRecipeAdapter;

    @BindView(R.id.rcvRecipes)
    RecyclerView rcvRecipes;

    @BindView(R.id.tvNoFound)
    TextView tvNoFound;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.spinnerRecipeTypes)
    Spinner spinnerRecipeTypes;

    private RecipeTypeAdapter mRecipeTypeAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initAttributes() {
        RecipeApplication.getInstance().getAppComponent().plus(new MainModule(this, this)).inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initToolbar();
        initRecyclerView();
        initSpinner();
        if (Helper.checkPermissions(mActivity)) {
            mMainPresenter.getRecipes();
        } else {
            Helper.establishPermission(mActivity);
        }
    }

    private void initToolbar() {
        toolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.black));
        toolbar.setTitle(getString(R.string.app_name));
    }

    /*
     * Initialize recycler view and load recipes
     */
    private void initRecyclerView() {
        rcvRecipes.setLayoutManager(new LinearLayoutManager(RecipeApplication.getInstance(), LinearLayoutManager.VERTICAL, false));
        rcvRecipes.setNestedScrollingEnabled(false);
        rcvRecipes.setHasFixedSize(true);
        rcvRecipes.setAdapter(mRecipeAdapter);
        mRecipeAdapter.setListener(this);
    }

    /*
     * Initialize a object Spinner, configure item layout and set Adapter.
     */
    private void initSpinner() {
        mRecipeTypeAdapter = new RecipeTypeAdapter(mContext,
                android.R.layout.simple_spinner_item,
                new ArrayList<>());
        spinnerRecipeTypes.setAdapter(mRecipeTypeAdapter);
        spinnerRecipeTypes.setOnItemSelectedListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.PERMISSION) {
            for (int permissionId : grantResults) {
                if (permissionId != PackageManager.PERMISSION_GRANTED) {
                    showToast(mContext.getString(R.string.error_permission));
                    return;
                }
            }
            mMainPresenter.getRecipes();
        }
    }

    @OnClick(R.id.btnNewRecipe)
    public void onClick(View v) {
        if (v.getId() == R.id.btnNewRecipe) {
            Intent intent = new Intent(this, RecipeActivity.class);
            mActivity.startActivityForResult(intent, Constant.REQUEST_RECIPE);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /*
     * This method can be used to get call back from RecipeActivity and refresh adapter in MainActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.REQUEST_RECIPE) {
            if (data != null) {
                tvNoFound.setVisibility(View.GONE);
                rcvRecipes.setVisibility(View.VISIBLE);
                mMainPresenter.getRecipes();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showDialogProgressBar(boolean isShowDialog) {

    }

    @Override
    public void showToast(String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRecipeList(ArrayList<Recipe> recipes) {
        if (recipes.size() > 0 ) {
            tvNoFound.setVisibility(View.GONE);
            rcvRecipes.setVisibility(View.VISIBLE);
        } else {
            tvNoFound.setVisibility(View.VISIBLE);
            rcvRecipes.setVisibility(View.GONE);
        }
        mRecipeAdapter.setRecipes(recipes);
    }

    @Override
    public void showRecipeTypeList(ArrayList<RecipeType> recipeTypes) {
        Collections.reverse(recipeTypes);
        recipeTypes.add(new RecipeType(recipeTypes.get(recipeTypes.size()-1).getId()+1, mContext.getString(R.string.all)));
        Collections.reverse(recipeTypes);
        mRecipeTypeAdapter.setList(recipeTypes);
    }

    /*
     * This method can be used to transfer data from MainActivity to RecipeActivity
     * @param recipe
     */
    @Override
    public void openRecipe(Recipe recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.Bundle.RECIPE, recipe);
        intent.putExtra(Constant.Bundle.RECIPE, bundle);
        mActivity.startActivityForResult(intent, Constant.REQUEST_RECIPE);
    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        mMainPresenter.delete(recipe);
        mRecipeAdapter.remove(recipe);
        showToast(mContext.getString(R.string.delete_recipe_successfully));
    }

    /*
     * Handle event when user select each recipe type
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        RecipeType recipeType = mRecipeTypeAdapter.getItem(position);
        mMainPresenter.filterRecipeList(Objects.requireNonNull(recipeType).getName());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
