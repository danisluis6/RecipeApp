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
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lib.localstorage.entities.RecipeType;

import java.util.List;

/**
 * Created by lorence on 27/November/2020.
 */
public class RecipeTypeAdapter extends ArrayAdapter<RecipeType> {

    private List<RecipeType> mRecipeTypes;

    public RecipeTypeAdapter(Context context, int textViewResourceId, List<RecipeType> values) {
        super(context, textViewResourceId, values);
        this.mRecipeTypes = values;
    }

    @Override
    public int getCount(){
        return mRecipeTypes.size();
    }

    @Override
    public RecipeType getItem(int position){
        return mRecipeTypes.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public void setList(List<RecipeType> recipeTypes) {
        mRecipeTypes = recipeTypes;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(mRecipeTypes.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(mRecipeTypes.get(position).getName());
        return label;
    }

}
