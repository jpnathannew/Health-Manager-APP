package com.homeworkouts.fitnessloseweight.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.homeworkouts.fitnessloseweight.R;

import com.homeworkouts.fitnessloseweight.Water_MainActivity;
import com.homeworkouts.fitnessloseweight.pill_reminder.Pill_MainActivity;

public class Fragment_Reminder extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    public static Fragment_Reminder newInstance(String str, String str2) {
        Fragment_Reminder mainFragment = new Fragment_Reminder();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_reminder, viewGroup, false);



        ImageView  button  = (ImageView) inflate.findViewById(R.id.set_water);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getActivity(), Water_MainActivity.class);
                startActivity(intent);

            }
        });


        ImageView  button1  = (ImageView) inflate.findViewById(R.id.set_medicion);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =  new Intent(getActivity(), Pill_MainActivity.class);
                startActivity(intent1);

            }
        });


        return inflate;
    }


}
