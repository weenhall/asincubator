package com.example.wen.asincubator.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wen.asincubator.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DashBoardFragment extends Fragment {

    private static final String TAG = "DashBoardFragment";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LineChart mLineChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    public void initViews() {
        mSwipeRefreshLayout = getView().findViewById(R.id.dashboard);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "Refreshing...");
                initLineChart();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        initLineChart();
    }

    public void initLineChart() {
        List<Map<String, Integer>> list = new ArrayList<>();
        Random random = new Random();
        Map<String, Integer> map = null;
        for (int i = 0; i < 10; i++) {
            map = new HashMap<>();
            map.put("year", 2010 + i);
            map.put("data", random.nextInt(100));
            list.add(map);
        }
        //first
        mLineChart = (LineChart) getView().findViewById(R.id.linechart);
        List<Entry> entries = new ArrayList<Entry>();
        for (Map<String, Integer> data : list) {
            entries.add(new Entry(data.get("year"), data.get("data")));
        }
        //second
        LineDataSet dataSet = new LineDataSet(entries, "年利润");
        dataSet.setColor(Color.parseColor("#F4606C"));
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        //final
        LineData lineData = new LineData(dataSet);
        //set chart style
        mLineChart.setBackgroundColor(Color.parseColor("#D6D5B7"));//设置背景颜色
        Description dp = new Description();
        dp.setText("折线图");
        dp.setPosition(1, 2);
        mLineChart.setDescription(dp);//设置图表描述
        mLineChart.setNoDataText("无可用数据");
        mLineChart.setDrawGridBackground(false);//是否显示网格
        mLineChart.setDrawBorders(false);//是否显示边界
        mLineChart.setDragEnabled(false);//是否拖拽
        mLineChart.setTouchEnabled(true);;//是否有触摸事件
        //设置XY轴动画效果
        mLineChart.animateX(1500);
        mLineChart.animateY(2500);
        mLineChart.setData(lineData);
        //set xy
        XAxis xAxis = mLineChart.getXAxis();
        YAxis leftYaxis=mLineChart.getAxisLeft();
        YAxis rightYaxis=mLineChart.getAxisRight();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        leftYaxis.setAxisMinimum(0f);
        leftYaxis.setDrawGridLines(true);
        leftYaxis.enableGridDashedLine(10f, 10f, 0f);
        rightYaxis.setAxisMinimum(0f);
        rightYaxis.setDrawGridLines(false);
        rightYaxis.setEnabled(false);
        //set lengend
        Legend legend=mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //设置图例在左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        mLineChart.invalidate();
    }
}
