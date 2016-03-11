package com.nacho.jugsgraphic;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;


public class Splash extends MyActivity {
    //private boolean checked = true;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        countDownTimer = new CountDownTimer(500, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                boolean hideInstructions = GlobalFunctions.pullBoolean("hideInstructions", getApplicationContext());
                if(!hideInstructions) {
                    Intent intent = new Intent(getBaseContext(), InstructionsActivity.class);
                    intent.putExtra("on", true);
                    startActivity(intent);
                    finish();
                }
                else {
                    changeScreenClosing(GameMode.class);
                }
                //changeScreenClosing(InstructionsActivity.class);
                //changeScreenClosing(GameMode.class);
                //changeScreenClosing(Garrafas.class);
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
