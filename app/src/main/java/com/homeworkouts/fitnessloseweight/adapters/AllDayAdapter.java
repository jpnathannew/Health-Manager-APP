package com.homeworkouts.fitnessloseweight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.homeworkouts.fitnessloseweight.R;

import java.util.List;

public class AllDayAdapter extends Adapter<AllDayAdapter.ViewHolder> {


    public List<WorkoutData> f1462a;
    public Context mContext;
    public int position = -1;

    class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {


        public TextView f1463a;
        public NumberProgressBar b;
        public CardView c;
        public ImageView d;
        public RelativeLayout rel1,rel2;

        public ViewHolder(View view) {
            super(view);
            this.f1463a = (TextView) view.findViewById(R.id.row_day);
            this.c = (CardView) view.findViewById(R.id.cardviewone);
            this.d = (ImageView) view.findViewById(R.id.restImageView);
            this.b = (NumberProgressBar) view.findViewById(R.id.progressbar);
            this.rel1 = (RelativeLayout) view.findViewById(R.id.rel1);
            this.rel2 = (RelativeLayout) view.findViewById(R.id.rel2);
        }
    }

    public AllDayAdapter(Context context, List<WorkoutData> list) {
        this.f1462a = list;
    }

    public int getItemCount() {
        List<WorkoutData> list = this.f1462a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.b.setMax(100);
        if ((i + 1) % 4 != 0 || i > 27) {
            viewHolder.d.setVisibility(View.GONE);
            viewHolder.rel2.setVisibility(View.GONE);


            viewHolder.f1463a.setText(((WorkoutData) this.f1462a.get(i)).getDay());
            viewHolder.b.setVisibility(View.VISIBLE);
            viewHolder.rel1.setVisibility(View.VISIBLE);
            if (((int) ((WorkoutData) this.f1462a.get(i)).getProgress()) >= 99) {
                viewHolder.b.setProgress(100);
            } else {
                viewHolder.b.setProgress((int) ((WorkoutData) this.f1462a.get(i)).getProgress());
            }
        } else {
            viewHolder.b.setVisibility(View.GONE);
            viewHolder.rel1.setVisibility(View.GONE);
            viewHolder.f1463a.setText("Rest Day");
            viewHolder.d.setVisibility(View.VISIBLE);
            viewHolder.rel2.setVisibility(View.VISIBLE);

        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_days_row, viewGroup, false));
    }
}
