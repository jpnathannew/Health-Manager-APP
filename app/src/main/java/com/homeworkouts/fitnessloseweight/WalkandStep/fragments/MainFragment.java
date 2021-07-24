
package com.homeworkouts.fitnessloseweight.WalkandStep.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.WalkandStep.activities.PreferencesActivity;
import com.homeworkouts.fitnessloseweight.WalkandStep.utils.StepDetectionServiceHelper;

import java.util.ArrayList;
import java.util.List;




public class MainFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        sharedPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main1, container, false);

        StepDetectionServiceHelper.startAllIfEnabled(true, getContext());

        imageView = (ImageView) view.findViewById(R.id.settle);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                Intent intent = new Intent(getActivity(), PreferencesActivity.class);
                startActivity(intent);
            }
        });

//        StepDetectionServiceHelper.startAllIfEnabled(true,
//                getActivity().getApplicationContext());

//        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        if (actionBar != null) {
//            //actionBar.setSubtitle(R.string.action_main);
//
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        container.removeAllViews();

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


            setHasOptionsMenu(false);



        return view;
    }

    @Override
    public void onDetach() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        sharedPref.unregisterOnSharedPreferenceChangeListener(this);
        StepDetectionServiceHelper.stopAllIfNotRequired(getActivity().getApplicationContext());
        super.onDetach();
    }

    @Override
    public void onPause() {
        StepDetectionServiceHelper.stopAllIfNotRequired(getActivity().getApplicationContext());
        super.onPause();
    }

    private void setupViewPager(ViewPager viewPager) {
        new ViewPagerAdapter(null);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(DailyReportFragment.newInstance(), getString(R.string.day));
        adapter.addFragment(WeeklyReportFragment.newInstance(), getString(R.string.week));
        adapter.addFragment(MonthlyReportFragment.newInstance(), getString(R.string.month));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_options, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

//        setPauseContinueMenuItemVisibility(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (item.getItemId()) {

//            case R.id.menu_pause_step_detection:
//                editor.putBoolean(getString(R.string.pref_step_counter_enabled), false);
//                editor.apply();
//                StepDetectionServiceHelper.stopAllIfNotRequired(getActivity().getApplicationContext());
//                return true;
//
//            case R.id.menu_continue_step_detection:
//                editor.putBoolean(getString(R.string.pref_step_counter_enabled), true);
//                editor.apply();
//                StepDetectionServiceHelper.startAllIfEnabled(true, getActivity().getApplicationContext());
//                return true;
            case R.id.menu_Setting_main:
                Intent intent = new Intent(getActivity(), PreferencesActivity.class);
                startActivity(intent);

            default:
                return false;
        }
    }


    private void setPauseContinueMenuItemVisibility(Menu menu) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        boolean isStepCounterEnabled = sharedPref.getBoolean(getString(R.string.pref_step_counter_enabled), true);
        MenuItem continueStepDetectionMenuItem = menu.findItem(R.id.menu_continue_step_detection);
        MenuItem pauseStepDetectionMenuItem = menu.findItem(R.id.menu_pause_step_detection);
        if (isStepCounterEnabled) {
            continueStepDetectionMenuItem.setVisible(false);
            pauseStepDetectionMenuItem.setVisible(true);
        } else {
            continueStepDetectionMenuItem.setVisible(true);
            pauseStepDetectionMenuItem.setVisible(false);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_step_counter_enabled))) {
            this.getActivity().invalidateOptionsMenu();
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        StepDetectionServiceHelper.startAllIfEnabled(true, getActivity().getApplicationContext());
        super.onAttach(activity);
    }
}
