package com.homeworkouts.fitnessloseweight.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.LruCache;
//import android.support.p000v4.util.LruCache;
import com.homeworkouts.fitnessloseweight.R;

public class TypefaceManager {
    private static String Bold = "";
    private static String Light = "";
    Context context;
    private final AssetManager mAssetManager;
    private final LruCache<String, Typeface> mCache = new LruCache<>(3);

    public TypefaceManager(AssetManager assetManager, Context context2) {
        this.mAssetManager = assetManager;
        this.context = context2;
        Light = context2.getResources().getString(R.string.font_light);
        Bold = context2.getResources().getString(R.string.font_bold);
    }

    public Typeface getLight() {
        return getTypeface(Light);
    }

    public Typeface getBold() {
        return getTypeface(Bold);
    }

    private Typeface getTypeface(String str) {
        Typeface typeface = (Typeface) this.mCache.get(str);
        if (typeface != null) {
            return typeface;
        }
        Typeface createFromAsset = Typeface.createFromAsset(this.mAssetManager, str);
        this.mCache.put(str, createFromAsset);
        return createFromAsset;
    }
}
