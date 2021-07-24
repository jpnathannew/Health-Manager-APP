package com.homeworkouts.fitnessloseweight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.net.Uri;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class GifImageView extends View {
    private Context mContext;
    private int mHeight;
    private InputStream mInputStream;
    private Movie mMovie;
    private long mStart;
    private int mWidth;

    public GifImageView(Context context) {
        super(context);
        this.mContext = context;
    }

    public GifImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GifImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        if (attributeSet.getAttributeName(1).equals("background")) {
            setGifImageResource(Integer.parseInt(attributeSet.getAttributeValue(1).substring(1)));
        }
    }

    private void init() {
        setFocusable(true);
        this.mMovie = Movie.decodeStream(this.mInputStream);
        this.mWidth = this.mMovie.width();
        this.mHeight = this.mMovie.height();
        requestLayout();
    }


    public void onMeasure(int i, int i2) {
        setMeasuredDimension(this.mWidth, this.mHeight);
    }


    public void onDraw(Canvas canvas) {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (this.mStart == 0) {
            this.mStart = uptimeMillis;
        }
        Movie movie = this.mMovie;
        if (movie != null) {
            int duration = movie.duration();
            if (duration == 0) {
                duration = 1000;
            }
            this.mMovie.setTime((int) ((uptimeMillis - this.mStart) % ((long) duration)));
            this.mMovie.draw(canvas, 0.0f, 0.0f);
            invalidate();
        }
    }

    public void setGifImageResource(int i) {
        this.mInputStream = this.mContext.getResources().openRawResource(i);
        init();
    }

    public void setGifImageUri(Uri uri) {
        try {
            this.mInputStream = this.mContext.getContentResolver().openInputStream(uri);
            init();
        } catch (FileNotFoundException unused) {
            Log.e("GIfImageView", "File not found");
        }
    }
}
