package com.nacho.jugsgraphic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import com.nacho.jugsgraphic.GarrafasEngine.GarrafasEngine;

import java.util.Random;
import android.animation.*;
import android.view.animation.*;
import android.content.res.*;
import java.io.*;


public class GameMode extends MyActivity {

//    private float screenWidth, screenHeight, dens;
//    int pad, buttonsHeight, buttonsWidth;
//    float size, smallSize;
    NumberTextView jugNumberPicker, goalPicker;
    RelativeLayout modeLay;
    LinearLayout mainLayout;
    //JugSelectionView centerJugs;
    JugSelection2 centerJugs2;
    //JugSelection3 centerJugs3;
    Spinner modeSP;
    EditText test;
    EditText editText;
    //NumberEditText numberEditText;
    //int vers;
    static final int MAX_JUG = 100, MIN_JUG = 1, MIN_NJUGS = 2, MAX_NJUGS = 5;
    int njugs;
    TextView space1, space2, rule1, rule2;
	static MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_mode_selection);
        mainLayout = (LinearLayout) findViewById(R.id.mainlay);
        //mainLayout.setBackgroundResource(R.drawable.spiral);


        //size = pxperc(3.4f);
        //smallSize = pxperc(2.7f);
        size = pxpercRatio(2.1f);
        smallSize = pxpercRatio(1.6f);

        int cota = GlobalFunctions.pullIntWithDefault("depth_limit", this, GarrafasEngine.getCota());
        GarrafasEngine.setCota(cota);
        //size = pxPercComp2(2f, 1.5f);
        //smallSize = pxPercComp2(1.7f, 0.3f);

// dens = getResources().getDisplayMetrics().density;
//        screenWidth = getResources().getDisplayMetrics().widthPixels / dens;
//        screenHeight = getResources().getDisplayMetrics().heightPixels / dens;
        //screenWidth = getResources().getDisplayMetrics().widthPixels;
        //screenHeight = getResources().getDisplayMetrics().heightPixels;
        //size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, metrics); //
        //size = percwf(3.8f, true);
        /*if(vers < 14) {
            size = pxpercw(3.8f);
            smallSize = pxpercw(2.7f);
        }
        else {
            size = pxpercw(4.2f);
            smallSize = pxpercw(2.7f);
        }*/

        //size = pxpercRatio(2.1f);
        //smallSize = pxpercRatio(1.6f);
        //size = pxpercRatio(2.5f);
        //smallSize = pxpercRatio(1.7f);

        //smallSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8f, metrics); //
        //smallSize = percwf(2.6f, true);;
        //pad = perchi(5);
        mainLayout.setPadding(padw, padh, padw, padh);
        // HERE WE WILL DO SOMETHING TO ADAPT TEXT SIZE AND OTHER SIZES TO SCREEN WIDTH.
        // WO WE NEED A NUMBER AND ADAPT DINAMICALLY THE SCREEN OPERATING WITH THIS NUMBER, USING IT IN ONCREATE, TO SET THE TEXT SIZE BASICALLY, SINCE THE REST OF THE THINGS ARE "WRAP_CONTENT".
        //TokenEvent.context = this;
        //=======================================
        // LAYOUT VARIABLES
        //=======================================
        //modeLay = (RelativeLayout) findViewById(R.id.MAIN_SCREEN);
        //tableEvent = (TableLayout) findViewById(R.id.tablEv);

        //===================================================
        // MAIN LAYOUT ELEMENTS
        //=================================================

        modeSP = new Spinner(this);
        SpinnerAdapter adapter = new SpinnerAdapter(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.mode));

        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.mode, android.R.layout.simple_spinner_item);
        modeSP.setAdapter(adapter);
        //mainLayout.addView(modeSP);

        //===================================================
        // PICK THE NUMBER OF JUGS ELEMENTS
        //=================================================
     //animation
        LinearLayout.LayoutParams p5 = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        p5.gravity = Gravity.CENTER;
        mainLayout.addView(GlobalFunctions.newText2Bold("SIMULATION OPTIONS", this, MyActivity.pxpercd(0.8f)), p5);
        //==================================================================
        LinearLayout jugsNumberLay = new LinearLayout(this);
        jugsNumberLay.setOrientation(LinearLayout.HORIZONTAL);
        jugsNumberLay.addView(GlobalFunctions.newText2Bold("Number of jugs:    ", this, MyActivity.pxpercd(0.5f)));
        jugsNumberLay.setGravity(Gravity.CENTER);
        jugNumberPicker = new NumberTextView(this, MIN_NJUGS, MAX_NJUGS);
        jugsNumberLay.addView(jugNumberPicker);

        //jugsNumberLay.addView(GlobalFunctions.newText1("    ", this, MyActivity.pxpercd(0.5f)));
        RelativeLayout numberLayWrapper = new RelativeLayout(this);
        RelativeLayout.LayoutParams p8 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p8.addRule(RelativeLayout.CENTER_IN_PARENT);
        numberLayWrapper.addView(jugsNumberLay, p8);
        //==================================================================

        Button button1 = new Button(this);
        button1.setText("Random");
        button1.setTypeface(null, Typeface.BOLD);

        RelativeLayout.LayoutParams p7 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p7.addRule(RelativeLayout.ALIGN_PARENT_END);
        numberLayWrapper.addView(button1, p7);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        //params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.gravity = Gravity.CENTER;
        mainLayout.addView(numberLayWrapper, params);

        //===================================================
        // PICK JUGS CAPACITIES ELEMENTS
        //=================================================

        final LinearLayout capacityLayout = new LinearLayout(this);
        capacityLayout.setGravity(Gravity.CENTER);
        capacityLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout tittle = new RelativeLayout(this);
        RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p1.addRule(RelativeLayout.CENTER_IN_PARENT);

        tittle.addView(GlobalFunctions.newText2Bold("Select the capacities of the jugs", this, MyActivity.pxpercd(0.5f)), p1);

        RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        p2.addRule(RelativeLayout.CENTER_VERTICAL);

        Button button2 = new Button(this);
        button2.setText("Random");
        button2.setTypeface(null, Typeface.BOLD);
        tittle.addView(button2, p2);
        capacityLayout.addView(tittle);

        //===================================================================
        goalPicker = new NumberTextView(this, 1, MAX_JUG); // we put this here so that centerJugs does not detect null reference...
        JugSelection2.goalText = goalPicker;

        final RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        centerJugs2 = new JugSelection2(this, MIN_NJUGS, MIN_JUG, MAX_JUG);
        capacityLayout.addView(centerJugs2, p);
        //JugSizeLayout.parentLayout = centerJugs2;
        //=====================================================================
        //RelativeLayout.LayoutParams p3 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //p3.addRule(RelativeLayout.CENTER_IN_PARENT);
        LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        p3.gravity = Gravity.CENTER;
        p3.topMargin = 20;
        mainLayout.addView(capacityLayout, p3);

        //===================================================
        // BUTTON "START SIMULATION"
        //=================================================

        RelativeLayout bottomGoal = new RelativeLayout(this);
        LinearLayout goalAsk = new LinearLayout(this);
        goalAsk.setGravity(Gravity.CENTER);
        goalAsk.setOrientation(LinearLayout.HORIZONTAL);
        goalAsk.addView(GlobalFunctions.newText2Bold("Select the goal (liters):    ", this, MyActivity.pxpercd(0.5f)));

        goalAsk.addView(goalPicker);

        Button button3 = new Button(this);
        button3.setText("Random");
        button3.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams p11 = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        p11.leftMargin = 20;
        goalAsk.addView(button3, p11);
        //button3.getLayoutParams()

        RelativeLayout.LayoutParams p9 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p9.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        p9.addRule(RelativeLayout.CENTER_VERTICAL);
        bottomGoal.addView(goalAsk, p9);

        RelativeLayout.LayoutParams p6 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        p6.addRule(RelativeLayout.CENTER_VERTICAL);
        // LinearLayout.LayoutParams p6 = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        //p6.gravity = Gravity.CENTER;
        //RelativeLayout.LayoutParams p6 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        //p6.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //p6.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //p6.topMargin = 50;
        //mainLayout.addView(GlobalFunctions.newText2Bold("SIMULATION OPTIONS", this, MyActivity.pxpercd(0.8f)), p5);
        Button startSimButton = new Button(this);
        startSimButton.setText("GO!");
        startSimButton.setTypeface(null, Typeface.BOLD);
        bottomGoal.addView(startSimButton, p6);

        LinearLayout.LayoutParams p10 = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        //params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        p10.gravity = Gravity.CENTER;
        p10.topMargin = 50;
        mainLayout.addView(bottomGoal, p10);


        //======================================================================================================================
        // ELEMENT ACTIONS
        //=======================================================================================================================

        //===================================================
        // PICK THE NUMBER OF JUGS ACTIONS
        //=================================================

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                jugNumberPicker.setText("" + (r.nextInt(MAX_NJUGS + 1 - MIN_NJUGS) + MIN_NJUGS));
            }
        });

        jugNumberPicker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                njugs = Integer.parseInt(jugNumberPicker.getText().toString());
                capacityLayout.removeView(centerJugs2);
                centerJugs2 = new JugSelection2(getBaseContext(), njugs, MIN_JUG, MAX_JUG);
                capacityLayout.addView(centerJugs2, p);
            }
        });

        //===================================================
        // PICK CAPACITIES ACTIONS
        //=================================================


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerJugs2.randomizeCapacities();
            }
        });

        //===================================================
        // SIMULATION BUTTON ACTIONS
        //=================================================
            startSimButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //changeScreenClosingPassing(Garrafas.class, "capArray", centerJugs2.getCapArray());
                    Intent intent = new Intent(getBaseContext(), Garrafas.class);
                    intent.putExtra("capArray", centerJugs2.getCapArray());
                    intent.putExtra("goal", Integer.parseInt(goalPicker.getText().toString()));
                    startActivity(intent);
                    finish();
                }
            });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalPicker.randomizeValue();
            }
        });

        /*RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        par.addRule(RelativeLayout.CENTER_HORIZONTAL);
        par.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        par.bottomMargin = 80;
        centerJugs3 = new JugSelection3(this, 5);*/
        //params.width = centerJugs.totalWidth;
        //mainLayout.addView(centerJugs3, par);


        /*NumberTextView numberTextView = new NumberTextView(this, 1, 100);

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        p.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mainLayout.addView(numberTextView);*/

        //JugSizeLayout jugSizeLayout = new JugSizeLayout(this, 1, MAX_JUG);
        //mainLayout.addView(jugSizeLayout);

        /*editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "onclick", Toast.LENGTH_SHORT).show();
            }
        });

        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "ontouch", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/

        //editText.setOnTouchListener(new MyMotionTracker());
        //editText.setOnGenericMotionListener(new MyGenericMoving());
        //editText.setOnLongClickListener(new MyLongListener());
        /*(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                //gdt.onTouchEvent(event);
                int action = MotionEventCompat.getActionMasked(event);

                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        Log.d(DEBUG_TAG, "Action was DOWN");
                        //Toast.makeText(getApplicationContext(), "Action was DOWN", Toast.LENGTH_SHORT).show();
                        return true;
                    case (MotionEvent.ACTION_MOVE) :
                        Log.d(DEBUG_TAG,"Action was MOVE");

                        editText.setText("" + event.getX() + ", " + event.getRawX());
                        return true;
                    case (MotionEvent.ACTION_UP) :
                        Log.d(DEBUG_TAG,"Action was UP");
                        //Toast.makeText(getApplicationContext(), "Action was UP", Toast.LENGTH_SHORT).show();
                        return true;
                    case (MotionEvent.ACTION_CANCEL) :
                        Log.d(DEBUG_TAG,"Action was CANCEL");
                        //Toast.makeText(getApplicationContext(), "Action was CANCEL", Toast.LENGTH_SHORT).show();
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE) :
                        Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                                "of current screen element");
                        //Toast.makeText(getApplicationContext(), "Movement occurred outside bounds " +
                        //        "of current screen element", Toast.LENGTH_SHORT).show();
                        return true;
                    default :
                        return false;
                }
            }
        });*/

        /*editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editText.setText("mierda");
                return false;
            }
        });


        OnSwipeTouchListener textSwipeListener*/
            //editText.setOnTouchListener(new OnSwipeTouchListener );

        /*seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                editText.setText("Covered: " + progress + "/" + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });*/

            //===================================================
            // MODE LAYOUT ELEMENTS
            //=================================================


            //===============================================================================
            // MODE LAYOUT ELEMENTS ACTIONS
            //========================================================================


            modeSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                             {
                                                 @Override
                                                 public void onItemSelected(AdapterView<?> parent, View view, int position,
                                                                            long id) {
                                                     if (position == 0) { // AUTOMATIC
                                                         modeLay.setVisibility(View.GONE);
                                                     } else {
                                                         modeLay.setVisibility(View.VISIBLE);
                                                     }
                                                 }

                                                 @Override
                                                 public void onNothingSelected(AdapterView<?> parent) {

                                                 }
                                             }

            );



        final ImageView spiral = (ImageView) findViewById(R.id.spiral);
        RelativeLayout.LayoutParams pp = (RelativeLayout.LayoutParams) spiral.getLayoutParams();
        //int h = Math.round(screenHeight*2);
		int w = Math.round(screenWidth*1.8f);
		pp.height = w;
		pp.width = w;
        pp.topMargin = -Math.round((w - screenHeight) / 2);
		pp.leftMargin = -Math.round((w - screenWidth) / 2);
        //pp.bottomMargin = p.topMargin;
        //pp.height = h;
		pp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        final Animation spiralRotate = AnimationUtils.loadAnimation(this, R.anim.clockwise);
		//spiralRotate.setRepeatCount(Animation.INFINITE);
		//spiralRotate.setRepeatMode(Animation.INFINITE);
		spiralRotate.setInterpolator(new LinearInterpolator());
		
		/*spiralRotate.setAnimationListener(
			new Animation.AnimationListener(){

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO A

		
		}
        


       });*/
		spiral.startAnimation(spiralRotate);
		//centerJugs2.adjustCapacities();
	   }
	   
		//
                //=======================================================================================================================================================================
//                            FUNCTION DECLARATIONS
//=======================================================================================================================================================================



    /*private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int distance = Math.round(e1.getX() - e2.getX()); // Right to left
            int prop = distance/ editText.getWidth()*MAX_JUG;
            float total = editText.getWidth();
            int c = Integer.parseInt(editText.getText().toString());
            if(distance > 0 *//*&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY*//*) {
                if((c+prop) > MAX_JUG) {
                    editText.setText("" + MAX_JUG);
                }
                else {
                    editText.setText("" + (c + prop));
                }

                return false; // Right to left
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                c = Integer.parseInt(editText.getText().toString());
                editText.setText("" + (c + 5));
                return false; // Left to right
            }

            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                return false; // Bottom to top
            }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                return false; // Top to bottom
            }
            return false;
        }
    }*


    /*private class SwipeListener extends OnSwipeTouchListener {
        SwipeListener(Context context) {
            super(context);
        }

        @Override
        public void onSwipeRight() {
            super.onSwipeRight();
            editText.setText("" + (Integer.parseInt(editText.getText().toString())) + 5);

        }
    }

    private void limitEditText(final EditText ed, int min, int max) {
        ed.addTextChangedListener(new MinMaxTextWatcher(min, max) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                int n = 0;
                try {
                    n = Integer.parseInt(str);
                    if(n < min) {
                        ed.setText("" + min);
                        //Toast.makeText(getApplicationContext(), "Minimum allowed is " + min, Toast.LENGTH_SHORT).show();
                    }
                    else if(n > max) {
                        ed.setText("" + max);
                        //Toast.makeText(getApplicationContext(), "Maximum allowed is " + max, Toast.LENGTH_SHORT).show();
                    }
                }
                catch(NumberFormatException nfe) {
                    ed.setText("" + min);
                    Toast.makeText(getApplicationContext(), "Bad format for number!" + max, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/


    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String[] items = new String[]{};

        public SpinnerAdapter(final Context context,
                              final int textViewResourceId, final String[] objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            //tv.setTextColor(Color.BLUE);
            /*if(vers > 13) {
                tv.setTextSize(size);
            }
            else {
                tv.setTextSize(smallSize);
            }*/
            tv.setTextSize(size);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            // android.R.id.text1 is default text view in resource of the android.
            // android.R.layout.simple_spinner_item is default layout in resources of android.

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            //tv.setTextColor(Color.BLUE);
            /*if(vers > 13) {
                tv.setTextSize(size);
            }
            else {
                tv.setTextSize(smallSize);
            }*/
            tv.setTextSize(size);
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gamemode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.advanced_settings) {

            return true;
        }
        if (id == R.id.change_depth_limit) {
            loadDepthDialog();
            return true;
        }

        if (id == R.id.instructions) {
            changeScreenClosing(InstructionsActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void loadDepthDialog() {

        MyDialog dialog = new MyDialog(this);
        //dialog.setContentView(R.layout.select_dept_layout);
        dialog.show();
    }

    class MyDialog extends  Dialog {

        public MyDialog(Context context) {
            super(context);
            setContentView(R.layout.select_dept_layout);
            setTitle("Select the depth limit");
            RelativeLayout selDepthLay = (RelativeLayout)findViewById(R.id.selDepthLayout);
            final NumberTextView selecDepth = new NumberTextView(this.getContext(), 1, 60);
            selecDepth.setText("" + GarrafasEngine.getCota());
            //p.gravity=Gravity.CENTER;
            //p.height = NumberTextView.h;
            //p.width = NumberTextView.w;
            selDepthLay.addView(selecDepth);
            Button b = (Button)findViewById(R.id.diagButton);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cota = Integer.parseInt(selecDepth.getText().toString());
                    GarrafasEngine.setCota(cota);
                    GlobalFunctions.pushInt("depth_limit", cota, getContext());
                    dismiss();
                }
            });
        }
    }
	
	

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		new Runnable() {
            @Override
            public void run() {
                if(player != null) {
				    player.stop();
                }

            }
        }.run();
		
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		centerJugs2.adjustCapacitiesNoAnim();
		new Runnable() {
            @Override
            public void run() {
                if(player == null) {
				    player = MediaPlayer.create(getApplicationContext(),R.raw.bgm1);
                }
				else {
					player.reset();

					try {
						AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.bgm1);
						if (afd == null) return;
						player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
						afd.close();
					    player.prepare();
					}
					catch(IOException e) {}
				}

				player.start();
                player.setLooping(true);

            }
        }.run();
		
	}
	
	
	
	

}
