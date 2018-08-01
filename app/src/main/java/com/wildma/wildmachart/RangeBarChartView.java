package com.wildma.wildmachart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import org.xclcharts.chart.RangeBarChart;
import org.xclcharts.chart.RangeBarData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.BarPosition;
import org.xclcharts.view.ChartView;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;


/**
 * Author       wildma
 * Github       https://github.com/wildma
 * CreateDate   2018/8/1
 * Desc	        ${范围柱状图自定义view}
 */
public class RangeBarChartView extends ChartView {

    private String        TAG   = "RangeBarChartView";
    private RangeBarChart chart = new RangeBarChart();
    List<String>       chartLabels = new LinkedList<String>();//标签轴集合
    List<RangeBarData> BarDataSet  = new LinkedList<RangeBarData>();//数据轴集合

    public RangeBarChartView(Context context) {
        super(context);
        initView();
    }

    public RangeBarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RangeBarChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        chartLabels();
        chartDataSet();
        chartRender(700, 0, 100);

        //綁定手势滑动事件
        //        this.bindTouch(this, chart);
        //        chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w, h);
    }


    /**
     * 图表渲染
     *
     * @param axisMax      轴最大值
     * @param axisMin      轴最小值
     * @param axisInterval 轴间距（即每个刻度之间的距离）
     */
    private void chartRender(double axisMax, double axisMin, double axisInterval) {
        try {

            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            float pxValue = getResources().getDimension(R.dimen.dp_40);//获取对应资源文件下的dp值
            chart.setPadding(pxValue, 0, pxValue, pxValue);

            //显示边框
            //			chart.showRoundBorder();

            //数据源
            chart.setCategories(chartLabels);
            chart.setDataSource(BarDataSet);

            //坐标系
            chart.getDataAxis().setAxisMax(axisMax);//如果有600高度，就需要再加100，多出来的是突出来的
            chart.getDataAxis().setAxisMin(axisMin);
            chart.getDataAxis().setAxisSteps(axisInterval);

            //			chart.getDataAxis().setDetailModeSteps(1);

            chart.setCategoryAxisMax(250d);//标签轴最大值（如果以10d为一个刻度，250dp实际是有24个刻度）
            chart.setCategoryAxisMin(0d);//标签轴最小值

            //指定数据轴标签旋转-45度显示
            //			chart.getCategoryAxis().setTickLabelRotateAngle(-45f);

            int color = Color.parseColor("#999999");
            chart.getDataAxis().getAxisPaint().setColor(color);//数据轴颜色
            chart.getCategoryAxis().getAxisPaint().setColor(color);//标签轴颜色

            chart.getDataAxis().getTickMarksPaint().setColor(color);//数据轴刻度颜色
            chart.getCategoryAxis().getTickMarksPaint().setColor(color);//标签轴刻度颜色

            chart.getPlotTitle().getTitlePaint().setColor(color);//标题颜色
            chart.getPlotTitle().getSubtitlePaint().setColor(color);//子标题颜色

            chart.getDataAxis().getTickLabelPaint().setColor(color);//数据轴标签颜色
            chart.getCategoryAxis().getTickLabelPaint().setColor(color);//标签轴标签颜色

            chart.getDataAxis().getTickLabelPaint().setTextSize(getResources().getDimension(R.dimen.sp_8));//数据轴字体大小
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(getResources().getDimension(R.dimen.sp_8));//标签轴字体大小

            //主标题
            /*chart.setTitle("范围柱状图");
            chart.addSubtitle("(XCL-Charts Demo)");
			chart.setTitleAlign(XEnum.HorizontalAlign.CENTER);
			chart.setTitleVerticalAlign(XEnum.VerticalAlign.MIDDLE);*/

            //轴标题
            /*chart.getAxisTitle().setTitleStyle(XEnum.AxisTitleStyle.ENDPOINT);
            chart.getAxisTitle().setLeftTitle("y轴标题");*/

            //数据轴显示方向
            //            chart.setDataAxisLocation(XEnum.AxisLocation.LEFT);

            //数据轴刻度方向
            chart.getDataAxis().setHorizontalTickAlign(Align.LEFT);


            //背景网格
            //            chart.getPlotGrid().showEvenRowBgColor();
            //            chart.getPlotGrid().showOddRowBgColor();
            chart.getPlotGrid().showHorizontalLines();//显示横向网格线

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

                @Override
                public String textFormatter(String value) {
                    DecimalFormat df = new DecimalFormat("#0");
                    Double tmp = Double.parseDouble(value);
                    String label = df.format(tmp).toString();
                    return label;
                }

            });

            //定义标签轴标签显示格式
            chart.getCategoryAxis().setLabelFormatter(new IFormatterTextCallBack() {

                @Override
                public String textFormatter(String value) {
                    String label = "" + value + "";
                    return label;
                }

            });


            //定义柱形上标签显示格式
            chart.getBar().setItemLabelVisible(true);
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    DecimalFormat df = new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }
            });

            //柱状图标签相关
            chart.getBar().getItemLabelPaint().setColor(Color.parseColor("#FF4081"));
            chart.getBar().getItemLabelPaint().setTypeface(Typeface.DEFAULT_BOLD);
            chart.getBar().setItemLabelVisible(true);

            //设置柱状图颜色（已封装到外面设置了）
            //            chart.getBar().getBarPaint().setColor(Color.rgb(178, 114, 248));

            //设置图例
            //            chart.setKey("图例");

            //激活点击监听
            //            chart.ActiveListenItemClick();
            //            chart.showClikedFocus();

            //            chart.enablePanMode();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void render(Canvas canvas) {
        try {

            //chart.setChartRange(this.getMeasuredWidth(), this.getMeasuredHeight());
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

        BarPosition record = chart.getPositionRecord(x, y);
        if (null == record)
            return;

		/*
        RangeBarData bData = BarDataSet.get(record.getDataID());
		Toast.makeText(this.getContext(),
				"info:" + record.getRectInfo() +
				" Min:" + Double.toString( bData.getMin()) + 		
				" Max:" + Double.toString( bData.getMax()) , 
				Toast.LENGTH_SHORT).show();		
		*/

        //显示选中框
        chart.showFocusRectF(record.getRectF());
        chart.getFocusPaint().setStyle(Style.STROKE);
        chart.getFocusPaint().setStrokeWidth(3);
        chart.getFocusPaint().setColor(Color.RED);

        this.invalidate();
    }

    /**
     * 图表数据设置
     */
    private void chartDataSet() {
        BarDataSet.add(new RangeBarData(10d, 190d, 350d));
        BarDataSet.add(new RangeBarData(20d, 50d, 400d));
        BarDataSet.add(new RangeBarData(30d, 100d, 300d));
        BarDataSet.add(new RangeBarData(40d, 90d, 350d));
        BarDataSet.add(new RangeBarData(50d, 100d, 380d));
        BarDataSet.add(new RangeBarData(60d, 50d, 580d));
        BarDataSet.add(new RangeBarData(70d, 110d, 380d));
        BarDataSet.add(new RangeBarData(80d, 160d, 480d));
        BarDataSet.add(new RangeBarData(90d, 100d, 280d));
        BarDataSet.add(new RangeBarData(100d, 160d, 580d));
        BarDataSet.add(new RangeBarData(110d, 90d, 280d));
        BarDataSet.add(new RangeBarData(120d, 50d, 400d));
        BarDataSet.add(new RangeBarData(130d, 100d, 290d));
        BarDataSet.add(new RangeBarData(140d, 160d, 280d));
        BarDataSet.add(new RangeBarData(150d, 90d, 380d));
        BarDataSet.add(new RangeBarData(160d, 110d, 360d));
        BarDataSet.add(new RangeBarData(170d, 50d, 350d));
        BarDataSet.add(new RangeBarData(180d, 90d, 280d));
        BarDataSet.add(new RangeBarData(190d, 110d, 580d));
        BarDataSet.add(new RangeBarData(200d, 160d, 280d));
        BarDataSet.add(new RangeBarData(210d, 80d, 400d));
        BarDataSet.add(new RangeBarData(220d, 100d, 450d));
        BarDataSet.add(new RangeBarData(230d, 160d, 400d));
        BarDataSet.add(new RangeBarData(240d, 100d, 280d));
    }

    /**
     * 图表标签（即X轴数据设置）
     */
    private void chartLabels() {
        chartLabels.add("0时");
        chartLabels.add("1时");
        chartLabels.add("2时");
        chartLabels.add("3时");
        chartLabels.add("4时");
        chartLabels.add("5时");
        chartLabels.add("6时");
        chartLabels.add("7时");
        chartLabels.add("8时");
        chartLabels.add("9时");
        chartLabels.add("10时");
        chartLabels.add("11时");
        chartLabels.add("12时");
        chartLabels.add("13时");
        chartLabels.add("14时");
        chartLabels.add("15时");
        chartLabels.add("16时");
        chartLabels.add("17时");
        chartLabels.add("18时");
        chartLabels.add("19时");
        chartLabels.add("20时");
        chartLabels.add("21时");
        chartLabels.add("22时");
        chartLabels.add("23时");
    }
}