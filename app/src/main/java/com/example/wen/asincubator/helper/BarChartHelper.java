package com.example.wen.asincubator.helper;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2018/7/9.
 */

public class BarChartHelper {
    private BarChart mBarChart;
    private YAxis leftYaxis;
    private YAxis rightYaxis;
    private XAxis mXAxis;

    public BarChartHelper(BarChart barChart) {
        this.mBarChart=barChart;
        leftYaxis=mBarChart.getAxisLeft();
        rightYaxis=mBarChart.getAxisRight();
        mXAxis=mBarChart.getXAxis();
    }

    private void initBarChart(){
        //background-color
        mBarChart.setBackgroundColor(Color.GRAY);
        //是否绘制网格
        mBarChart.setDrawGridBackground(false);
        //背景阴影
        mBarChart.setDrawBarShadow(false);
        mBarChart.setHighlightFullBarEnabled(false);
        //是否显示边界
        mBarChart.setDrawBorders(true);
        //设置动画效果
        mBarChart.animateY(1000, Easing.EasingOption.Linear);
        mBarChart.animateX(1000,Easing.EasingOption.Linear);
        //图例
        Legend legend=mBarChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        //xy
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mXAxis.setGranularity(1f);
        leftYaxis.setAxisMinimum(0f);
        leftYaxis.setDrawGridLines(false);
        rightYaxis.setAxisMinimum(0f);
        rightYaxis.setDrawGridLines(false);
        rightYaxis.setEnabled(false);
    }

    /**
     * 单条柱状图
     */
    public void singleBarChart(List<Float> xDatas,List<Float> yDatas,String label,int color){
        initBarChart();
        ArrayList<BarEntry> entries=new ArrayList<>();
        for (int i = 0; i < xDatas.size(); i++) {
            entries.add(new BarEntry(xDatas.get(i),yDatas.get(i)));
        }

        //设置柱状图数据和样式
        BarDataSet barDataSet=new BarDataSet(entries,label);
        barDataSet.setColor(color);
        barDataSet.setValueTextSize(9f);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);

        ArrayList<IBarDataSet> dataSets=new ArrayList<>();
        dataSets.add(barDataSet);
        BarData barData=new BarData(dataSets);

        //x轴刻度
        mXAxis.setLabelCount(xDatas.size()-1,false);
        mBarChart.setData(barData);
    }

    /**
     * 多条柱状图
     */
    public void mutilBarChart(List<Float> xDatas,List<List<Float>> yDatas,List<String> labels,List<Integer> colours){
        initBarChart();
        BarData data=new BarData();
        for (int i = 0; i < yDatas.size(); i++) {
            ArrayList<BarEntry> entries=new ArrayList<>();
            for (int j = 0; j < yDatas.get(i).size(); j++) {
                entries.add(new BarEntry(xDatas.get(j),yDatas.get(i).get(j)));
            }
            BarDataSet barDataSet=new BarDataSet(entries,labels.get(i));

            barDataSet.setColor(colours.get(i));
            barDataSet.setValueTextColor(colours.get(i));
            barDataSet.setValueTextSize(10f);
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            data.addDataSet(barDataSet);
        }
        //柱状图条数
        int count=yDatas.size();
        //分组柱状图间距
        float groupSpace=0.12f;
        float barSpace = (float) ((1 - 0.12) / count / 10); // x4 DataSet
        float barWidth = (float) ((1 - 0.12) / count / 10 * 9); // x4 DataSet

        // (0.2 + 0.02) * 4 + 0.08 = 1.00 -> interval per "group"
        mXAxis.setLabelCount(xDatas.size() - 1, false);
        data.setBarWidth(barWidth);
        data.groupBars(0,groupSpace,barSpace);
        mBarChart.setData(data);
    }

    /**
     * 设置Y轴值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setYAxis(float max, float min, int labelCount) {
        if (max < min) {
            return;
        }
        leftYaxis.setAxisMaximum(max);
        leftYaxis.setAxisMinimum(min);
        leftYaxis.setLabelCount(labelCount, false);

        leftYaxis.setAxisMaximum(max);
        rightYaxis.setAxisMinimum(min);
        rightYaxis.setLabelCount(labelCount, false);
        mBarChart.invalidate();
    }

    /**
     * 设置X轴的值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setXAxis(float max, float min, int labelCount) {
        mXAxis.setAxisMaximum(max);
        mXAxis.setAxisMinimum(min);
        mXAxis.setLabelCount(labelCount, false);

        mBarChart.invalidate();
    }

    /**
     * 设置高限制线
     *
     * @param high
     * @param name
     */
    public void setHightLimitLine(float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine hightLimit = new LimitLine(high, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        leftYaxis.addLimitLine(hightLimit);
        mBarChart.invalidate();
    }

    /**
     * 设置低限制线
     *
     * @param low
     * @param name
     */
    public void setLowLimitLine(int low, String name) {
        if (name == null) {
            name = "低限制线";
        }
        LimitLine hightLimit = new LimitLine(low, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        leftYaxis.addLimitLine(hightLimit);
        mBarChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        Description description = new Description();
        description.setText(str);
        description.setEnabled(false);
        mBarChart.setDescription(description);
        mBarChart.invalidate();
    }
}
