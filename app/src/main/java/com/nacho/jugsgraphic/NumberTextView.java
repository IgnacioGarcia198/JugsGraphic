package com.nacho.jugsgraphic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.VelocityTrackerCompat;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by nacho on 8/6/15.
 */
public class NumberTextView extends TextView {
    //TextView textView;
    int MIN_JUG, MAX_JUG;
    static final int h = MyActivity.pdperc(5f), w = MyActivity.pdperc(10f);
    byte slowFlag;
    static final byte maxFlag = 5;
    public NumberTextView(Context context, int min, int max) {

        super(context);
        //this.setOrientation(LinearLayout.VERTICAL);
        MIN_JUG = min;
        MAX_JUG = max;

        //LinearLayout.LayoutParams par = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //par.gravity = Gravity.TOP;

        //textView = GlobalFunctions.newEdit1(context, fArray, MyActivity.pxpercd(0.5f));

        //GlobalFunctions.limittextView(textView, context, MIN_JUG, MAX_JUG);
        setTextSize(MyActivity.pdperc(1.2f));
        setTypeface(null, Typeface.BOLD);
        setGravity(Gravity.CENTER);
        setTextColor(Color.RED);

        setText("" + MIN_JUG);

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        //p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //p.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        p.height = h;
        p.width = w;
        setLayoutParams(p);

        //this.addView(textView);

        /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        params.height = h;
        params.width = w;
        slider = new TextView(context);*/
        setBackgroundResource(R.drawable.broadarrow);
        setOnTouchListener(new MyMotionTracker());

        //slider.setHeight(MyActivity.pdperc(7.5f));
        //slider.setWidth(MyActivity.pdperc(13f));
        //this.addView(slider, params);

    }

    class MyMotionTracker implements View.OnTouchListener {
        private VelocityTracker mVelocityTracker = null;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int index = event.getActionIndex();
            int action = event.getActionMasked();
            int pointerId = event.getPointerId(index);

            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    if(mVelocityTracker == null) {
                        // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                        mVelocityTracker = VelocityTracker.obtain();
                    }
                    else {
                        // Reset the velocity tracker back to its initial state.
                        mVelocityTracker.clear();
                    }
                    // Add a user's movement to the tracker.
                    mVelocityTracker.addMovement(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mVelocityTracker.addMovement(event);
                    // When you want to determine the velocity, call
                    // computeCurrentVelocity(). Then call getXVelocity()
                    // and getYVelocity() to retrieve the velocity for each pointer ID.
                    mVelocityTracker.computeCurrentVelocity(1000);
                    // Log velocity of pixels per second
                    // Best practice to use VelocityTrackerCompat where possible.
                    float xvel = VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId);
                    //slider.setText("x velocity: " + xvel);

                    int c = Integer.parseInt(getText().toString());
                    if((xvel > 0) && (c < MAX_JUG)) {
                        int distance = Math.round(xvel * MAX_JUG / getLayoutParams().width / 50);
                        //slider.setText("" + distance);
                        if(distance < 10) {
                            if(slowFlag == maxFlag) {
                                c ++;
                                slowFlag = 0;
                            }
                            else {
                                slowFlag ++;
                            }
                        }
                        else {
                            if(slowFlag == maxFlag) {
                                c += distance;
                                slowFlag = 0;
                            }
                            else {
                                slowFlag ++;
                            }
                        }
                        //c += distance;


                        //slowFlag = (slowFlag == 3? 0 : slowFlag + 1);
                        if(c <= MAX_JUG) {
                            setText("" + c);
                        }
                        else {
                            setText("" + MAX_JUG);
                        }
                    }
                    else if((xvel < 0) && (c > MIN_JUG)) {
                        int distance = Math.round(xvel * MAX_JUG / getLayoutParams().width / 50);
                        //slider.setText("" + distance);
                        if(distance > -10) {
                            if(slowFlag == maxFlag) {
                                c --;
                                slowFlag = 0;
                            }
                            else {
                                slowFlag ++;
                            }
                        }
                        else {
                            if(slowFlag == maxFlag) {
                                c += distance;
                                slowFlag = 0;
                            }
                            else {
                                slowFlag ++;
                            }
                        }

                        if(c >= MIN_JUG) {
                            setText("" + c);
                        }

                        else {
                            setText("" + MIN_JUG);
                        }
                    }
                    /*int sign = xvel < 0 ? -1: 1;

                    int distance = Math.round(xvel * MAX_JUG / slider.getLayoutParams().width/ 1000);
                    c += distance;

                    if(c > MAX_JUG) {
                        textView.setText("" + MAX_JUG);
                    }
                    else if(c < MIN_JUG) {
                        textView.setText("" + MIN_JUG);
                    }
                    else {
                        textView.setText("" + c);
                    }*/
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Return a VelocityTracker object back to be re-used by others.
                    //mVelocityTracker.r;
                    //mVelocityTracker.clear();
                    //break;
            }
            return true;
        }
    }

    public void setMIN_JUG(int min) {
        MIN_JUG = min;
        try {
            int c = Integer.parseInt(getText().toString());
            if(c < MIN_JUG) {
                setText("" + MIN_JUG);
            }
        }
        catch(NumberFormatException nfe) {
            setText("" + MIN_JUG);
        }
    }

    public void setMAX_JUG(int max) {
        MAX_JUG = max;
        try {
            int c = Integer.parseInt(getText().toString());
            if (c > MAX_JUG) {
                setText("" + MAX_JUG);
            }
        }
        catch(NumberFormatException nfe) {
            setText("" + MAX_JUG);
        }
    }

    public int getMAX_JUG() {
        return MAX_JUG;
    }

    public int getMIN_JUG() {
        return MIN_JUG;
    }

    public void randomizeValue() {
        Random r = new Random();
        setText("" + (r.nextInt(MAX_JUG + 1 - MIN_JUG) + MIN_JUG));
    }

    
    
}
