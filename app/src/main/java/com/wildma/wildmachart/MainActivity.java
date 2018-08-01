package com.wildma.wildmachart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void barChart(View view) {
        startActivity(new Intent(this,BarChartActivity.class));
    }

    public void radarChart(View view) {
        startActivity(new Intent(this,RadarChartActivity.class));
    }
}
