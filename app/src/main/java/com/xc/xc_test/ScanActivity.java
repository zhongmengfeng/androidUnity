package com.xc.xc_test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScanActivity extends UnityPlayerActivity {
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RelativeLayout relativeLayout = new RelativeLayout(this);
        setContentView(R.layout.activity_scan);
        frameLayout = findViewById(R.id.frame_layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        TextView view = new TextView(this);
        view.setWidth(600);
        view.setHeight(600);
        view.setBackgroundColor(Color.parseColor("#000000"));
        view.setTextColor(Color.parseColor("#000000"));
        view.setTextSize(50f);
        view.setText("1234569999");
        view.setLayoutParams(params);
        frameLayout.addView(mUnityPlayer.getView(), params);

    }


}
