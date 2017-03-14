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

    private static CaseClickListener caseClickListener;
    private List<Case> cases;

    public static class CaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView caseName;
        TextView caseId;

        public CaseViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.case_cv);
            caseName = (TextView)itemView.findViewById(R.id.case_name);
            caseId = (TextView)itemView.findViewById(R.id.case_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            caseClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(CaseClickListener caseClickListener){
        this.caseClickListener = caseClickListener;
    }

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
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    public interface CaseClickListener{
        void onItemClick(int position, View v);
    }
}
