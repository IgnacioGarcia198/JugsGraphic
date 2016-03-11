package com.nacho.jugsgraphic;

/**
 * Created by nacho on 6/9/15.
 */
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nacho on 20/03/2015.
 */
public class MyActivity extends FragmentActivity {
    protected String TAG = "TKT";
    //protected static boolean checked = true;
    // SCREEN THINGS...
    static float screenWidth, screenHeight, dens;
    static float diagonal,ratio;
    float size, smallSize;
    static int padw, padh, buttonsHeight, buttonsWidth;
    static float totalpx;
    DisplayMetrics metrics;
    //static int vers = Build.VERSION.SDK_INT;

    void changeScreen(Class c) {
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
    void changeScreenClosing(Class c) {
        Intent intent = new Intent(this,c);
        startActivity(intent);
        finish();
    }

    void changeScreenClosingPassing(Class c, String variable, int[] value) {
        Intent intent = new Intent(this,c);
        intent.putExtra(variable, value);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        //onPause();
        //finish();
        //System.exit(0);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        metrics = getResources().getDisplayMetrics();
        dens = metrics.density;
        //screenWidth = metrics.widthPixels / dens;
        //screenHeight = metrics.heightPixels / dens;
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        diagonal = (float)Math.sqrt(Math.pow(screenWidth,2) + Math.pow(screenHeight,2));
        totalpx = screenHeight*screenWidth;
        ratio = screenWidth/screenHeight;
        //pad = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, metrics);
        //pad = perchi(5, true);
        //pad = myDpToPx(1);
        /*if(vers < 14) {
            padw = pxpercw(1f);
            padh = pxperch(1f);
        }
        else {*/
//            padw = pxpercw(1f);
//            padh = pxperch(1f);
        padw = percwi(5f, false);
        padh = perchi(5f, false);
        //padw = dpToPx(2.5f);
        //padh = dpToPx(2.5f);
        //}
        //buttonsHeight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, perchf(50), metrics);//perchi(50);
        //buttonsWidth = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, percwf(50), metrics);//percwi(50);
        //buttonsHeight = perchi(14,false);
        //buttonsWidth = percwi(14,false);
//        buttonsHeight = dpToPx(60);
//        buttonsWidth = dpToPx(100);

        //buttonsHeight = pxperc(12);
       // buttonsWidth = pxperc(20);
        buttonsWidth = pdperc(13);
        buttonsHeight = pdperc(7.5f);
//        buttonsWidth = pxperc(10f);
//        buttonsHeight = pxperc(6f);
        //(int)(perchf(50)*dens);
        // (int)(percwf(50)*dens);
        //screenWidth = getResources().getDisplayMetrics().widthPixels;
        //screenHeight = getResources().getDisplayMetrics().heightPixels;
        //setContentView(R.layout.greetinglayout);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d(TAG, this.getLocalClassName() + " onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, this.getLocalClassName() + " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG,this.getLocalClassName() + " onResume");
        //sacaValores();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, this.getLocalClassName() + " onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG,this.getLocalClassName() + " onStop");
        //sacaValores();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, this.getLocalClassName() + " onDestroy");
    }

    protected static float percwf(float p, boolean d) {
        if(d) {
            return p*screenWidth/dens/100;
        }
        return p*screenWidth/100;
    }
    protected static int percwi(float p, boolean d) {
        if(d) {
            return Math.round(p*screenWidth/dens/100);
        }
        return Math.round(p*screenWidth/100);
    }
    protected static float perchf(float p, boolean d) {
        if(d) {
            return p*screenHeight/dens/100;
        }
        return p*screenHeight/100;
    }
    protected static int perchi(float p, boolean d) {
        if(d) {
            return Math.round(p*screenHeight/dens/100);
        }
        return Math.round(p*screenHeight/100);
    }

    protected void styleButton1(Button b, int w, int h) {
        b.setTextSize(Math.round(size * 0.9f));
        b.setWidth(w);
        b.setHeight(h);
        b.setTypeface(null, Typeface.BOLD);
    }

    protected EditText newEdit1(InputFilter[] fArray) {
        EditText ed = new EditText(this);
        ed.setInputType(InputType.TYPE_CLASS_NUMBER);
        ed.setGravity(Gravity.CENTER_HORIZONTAL);
        ed.setTextSize(size);
        ed.setTypeface(null, Typeface.BOLD);
        ed.setFilters(fArray);
        return ed;
    }

    protected TextView newText1(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(size);
        //TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, metrics);
        return textView;
    }

    protected TextView newText2Bold(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(size);
        textView.setTypeface(null, Typeface.BOLD);
        return textView;
    }

    protected TextView newText(String text, boolean bold, int centered, float newSize) {
        TextView textView = new TextView(this);
        textView.setText(text);
        if(newSize > 0) {
            textView.setTextSize(newSize);
        }
        else {
            textView.setTextSize(size);
        }
        if(bold) {
            textView.setTypeface(null, Typeface.BOLD);
        }

        if(centered == 1) {
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        else if(centered > 1) {
            textView.setGravity(Gravity.CENTER);
        }

        return textView;
    }

    protected TextView newText3SmallBold(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(smallSize);
        textView.setTypeface(null, Typeface.BOLD);
        return textView;
    }

    protected TextView newText4Small(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(smallSize);
        return textView;
    }

    int dpToPx(float dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * dens);//(displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    int pxToDp(int px) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    int myDpToPx(int dp) {
        //int px = Math.round(dp * metrics.density);//Math.round(dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        //int px = Math.round(dp * (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        //DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        //int px = Math.round(perc*totalpx);
        //Toast.makeText(getApplicationContext(), ""+px, Toast.LENGTH_SHORT).show();
        int px = Math.round(dp * (DisplayMetrics.DENSITY_DEFAULT /metrics.xdpi));
        return px;
    }

    int myPxToDp(int px) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    protected int pxperc(float p) {
        //int px = Math.round(p * totalpx / dens / 100000);
        //Toast.makeText(getApplicationContext(), "" + px, Toast.LENGTH_SHORT).show();
        int px = Math.round(p * diagonal / dens / 100);
        //Toast.makeText(getApplicationContext(), "" + px, Toast.LENGTH_SHORT).show();
        return px;

    }

    protected static int pxpercw(float p) {
        return Math.round(p*screenWidth/dens/100);
    }
    protected static int pxperch(float p) {
        return Math.round(p*screenHeight/dens/100);
    }

    static int pxpercd(float p) {
        //int px = Math.round(p * totalpx / dens / 100000);
        //Toast.makeText(getApplicationContext(), "" + px, Toast.LENGTH_SHORT).show();
        int px = Math.round(p * diagonal * dens / 100);
        //Toast.makeText(getApplicationContext(), "" + px, Toast.LENGTH_SHORT).show();
        return px;

    }

    protected static int pxpercwd(float p) {
        return Math.round(p*screenWidth*dens/100);
    }
    protected static int pxperchd(float p) {
        return Math.round(p*screenHeight*dens/100);
    }

    protected static int pdperc(float p) {

        return Math.round(p*diagonal/100);

    }

    protected static int pxpercRatio(float p) {
        return Math.round(p*diagonal*screenWidth/screenHeight/dens/100);
    }

    protected static int pxPercComp1(float f1, float f2) {
        float diag = f1 * diagonal / dens / 100;
        float rel = f2 * diagonal * ratio;
        return Math.round(diag + rel);
    }


    // BEST ONES PERHAPS

    protected static int pxPercComp2(float f1, float f2) {
        float diag = f1 * diagonal / dens;
        float rel = f2 * diagonal * ratio / dens;
        return Math.round((diag + rel) / 100);
    }

    protected static int pxPercComp3(float f1, float f2) {
        float rel = f1 * diagonal * ratio / dens / 100;
        float sus = f2 * diagonal * ratio;
        return Math.round(f1 - f2);
    }

}
