package com.danielburgnerjr.bakingapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielburgnerjr.bakingapp.model.Step;
import com.danielburgnerjr.bakingapp.R;


import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private List<Step> mStepList;
    Context mContext;
    onProcessAdapterClickHandler mStepClickHandler;

    public interface onProcessAdapterClickHandler{
        void onProcessClicked(List<Step> stepList, int position);
    }

    public StepAdapter(List<Step> mStepList, onProcessAdapterClickHandler mStepClickHandler) {
        this.mStepList = mStepList;
        this.mStepClickHandler = mStepClickHandler;
    }

    @NonNull
    @Override
    public StepAdapter.StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.process_layout, parent, false);
        return new StepAdapter.StepAdapterViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (mStepList == null) {
            return 0;
        } else {
            return mStepList.size();
        }
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView process_tv, process_recipe_step,txt_exo_player_null;

        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            process_tv=itemView.findViewById(R.id.process_textView);
            process_recipe_step=itemView.findViewById(R.id.process_number);
            txt_exo_player_null=itemView.findViewById(R.id.exoplayer_null);
        }

        @Override
        public void onClick(View view) {
            mStepClickHandler.onProcessClicked(mStepList ,getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.StepAdapterViewHolder holder, int position) {
        Step stepObject = mStepList.get(position);
        holder.step_tv.setText(stepObject.getShortDescription());
        holder.recipe_step.setText(String.valueOf(stepObject.getId()));
    }
}

