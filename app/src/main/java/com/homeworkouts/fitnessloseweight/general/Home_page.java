package com.homeworkouts.fitnessloseweight.general;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;




public class Home_page extends Fragment {
    private static final String TAG = "Home_page";
    AdView adView;
    ArrayList<All_Home> all_home = new ArrayList<>();
    //    AppChecker appChecker;
//    String[] arr_color;
    String[] arr_image;
    String[] arr_title;
    boolean doubleBackToExitPressedOnce = false;
    EditText et_sesrch;
    Home_Adapter home_adapter;
    //    ImageView iv_view_more;
    private JazzyRecyclerViewScrollListener jazzyScrollListener;
//    LinearLayoutManager linearLayoutManager;
    GridLayoutManager manager;
    RecyclerView recyclerview_homepage;
    //    public RecyclerView recyclerview_recommendedapp;
    SharedPreferenceManager sharedPreferenceManager;

    TypefaceManager typefaceManager;


//    public void attachBaseContext(Context context) {
//        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
//    }


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.home_page, viewGroup, false);






        this.sharedPreferenceManager = new SharedPreferenceManager(getActivity());
        this.typefaceManager = new TypefaceManager(getActivity().getAssets(), getActivity());
//        this.appChecker = new AppChecker(this, this.sharedPreferenceManager.get_Remove_Ad().booleanValue());
        this.et_sesrch = (EditText) inflate.findViewById(R.id.et_sesrch);
//        this.iv_view_more = (ImageView) findViewById(R.id.iv_view_more);
        this.recyclerview_homepage = (RecyclerView) inflate.findViewById(R.id.recyclerview_homepage);



//        this.linearLayoutManager = new LinearLayoutManager(getActivity());
        this.manager = new GridLayoutManager(getActivity(), 2);
        this.typefaceManager = new TypefaceManager(getActivity().getAssets(), getActivity());

        this.recyclerview_homepage.setLayoutManager(this.manager);
        this.home_adapter = new Home_Adapter(getActivity(), this.all_home, getContext());

        this.recyclerview_homepage.setAdapter(this.home_adapter);
        this.et_sesrch.setTypeface(this.typefaceManager.getLight());
        this.jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        this.recyclerview_homepage.addOnScrollListener(this.jazzyScrollListener);
        this.jazzyScrollListener.setTransitionEffect(11);
        this.jazzyScrollListener.setMaxAnimationVelocity(0);
        this.arr_title = getResources().getStringArray(R.array.arr_home_title);
        this.arr_image = getResources().getStringArray(R.array.arr_home_image1);
//        this.arr_color = getResources().getStringArray(R.array.arr_home_color1);
        for (int i = 0; i < this.arr_title.length; i++) {
            this.all_home.add(new All_Home(i, this.arr_title[i],  this.arr_image[i]));
        }

        this.et_sesrch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    Home_page.this.home_adapter.getFilter().filter(charSequence);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        this.iv_view_more.setOnClickListener(new OnClickListener() {
//            public void onClick(View view) {
//                Home_page.this.displayPopupWindow_main(view);
//            }
//        });


        return inflate;

    }


    public void onResume() {
        super.onResume();
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue() && MyApplication.interstitial != null && !MyApplication.interstitial.isLoaded() && !MyApplication.interstitial.isLoading()) {
            MyApplication.interstitial.loadAd(new Builder().build());
        }
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            MyApplication.interstitial.setAdListener(new AdListener() {
                public void onAdClosed() {
                    super.onAdClosed();
                    MyApplication.interstitial.loadAd(new Builder().build());
//                    Home_page.this.startActivity(new Intent(Home_page.this, About_Us.class));
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    if (MyApplication.interstitial != null && !MyApplication.interstitial.isLoading()) {
                        MyApplication.interstitial.loadAd(new Builder().build());
                    }
                }
            });
        }
    }


    public void showIntertitial() {
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
//            startActivity(new Intent(this, About_Us.class));
        } else if (MyApplication.interstitial == null || !MyApplication.interstitial.isLoaded()) {
            if (!MyApplication.interstitial.isLoading()) {
                MyApplication.interstitial.loadAd(new Builder().build());
            }
//            startActivity(new Intent(this, About_Us.class));
        } else {
            MyApplication.interstitial.show();
        }
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            int random = ((int) (Math.random() * 3.0d)) + 1;
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("random_number->");
            sb.append(random);
            Log.d(str, sb.toString());
            if (random == 1 || random == 2) {
//                this.appChecker.show_recommendedApps(true);
            } else {
                getActivity().finishAffinity();
            }
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getContext(), getResources().getString(R.string.backtext), 0).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Home_page.this.doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }


}
