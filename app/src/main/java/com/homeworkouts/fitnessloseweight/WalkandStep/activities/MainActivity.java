
package com.homeworkouts.fitnessloseweight.WalkandStep.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.WalkandStep.fragments.MainFragment;
import com.homeworkouts.fitnessloseweight.WalkandStep.utils.StepDetectionServiceHelper;



public class MainActivity extends Fragment



//        DailyReportFragment.OnFragmentInteractionListener,
//        WeeklyReportFragment.OnFragmentInteractionListener,
//        MonthlyReportFragment.OnFragmentInteractionListener

{
    static final int NAVDRAWER_LAUNCH_DELAY = 250;
    // fade in and fade out durations for the main content when switching between
    // different Activities of the app through the Nav Drawer
    static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
    static final int MAIN_CONTENT_FADEIN_DURATION = 250;

    // Navigation drawer:
//    private DrawerLayout mDrawerLayout;
//    private NavigationView mNavigationView;

    // Helper
    private Handler mHandler;
    protected SharedPreferences mSharedPreferences;



//    public boolean onNavigationItemSelected(MenuItem item) {
//        final int itemId = item.getItemId();
//
//        return goToNavigationItem(itemId);
//    }

//    protected boolean goToNavigationItem(final int itemId) {
//
//        if (itemId == getNavigationDrawerID()) {
//            // just close drawer because we are already in this activity
//            mDrawerLayout.closeDrawer(GravityCompat.START);
//            return true;
//        }
//
//        // delay transition so the drawer can close
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                callDrawerItem(itemId);
//            }
//        }, NAVDRAWER_LAUNCH_DELAY);
//
//        mDrawerLayout.closeDrawer(GravityCompat.START);
//
//        selectNavigationItem(itemId);
//
//        // fade out the active activity
//        View mainContent = getActivity().findViewById(R.id.main_content);
//        if (mainContent != null) {
//            mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
//        }
//        return true;
//    }
//
//    // set active navigation item
//    private void selectNavigationItem(int itemId) {
//        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
//            boolean b = itemId == mNavigationView.getMenu().getItem(i).getItemId();
//            mNavigationView.getMenu().getItem(i).setChecked(b);
//        }
//    }


//    private void createBackStack(Intent intent) {
//        if (Build.VERSION.SDK_INT >= 21) {
//            TaskStackBuilder builder = TaskStackBuilder.create(getContext());
//            builder.addNextIntentWithParentStack(intent);
//            builder.startActivities();
//        } else {
//            startActivity(intent);
//            getActivity().finish();
//        }
//    }

//    private void callDrawerItem(final int itemId) {
//
//        Intent intent;
//
//        switch (itemId) {
//            case R.id.menu_home:
//                //Fragment fragment = new MainFragment();
//
//                //FragmentManager fragmentManager = getSupportFragmentManager();
//                //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
//                intent = new Intent(getContext(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//
//                break;
//            case R.id.menu_training:
//                intent = new Intent(getContext(), TrainingOverviewActivity.class);
//                createBackStack(intent);
//                break;
//            case R.id.menu_distance_measurement:
//                intent = new Intent(getContext(), DistanceMeasurementActivity.class);
//                createBackStack(intent);
//                break;
//
//            case R.id.menu_settings:
//                intent = new Intent(getContext(), PreferencesActivity.class);
//                startActivity(intent);
//                break;
//            default:
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();

        View mainContent = getActivity().findViewById(R.id.main_content);
        if (mainContent != null && mainContent.getAlpha() != 1.0f) {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
        }

//        selectNavigationItem(getNavigationDrawerID());

    }




    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.walk_activity_main, viewGroup, false);

//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.walk_activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mHandler = new Handler();

//        ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            mActionBar = ab;
//            ab.setDisplayHomeAsUpEnabled(true);
//        }
        getActivity().overridePendingTransition(0, 0);

//        Toolbar toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
//        if (((AppCompatActivity)getActivity()).getSupportActionBar() == null) {
////            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////            getSupportActionBar().setHomeButtonEnabled(false);
//            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        }

//        mDrawerLayout = (DrawerLayout) inflate.findViewById(R.id.drawer_layout);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                getActivity(), mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        toggle.setDrawerIndicatorEnabled(false);
//        mNavigationView = (NavigationView) inflate.findViewById(R.id.nav_view);
//        mNavigationView.setNavigationItemSelectedListener(this);

//        selectNavigationItem(getNavigationDrawerID());

        View mainContent = inflate.findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
        }


//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);




        // init preferences
        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(getContext(), R.xml.pref_notification, false);

        // Load first view
        final FragmentTransaction fragmentTransaction = ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new MainFragment(), "MainFragment");
        fragmentTransaction.commit();

        // Start step detection if enabled and not yet started
        StepDetectionServiceHelper.startAllIfEnabled(getContext());
        //Log.i(LOG_TAG, "MainActivity initialized");
        StepDetectionServiceHelper.startAllIfEnabled(true, getContext());

        return inflate;
    }


//    protected int getNavigationDrawerID() {
//        return R.id.menu_home;
//    }

    public void onBackPressed() {

        StepDetectionServiceHelper.startAllIfEnabled(true, getContext());

        Intent  intent = new Intent(getContext(), com.homeworkouts.fitnessloseweight.MainActivity.class);
        startActivity(intent);

//        moveTaskToBack(true);
//        super.onBackPressed();



//            finish();
//        }
    }


}
