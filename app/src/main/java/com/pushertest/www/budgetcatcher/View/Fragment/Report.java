package com.pushertest.www.budgetcatcher.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pushertest.www.budgetcatcher.R;
import com.pushertest.www.budgetcatcher.View.Activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;

public class Report extends Fragment {

    private PieChart pieChart;
    private BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null)
            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Report");

        pieChart = rootView.findViewById(R.id.pieChart);
        barChart = rootView.findViewById(R.id.barChart);

        pieChart.setRotationEnabled(true);
        pieChart.getDescription().setEnabled(false);
        //pieChart.setUsePercentValues(true);
        pieChart.setHoleColor(Color.parseColor("#703aa9ae"));
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(60f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(false);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextColor(Color.WHITE);
        legend.setWordWrapEnabled(true);

        addDataSet();
        addBarChart();

        return rootView;
    }

    private void addBarChart() {

        ArrayList<BarEntry> NoOfEmp = new ArrayList<>();

        NoOfEmp.add(new BarEntry(0f, 5));
        NoOfEmp.add(new BarEntry(1f, 2));
        NoOfEmp.add(new BarEntry(2f, 5));
        NoOfEmp.add(new BarEntry(3f, 0));
        NoOfEmp.add(new BarEntry(4f, 7));
        NoOfEmp.add(new BarEntry(5f, 5));
        NoOfEmp.add(new BarEntry(6f, 6));
        NoOfEmp.add(new BarEntry(7f, 5));
        NoOfEmp.add(new BarEntry(8f, 2));
        NoOfEmp.add(new BarEntry(9f, 0));
        NoOfEmp.add(new BarEntry(10f, 6));
        NoOfEmp.add(new BarEntry(11f, 1));
        NoOfEmp.add(new BarEntry(12f, 9));
        NoOfEmp.add(new BarEntry(13f, 7));
        NoOfEmp.add(new BarEntry(14f, 4));
        NoOfEmp.add(new BarEntry(15f, 2));
        NoOfEmp.add(new BarEntry(16f, 3));
        NoOfEmp.add(new BarEntry(17f, 4));
        NoOfEmp.add(new BarEntry(18f, 1));

        BarDataSet barDataSet = new BarDataSet(NoOfEmp, "");
        barDataSet.setDrawValues(false);
        barChart.animateY(3000);
        BarData data = new BarData(barDataSet);
        int[] colors = {getResources().getColor(R.color.hinoki), getResources().getColor(R.color.deep_sea_dive)};
        barDataSet.setColors(ColorTemplate.createColors(colors));
        barChart.setData(data);
        barChart.getLegend().setEnabled(false);
        barChart.getXAxis().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getDescription().setEnabled(false);

    }

    private void addDataSet() {

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(20.79f, "20.79% Home"));
        entries.add(new PieEntry(17.11f, "17.11% Food"));
        entries.add(new PieEntry(2.91f, "2.91% Medical"));
        entries.add(new PieEntry(7.48f, "7.48% Auto"));
        entries.add(new PieEntry(8.31f, "8.31% Utilities"));
        entries.add(new PieEntry(29.10f, "29.10% Travel"));
        entries.add(new PieEntry(2.66f, "2.66% Entertainment"));
        entries.add(new PieEntry(6.68f, "6.68% Personal Items"));
        entries.add(new PieEntry(4.99f, "4.99% Other"));

        PieDataSet set = new PieDataSet(entries, "");
        set.setSliceSpace(4);
        set.setDrawValues(false);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.LTGRAY);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
        colors.add(Color.DKGRAY);

        set.setColors(colors);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

    }

}
