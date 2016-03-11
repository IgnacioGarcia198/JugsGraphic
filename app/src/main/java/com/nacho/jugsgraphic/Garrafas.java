package com.nacho.jugsgraphic;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nacho.jugsgraphic.GarrafasEngine.GarrafasEngine;
import com.nacho.jugsgraphic.GarrafasEngine.JugsEngine;
import com.nacho.jugsgraphic.GarrafasEngine.JugsNode;

import java.util.LinkedList;
import java.io.*;
import android.content.res.*;


public class Garrafas extends MyActivity {
    Button prueba;
    AnimationDrawable gearAnimation;
    JugsLayoutR centerJugs;
    public static ImageView processImage, processImage2;
    boolean calculated;
    JugsEngine jugsEngine;
    JugsNode solution;
    TextView depthText;
    static MyDialog dialog;
	static MediaPlayer player; 

    ProgressDialog progress;
	boolean trigger;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garrafas);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setPadding(padw, padh, padw, padh);

        processImage = (ImageView) findViewById(R.id.processImage);
        //processImage.setBackgroundResource(R.drawable.gear_animation);
        processImage.setImageResource(R.drawable.gear_animation);
        gearAnimation = (AnimationDrawable) processImage.getDrawable();
        //prueba = (Button) findViewById(R.id.botonPrueba);

        int[] capacities = null;
        int goal = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //String value = extras.getString("new_variable_name");
            capacities = extras.getIntArray("capArray");
            goal = extras.getInt("goal");
            jugsEngine = new GarrafasEngine(goal, capacities);
        }


        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView goalText = GlobalFunctions.newText2Bold("Goal: " + goal, this, MyActivity.pxpercd(1f));
        depthText = GlobalFunctions.newText2Bold("Depth: " , this, MyActivity.pxpercd(1f));
        ll.addView(goalText);
        ll.addView(depthText);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        params1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.addView(ll, params1);

        //int[] capacities = new int[]{100,3,5,7,4,9};
        centerJugs = new JugsLayoutR(this, capacities, goal);
        centerJugs.txtDepth = depthText;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT );
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        //params.width = centerJugs.totalWidth;
        layout.addView(centerJugs, params);

        /*prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //centerJugs.execute();

            }
        });*/

        

    }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_garrafas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_simulation) {
            centerJugs.cancelAnimation();
            changeScreenClosing(GameMode.class);
            //Toast.makeText(getBaseContext(), "el menu de tu madre", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.repeat_simulation) {
            centerJugs.execute();
            return true;
        }

        if (id == R.id.stop_simulation) {
            centerJugs.endAnimation();
            return true;
        }

        if (id == R.id.restart_simulation) {
            centerJugs.restartAnimation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        centerJugs.cancelAnimation();
        changeScreenClosing(GameMode.class);
    }

    

    class MyDialog extends  Dialog {

        public MyDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            setContentView(R.layout.my_progress_dialog);
            ImageView gear = (ImageView) findViewById(R.id.animation);
            gear.setImageResource(R.drawable.gear_animation);

            //gearAnimation2 = (AnimationDrawable) gear.getDrawable();


        }

        /*@Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            new pepeTask().execute();
        }*/
        /* @Override
        public void show() {

            //getWindow().setBackgroundDrawable(gearAnimation);
            super.show();
        }*/
    }



    void showGear() {
        dialog = new MyDialog(this);
        dialog.show();
    }

    void cristo() {
        progress = ProgressDialog.show(this, "dialog title",
                "dialog message", true);
        centerJugs.setTotalWaterHeight();

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                // do the thing that takes a long time
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        centerJugs.execute();
                    }
                });



                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                });*/
            }
        }).start();
    }

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		centerJugs.endAnimation();
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
		centerJugs.reset();
		new Runnable() {
            @Override
            public void run() {
                if(player == null) {
				    player = MediaPlayer.create(getApplicationContext(),R.raw.mixdown);
                }
				else {
					player.reset();
					
					try {
						AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.mixdown);
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
		if(!trigger) {
		  centerJugs.execute();
		  trigger = true;
		}
		
	}
}
