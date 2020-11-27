package com.example.framework.adapter;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lib.localstorage.entities.Recipe;
import com.example.recipe.R;

import java.util.ArrayList;

/**
 * Created by lorence on 27/November/2020.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> mRecipeList;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes){
        this.mContext = context;
        this.mRecipeList = recipes;
    }

    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recipe, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i ) {
        holder.tvRecipeName.setText(mRecipeList.get(i).getName());
        holder.tvIngredientCount.setText(String.format(mContext.getString(R.string.label_count_ingredients), String.valueOf(mRecipeList.get(i).getIngredients().size())));
        holder.tvStepCount.setText(String.format(mContext.getString(R.string.label_count_ingredients), String.valueOf(mRecipeList.get(i).getSteps().size())));
        Glide.with(mContext)
                .load(mRecipeList.get(i).getPicture())
                .into(holder.imvThumbnail);
        holder.itemView.setOnClickListener(v -> mListener.openRecipe(mRecipeList.get(i)));
        holder.itemView.setOnLongClickListener(v -> {
            mListener.deleteRecipe(mRecipeList.get(i));
            return false;
        });
        holder.imvDelete.setOnClickListener(v -> mListener.deleteRecipe(mRecipeList.get(i)));
    }

    @Override
    public int getItemCount(){
        return mRecipeList.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        mRecipeList = recipes;
        notifyDataSetChanged();
    }

    /*
     * This method can be used to remove recipe
     * @param recipe
     */
    public void remove(Recipe recipe) {
        for (int index = 0; index < mRecipeList.size(); index++) {
            if (mRecipeList.get(index).getId() == recipe.getId()) {
                mRecipeList.remove(index);
                break;
            }
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvRecipeName, tvIngredientCount, tvStepCount;
        ImageView imvThumbnail, imvDelete;

        MyViewHolder(View view){
            super(view);
            tvRecipeName = view.findViewById(R.id.tvRecipeName);
            imvThumbnail = view.findViewById(R.id.imvThumbnail);
            imvDelete = view.findViewById(R.id.imvDelete);
            tvIngredientCount = view.findViewById(R.id.tvIngredientCount);
            tvStepCount = view.findViewById(R.id.tvStepCount);
        }
    }

    /*
     * This interface can be used to transfer a recipe from RecipeAdapter to MainActivity
     */
    public interface RecipeListener {
        void openRecipe(Recipe recipe);
        void deleteRecipe(Recipe recipe);
    }

    private RecipeListener mListener;

    public void setListener(RecipeListener listener) {
        mListener = listener;
    }
}
