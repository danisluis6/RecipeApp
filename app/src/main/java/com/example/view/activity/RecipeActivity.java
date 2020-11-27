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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.app.RecipeApplication;
import com.example.callback.RecipeView;
import com.example.framework.adapter.IngredientAdapter;
import com.example.framework.adapter.RecipeTypeAdapter;
import com.example.framework.adapter.StepsAdapter;
import com.example.framework.di.module.RecipeModule;
import com.example.framework.presenter.RecipePresenter;
import com.example.lib.customui.RecipeLoadingDialog;
import com.example.lib.localstorage.entities.Recipe;
import com.example.lib.localstorage.entities.RecipeType;
import com.example.lib.utils.Constant;
import com.example.lib.utils.Helper;
import com.example.recipe.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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


/**
 * Created by lorence on 27/November/2020.
 */
public class RecipeActivity extends BaseActivity implements RecipeView, IngredientAdapter.IngredientListener, StepsAdapter.StepListener, TextView.OnEditorActionListener {

    @Inject
    Context mContext;

    @Inject
    RecipeActivity mActivity;

    @Inject
    RecipePresenter mRecipePresenter;

    @Inject
    IngredientAdapter mIngredientAdapter;

    @Inject
    StepsAdapter mStepsAdapter;

    @Inject
    RecipeLoadingDialog mPgDialog;

    @BindView(R.id.spinnerRecipeTypes)
    Spinner spinnerRecipeTypes;

    @BindView(R.id.imgPicture)
    ImageView imgPicture;

    @BindView(R.id.labelPicture)
    TextView labelPicture;

    @BindView(R.id.edtIngredient)
    EditText edtIngredient;

    @BindView(R.id.edtStep)
    EditText edtStep;

    @BindView(R.id.edtRecipeName)
    EditText edtRecipeName;

    @BindView(R.id.rcvIngredients)
    RecyclerView rcvIngredients;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rcvSteps)
    RecyclerView rcvSteps;

    private RecipeTypeAdapter mRecipeTypeAdapter;
    private List<String> mIngredientList;
    private List<String> mStepList;
    private String mRecipeImagePath = Constant.EMPTY_STRING;
    private int mIndexOfIngredient;
    private int mIndexOfStep;
    private boolean isIngredientUpdate = false;
    private boolean isStepUpdate = false;
    private int mRecipeId = 0;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_recipe;
    }

    @Override
    protected void initAttributes() {
        RecipeApplication.getInstance().getAppComponent().plus(new RecipeModule(this, this)).inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initToolbar();
        initSpinner();
        initRcvIngredient();
        initRcvStep();
        imgPicture.setVisibility(View.GONE);
        labelPicture.setVisibility(View.VISIBLE);
        mIngredientList = new ArrayList<>();
        mStepList = new ArrayList<>();
        mRecipePresenter.getRecipeTypes();
        edtIngredient.setOnEditorActionListener(this);
        edtStep.setOnEditorActionListener(this);
        edtRecipeName.setOnEditorActionListener(this);
        checkOpenRecipe();
    }

    private void initToolbar() {
        toolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.black));
        toolbar.setTitle(getString(R.string.toolbar_title_create_recipe));
    }

    /*
     * This method can be used to load recipe
     */
    private void checkOpenRecipe() {
        if (getIntent() != null && getIntent().getBundleExtra(Constant.Bundle.RECIPE) != null) {
            Bundle bundle = getIntent().getBundleExtra(Constant.Bundle.RECIPE);
            Recipe recipe = bundle.getParcelable(Constant.Bundle.RECIPE);
            if (recipe != null) {
                mRecipeId = recipe.getId();
                edtRecipeName.setText(recipe.getName());
                mRecipeImagePath = recipe.getPicture();
                mIngredientList.addAll(recipe.getIngredients());
                mStepList.addAll(recipe.getSteps());
                labelPicture.setVisibility(View.GONE);
                imgPicture.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                    .load(mRecipeImagePath)
                    .into(imgPicture);
                notifyStepAdapter();
                notifyIngredientAdapter();
            }
        }
    }

    /*
     * Initialize ingredient in RecipeActivity
     */
    private void initRcvIngredient() {
        rcvIngredients.setLayoutManager(new LinearLayoutManager(RecipeApplication.getInstance(), LinearLayoutManager.VERTICAL, false));
        rcvIngredients.setNestedScrollingEnabled(false);
        rcvIngredients.setHasFixedSize(true);
        rcvIngredients.setAdapter(mIngredientAdapter);
        mIngredientAdapter.setIngredientListener(this);
    }

    /*
     * Initialize step in RecipeActivity
     */
    private void initRcvStep() {
        rcvSteps.setLayoutManager(new LinearLayoutManager(RecipeApplication.getInstance(), LinearLayoutManager.VERTICAL, false));
        rcvSteps.setNestedScrollingEnabled(false);
        rcvSteps.setHasFixedSize(true);
        rcvSteps.setAdapter(mStepsAdapter);
        mStepsAdapter.setStepListener(this);
    }

    /*
     * Initialize a object Spinner, configure item layout and set Adapter.
     */
    private void initSpinner() {
        mRecipeTypeAdapter = new RecipeTypeAdapter(mContext,
                android.R.layout.simple_spinner_item,
                new ArrayList<>());
        spinnerRecipeTypes.setAdapter(mRecipeTypeAdapter);
    }

    @OnClick({R.id.labelPicture, R.id.imgPicture, R.id.btnIngredientDone, R.id.btnStepDone, R.id.buttonSubNewRecipe})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.labelPicture:
            case R.id.imgPicture:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, Constant.GALLERY);
                break;
            case R.id.btnIngredientDone:
                if (TextUtils.equals(Constant.EMPTY_STRING, edtIngredient.getText().toString())) {
                    showToast(mContext.getString(R.string.error_require_ingredient));
                } else {
                    if (mIngredientList.contains(edtIngredient.getText().toString())) {
                        showToast(mContext.getString(R.string.error_duplicate_ingredient));
                    } else {
                        if (isIngredientUpdate) {
                            mIngredientList.set(mIndexOfIngredient, edtIngredient.getText().toString());
                            showToast(mContext.getString(R.string.update_ingredient_successfully));
                        } else {
                            mIngredientList.add(edtIngredient.getText().toString());
                            Collections.reverse(mIngredientList);

                        }
                        edtIngredient.setText(Constant.EMPTY_STRING);
                        notifyIngredientAdapter();
                    }
                    isIngredientUpdate = false;
                    Helper.hiddenKeyBoard(mActivity);
                }
                break;
            case R.id.btnStepDone:
                if (TextUtils.equals(Constant.EMPTY_STRING, edtStep.getText().toString())) {
                    showToast(mContext.getString(R.string.error_require_step));
                } else {
                    if (mStepList.contains(edtStep.getText().toString())) {
                        showToast(mContext.getString(R.string.error_duplicate_step));
                    } else {
                        if (isStepUpdate) {
                            mStepList.set(mIndexOfStep, edtStep.getText().toString());
                            showToast(mContext.getString(R.string.update_step_successfully));
                        } else {
                            mStepList.add(edtStep.getText().toString());
                            Collections.reverse(mStepList);

                        }
                        edtStep.setText(Constant.EMPTY_STRING);
                        notifyStepAdapter();
                    }
                    isStepUpdate = false;
                    Helper.hiddenKeyBoard(mActivity);
                }
                break;
            case R.id.buttonSubNewRecipe:
                if (!TextUtils.equals(Constant.EMPTY_STRING, edtRecipeName.getText().toString())
                        && !TextUtils.equals(Constant.EMPTY_STRING, mRecipeImagePath) && mStepList.size() != 0 && mIngredientList.size() != 0) {
                    RecipeType recipeType = (RecipeType) spinnerRecipeTypes.getSelectedItem();
                    showDialogProgressBar(true);
                    mRecipePresenter.saveRecipe(mRecipeId, edtRecipeName.getText().toString(), recipeType.getName(), mRecipeImagePath, mIngredientList, mStepList);
                    mRecipeId = 0;
                } else {
                    Snackbar snackbar = Snackbar.make(view, mContext.getString(R.string.error_fill_information), Snackbar.LENGTH_LONG)
                            .setAction(null, null);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
                    snackbar.show();
                }
                break;
        }
    }

    /*
     *  Refresh IngredientAdapter
     */
    private void notifyIngredientAdapter() {
        mIngredientAdapter.setList((ArrayList<String>) mIngredientList);
    }

    /*
     *  Refresh StepAdapter
     */
    private void notifyStepAdapter() {
        mStepsAdapter.setList((ArrayList<String>) mStepList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.GALLERY) {
            if (data == null) {
                return;
            }
            final Uri uri = data.getData();
            try {
                String path = Helper.getRealPathFromURI(uri, mActivity);
                if (path == null) {
                    path = uri != null ? uri.getPath() : null;
                }
                Helper.fixOrientationBugOfProcessedBitmap(mContext,
                        BitmapFactory.decodeFile(path), path);
                mRecipeImagePath = path;
                updateImageRecipe(path);
            } catch (Exception e) {
                showToast(mContext.getString(R.string.error_pick_image_from_gallery));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
     * Show Image in RecipeActivity
     * @param path
     */
    private void updateImageRecipe(String path) {
        labelPicture.setVisibility(View.GONE);
        imgPicture.setVisibility(View.VISIBLE);
        Glide.with(mContext)
                .load(path)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // On load failed
                        labelPicture.setVisibility(View.VISIBLE);
                        imgPicture.setVisibility(View.GONE);
                        showToast(mContext.getString(R.string.error_image_load));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // On load success
                        labelPicture.setVisibility(View.GONE);
                        imgPicture.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(imgPicture);
    }

    @Override
    public void showTypeRecipeList(ArrayList<RecipeType> t) {
        mRecipeTypeAdapter.setList(t);
        if (getIntent() != null && getIntent().getBundleExtra(Constant.Bundle.RECIPE) != null) {
            Bundle bundle = getIntent().getBundleExtra(Constant.Bundle.RECIPE);
            assert bundle != null;
            Recipe recipe = bundle.getParcelable(Constant.Bundle.RECIPE);
            if (recipe != null && recipe.getType() != null) {
                mRecipePresenter.getRecipeByName(recipe.getType());
            }
        }
    }

    /*
     * Save recipe successfully and transfer Recipe to MainActivity
     */
    @Override
    public void saveRecipeSuccessfully(Recipe recipe) {
        showToast(mContext.getString(R.string.save_recipe_successfully));
        showDialogProgressBar(false);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constant.Bundle.RECIPE, recipe);
        mActivity.setResult(Activity.RESULT_OK, returnIntent);
        mActivity.finish();
    }

    @Override
    public void showRecipeType(RecipeType recipeType) {
        for (int index = 0; index < mRecipeTypeAdapter.getCount(); index++) {
            if (TextUtils.equals(Objects.requireNonNull(mRecipeTypeAdapter.getItem(index)).getName(), recipeType.getName())) {
                spinnerRecipeTypes.setSelection(index);
                break;
            }
        }
    }

    @Override
    public void showDialogProgressBar(boolean isShowDialog) {
        mActivity.runOnUiThread(() -> {
            if (isShowDialog) {
                if (!mActivity.isDestroyed() && !mPgDialog.isShowing()) {
                    mPgDialog.show();
                }
            } else {
                if (!mActivity.isDestroyed() && mPgDialog.isShowing()) {
                    mPgDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void showToast(String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
    }

    /*
     * Update ingredient by get index in list of ingredients
     * @param ingredient
     */
    @Override
    public void onIngredientUpdate(String ingredient) {
        edtIngredient.setText(ingredient);
        for (int index = 0; index < mIngredientList.size(); index++) {
            if (TextUtils.equals(mIngredientList.get(index), ingredient)) {
                mIndexOfIngredient = index;
                break;
            }
        }
        isIngredientUpdate = true;
        edtIngredient.setSelection(ingredient.length());
    }

    /*
     * Get object ingredient and perform delete it in mIngredientList
     * And then call notifyIngredientAdapter to update list again.
     * @param ingredient
     */
    @Override
    public void onIngredientDelete(String ingredient) {
        mIngredientList.remove(ingredient);
        notifyIngredientAdapter();
    }

    /*
     * Update step by get index in list of steps
     * @param step
     */
    @Override
    public void onStepUpdate(String step) {
        edtStep.setText(step);
        for (int index = 0; index < mStepList.size(); index++) {
            if (TextUtils.equals(mStepList.get(index), step)) {
                mIndexOfStep = index;
                break;
            }
        }
        isStepUpdate = true;
        edtStep.setSelection(step.length());
    }

    /*
     * Get object step and perform delete it in mStepList
     * And then call notifyStepAdapter to update list again.
     * @param step
     */
    @Override
    public void onStepDelete(String step) {
        mStepList.remove(step);
        notifyStepAdapter();
    }

    /*
     * This method can be used to show keyboard when tab done in Android device
     * @param textView
     * @param i
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            Helper.hiddenKeyBoard(mActivity);
        }
        return true;
    }

}
