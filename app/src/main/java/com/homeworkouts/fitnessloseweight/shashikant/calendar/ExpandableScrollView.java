package com.homeworkouts.fitnessloseweight.shashikant.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ExpandableScrollView extends ScrollView {
    private boolean enableScrolling = true;

    public boolean isEnableScrolling() {
        return this.enableScrolling;
    }

    public void setEnableScrolling(boolean z) {
        this.enableScrolling = z;
    }

    public ExpandableScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ExpandableScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ExpandableScrollView(Context context) {
        super(context);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (isEnableScrolling()) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }
}
