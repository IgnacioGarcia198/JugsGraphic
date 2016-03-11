package com.nacho.jugsgraphic;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nacho on 7/28/15.
 */
public class GlobalFunctions {

    public static EditText newEdit1(Context context, InputFilter[] fArray, float size) {
        EditText ed = new EditText(context);
        ed.setInputType(InputType.TYPE_CLASS_NUMBER);
        ed.setGravity(Gravity.CENTER_HORIZONTAL);
        ed.setTextSize(size);
        ed.setTypeface(null, Typeface.BOLD);
        ed.setFilters(fArray);
        return ed;
    }

    public static TextView newText1(String text, Context context, float size) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(size);
        //TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, metrics);
        return textView;
    }

    public static TextView newText2Bold(String text, Context context, float size) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(size);
        textView.setTypeface(null, Typeface.BOLD);
        return textView;
    }

    public static void limitEditText(final EditText ed, final Context context, int min, int max) {
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
                    if (n < min) {
                        ed.setText("" + min);
                        Toast.makeText(context, "Minimum allowed is " + min, Toast.LENGTH_SHORT).show();
                    } else if (n > max) {
                        ed.setText("" + max);
                        Toast.makeText(context, "Maximum allowed is " + max, Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException nfe) {
                    ed.setText("" + min);
                    Toast.makeText(context, "Bad format for number!" + max, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static int pullInt(String key, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // LEER

        return sharedPref.getInt(key, 0);
    }

    public static int pullIntWithDefault(String key, Context context, int def) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // LEER

        return sharedPref.getInt(key, def);
    }

    public static void pushInt (String key, int value, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // EDITAR
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public static boolean pullBoolean(String key, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // LEER

        return sharedPref.getBoolean(key, false);
    }

    public static void pushBoolean (String key, boolean value, Context context) {
        // ARCHIVO DE CLAVES: ACCEDER

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // EDITAR
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }


}
