package com.nacho.jugsgraphic;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by nacho on 8/7/15.
 */
public class JugSelection2 extends RelativeLayout {

    JugSizeLayout[] jugTable;
    int njugs;
    static int jugWidth = JugSizeLayout.w;
    int gap = Math.round(jugWidth * 0.6f);
    int totalWidth;
    int maxRelativeJug;
    float dropend;
    //Context context;
    static NumberTextView goalText;

    public JugSelection2(Context context, int njugs, int min, int max) {
        super(context);

        this.njugs = njugs;
        JugSizeLayout.MIN_JUG = min;
        JugSizeLayout.MAX_JUG = max;
        JugSizeLayout.maxRelJug = max;

        jugTable = new JugSizeLayout[njugs];
        RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //par.addRule(RelativeLayout.CENTER_IN_PARENT);
        par.addRule(RelativeLayout.CENTER_VERTICAL);
        par.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        par.height = JugSizeLayout.h;
        par.width = jugWidth;


        for (int i = 0; i < njugs; i++) {
            jugTable[i] = new JugSizeLayout(context);
            jugTable[i].setX(gap * (i + 1) + jugWidth * i);
            this.addView(jugTable[i], par);

        }
        totalWidth = Math.round(jugTable[njugs - 1].getX() + jugWidth + gap);
        this.setMinimumWidth(totalWidth);
        JugSizeLayout.parentLayout = this;
        goalText.setMAX_JUG(max);
        goalText.setText("" +  max);
		
    }

    public void randomizeCapacities() {
        JugSizeLayout.maxRelJug = JugSizeLayout.MAX_JUG;
        for (int i = 0; i < njugs; i++) {
            jugTable[i].randomizeCapacity();

        }
        adjustCapacities();
    }

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		// TODO: Implement this method
		super.onLayout(changed, l, t, r, b);
		adjustCapacities();
	}
	
	

    void adjustCapacities() {
        JugSizeLayout.maxRelJug = maxCapacity();

        for (int i = 0; i < njugs; i++) {
            jugTable[i].adjustCapacity();
        }
        goalText.setMAX_JUG(JugSizeLayout.maxRelJug);

    }
	
	void adjustCapacitiesNoAnim() {
        JugSizeLayout.maxRelJug = maxCapacity();

        for (int i = 0; i < njugs; i++) {
            jugTable[i].adjustCapacityNoAnim();
        }
        goalText.setMAX_JUG(JugSizeLayout.maxRelJug);

    }
	

    int maxCapacity() {
        int max = 0;
        for (int i = 0; i < njugs; i++) {
            if(jugTable[i].cap >  max) {
                max = jugTable[i].cap;
            }
        }
        return  max;
    }

    public int[] getCapArray() {
        int[] array = new int[njugs];
        for (int i = 0; i < njugs; i++) {
            array[i] = jugTable[i].cap;
        }
        return array;
    }



}
