package com.homeworkouts.fitnessloseweight.alarm.alarm_activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.alarm.alarm_fragments.ReminderFragment;

//import com.crazytrends.fitnessloseweight.R;
//import com.crazytrends.fitnessloseweight.alarm.alarm_fragments.ReminderFragment;

public class AlarmMainActivity extends AppCompatActivity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.alarm_activity_main);
        if (bundle == null) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.sample_content_fragment, new ReminderFragment());
            beginTransaction.commit();
        }
    }
}
