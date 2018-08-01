package com.wildma.wildmachart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
/**
 * Author       wildma
 * Github       https://github.com/wildma
 * CreateDate   2018/8/1
 * Desc	        ${雷达图}
 */
public class RadarChartActivity extends AppCompatActivity {

    private FrameLayout mFramelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart);

        mFramelayout = (FrameLayout) findViewById(R.id.framelayout);

        /*动态添加view*/
        View view = LayoutInflater.from(this).inflate(R.layout.view_radar_chart, mFramelayout, false);
        mFramelayout.addView(view);
    }
}
