package com.homeworkouts.fitnessloseweight.listners;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener;

public class RecyclerItemClickListener implements OnItemTouchListener {


    public GestureDetector f1496a;
    public onItemClickListener mListener;

    public interface onItemClickListener {
        void OnItem(View view, int i);
    }

    public RecyclerItemClickListener(Context context, onItemClickListener onitemclicklistener) {
        this.mListener = onitemclicklistener;
        this.f1496a = new GestureDetector(context, new SimpleOnGestureListener() {
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View findChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (!(findChildViewUnder == null || this.mListener == null || !this.f1496a.onTouchEvent(motionEvent))) {
            this.mListener.OnItem(findChildViewUnder, recyclerView.getChildAdapterPosition(findChildViewUnder));
        }
        return false;
    }

    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }
}
