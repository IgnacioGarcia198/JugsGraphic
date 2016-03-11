package com.nacho.jugsgraphic;

import android.text.TextWatcher;

/**
 * Created by nacho on 8/5/15.
 */
public abstract class MinMaxTextWatcher implements TextWatcher {
    int min, max;
    public MinMaxTextWatcher(int min, int max) {
        super();
        this.min = min;
        this.max = max;
    }

}
