package com.vvage.futuretown.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.vvage.futuretown.Global;
import com.vvage.futuretown.R;

public class FTButton extends Button {

	private StateListDrawable bg = new StateListDrawable();
    Context context;
    
    public FTButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        Drawable normal = getResources().getDrawable(R.drawable.bg_btn_normal);
        Drawable pressed = getResources().getDrawable(R.drawable.bg_btn_pressed);
        bg.addState(View.PRESSED_ENABLED_STATE_SET, pressed);
        bg.addState(View.ENABLED_FOCUSED_STATE_SET, pressed);
        bg.addState(View.ENABLED_STATE_SET, normal);
        bg.addState(View.FOCUSED_STATE_SET, pressed);
        bg.addState(View.EMPTY_STATE_SET, normal);
        setBackgroundDrawable(bg);

        setTextColor(0xffffffff);
        setTextSize(16);
        setPadding(Global.PADDING_GLOBAL/2, Global.PADDING_GLOBAL/4, Global.PADDING_GLOBAL/2, Global.PADDING_GLOBAL/4);
        setGravity(Gravity.CENTER);
    }
}
