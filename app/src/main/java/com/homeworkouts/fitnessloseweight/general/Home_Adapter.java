package com.homeworkouts.fitnessloseweight.general;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.p000v4.app.ActivityOptionsCompat;
//import android.support.p000v4.util.Pair;
//import android.support.p003v7.widget.RecyclerView.Adapter;
//import android.support.p003v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
//import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.homeworkouts.fitnessloseweight.BMR.bmr_calculator;
import com.homeworkouts.fitnessloseweight.Body_Surface_Area_Calculator.C1376Body_Surface_Area_Calculator;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.alcohol.Alcohol_Calculator;
import com.homeworkouts.fitnessloseweight.blood_donation.Blood_Donation_Calculator;
import com.homeworkouts.fitnessloseweight.blood_pressure.BloodPressure_Calculator;
import com.homeworkouts.fitnessloseweight.blood_volume.Blood_Volume_Calculator;
import com.homeworkouts.fitnessloseweight.bmi.BMI_Calculator;
import com.homeworkouts.fitnessloseweight.body_adiposity_index.Body_Adiposity_Index_Calculator;
import com.homeworkouts.fitnessloseweight.body_fat.Bodyfat_Calculator;
import com.homeworkouts.fitnessloseweight.body_frame_size.Body_Frame_Size_Calculator;
import com.homeworkouts.fitnessloseweight.calories_burn.Calories_burn_Calculator;
import com.homeworkouts.fitnessloseweight.calories_intake.Daily_Calories_Intake_Calculator;
import com.homeworkouts.fitnessloseweight.chest_hip_ratio.Chest_Hip_Ratio_Calculator;
import com.homeworkouts.fitnessloseweight.child_growth.Child_Growth_Calculator;
import com.homeworkouts.fitnessloseweight.children_height_growth.Children_Height_Growth_Prediction_Calculator;
import com.homeworkouts.fitnessloseweight.cholestrol.Cholestrol_Calculator;
import com.homeworkouts.fitnessloseweight.daily_water.Daily_WaterIntake_Calculator;
import com.homeworkouts.fitnessloseweight.heart_rate.Heart_Rate_Calculator;
import com.homeworkouts.fitnessloseweight.ideal_body_weight.Ideal_Body_Weight_Calculator;
import com.homeworkouts.fitnessloseweight.lean_body_mass.Lean_Body_Mass_Calculator;
import com.homeworkouts.fitnessloseweight.menstrual_and_ovulation.Menstrual_Ovulation_Calculator;
import com.homeworkouts.fitnessloseweight.pregnancy_due_date.Pregnancy_Due_Date_Calculator;
import com.homeworkouts.fitnessloseweight.smoking_risk.Smoking_Risk_Calculator;
import com.homeworkouts.fitnessloseweight.sugar.Sugar_calculator;
import com.homeworkouts.fitnessloseweight.trademill.Trademill_Calculator;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;
import com.homeworkouts.fitnessloseweight.waist_height_ratio.Waist_Height_Ratio_Calculator;
import com.homeworkouts.fitnessloseweight.waist_hip_ratio.Waist_Hip_Ratio_Calculator;
import java.util.ArrayList;

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.MyRestaurant_ViewHolder> implements Filterable {
    ArrayList<All_Home> FilteredList = new ArrayList<>();
    Activity activity;
    public ArrayList<All_Home> allVideo;
    ArrayList<All_Home> all_videohistoryArrayList;
    Context context;
    boolean isFilter = false;
    TypefaceManager typefaceManager;

    public class MyRestaurant_ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_home;
        RelativeLayout rl_home;
        TextView tv_title_home;

        public MyRestaurant_ViewHolder(View view) {
            super(view);
            this.tv_title_home = (TextView) view.findViewById(R.id.tv_title_home);
            this.image_home = (ImageView) view.findViewById(R.id.image_home);
            this.rl_home = (RelativeLayout) view.findViewById(R.id.rl_home);
            this.tv_title_home.setTypeface(Home_Adapter.this.typefaceManager.getLight());
        }
    }

    public Home_Adapter(Activity activity2, ArrayList<All_Home> arrayList, Context context2) {
        this.activity = activity2;
        this.context = context2;
        this.all_videohistoryArrayList = arrayList;
        this.allVideo = arrayList;
        this.typefaceManager = new TypefaceManager(context2.getAssets(), context2);
    }

    public MyRestaurant_ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyRestaurant_ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_home, viewGroup, false));
    }

    public void onBindViewHolder(final MyRestaurant_ViewHolder myRestaurant_ViewHolder, int i) {
        try {
            myRestaurant_ViewHolder.tv_title_home.setText(((All_Home) this.all_videohistoryArrayList.get(i)).title);
//            GradientDrawable gradientDrawable = (GradientDrawable) myRestaurant_ViewHolder.rl_home.getBackground();
//            StringBuilder sb = new StringBuilder();
//            sb.append("#");
//            sb.append(((All_Home) this.all_videohistoryArrayList.get(i)).color);
//            gradientDrawable.setColor(Color.parseColor(sb.toString()));
            try {
                Resources resources = this.context.getResources();
                myRestaurant_ViewHolder.image_home.setImageDrawable(resources.getDrawable(resources.getIdentifier(((All_Home) this.all_videohistoryArrayList.get(i)).image, "drawable", this.context.getPackageName())));
            } catch (Exception e) {
                e.printStackTrace();
            }
            myRestaurant_ViewHolder.rl_home.setTag(Integer.valueOf(i));
            myRestaurant_ViewHolder.rl_home.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    int i = ((All_Home) Home_Adapter.this.all_videohistoryArrayList.get(((Integer) view.getTag()).intValue())).f2614id;
                    if (i == 0) {
                        Intent intent = new Intent(Home_Adapter.this.activity, bmr_calculator.class);
                        intent.putExtra("from", "bmr");
                        Pair create = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create).toBundle());
                    } else if (i == 1) {
                        Intent intent2 = new Intent(Home_Adapter.this.activity, Alcohol_Calculator.class);
                        Pair create2 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent2, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create2).toBundle());
                    } else if (i == 2) {
                        Intent intent3 = new Intent(Home_Adapter.this.activity, Blood_Donation_Calculator.class);
                        Pair create3 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent3, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create3).toBundle());
                    } else if (i == 3) {
                        Intent intent4 = new Intent(Home_Adapter.this.activity, BloodPressure_Calculator.class);
                        Pair create4 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent4, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create4).toBundle());
                    } else if (i == 4) {
                        Intent intent5 = new Intent(Home_Adapter.this.activity, Sugar_calculator.class);
                        Pair create5 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent5, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create5).toBundle());
                    } else if (i == 5) {
                        Intent intent6 = new Intent(Home_Adapter.this.activity, Blood_Volume_Calculator.class);
                        Pair create6 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent6, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create6).toBundle());
                    } else if (i == 6) {
                        Intent intent7 = new Intent(Home_Adapter.this.activity, Body_Adiposity_Index_Calculator.class);
                        Pair create7 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent7, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create7).toBundle());
                    } else if (i == 7) {
                        Intent intent8 = new Intent(Home_Adapter.this.activity, Bodyfat_Calculator.class);
                        Pair create8 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent8, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create8).toBundle());
                    } else if (i == 8) {
                        Intent intent9 = new Intent(Home_Adapter.this.activity, Body_Frame_Size_Calculator.class);
                        Pair create9 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent9, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create9).toBundle());
                    } else if (i == 9) {
                        Intent intent10 = new Intent(Home_Adapter.this.activity, BMI_Calculator.class);
                        Pair create10 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent10, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create10).toBundle());
                    } else if (i == 10) {
                        Intent intent11 = new Intent(Home_Adapter.this.activity, C1376Body_Surface_Area_Calculator.class);
                        Pair create11 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent11, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create11).toBundle());
                    } else if (i == 11) {
                        Intent intent12 = new Intent(Home_Adapter.this.activity, Trademill_Calculator.class);
                        Pair create12 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent12, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create12).toBundle());
                    } else if (i == 12) {
                        Intent intent13 = new Intent(Home_Adapter.this.activity, Calories_burn_Calculator.class);
                        Pair create13 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent13, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create13).toBundle());
                    } else if (i == 13) {
                        Intent intent14 = new Intent(Home_Adapter.this.activity, Chest_Hip_Ratio_Calculator.class);
                        Pair create14 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent14, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create14).toBundle());
                    } else if (i == 14) {
                        Intent intent15 = new Intent(Home_Adapter.this.activity, Children_Height_Growth_Prediction_Calculator.class);
                        Pair create15 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent15, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create15).toBundle());
                    } else if (i == 15) {
                        Intent intent16 = new Intent(Home_Adapter.this.activity, Cholestrol_Calculator.class);
                        Pair create16 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent16, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create16).toBundle());
                    } else if (i == 16) {
                        Intent intent17 = new Intent(Home_Adapter.this.activity, Daily_Calories_Intake_Calculator.class);
                        Pair create17 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent17, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create17).toBundle());
                    } else if (i == 17) {
                        Intent intent18 = new Intent(Home_Adapter.this.activity, Daily_WaterIntake_Calculator.class);
                        Pair create18 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent18, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create18).toBundle());
                    } else if (i == 18) {
                        Intent intent19 = new Intent(Home_Adapter.this.activity, Heart_Rate_Calculator.class);
                        Pair create19 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent19, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create19).toBundle());
                    } else if (i == 19) {
                        Intent intent20 = new Intent(Home_Adapter.this.activity, Ideal_Body_Weight_Calculator.class);
                        Pair create20 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent20, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create20).toBundle());
                    } else if (i == 20) {
                        Intent intent21 = new Intent(Home_Adapter.this.activity, Child_Growth_Calculator.class);
                        Pair create21 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent21, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create21).toBundle());
                    } else if (i == 21) {
                        Intent intent22 = new Intent(Home_Adapter.this.activity, Lean_Body_Mass_Calculator.class);
                        Pair create22 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent22, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create22).toBundle());
                    } else if (i == 22) {
                        Intent intent23 = new Intent(Home_Adapter.this.activity, Menstrual_Ovulation_Calculator.class);
                        Pair create23 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent23, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create23).toBundle());
                    } else if (i == 23) {
                        Intent intent24 = new Intent(Home_Adapter.this.activity, Pregnancy_Due_Date_Calculator.class);
                        Pair create24 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent24, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create24).toBundle());
                    } else if (i == 24) {
                        Intent intent25 = new Intent(Home_Adapter.this.activity, bmr_calculator.class);
                        intent25.putExtra("from", "rmr");
                        Pair create25 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent25, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create25).toBundle());
                    } else if (i == 25) {
                        Intent intent26 = new Intent(Home_Adapter.this.activity, Smoking_Risk_Calculator.class);
                        Pair create26 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent26, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create26).toBundle());
                    } else if (i == 26) {
                        Intent intent27 = new Intent(Home_Adapter.this.activity, Waist_Height_Ratio_Calculator.class);
                        Pair create27 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent27, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create27).toBundle());
                    } else if (i == 27) {
                        Intent intent28 = new Intent(Home_Adapter.this.activity, Waist_Hip_Ratio_Calculator.class);
                        Pair create28 = Pair.create(myRestaurant_ViewHolder.rl_home, Home_Adapter.this.activity.getString(R.string.transition1));
                        ActivityCompat.startActivity(Home_Adapter.this.activity, intent28, ActivityOptionsCompat.makeSceneTransitionAnimation(Home_Adapter.this.activity, create28).toBundle());
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.all_videohistoryArrayList.size();
    }

    public Filter getFilter() {
        return new Filter() {

            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                Home_Adapter.this.all_videohistoryArrayList = (ArrayList) filterResults.values;
                Home_Adapter.this.notifyDataSetChanged();
            }


            public FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.values = Home_Adapter.this.allVideo;
                    filterResults.count = Home_Adapter.this.allVideo.size();
                    Home_Adapter.this.isFilter = false;
                } else {
                    Home_Adapter.this.FilteredList.clear();
                    Home_Adapter.this.isFilter = true;
                    for (int i = 0; i < Home_Adapter.this.allVideo.size(); i++) {
                        All_Home all_Home = (All_Home) Home_Adapter.this.allVideo.get(i);
                        if (all_Home.title.toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("pos->");
                            sb.append(String.valueOf(i));
                            Log.d("pos->", sb.toString());
                            Home_Adapter.this.FilteredList.add(all_Home);
                        }
                    }
                    filterResults.values = Home_Adapter.this.FilteredList;
                    filterResults.count = Home_Adapter.this.FilteredList.size();
                }
                return filterResults;
            }
        };
    }
}
