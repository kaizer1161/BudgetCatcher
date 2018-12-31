package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.PieChartData;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;
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

        headerTop.setText("Year To Date");
        headerBottom.setText("2018 - 2019");

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

        NoOfEmp.add(new BarEntry(0f, 500));
        NoOfEmp.add(new BarEntry(1f, 250));
        NoOfEmp.add(new BarEntry(2f, 125));
        NoOfEmp.add(new BarEntry(3f, 0));
        NoOfEmp.add(new BarEntry(4f, 375));
        NoOfEmp.add(new BarEntry(5f, 500));
        NoOfEmp.add(new BarEntry(6f, 375));
        NoOfEmp.add(new BarEntry(7f, 500));
        NoOfEmp.add(new BarEntry(8f, 250));
        NoOfEmp.add(new BarEntry(9f, 0));
        NoOfEmp.add(new BarEntry(10f, 375));
        NoOfEmp.add(new BarEntry(11f, 125));
        NoOfEmp.add(new BarEntry(12f, 500));
        NoOfEmp.add(new BarEntry(13f, 375));
        NoOfEmp.add(new BarEntry(14f, 400));
        NoOfEmp.add(new BarEntry(15f, 250));
        NoOfEmp.add(new BarEntry(16f, 300));
        NoOfEmp.add(new BarEntry(17f, 400));
        NoOfEmp.add(new BarEntry(18f, 125));

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

            }
        });

    }

}
