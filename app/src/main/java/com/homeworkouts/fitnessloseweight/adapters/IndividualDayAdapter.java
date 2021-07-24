package com.homeworkouts.fitnessloseweight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import com.homeworkouts.fitnessloseweight.R;

import java.util.ArrayList;
import java.util.HashMap;

import kr.pe.burt.android.lib.faimageview.FAImageView;

public class IndividualDayAdapter extends Adapter<IndividualDayAdapter.ViewHolder> {
    public HashMap<String, ArrayList<Integer>> arrayListHashMap = this.arrayListHashMap;
    public Context context;
    public String day;
    public int screenWidth;
    public ArrayList<WorkoutData> workoutDataList;

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView f1465a;
        public TextView b;
        public FAImageView c;
        public RelativeLayout d;

        public ViewHolder(View view) {
            super(view);
            this.f1465a = (TextView) view.findViewById(R.id.exerciseName);
            this.b = (TextView) view.findViewById(R.id.rotation);
            this.c = (FAImageView) view.findViewById(R.id.animation);
            this.d = (RelativeLayout) view.findViewById(R.id.cardViewInRecycler);
        }
    }

    public IndividualDayAdapter(Context context2, String str, ArrayList<WorkoutData> arrayList, int i) {
        this.context = context2;
        this.screenWidth = i;
        this.day = str;
        this.workoutDataList = arrayList;
    }

    public int getItemCount() {
        return this.workoutDataList.size() + 1;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TextView textView;
        StringBuilder sb;
        if (i < this.workoutDataList.size()) {
            viewHolder.d.setVisibility(0);
            viewHolder.c.setInterval(1000);
            viewHolder.c.setLoop(true);
            viewHolder.c.reset();
            for (int addImageFrame : ((WorkoutData) this.workoutDataList.get(i)).getImageIdList()) {
                viewHolder.c.addImageFrame(addImageFrame);
            }
            viewHolder.c.startAnimation();
            viewHolder.f1465a.setText(((WorkoutData) this.workoutDataList.get(i)).getExcName().replace("_", " ").toUpperCase());
            if (((WorkoutData) this.workoutDataList.get(i)).getExcName().equals("plank")) {
                textView = viewHolder.b;
                sb = new StringBuilder();
                sb.append(((WorkoutData) this.workoutDataList.get(i)).getExcCycles());
                sb.append("s");
            } else {
                textView = viewHolder.b;
                sb = new StringBuilder();
                sb.append("x");
                sb.append(((WorkoutData) this.workoutDataList.get(i)).getExcCycles());
            }
            textView.setText(sb.toString());
            return;
        }
        viewHolder.d.setVisibility(8);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_days, viewGroup, false));
    }
}
