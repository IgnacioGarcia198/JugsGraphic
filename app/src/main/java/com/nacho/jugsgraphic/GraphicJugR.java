package com.nacho.jugsgraphic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//import com.nacho.jugsgraphic.GlobalFunctions.*;

/**
 * Created by nacho on 7/26/15.
 */
public class GraphicJugR extends RelativeLayout {

    TextView agua, indicator;
    static int totalHeight;
    int content, capacity;// waterAmount, newTop;
    float fallValue;
    /*@Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //agua.setHeight(bottom - top - getPaddingBottom() - getPaddingTop());
        super.onLayout(changed, left, top, right, bottom);
        agua.setLeft(left + getPaddingLeft());
        agua.setTop(top + getPaddingTop());
        agua.setBottom(bottom - getPaddingBottom());
        agua.setRight(right + getPaddingRight());
        Toast.makeText(getContext(), "agua hecha: " + agua.getTop() + ", " + agua.getBottom(), Toast.LENGTH_SHORT).show();
    }*/
    public GraphicJugR(Context context, int capacity) {
        super(context);
		int pad = MyActivity.pdperc(0.7f);
        this.setPadding(pad, pad, pad, pad);
        //this.setLayoutParams(lp);
        //int bottomMargin = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics()));
        this.setBackgroundResource(R.drawable.empty_jug);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        agua = new TextView(context);
        agua.setBackgroundResource(R.drawable.jug_water);



        //agua.setVisibility(INVISIBLE);
        this.addView(agua, params);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //params.addRule(RelativeLayout.CENTER_IN_PARENT);

        indicator = GlobalFunctions.newText2Bold(content + "\n---\n" +  capacity, context, MyActivity.pdperc(1f));
        indicator.setGravity(Gravity.CENTER);
        indicator.setTextColor(Color.RED);
        this.addView(indicator, params2);

        this.content = 0;
        this.capacity = capacity;
        agua.getLayoutParams().height = 0;


    }


    public ObjectAnimator addWater(int litres) {
        content += litres;
        int newTop = 0;
        if(content == capacity) {
            newTop = agua.getBottom() - totalHeight;
            //agua.getLayoutParams().height = totalHeight;
        }
        else {
            //agua.getLayoutParams().height += Math.round(litres * (float) jugHeight / capacity);
            newTop = agua.getTop() - Math.round(litres * (float) totalHeight / capacity);
            //newTop = agua.getBottom() - Math.round(content * (float) totalHeight / capacity);
            //Toast.makeText(getContext(), "" + totalHeight, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), Math.round(litres * (float) totalHeight / capacity) + ", " + newTop + ", " + totalHeight + ", " + capacity + ", " + litres, Toast.LENGTH_SHORT).show();
        }

        ObjectAnimator animationAdd = ObjectAnimator.ofInt(agua, "top", newTop);
        animationAdd.setDuration(500);
        animationAdd.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                updateIndicator();
            }
        });
        return animationAdd;
    }

    public ObjectAnimator removeWater(int litres) {
        content -= litres;
        int newTop = 0;
        if(content == 0) {
            newTop = agua.getBottom();
            //agua.getLayoutParams().height = totalHeight;
        }
        else {
            //agua.getLayoutParams().height += Math.round(litres * (float) jugHeight / capacity);
            newTop = agua.getTop() + Math.round(litres * (float) totalHeight / capacity);
            //Toast.makeText(getContext(), "" + totalHeight, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), Math.round(litres * (float) totalHeight / capacity) + ", " + newTop + ", " + totalHeight + ", " + capacity + ", " + litres, Toast.LENGTH_SHORT).show();
        }

        ObjectAnimator animationRemove = ObjectAnimator.ofInt(agua, "top", newTop);
        animationRemove.setDuration(500);
        animationRemove.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                updateIndicator();
            }
        });
        return animationRemove;
    }

    private ObjectAnimator fill() {
        return addWater(capacity - content);
    }

    private ObjectAnimator empty() {
        return removeWater(content);
    }

    AnimatorSet triggerJugPour(final GraphicJugR to) {
        AnimatorSet trigger = new AnimatorSet();
        trigger.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                AnimatorSet pourAnimation = new AnimatorSet();
                int waterAmount = 0;
                int cabe = to.capacity - to.content;
                if(content > cabe) {
                    waterAmount = cabe;

                }
                else {
                    waterAmount = content;
                }
                pourAnimation.playSequentially(removeWater(waterAmount), to.addWater(waterAmount));
                pourAnimation.start();
            }
        });

        return trigger;
        //ObjectAnimator pourAnimation =
    }

    AnimatorSet triggerJugFill() {
        AnimatorSet trigger = new AnimatorSet();
        trigger.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                AnimatorSet fillAnimation = new AnimatorSet();
                fillAnimation.playSequentially(addWater(capacity - content));
                fillAnimation.start();
            }
        });

        return trigger;
        //ObjectAnimator pourAnimation =
    }

    AnimatorSet triggerJugEmpty() {
        AnimatorSet trigger = new AnimatorSet();
        trigger.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                AnimatorSet emptyAnimation = new AnimatorSet();
                emptyAnimation.playSequentially(removeWater(content));
                emptyAnimation.start();
            }
        });

        return trigger;
        //ObjectAnimator pourAnimation =
    }

    /*ObjectAnimator dropFall() {
        ObjectAnimator fall = ObjectAnimator.ofFloat(JugsLayoutR.drop, "y", fallValue);
        fall.setDuration(500);
        //Toast.makeText(getContext(), "fin gota: " + fallValue, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "fondo garrafa: " + (getBottom() - getPaddingBottom() - JugsLayoutR.drop.getHeight()), Toast.LENGTH_SHORT).show();

        return fall;
    }*/

    /*AnimatorSet triggerJugDropFall() {
        AnimatorSet trigger = new AnimatorSet();
        trigger.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fallValue = getBottom() - getPaddingBottom() - totalHeight*content/capacity - JugsLayoutR.drop.getHeight();

                AnimatorSet fallAnimation = new AnimatorSet();
                fallAnimation.playSequentially(dropFall());
                fallAnimation.start();
            }
        });

        return trigger;
        //ObjectAnimator pourAnimation =
    }*/



    boolean isFull() {
        return content == capacity;
    }

    boolean isEmpty() {
        return content == 0;
    }

    private void updateIndicator() {
        indicator.setText(content + "\n---\n" +  capacity);
    }

    void reset() {
        content = 0;
        agua.setTop(agua.getBottom());
        updateIndicator();
    }

}
