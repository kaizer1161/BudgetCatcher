package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.PieChartData;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class Report extends Fragment {

    private PieChart pieChart;
    private BarChart barChart;
    @BindView(R.id.header_top)
    TextView headerTop;
    @BindView(R.id.header_bottom)
    TextView headerBottom;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Report");
            ((MainActivity) getActivity()).navigationView.setCheckedItem(R.id.nav_report);
            userID = getActivity().getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");
        }

        headerTop.setText("Year of 2019");
//        headerBottom.setText("2018 - 2019");

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

        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        // empty labels so that the names are spread evenly
        String[] labels = {"", "Jan", "Feb", "Mar", "Apr", "May", "June", "july", "Aug", "Sep", "Oct", "Nov", "Dec", ""};
        IAxisValueFormatter xAxisFormatter = new LabelFormatter(barChart, labels);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(10);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(10);
        leftAxis.setLabelCount(labels.length, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        barChart.getAxisRight().setEnabled(false);

        Legend barChartLegend = barChart.getLegend();
        barChartLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        barChartLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        barChartLegend.setOrientation(Legend.LegendOrientation.VERTICAL);
        barChartLegend.setDrawInside(true);
        barChartLegend.setTextColor(Color.WHITE);
        barChartLegend.setWordWrapEnabled(true);

        float[] valOne = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120};
        float[] valTwo = {120, 110, 100, 90, 80, 70, 60, 50, 40, 30, 20, 10};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        for (int i = 0; i < valOne.length; i++) {
            barOne.add(new BarEntry(i, valOne[i]));
            barTwo.add(new BarEntry(i, valTwo[i]));
        }

        BarDataSet set1 = new BarDataSet(barOne, "Budget");
        set1.setColor(getResources().getColor(R.color.deep_sea_dive));
        BarDataSet set2 = new BarDataSet(barTwo, "Actual");
        set2.setColor(getResources().getColor(R.color.hinoki));

        set1.setHighlightEnabled(true);
        set1.setDrawValues(true);

        set2.setHighlightEnabled(true);
        set2.setDrawValues(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        float groupSpace = 0.2f;
        float barSpace = 0f;
        float barWidth = 0.1f;
        barChart.setExtraBottomOffset(5f);
        // (barSpace + barWidth) * 5 + groupSpace = 1
        // multiplied by 5 because there are 5 five bars
        // labels will be centered as long as the equation is satisfied
        data.setBarWidth((barSpace + barWidth) * 2 + groupSpace);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length - 1.1f);
        barChart.setData(data);
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(true);
        barChart.setHighlightFullBarEnabled(true);
        barChart.setVisibleXRangeMaximum(2);
        barChart.groupBars(1f, groupSpace, barSpace);
        barChart.invalidate();

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                Log.d("Report", "onValueSelected: " + h.getX());
                Log.d("Report", "onValueSelected: " + h.getDataSetIndex());

            }

            @Override
            public void onNothingSelected() {

                Log.d("Report", "onNothingSelected: ");


            }
        });

    }

    private void addDataSet() {

        BudgetCatcher.apiManager.getPieChart(userID, new QueryCallback<ArrayList<PieChartData>>() {
            @Override
            public void onSuccess(ArrayList<PieChartData> data) {

                List<PieEntry> entries = new ArrayList<>();

                for (int i = 0; i < data.size(); i++) {

                    entries.add(new PieEntry(Float.valueOf(data.get(i).getPercentage()), data.get(i).getPercentage() + "% " + data.get(i).getCategory()));

                }

                /*entries.add(new PieEntry(17.11f, "17.11% Food"));
                entries.add(new PieEntry(2.91f, "2.91% Medical"));
                entries.add(new PieEntry(7.48f, "7.48% Auto"));
                entries.add(new PieEntry(8.31f, "8.31% Utilities"));
                entries.add(new PieEntry(29.10f, "29.10% Travel"));*/
        /*entries.add(new PieEntry(2.66f, "2.66% Entertainment"));
        entries.add(new PieEntry(6.68f, "6.68% Personal Items"));
        entries.add(new PieEntry(4.99f, "4.99% Other"));*/

                PieDataSet set = new PieDataSet(entries, "");
                set.setSliceSpace(4);
                set.setDrawValues(false);

                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.GRAY);
                colors.add(Color.BLUE);
                colors.add(Color.RED);
        /*colors.add(Color.GREEN);
        colors.add(Color.LTGRAY);*/
                colors.add(Color.CYAN);
                /*colors.add(Color.YELLOW);*/
                colors.add(Color.MAGENTA);
                colors.add(Color.DKGRAY);

                set.setColors(colors);
                PieData pieData = new PieData(set);
                pieChart.setData(pieData);
                pieChart.invalidate(); // refresh

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(Throwable th) {

                if (getActivity() != null) {
                    Log.e("SerVerErrAddBill", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(getActivity(), getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.server_reach_error), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private class LabelFormatter implements IAxisValueFormatter {

        String[] labels;
        BarLineChartBase<?> chart;

        LabelFormatter(BarLineChartBase<?> chart, String[] labels) {
            this.chart = chart;
            this.labels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (labels.length > (int) value) {
                return labels[(int) value];
            } else return null;
        }
    }

}
