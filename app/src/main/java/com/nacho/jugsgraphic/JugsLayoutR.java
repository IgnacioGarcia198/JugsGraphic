package com.nacho.jugsgraphic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nacho.jugsgraphic.GarrafasEngine.GarrafasEngine;
import com.nacho.jugsgraphic.GarrafasEngine.GarrafasEngine2;
import com.nacho.jugsgraphic.GarrafasEngine.JugsEngine;
import com.nacho.jugsgraphic.GarrafasEngine.JugsNode;
import com.nacho.jugsgraphic.GarrafasEngine.NodoNumero;

import java.util.LinkedList;


/**
 * Created by nacho on 7/26/15.
 */
public class JugsLayoutR extends RelativeLayout {
    GraphicJugR[] jugTable;
    int njugs;
    static int jugWidth = MyActivity.pdperc(7), jugHeight = MyActivity.pdperc(9); // Width and height of the jugs.
    //static int cota = 30;
    int gap = Math.round(jugWidth * 0.6f);  // Gap between consecutive jugs drawn.
    int goal;  // The desired goal in litres
    int totalWidth;  // The total width of this view. We calculate it in order to allow it to be viewed properly.
    //float dropend;
    //Context context;
    TextView drop, tap;  // Graphic textview representing the drop and the tap
    JugsEngine jugsEngine;  // This is the class in which lies the algorithm. As "Jugsnode", it is a generalized abstract superclass we can implement.
    JugsNode solution;  // Variable to contain the solution of the algorithm.
    //static ProgressDialog pd;
    AnimatorSet chain;  // AnimatorSet to chain the steps of the problem solution.
    boolean calculated; // We check whether we have already calculated the problem so that we don't need to repeat the calculation.
    TextView txtDepth; // Reference to a Textview in the activity which contains this view
    Dialog progress; // The gear for when the algorithm is "thinking"


    /**
     * Constructor for this class. We draw all the jugs, as well as the tap and the drop. Also we create the JugsEngine object.
     * @param context The context in with we are creating this view
     * @param capacities A table with the capacities of the jugs. The nimber of jugs is inferred from  this table length.
     * @param goal The goal in litres the user has asked for in the interface
     */
    public JugsLayoutR(Context context, int[] capacities, int goal) {
        super(context);
        this.goal = goal;
        njugs = capacities.length;
        jugTable = new GraphicJugR[njugs];
        //garrafasEngine = new GarrafasEngine(goal, capacities);
        jugsEngine = new GarrafasEngine(goal, capacities);
        RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        //par.addRule(RelativeLayout.CENTER_IN_PARENT);
        par.addRule(RelativeLayout.CENTER_VERTICAL);
        par.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        par.height = jugHeight;
        par.width = jugWidth;
       // Toast.makeText(getContext(), "PRIMERO " + par.height, Toast.LENGTH_SHORT).show();
        //GraphicJugR.totalHeight = par.height+30;

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        p.height = Math.round(jugHeight * 0.8f);
        p.width = gap;

        drop = new TextView(getContext());
        drop.setBackgroundResource(R.drawable.gotadeagua1);
        this.addView(drop, p);
        drop.setAlpha(0);

        RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p2.addRule(RelativeLayout.CENTER_VERTICAL);
        p2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        p2.width = jugHeight;
        p2.height = jugHeight * 359/400;

        tap = new TextView(getContext());
        tap.setBackgroundResource(R.drawable.tapgood);
        this.addView(tap, p2);
        tap.setAlpha(0);
        //tap.setAlpha(0);
        //drop.setHeight(Math.round(jugHeight * 0.8f));
        //drop.setWidth(gap);
        //drop.bringToFront();
        //drop.setVisibility(View.INVISIBLE);

        //TextView sp = new TextView(context);
        //sp.setText("       ");

        //sp.setHeight(Math.round(jugHeight * 0.8f));
        //sp.setWidth(gap);
        //this.addView(sp, p);
        for(int i = 0; i < njugs; i ++) {
            jugTable[i] = new GraphicJugR(context, capacities[i]);
            jugTable[i].setX(gap*(i+1) + jugWidth*i);
            this.addView(jugTable[i], par);


            //jugTable[i].setId();
            //jugTable[i].x = jugTable[i].getLeft() +  jugTable[i].getWidth()/2;
            //jugTable[i].y = jugTable[i].getTop() +  jugTable[i].getWidth()/2;

            //par.setMargins(0, 0, 0, 0);
            //sp = new TextView(context);
            //sp.setText("       ");

            //sp.setHeight(Math.round(jugHeight * 0.8f));
            //sp.setWidth(gap);
            //this.addView(sp, p);
            //Toast.makeText(getContext(), "OMG " + jugTable[i].getLayoutParams().height, Toast.LENGTH_SHORT).show();
        }
        totalWidth = Math.round(jugTable[njugs-1].getX() + jugWidth + gap);
        this.setMinimumWidth(totalWidth);

    }

    /**
     * Sets the height of the water drawable corresponding to a 100% full jug.
     */
    public void setTotalWaterHeight() {
        GraphicJugR.totalHeight = jugTable[0].indicator.getHeight();
        //Toast.makeText(getContext(), "agua height " + to.agua.getHeight() + ": " + to.agua.getTop() + ", " + to.agua.getBottom(), Toast.LENGTH_SHORT).show();
    }


    /**
     * Creates the animation of pouring water from one jug to another
     * @param nfrom Origin jug
     * @param nto Destiny jug
     * @return An AnimatorSet object with the corresponding animation
     */
    public AnimatorSet pour(int nfrom, int nto) {

        final GraphicJugR from = jugTable[nfrom];
        final GraphicJugR to = jugTable[nto];
        /*if(from.isEmpty() || to.isFull()) {
            return;
        }*/
        //Toast.makeText(getContext(), "agua height " + to.agua.getHeight() + ": " + to.agua.getTop() + ", " + to.agua.getBottom(), Toast.LENGTH_SHORT).show();
        float y = from.getY();
        float x = from.getX();
        Float value;

        AnimatorSet animatorSet = new AnimatorSet();
        // lift the jug from
        //ObjectAnimator animation14 = from.addWater(5);
        //animation14.setDuration(200);
        final float newY = y - 1.6f * jugHeight;
        //from.agua.setTop(from.agua.getBottom() - 60);
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(from, "y", newY);
        animation1.setDuration(1000);
        // move from the lift from to the lift to
        float newX = 0;
        float newAngle = 0;
        if(nfrom < nto) {
            newX = to.getX() - jugHeight/2;
            newAngle = 90;

        }
        else {
            newX = to.getX() + jugHeight/2;
            newAngle = -90;

        }
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(from, "x", newX);
        animation2.setDuration(1000);
        // rotate the jug from


        ObjectAnimator animation3 = ObjectAnimator.ofFloat(from, "rotation", newAngle);
        animation3.setDuration(1000);


        // HERE WE MUST PUT THE ANIMATION OF THE WATER DROP FALLING
        //drop.setX(to.getX());
        //drop.setX(to.getX() + jugWidth / 2 - drop.getWidth() / 2);
        //drop.setY(newY + (jugHeight + jugWidth) / 2);
        //drop.setAlpha(0);
        //drop.setVisibility(View.VISIBLE);

//        drop.animate().x(newX);
        //drop.setTranslationX(to.getX() + to.getTranslationX());
        //int[] res = new int[2];
        //to.getLocationInWindow(res);
        //ObjectAnimator animation7 = ObjectAnimator.ofFloat(drop, "x", this.getWidth()/2);
        //animation7.setDuration(200);
        //ObjectAnimator animation8 = ObjectAnimator.ofFloat(drop, "y", newY+jugHeight);
        //animation8.setDuration(200);
        ObjectAnimator animation9 = ObjectAnimator.ofFloat(drop, "alpha", 1);
        animation9.setDuration(100);

//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) to.agua.getLayoutParams();
//        lp.height = 60;
//        to.agua.setLayoutParams(lp);
        //to.agua.setHeight(60);
    // SUPER CAGADA CON LA GOTA QUE PASA EL NIVEL DEL AGUA CUANDO NO ES 0...
        AnimatorSet dropFallTrigger = new AnimatorSet();
        dropFallTrigger.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator animation10 = ObjectAnimator.ofFloat(drop, "y", to.getY() + to.getHeight() - from.getPaddingBottom() - drop.getHeight());
                animation10.setDuration(500);
                //animation10.start();
                AnimatorSet dropFall = new AnimatorSet();
                dropFall.playSequentially(animation10);
                dropFall.start();
            }
        });

        ObjectAnimator animation10 = ObjectAnimator.ofFloat(drop, "y", to.getY() + to.getHeight() - to.getPaddingBottom() - drop.getHeight() /* to.agua.getLayoutParams().height*/ );
        animation10.setDuration(500);

        //ObjectAnimator dropFall = to.dropFall();
        //AnimatorSet dropFall = to.triggerJugDropFall();

        ObjectAnimator animation11 = ObjectAnimator.ofFloat(drop, "alpha", 0);
        animation11.setDuration(100);

        //ObjectAnimator animation12 = ObjectAnimator.ofInt(to.agua, "bottom", to.agua.getBottom() - 30 );
//        //ObjectAnimator animation12 = ObjectAnimator.ofInt(to.agua, "height", to.agua.getLayoutParams().height + 30);
        //animation12.setDuration(500);
        int waterAmount = 0;
        int cabe = to.capacity - to.content;
        if(from.content > cabe) {
            waterAmount = cabe;

        }
        else {
            waterAmount = from.content;
        }
        //ObjectAnimator animation12 = to.addWater(waterAmount);
        //ObjectAnimator animation13 = from.removeWater(waterAmount);
        AnimatorSet levelsAni = from.triggerJugPour(to);


        ObjectAnimator animation4 = ObjectAnimator.ofFloat(from, "rotation", 0);
        animation4.setDuration(1000);


        ObjectAnimator animation5 = ObjectAnimator.ofFloat(from, "x", x );
        animation5.setDuration(1000);


        ObjectAnimator animation6 = ObjectAnimator.ofFloat(from, "y", y );
        animation6.setDuration(1000);

        //to.agua.setHeight(60);


        animatorSet.playSequentially(animation1, animation2, animation3, animation9, animation10, animation11, levelsAni,/*animation13, animation12,*/ animation4, animation5, animation6);
        //animatorSet.playTogether(animation10, animation13);
        //animatorSet.play(animation10).with(animation13);
        //animatorSet.play(animation11).with(animation12);
        //animatorSet.play(animation7).with(animation3);
        //animatorSet.play(animation8).after(animation7);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

                drop.setX(to.getX() + jugWidth / 2 - drop.getWidth() / 2);
                drop.setY(newY + (jugHeight + jugWidth) / 2);
                //dropend = to.getY() + to.getHeight() - from.getPaddingBottom() - drop.getHeight();// - /*GraphicJugR.totalHeight * to.content/to.capacity;*/  to.agua.getLayoutParams().height;
                //to.fallValue = getBottom() - getPaddingBottom() - totalHeight*content/capacity - JugsLayoutR.drop.getHeight();
            }

        });
        //animatorSet.start
        //Toast.makeText(getContext(), "duration: " + animatorSet.getDuration(), Toast.LENGTH_SHORT).show();
        return animatorSet;
    }

    /**
     * Creates the animation of emptying a jug
     * @param njug The jug we want to empty
     * @return An AnimatorSet object with the corresponding animation
     */
    AnimatorSet empty(int njug) {
        final GraphicJugR from = jugTable[njug];
        float y = from.getY();
        float x = from.getX();

        AnimatorSet animatorSet = new AnimatorSet();

        // lift the jug from
        final float newY = y - 1.5f * jugHeight;
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(from, "y", newY);
        animation1.setDuration(1000);
        ObjectAnimator animation3 = ObjectAnimator.ofFloat(from, "rotation", -90);
        animation3.setDuration(1000);


        // HERE WE MUST PUT THE ANIMATION OF THE WATER DROP FALLING
        //drop.setPivotX(drop.getPivotX()+drop.getWidth()/2);
        //drop.setX(from.getX() - drop.getWidth() / 2 - (jugHeight - jugWidth) / 2);
        //drop.setY(newY + (jugHeight + jugWidth) / 2);



        ObjectAnimator animation9 = ObjectAnimator.ofFloat(drop, "alpha", 1);
        animation9.setDuration(200);

        ObjectAnimator animation10 = ObjectAnimator.ofFloat(drop, "y", from.getY() + from.getHeight() - drop.getHeight());
        animation10.setDuration(500);

        //ObjectAnimator animation12 = from.empty();
        AnimatorSet levelsAni = from.triggerJugEmpty();

        ObjectAnimator animation11 = ObjectAnimator.ofFloat(drop, "alpha", 0);
        animation11.setDuration(200);

        ObjectAnimator animation4 = ObjectAnimator.ofFloat(from, "rotation", 0);
        animation4.setDuration(1000);

        ObjectAnimator animation6 = ObjectAnimator.ofFloat(from, "y", y );
        animation6.setDuration(1000);

        animatorSet.playSequentially(animation1, animation3, animation9, animation10, animation11, /*animation12,*/levelsAni, animation4, animation6);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

                drop.setX(from.getX() - drop.getWidth() / 2 - (jugHeight - jugWidth) / 2);
                drop.setY(newY + (jugHeight + jugWidth) / 2);
            }

        });
        //animatorSet.start();
        //from.content = 0;

        //jugTable[njug].empty();
        return animatorSet;
    }

    /**
     * Creates the animation of filling a jug up to the brim
     * @param nto The jug we want to fill
     * @return An AnimatorSet object with the corresponding animation.
     */
    AnimatorSet fill(int nto) {
        // GRIFO!!!
        final GraphicJugR to = jugTable[nto];

        AnimatorSet animatorSet = new AnimatorSet();
        // OBLIGADISIMOOOOOOO PONER COORDENADAS CON ANIMACION!!!!!!!!!!!!!!!!!!!! Q SI NO NO CAMBIAN EN PASOS SUCESIVOS...
        // THEN, SEARCH WHY THERE IS BEING ISSUES WITH TAP APPEARANCE AND VANISH...
        /*tap.setX(to.getX() + jugWidth / 2 - jugHeight / 8);
        tap.setY(to.getY() - 1.5f * jugHeight);

        drop.setX(to.getX() + jugWidth / 2 - drop.getWidth() / 2);
        drop.setY(tap.getY() + (jugHeight + jugWidth) / 2);
        ObjectAnimator locateTapX = ObjectAnimator.ofFloat(tap, "x", to.getX() + jugWidth / 2 - jugHeight / 8);
        locateTapX.setDuration(50);

        ObjectAnimator locateTapY = ObjectAnimator.ofFloat(tap, "y", tap.getY() - 1.5f * jugHeight);
        locateTapY.setDuration(50);

        ObjectAnimator locateDropX = ObjectAnimator.ofFloat(drop, "x", to.getX() + jugWidth / 2 - drop.getWidth() / 2);
        locateDropX.setDuration(50);

        ObjectAnimator locateDropY = ObjectAnimator.ofFloat(drop, "y", tap.getY() + (jugHeight + jugWidth) / 2);
        locateDropY.setDuration(50);*/

        ObjectAnimator anitap1 = ObjectAnimator.ofFloat(tap, "alpha", 1);
        anitap1.setDuration(500);

        ObjectAnimator anidrop1 = ObjectAnimator.ofFloat(drop, "alpha", 1);
        anidrop1.setDuration(100);

        ObjectAnimator animation10 = ObjectAnimator.ofFloat(drop, "y", to.getY() + to.getHeight() - drop.getHeight() - to.agua.getLayoutParams().height - to.getPaddingBottom());
        animation10.setDuration(500);

        ObjectAnimator animation11 = ObjectAnimator.ofFloat(drop, "alpha", 0);
        animation11.setDuration(100);

        //ObjectAnimator animation12 = to.fill();
        AnimatorSet levelsAni = to.triggerJugFill();

        ObjectAnimator anitap2 = ObjectAnimator.ofFloat(tap, "alpha", 0);
        anitap2.setDuration(500);

        animatorSet.playSequentially(anitap1, anidrop1, animation10, animation11, /*animation12,*/levelsAni, anitap2);
        animatorSet.addListener(new MyAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                tap.setX(to.getX() + jugWidth / 2 - jugHeight / 4);
                tap.setY(to.getY() - 1.5f * jugHeight);
                //tap.setAlpha(1);

                drop.setX(to.getX() + jugWidth / 2 - drop.getWidth() / 2);
                drop.setY(tap.getY() + (jugHeight + jugWidth) / 2);
            }

        });
        return animatorSet;
        //animatorSet.start();
    }

    /**
     * Chains a collection of animators in the AnimatorSet object "chain", which is a field of JugsLayoutR.
     * @param animators A collection of Animator objects
     */
    public void jugChain(final Animator... animators) {

        /*for(int i = 0; i < animators.length - 1; i ++) {
            Animator animator = animators[i];
            final Animator next = animators[i+1];
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    next.start();
                }
            });
        }
        animators[0].start();*/
        AnimatorSet chain = new AnimatorSet();
        chain.playSequentially(animators);
        chain.start();
    }

    /**
     * Test an animation chain with some steps
     */
    public void chainTest() {
        jugChain(fill(1), fill(3), pour(1, 2), pour(3, 4), fill(3), pour(4, 2), empty(4));

    }

    /**
     * Calculates the algorithm output for the given problem and shows the solution as an animation. If the result is already calculated, it only runs the animation again.
     */
    public void execute() {
        if((chain == null) || (!chain.isStarted())) {
            if (!calculated) {
                new pepeTask().execute();
            } else {
                if (solution != null) {
                    reset();
                    Toast.makeText(getContext(), "resetted", Toast.LENGTH_SHORT).show();
                    chain.start();
                } else {
                    Toast.makeText(getContext(), "OMG I already told you... :(", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * AsyncTask to launch the dialog of waiting or processing (a gear rotating). We need AsyncTask because the part of code we run in another thread can never affect views of the activities.
     */
    private class pepeTask extends AsyncTask<Void, Void, Boolean> {

        /**
         * Things done befor the thread
         */
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //showGear();
//            progress = ProgressDialog.show(getContext(), "dialog title",
//                    "dialog message", true);


            //MediaPlayer player = MediaPlayer.create(getContext(),R.raw.letsgo);
//
                    //player.start();
            progress = new MyDialog(getContext());
            progress.show();


            //processImage.setVisibility(View.VISIBLE);
            //gearAnimation.start();

        }

        /**
         * Things done in the thread
         * @param params Dummy params, we don't need any
         * @return Dummy boolean result to fullfill the format of the function.
         */
        protected Boolean doInBackground(Void... params) {
            MediaPlayer player = MediaPlayer.create(getContext(),R.raw.letsgo);

            player.start();
            solution = jugsEngine.search();
            if (solution == null) {
                return false;
				
            }

            calculated = true;
             /*new Runnable() {
                @Override
                public void run() {

                    gearAnimation.start();
                }
            }.run();*/
            //processImage.setVisibility(View.VISIBLE);
            return true;
        }

        /**
         * Thing done after the thread
         * @param result Dummy parameter for the function pattern, we don't need any.
         */
        protected void onPostExecute(Boolean result) {
 //           Activity activity = (Activity) getContext();
//
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                progress.dismiss();
//                            }
//                        });
            progress.dismiss();
            Toast.makeText(getContext(), "calculated: " + calculated, Toast.LENGTH_SHORT).show();

            if (solution != null) {
                //Toast.makeText(getContext(), "we have solution!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),solution.nodoTexto(), Toast.LENGTH_SHORT).show();
                compileRoute2(solution.getRoute());
            } else {
                Toast.makeText(getContext(), "OMG there is no solution :(", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     *  A dialog with transparent background showing the gear rotating when the algorithm is thinking
     */
    class MyDialog extends Dialog {

        public MyDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            setContentView(R.layout.my_progress_dialog);
            ImageView gear = (ImageView) findViewById(R.id.animation);
            gear.setImageResource(R.drawable.gear_animation);

        }

        @Override
        public void show() {
            super.show();

        }
    }

    /**
     * Transforms the text output of the algorithm to the animators chain and shows the animation in the screen
     * @param route The text route in the solution node (if the problem had solution)
     */
    void compileRoute2(String route) {
        //AnimatorSet chain = new AnimatorSet();
        //pd.dismiss();
        String[] rouTable = route.split(" ");
        txtDepth.setText("Depth: " + (rouTable.length - 1));
        //Toast.makeText(getContext(), "Solution depth: " + (rouTable.length-1), Toast.LENGTH_SHORT).show();
        LinkedList<Animator> animatorList = new LinkedList<Animator>();
        //Animator[] animatorList = new AnimatorSet[rouTable.length];
        for (int i = 0; i < rouTable.length; i++) {
            String str = rouTable[i];
            if (str.matches(">\\d+")) {
                // fill
                animatorList.add(fill(Integer.parseInt(str.substring(1))));
            } else if (str.matches("<\\d+")) {
                // empty
                animatorList.add(empty(Integer.parseInt(str.substring(1))));

            } else if (str.matches("\\d+>\\d+")) {
                // pour
                String[] ts = str.split(">");
                animatorList.add(pour(Integer.parseInt(ts[0]), Integer.parseInt(ts[1])));
            }
        }
        GraphicJugR goalJug = jugTable[solution.pruebaMeta()];
        ObjectAnimator blink = ObjectAnimator.ofFloat(goalJug, "alpha", 0);
        blink.setDuration(500);
        ObjectAnimator blink2 = ObjectAnimator.ofFloat(goalJug, "alpha", 1);
        blink2.setDuration(500);
        ObjectAnimator blink3 = ObjectAnimator.ofFloat(goalJug, "alpha", 0);
        blink3.setDuration(500);
        ObjectAnimator blink4 = ObjectAnimator.ofFloat(goalJug, "alpha", 1);
        blink4.setDuration(500);
        ObjectAnimator blink5 = ObjectAnimator.ofFloat(goalJug, "alpha", 0);
        blink5.setDuration(500);
        ObjectAnimator blink6 = ObjectAnimator.ofFloat(goalJug, "alpha", 1);
        blink6.setDuration(500);
        //blink2.setRepeatCount(3);
        AnimatorSet blinkAnim = new AnimatorSet();
        blinkAnim.playSequentially(blink, blink2, blink3, blink4, blink5, blink6);
        blinkAnim.setStartDelay(500);

        animatorList.add(blinkAnim);

        chain = new AnimatorSet();
        chain.playSequentially(animatorList);
        /*chain.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                GraphicJugR goalJug = searchGoalJug();
                Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.blink);
                goalJug.startAnimation(animation1);
            }
        });*/


        chain.start();
    }

    /**
     * Used when the user wants to repeat the animation. Resets all the jugs to 0 litres.
     */
    void reset() {
        for(int i = 0; i < njugs; i ++) {
            jugTable[i].reset();

        }
    }

    /**
     * Runs the animation from the beginning again.
     */
    void restartAnimation() {
        if(chain != null) {
            if(chain.isStarted()) {
                chain.end();
            }
            reset();
            chain.start();
        }
    }

    /**
     * Stops the animation in the next state. It does not terminate all the steps.
     */
    void endAnimation() {
        if((chain != null) && (chain.isStarted())) {
            chain.end();
        }
    }

    /**
     * Cancels quickly the animation for further purposes.
     */
    void cancelAnimation() {
        if((chain != null) && (chain.isStarted())) {
            chain.cancel();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setTotalWaterHeight();
		
		//execute();
    }
}
