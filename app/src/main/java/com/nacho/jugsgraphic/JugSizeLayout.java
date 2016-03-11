package com.nacho.jugsgraphic;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by nacho on 8/7/15.
 */
public class JugSizeLayout extends RelativeLayout {

    static int MIN_JUG, MAX_JUG, maxRelJug;
    static final int h = MyActivity.pdperc(9f), w = MyActivity.pdperc(7f);
    int totalH;
    byte slowFlag;
    int cap;
    static final byte maxFlag = 5;
    
    TextView jug, indicator;
    static int totalHeight;
    float fallValue;
    static JugSelection2 parentLayout;

    public JugSizeLayout(Context context) {
        super(context);
        //this.setPadding(10, 10, 10, 10);
        //MIN_JUG = min;
        //MAX_JUG = max;
        //maxRelJug = MAX_JUG;

        //maxRelJug = max; // IN THIS CONSTRUCTOR WE COULD ALREADY USE THE RELATIVE HEIGHT AS AN EXTRA PARAMETER FOR CALCULATING THE LEVEL OF THE WATER...
        //this.setLayoutParams(lp);
        //int bottomMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics()));
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        //p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        p.height = h;
        p.width = w;
        setLayoutParams(p);

        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //params.height = ltoH(MAX_JUG);
        jug = new TextView(context);
        this.addView(jug, params);
        //jug.setBackgroundColor(Color.BLUE);
        jug.setBackgroundResource(R.drawable.empty_jug);


        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        indicator = GlobalFunctions.newText2Bold("" + MAX_JUG, context, MyActivity.pdperc(1.2f));
        cap = MAX_JUG;
        indicator.setGravity(Gravity.CENTER);
        indicator.setTextColor(Color.RED);
        setOnTouchListener(new MyMotionTracker());
        this.addView(indicator, params2);
		adjustCapacityNoAnim();
        //indicator.setBackgroundResource(R.drawable.empty_jug);

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
                    totalH = indicator.getLayoutParams().height;
                    mVelocityTracker.addMovement(event);
                    // When you want to determine the velocity, call
                    // computeCurrentVelocity(). Then call getXVelocity()
                    // and getYVelocity() to retrieve the velocity for each pointer ID.
                    mVelocityTracker.computeCurrentVelocity(1000);
                    // Log velocity of pixels per second
                    // Best practice to use VelocityTrackerCompat where possible.
                    float yvel = VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId);
                    //slider.setText("x velocity: " + xvel);

                    int c = cap;
                    if((yvel < 0) && (c < MAX_JUG)) {
                        int distance = -Math.round(yvel * MAX_JUG / h / 50);
                        //jug.setText("" + distance);
                        if(distance < 10) {
                            if(slowFlag == maxFlag) {
                                c ++;
                                jug.getLayoutParams().height ++;
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
                        if(c < MAX_JUG) {
                            cap = c;
                            indicator.setText("" + c);
                            jug.setTop(jug.getBottom() - ltoH(c));
                        }
                        else {
                            cap = MAX_JUG;
                            indicator.setText("" + MAX_JUG);
                            jug.setTop(jug.getBottom() - ltoH(MAX_JUG));
                        }
                        //parentLayout.adjustCapacities();
                        // call the adjustment here!!
                        //jug.setTop(jug.getBottom() - ltoH(c));

                    }
                    else if((yvel > 0) && (c > MIN_JUG)) {
                        int distance = -Math.round(yvel * MAX_JUG / h / 50);
                        //jug.setText("" + distance);
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

                        if(c > MIN_JUG) {
                            cap = c;
                            indicator.setText("" + c);
                            jug.setTop(jug.getBottom() - ltoH(c));
                        }

                        else {
                            cap = MIN_JUG;
                            indicator.setText("" + MIN_JUG);
                            jug.setTop(jug.getBottom() - ltoH(MIN_JUG));
                        }
                        //parentLayout.adjustCapacities();
                        //jug.setTop(jug.getBottom() - ltoH(c));
                    }

                    break;

                case MotionEvent.ACTION_UP:
                    parentLayout.adjustCapacities();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    // Return a VelocityTracker object back to be re-used by others.
                    //mVelocityTracker.r;
                    //mVelocityTracker.clear();
                    //break;
            }
            return true;
        }
    }

    /**
     * Converts liters to height of the jug.
     * @param capacity liters
     * @return the equivalent heigh of the jug with that volume.
     */
    int ltoH(int capacity) {
        return Math.round((float)capacity*h/maxRelJug);
        //(indicator.getBottom() - indicator.getTop())
    }

    public void randomizeCapacity() {
        Random r = new Random();
        cap = r.nextInt(MAX_JUG+1 - MIN_JUG) + MIN_JUG;
        indicator.setText("" + cap);
        jug.setTop(jug.getBottom() - ltoH(cap));
    }

    void adjustCapacity() {
        int newTop = jug.getBottom() - ltoH(cap);
        if(jug.getTop() != newTop) {
            ObjectAnimator animation = ObjectAnimator.ofInt(jug, "top", newTop);
            animation.setDuration(500);
            animation.start();
        }
        //jug.setTop(jug.getBottom() - ltoH(cap));
    }
    
	void adjustCapacityNoAnim() {
        int newTop = jug.getBottom() - ltoH(cap);
		jug.setTop(newTop);
        
        //jug.setTop(jug.getBottom() - ltoH(cap));
    }
}
