package com.techops.mankitt.permission;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ryan on 13/3/2017.
 */

public class Case_RVAdapter extends RecyclerView.Adapter<Case_RVAdapter.CaseViewHolder>{

    public static class CaseViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView caseName;
        TextView caseId;
        ImageView caseImg;

        CaseViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            caseName = (TextView)itemView.findViewById(R.id.case_name);
            caseId = (TextView)itemView.findViewById(R.id.case_id);
            caseImg = (ImageView)itemView.findViewById(R.id.case_img);
        }
    }

    List<Case> cases;

    Case_RVAdapter(List<Case> cases){
        this.cases = cases;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CaseViewHolder pvh = new CaseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CaseViewHolder caseViewHolder, int i) {
        caseViewHolder.caseName.setText(cases.get(i).name);
        caseViewHolder.caseId.setText(cases.get(i).identifier);
        caseViewHolder.caseImg.setImageResource(cases.get(i).imgId);
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }
}
