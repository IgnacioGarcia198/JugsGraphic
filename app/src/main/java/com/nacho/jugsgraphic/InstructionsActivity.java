package com.nacho.jugsgraphic;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nacho.jugsgraphic.GarrafasEngine.GarrafasEngine;


public class InstructionsActivity extends MyActivity {

     LinearLayout instructionsLayout;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        instructionsLayout = (LinearLayout)findViewById(R.id.instructionsLayout);
        //size = pxPercComp2(2f, 1.5f);
        //smallSize = pxPercComp2(1.7f, 0.3f);
        size = pxPercComp2(1.7f, 0.3f);

        TextView txt = newText(getString(R.string.instructions_title), true, 2, 0);
        txt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        instructionsLayout.addView(txt);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            checkBox = new CheckBox(this);
            checkBox.setText("Show this screen at startup (you can also check this instructions from the main screen)");
            checkBox.setChecked(!GlobalFunctions.pullBoolean("hideInstructions", getApplicationContext()));
            instructionsLayout.addView(checkBox);
        }



        instructionsLayout.addView(newText1(getString(R.string.presentation)));

        instructionsLayout.addView(newText2Bold(getString(R.string.layout_title)));
        instructionsLayout.addView(newText1("We have several jugs of different capacities."));

        instructionsLayout.addView(newText2Bold(getString(R.string.goal_title)));
        instructionsLayout.addView(newText1("Get a certain amount of water in any of the jugs. For example, 5 litres."));

        instructionsLayout.addView(newText2Bold(getString(R.string.rules_title)));
        instructionsLayout.addView(newText1(getString(R.string.operations_introduction) +
                getString(R.string.operations1) +
                getString(R.string.operations2) +
                getString(R.string.operations3)));

        instructionsLayout.addView(newText2Bold("Example:"));
        instructionsLayout.addView(newText1("We have two jugs: One with a capacity of 3 litres and the other with 5 litres.\n"));
        LinearLayout exampleJugs = new LinearLayout(this);
        exampleJugs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        exampleJugs.setGravity(Gravity.CENTER);

        exampleJugs.addView(new GraphicJugR(this, 3), new LinearLayout.LayoutParams(JugsLayoutR.jugWidth, JugsLayoutR.jugHeight));
        exampleJugs.addView(newText1("    "));
        exampleJugs.addView(new GraphicJugR(this, 5), new LinearLayout.LayoutParams(JugsLayoutR.jugWidth, JugsLayoutR.jugHeight));
        instructionsLayout.addView(exampleJugs);

        instructionsLayout.addView(newText1("\nAnd we want to get 4 litres. We could achieve it like this:"));
        instructionsLayout.addView(newText1("1. Fill the 5 litres jug (from now on 5lj)\n" +
                "2. Pour it into the 3 litres jug(from now on 3lj)\n" +
                "3. Empty the 3lj\n" +
                "4. Pour the 5lj into the 3 lj\n" +
                "5. Fill the 5lj\n" +
                "6. Pour it into the 3lj."));

        Button runExampleBtn = new Button(this);
        runExampleBtn.setText("Run Example...");
        runExampleBtn.setTypeface(null, Typeface.BOLD);
        instructionsLayout.addView(runExampleBtn);

        runExampleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyCheck();
                Intent intent = new Intent(getBaseContext(), Garrafas.class);
                intent.putExtra("capArray", new int[]{3,5});
                intent.putExtra("goal", 4);
                startActivity(intent);
                finish();
            }
        });



        //JugsLayoutR centerJugs = new JugsLayoutR(this, new int[]{3,5}, 4);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instructions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        copyCheck();
        changeScreenClosing(GameMode.class);
    }

    void copyCheck() {
        if(checkBox != null) {
            if (checkBox.isChecked()) {
                GlobalFunctions.pushBoolean("hideInstructions", false, getApplicationContext());
            } else {
                GlobalFunctions.pushBoolean("hideInstructions", true, getApplicationContext());
            }
        }
    }
}
