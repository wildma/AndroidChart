/**
 * Copyright 2014  XCL-Charts
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts
 * @Description Android图表基类库演示
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.3
 */
package com.wildma.wildmachart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import org.xclcharts.chart.RadarChart;
import org.xclcharts.chart.RadarData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.view.ChartView;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;


/**
 * Author       wildma
 * Github       https://github.com/wildma
 * CreateDate   2018/8/1
 * Desc	        ${雷达图自定义view}
 */
public class RadarChartView extends ChartView {

    private String          TAG       = "RadarChartView";
    private RadarChart      chart     = new RadarChart();
    private List<String>    labels    = new LinkedList<String>();//标签轴集合
    private List<RadarData> chartData = new LinkedList<RadarData>();//数据轴集合


    public RadarChartView(Context context) {
        super(context);
        initView();
    }

    public RadarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RadarChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        chartLabels();
        chartDataSet();
        chartRender(80d, 20d, 20);

        //綁定手势滑动事件
        //			this.bindTouch(this,chart);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w, h);
    }

    private void chartRender(double axisMax, double axisInterval, int tickLabelMargin) {
        try {

            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            float pxValue = getResources().getDimension(R.dimen.dp_40);//获取对应资源文件下的dp值
            chart.setPadding(pxValue, pxValue, pxValue, pxValue);

            //标题
            //            chart.setTitle("圆形雷达图");
            //            chart.addSubtitle("(XCL-Charts Demo)");

            //设定数据源
            chart.setCategories(labels);
            chart.setDataSource(chartData);

            //指定类型为圆形雷达图
            chart.setChartType(XEnum.RadarChartType.ROUND);

            //点击事件处理
            //            chart.ActiveListenItemClick();
            //            chart.extPointClickRange(30);

            //数据轴最大值
            chart.getDataAxis().setAxisMax(axisMax);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(axisInterval);
            //主轴标签偏移50，以便留出空间用于显示点和标签(即主轴标签距离"正北轴"的距离)
            chart.getDataAxis().setTickLabelMargin(tickLabelMargin);

            chart.getLinePaint().setColor(Color.parseColor("#CCCCCC")); //网线颜色
            chart.getLabelPaint().setColor(Color.parseColor("#333333"));//标签颜色
            chart.getLabelPaint().setFakeBoldText(true);

            chart.getDataAxis().getTickLabelPaint().setTextSize(getResources().getDimension(R.dimen.sp_8));//数据轴字体大小
            chart.getLabelPaint().setTextSize(getResources().getDimension(R.dimen.sp_8));//标签轴字体大小

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

                @Override
                public String textFormatter(String value) {
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df = new DecimalFormat("#0");
                    String label = df.format(tmp).toString();
                    return (label);
                }

            });

            //定义数据点标签显示格式
            chart.setDotLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    DecimalFormat df = new DecimalFormat("#0");
                    String label = "[" + df.format(value).toString() + "]";
                    return label;
                }
            });

            chart.enablePanMode();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

    }

    @Override
    public void render(Canvas canvas) {
        try {
            chart.render(canvas);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            triggerClick(event.getX(), event.getY());
        }
        return true;
    }

    /**
     * 触发监听
     *
     * @param x
     * @param y
     */
    private void triggerClick(float x, float y) {
        PointPosition record = chart.getPositionRecord(x, y);
        if (null == record)
            return;

        if (record.getDataID() < chartData.size()) {
            RadarData lData = chartData.get(record.getDataID());
            Double lValue = lData.getLinePoint().get(record.getDataChildID());

            Toast.makeText(this.getContext(),
                    " Current Value:" + Double.toString(lValue) +
                            " Point info:" + record.getPointInfo(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 图标数据设置
     */
    private void chartDataSet() {
        LinkedList<Double> dataSeriesA = new LinkedList<Double>();
        dataSeriesA.add(30d);
        dataSeriesA.add(35d);
        dataSeriesA.add(60d);
        dataSeriesA.add(65d);
        dataSeriesA.add(20d);
        dataSeriesA.add(0d);
        dataSeriesA.add(0d);
        dataSeriesA.add(0d);
        dataSeriesA.add(0d);
        dataSeriesA.add(0d);
        dataSeriesA.add(0d);
        dataSeriesA.add(0d);
        dataSeriesA.add(0d);
        dataSeriesA.add(0d);
        dataSeriesA.add(0d);
        dataSeriesA.add(10d);

        /*填充区域方式*/
        RadarData lineData1 = new RadarData("", dataSeriesA,
                Color.parseColor("#FF4081"), XEnum.DataAreaStyle.FILL);
        lineData1.setLabelVisible(false);
        lineData1.getPlotLine().getDotLabelPaint().setTextAlign(Align.LEFT);
        chartData.add(lineData1);

        /*线条方式，再画一条线条方式是为了补充填充区域方式遇到数据中间被0隔开后，单条线条不画的情况。例如数据0,50,0*/
        /*RadarData lineData2 = new RadarData("", dataSeriesA,
                Color.parseColor("#FF4081"), XEnum.DataAreaStyle.STROKE);
        lineData2.setLabelVisible(false);
        lineData2.getPlotLine().getDotLabelPaint().setTextAlign(Align.LEFT);
        chartData.add(lineData2);*/
    }

    /**
     * 图表标签设置
     */
    private void chartLabels() {
        labels.add("正北");
        labels.add("东北偏北");
        labels.add("东北");
        labels.add("东北偏东");
        labels.add("正东");
        labels.add("东南偏东");
        labels.add("东南");
        labels.add("东南偏南");
        labels.add("正南");
        labels.add("西南偏南");
        labels.add("西南");
        labels.add("西南偏西");
        labels.add("正西");
        labels.add("西北偏西");
        labels.add("西北");
        labels.add("西北偏北");
    }

}
