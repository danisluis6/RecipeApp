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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.R;

import java.util.ArrayList;

/**
 * Created by lorence on 27/November/2020.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private ArrayList<String> mIngredientList;

    public IngredientAdapter(ArrayList<String> temps){
        this.mIngredientList = temps;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_ingredient, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i ) {
        holder.tvIngredientName.setText(mIngredientList.get(i));
        holder.iconEdit.setOnClickListener(v -> mListener.onIngredientUpdate(mIngredientList.get(i)));
        holder.iconDelete.setOnClickListener(v -> mListener.onIngredientDelete(mIngredientList.get(i)));
    }

    @Override
    public int getItemCount(){
        return mIngredientList.size();
    }

    public void setList(ArrayList<String> temps) {
        mIngredientList = temps;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        ImageView iconEdit, iconDelete;
        MyViewHolder(View view){
            super(view);
            iconDelete = view.findViewById(R.id.iconDelete);
            iconEdit = view.findViewById(R.id.iconEdit);
            tvIngredientName = view.findViewById(R.id.tvIngredientName);
        }
    }

    /*
     * Create interface to communicate between IngredientAdapter and RecipeActivity.
     */
    public interface IngredientListener {
        void onIngredientUpdate(String ingredient);
        void onIngredientDelete(String ingredient);
    }

    private IngredientListener mListener;

    public void setIngredientListener(IngredientListener listener) {
        mListener = listener;
    }
}