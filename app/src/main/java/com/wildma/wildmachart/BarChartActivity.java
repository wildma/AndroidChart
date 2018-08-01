package com.wildma.wildmachart;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import org.xclcharts.common.DrawHelper;
/**
 * Author       wildma
 * Github       https://github.com/wildma
 * CreateDate   2018/8/1
 * Desc	        ${范围柱状图}
 */
public class BarChartActivity extends AppCompatActivity {

    private FrameLayout mFramelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_bar_chart);

        mFramelayout = (FrameLayout) findViewById(R.id.framelayout);

        //设置柱状图颜色
        DrawHelper.getInstance().setColor(ContextCompat.getColor(this, R.color.colorAccent), ContextCompat.getColor(this, R.color.colorPrimary));

        /*动态添加view*/
        View view = LayoutInflater.from(this).inflate(R.layout.view_range_bar_chart, mFramelayout, false);
        mFramelayout.addView(view);
    }
}
