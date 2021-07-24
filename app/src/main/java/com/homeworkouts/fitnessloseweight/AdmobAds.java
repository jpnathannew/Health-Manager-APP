package com.homeworkouts.fitnessloseweight;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd.OnUnifiedNativeAdLoadedListener;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class AdmobAds {
    public String ADMOB_AD_UNIT_ID = "";

    public boolean admobAdLoaded_dialog;
    public Context context;
    public LinearLayout nativeAdContainer;
    public UnifiedNativeAd unifiedNativeAdObject_dialog;

    public AdmobAds(Context context2, LinearLayout linearLayout, String str) {
        this.context = context2;
        this.nativeAdContainer = linearLayout;
        this.ADMOB_AD_UNIT_ID = str;
        MobileAds.initialize(context2, String.valueOf(R.string.admob_app_id));
    }

    public AdmobAds(Context context2, String str) {
        this.context = context2;
        this.ADMOB_AD_UNIT_ID = str;
        MobileAds.initialize(context2, String.valueOf(R.string.admob_app_id));
    }

    public void displayAdmobAdOnLoad_Dialog(LinearLayout linearLayout) {
        linearLayout.setVisibility(View.VISIBLE);
        UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) LayoutInflater.from(this.context).inflate(R.layout.ad_unified_dialog, null);
        populateUnifiedNativeAdView_dialog(this.unifiedNativeAdObject_dialog, unifiedNativeAdView);
        linearLayout.addView(unifiedNativeAdView);
    }

    public boolean isConnectedToInternet() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
                if (allNetworkInfo != null) {
                    for (NetworkInfo state : allNetworkInfo) {
                        if (state.getState() == State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        int i;
        View view;
        unifiedNativeAd.getVideoController().setVideoLifecycleCallbacks(new VideoLifecycleCallbacks() {
            public void onVideoEnd() {
                super.onVideoEnd();
            }
        });
        int i2 = this.context.getResources().getDisplayMetrics().heightPixels;
        MediaView mediaView = (MediaView) unifiedNativeAdView.findViewById(R.id.popup_appinstall_image);
        LayoutParams layoutParams = mediaView.getLayoutParams();
        layoutParams.height = (int) (((float) i2) / 3.0f);
        mediaView.setLayoutParams(layoutParams);
        unifiedNativeAdView.setMediaView(mediaView);
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_app_icon));
        unifiedNativeAdView.findViewById(R.id.close_ad_popup).setVisibility(View.INVISIBLE);
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        if (unifiedNativeAd.getIcon() == null) {
            view = unifiedNativeAdView.getIconView();
            i = 8;
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            view = unifiedNativeAdView.getIconView();
            i = 0;
        }
        view.setVisibility(i);
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }

    public void populateUnifiedNativeAdView_dialog(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        int i;
        View view;
        unifiedNativeAd.getVideoController().setVideoLifecycleCallbacks(new VideoLifecycleCallbacks() {
            public void onVideoEnd() {
                super.onVideoEnd();
            }
        });
        int i2 = this.context.getResources().getDisplayMetrics().heightPixels;
        MediaView mediaView = (MediaView) unifiedNativeAdView.findViewById(R.id.popup_appinstall_image_dialog);
        LayoutParams layoutParams = mediaView.getLayoutParams();
        layoutParams.height = (int) (((float) i2) / 3.0f);
        mediaView.setLayoutParams(layoutParams);
        unifiedNativeAdView.setMediaView(mediaView);
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_headline_dialog));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_body_dialog));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_call_to_action_dialog));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.popup_appinstall_app_icon_dialog));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        if (unifiedNativeAd.getIcon() == null) {
            view = unifiedNativeAdView.getIconView();
            i = 8;
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            view = unifiedNativeAdView.getIconView();
            i = 0;
        }
        view.setVisibility(i);
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }

    public void refreshAd() {
        if (isConnectedToInternet()) {
            Builder builder = new Builder(this.context, this.ADMOB_AD_UNIT_ID);
            builder.forUnifiedNativeAd(new OnUnifiedNativeAdLoadedListener() {
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) LayoutInflater.from(AdmobAds.this.context).inflate(R.layout.ad_unified, null);
                    AdmobAds.this.populateUnifiedNativeAdView(unifiedNativeAd, unifiedNativeAdView);
                    AdmobAds.this.nativeAdContainer.setVisibility(View.VISIBLE);
                    AdmobAds.this.nativeAdContainer.setBackgroundResource(R.drawable.shape_roundedwhite);
                    AdmobAds.this.nativeAdContainer.addView(unifiedNativeAdView);
                }
            });
            builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
            builder.withAdListener(new AdListener() {
                public void onAdFailedToLoad(int i) {
                    AdmobAds.this.refreshAd();
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    public boolean refreshAd_dialog() {
        if (isConnectedToInternet()) {
            Builder builder = new Builder(this.context, this.ADMOB_AD_UNIT_ID);
            builder.forUnifiedNativeAd(new OnUnifiedNativeAdLoadedListener() {
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    AdmobAds.this.admobAdLoaded_dialog = true;
                    AdmobAds.this.unifiedNativeAdObject_dialog = unifiedNativeAd;
                }
            });
            builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
            builder.withAdListener(new AdListener() {
                public void onAdFailedToLoad(int i) {
                    AdmobAds.this.admobAdLoaded_dialog = false;
                    AdmobAds.this.refreshAd_dialog();
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
        return this.admobAdLoaded_dialog;
    }
}
