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
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    private ArrayList<String> mStepList;

    public StepsAdapter(ArrayList<String> temps){
        this.mStepList = temps;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_step, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i ) {
        holder.tvStepName.setText(mStepList.get(i));
        holder.iconEdit.setOnClickListener(v -> mListener.onStepUpdate(mStepList.get(i)));
        holder.iconDelete.setOnClickListener(v -> mListener.onStepDelete(mStepList.get(i)));
    }

    @Override
    public int getItemCount(){
        return mStepList.size();
    }

    public void setList(ArrayList<String> temps) {
        mStepList = temps;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvStepName;
        ImageView iconEdit, iconDelete;
        MyViewHolder(View view){
            super(view);
            iconDelete = view.findViewById(R.id.iconDelete);
            iconEdit = view.findViewById(R.id.iconEdit);
            tvStepName = view.findViewById(R.id.tvStepName);
        }
    }

    /*
     * Create interface to communicate between StepsAdapter and RecipeActivity.
     */
    public interface StepListener {
        void onStepUpdate(String step);
        void onStepDelete(String step);
    }

    private StepListener mListener;

    public void setStepListener(StepListener listener) {
        mListener = listener;
    }
}