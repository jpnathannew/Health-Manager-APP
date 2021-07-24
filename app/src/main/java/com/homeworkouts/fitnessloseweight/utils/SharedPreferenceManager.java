package com.homeworkouts.fitnessloseweight.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceManager {
    String TAG = SharedPreferenceManager.class.getSimpleName();
    Context context;

    public SharedPreferenceManager(Context context2) {
        this.context = context2;
    }

    public void clear_user_data() {
        Editor edit = this.context.getSharedPreferences(Constants.Loopbots_sharedpreference, 0).edit();
        edit.clear();
        edit.apply();
    }

    public void set_Remove_Ad(Boolean bool) {
        Editor edit = this.context.getSharedPreferences(Constants.Loopbots_sharedpreference, 0).edit();
        edit.putBoolean(Constants.KEY_remove_ad, bool.booleanValue());
        edit.apply();
    }

    public void set_Language(String str) {
        Editor edit = this.context.getSharedPreferences(Constants.Loopbots_sharedpreference, 0).edit();
        edit.putString(Constants.KEY_language, str);
        edit.apply();
    }

    public void set_Prev_Phone_Language(String str) {
        Editor edit = this.context.getSharedPreferences(Constants.Loopbots_sharedpreference, 0).edit();
        edit.putString(Constants.KEY_prev_phone_lang, str);
        edit.apply();
    }

    public Boolean get_Remove_Ad() {
        return Boolean.valueOf(this.context.getSharedPreferences(Constants.Loopbots_sharedpreference, 0).getBoolean(Constants.KEY_remove_ad, false));
    }

    public String get_Language() {
        return this.context.getSharedPreferences(Constants.Loopbots_sharedpreference, 0).getString(Constants.KEY_language, "en");
    }

    public String get_Prev_Phone_Language() {
        return this.context.getSharedPreferences(Constants.Loopbots_sharedpreference, 0).getString(Constants.KEY_prev_phone_lang, "en");
    }
}
