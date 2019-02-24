package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.BarChartActual;
import com.budgetcatcher.www.budgetcatcher.Model.BarChartBudget;
import com.budgetcatcher.www.budgetcatcher.Model.BarChartData;
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
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class Report extends Fragment {

    private PieChart pieChart;
    private BarChart barChart;
    @BindView(R.id.header_top)
    TextView headerTop;
    ArrayList<BarEntry> budget = new ArrayList<>();
    ArrayList<BarEntry> actual = new ArrayList<>();
    ArrayList<BarChartActual> actualArrayList = new ArrayList<>();
    ArrayList<BarChartBudget> budgetArrayList = new ArrayList<>();
    ProgressDialog pieChartDialog, barChartDialog;
    /*@BindView(R.id.header_bottom)
    TextView headerBottom;*/
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

        pieChartDialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.loading), true);
        pieChartDialog.dismiss();

        barChartDialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.loading), true);
        barChartDialog.dismiss();

        pieChart = rootView.findViewById(R.id.pieChart);
        barChart = rootView.findViewById(R.id.barChart);

        headerTop.setText("Year of 2019");
//        headerBottom.setText("2018 - 2019");

        addPieChart();
        addBarChart();

        return rootView;
    }

    private void addBarChart() {

        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false);

        // empty labels so that the names are spread evenly
        /*final String[] labels = {"", "Jan", "Feb", "Mar", "Apr", "May", "June", "july", "Aug", "Sep", "Oct", "Nov", "Dec", ""};
        IAxisValueFormatter xAxisFormatter = new LabelFormatter(barChart, labels);*/
        final XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(10);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(1f);
        /*xAxis.setValueFormatter(xAxisFormatter);*/

        final YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
//        leftAxis.setGranularity(10);
        /*leftAxis.setLabelCount(labels.length, true);*/
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        barChart.getAxisRight().setEnabled(false);

        Legend barChartLegend = barChart.getLegend();
        barChartLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        barChartLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        barChartLegend.setOrientation(Legend.LegendOrientation.VERTICAL);
        barChartLegend.setDrawInside(false);
        barChartLegend.setTextColor(Color.WHITE);
        barChartLegend.setWordWrapEnabled(true);

        barChartDialog.show();

        BudgetCatcher.apiManager.getBarChart(userID, new QueryCallback<ArrayList<BarChartData>>() {
            @Override
            public void onSuccess(ArrayList<BarChartData> data) {

                barChartDialog.dismiss();

                /*float[] valOne = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120};
                float[] valTwo = {120, 110, 100, 90, 80, 70, 60, 50, 40, 30, 20, 10};*/

                String[] month = {"", "Jan", "Feb", "Mar", "Apr", "May", "June", "july", "Aug", "Sep", "Oct", "Nov", "Dec", ""};

                String[] labels = new String[data.size() + 2];

                labels[0] = "";

                for (int i = 0; i < data.size(); i++) {

                    BarChartData barChartData = data.get(i);

                    labels[i + 1] = month[barChartData.getMonths()];

                    actualArrayList.add(i, new BarChartActual(barChartData.getYears(), barChartData.getMonths(), barChartData.getIncomeActual(), barChartData.getAllowanceActual(), barChartData.getBillsActual(), barChartData.getIncidentalTotal()));

                    budgetArrayList.add(i, new BarChartBudget(barChartData.getYears(), barChartData.getMonths(), barChartData.getIncomeBudget(), barChartData.getAllowanceBudget(), barChartData.getBillsBudget()));

                    actual.add(new BarEntry(i, actualArrayList.get(i).getCashDeficit()));
                    budget.add(new BarEntry(i, budgetArrayList.get(i).getCashDeficit()));

                }

                labels[data.size() + 1] = "";

                IAxisValueFormatter xAxisFormatter = new LabelFormatter(barChart, labels);
                xAxis.setValueFormatter(xAxisFormatter);
                leftAxis.setLabelCount(labels.length, true);

                BarDataSet set1 = new BarDataSet(budget, "Budget");
                set1.setColor(getResources().getColor(R.color.deep_sea_dive));
                BarDataSet set2 = new BarDataSet(actual, "Actual");
                set2.setColor(getResources().getColor(R.color.hinoki));

                set1.setHighlightEnabled(true);
                set1.setDrawValues(true);

                set2.setHighlightEnabled(true);
                set2.setDrawValues(true);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);
                dataSets.add(set2);

                BarData barData = new BarData(dataSets);
                float groupSpace = 0.2f;
                float barSpace = 0f;
                float barWidth = 0.1f;
                barChart.setExtraBottomOffset(5f);
                barChart.setExtraRightOffset(5f);
                // (barSpace + barWidth) * 5 + groupSpace = 1
                // multiplied by 5 because there are 5 five bars
                // labels will be centered as long as the equation is satisfied
                barData.setBarWidth((barSpace + barWidth) * 2 + groupSpace);
                // so that the entire chart is shown when scrolled from right to left
                xAxis.setAxisMaximum(labels.length - 1.1f);
                barChart.setData(barData);
                barChart.setScaleEnabled(false);
                barChart.setTouchEnabled(true);
                barChart.setHighlightFullBarEnabled(true);
                barChart.setVisibleXRangeMaximum(4);
                barChart.groupBars(1f, groupSpace, barSpace);
                barChart.invalidate();

            }

            @Override
            public void onFail() {

                barChartDialog.dismiss();

            }

            @Override
            public void onError(Throwable th) {

                barChartDialog.dismiss();
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

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                if ((h.getDataSetIndex() % 2) == 0) {

                    budgetAlertDetail((int) h.getX() - 1);

                } else {

                    actualAlertDetail((int) h.getX() - 1);

                }


            }

            @Override
            public void onNothingSelected() {

                Log.d("Report", "onNothingSelected: ");


            }
        });

    }

    private void addPieChart() {

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

        pieChartDialog.show();

        BudgetCatcher.apiManager.getPieChart(userID, new QueryCallback<ArrayList<PieChartData>>() {
            @Override
            public void onSuccess(ArrayList<PieChartData> data) {

                pieChartDialog.dismiss();

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

                if (getActivity() != null) {
                    ArrayList<Integer> colors = new ArrayList<>();
                    colors.add(getActivity().getResources().getColor(R.color.hinoki));
                    colors.add(getActivity().getResources().getColor(R.color.aquitant));
                    colors.add(getActivity().getResources().getColor(R.color.deep_sea_dive));
                    set.setColors(colors);

                }

                PieData pieData = new PieData(set);
                pieChart.setData(pieData);
                pieChart.invalidate(); // refresh

            }

            @Override
            public void onFail() {

                pieChartDialog.dismiss();

            }

            @Override
            public void onError(Throwable th) {

                pieChartDialog.dismiss();
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

    private void actualAlertDetail(int position) {

        if (getContext() != null) {

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setCancelable(true);

            LayoutInflater inflater = getLayoutInflater();
            View alertView = inflater.inflate(R.layout.actual_alert_box, null);
            builder1.setView(alertView);
            AlertDialog alertDialog = builder1.create();

            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getMonths();

            TextView actualHeader = alertView.findViewById(R.id.actual_header);
            TextView income = alertView.findViewById(R.id.income);
            TextView allowance = alertView.findViewById(R.id.allowance);
            TextView bill = alertView.findViewById(R.id.bill);
            TextView cashDeficit = alertView.findViewById(R.id.cash_deficit);
            TextView incidentals = alertView.findViewById(R.id.incidentals);

            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);

            income.setText(String.format("$%s", format.format(actualArrayList.get(position).getIncomeActual())));
            allowance.setText(String.format("$%s", format.format(actualArrayList.get(position).getAllowanceActual())));
            bill.setText(String.format("$%s", format.format(actualArrayList.get(position).getBillsActual())));
            cashDeficit.setText(String.format("$%s", format.format(actualArrayList.get(position).getCashDeficit())));
            incidentals.setText(String.format("$%s", format.format(actualArrayList.get(position).getIncidentalTotal())));
            actualHeader.setText(months[(actualArrayList.get(position).getMonths()) - 1] + " Actual");

            alertDialog.show();

        }

    }

    private void budgetAlertDetail(int position) {

        if (getContext() != null) {

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setCancelable(true);

            LayoutInflater inflater = getLayoutInflater();
            View alertView = inflater.inflate(R.layout.budget_alert_box, null);
            builder1.setView(alertView);
            AlertDialog alertDialog = builder1.create();

            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getMonths();

            TextView actualHeader = alertView.findViewById(R.id.actual_header);
            TextView income = alertView.findViewById(R.id.income);
            TextView allowance = alertView.findViewById(R.id.allowance);
            TextView bill = alertView.findViewById(R.id.bill);
            TextView cashDeficit = alertView.findViewById(R.id.cash_deficit);

            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);

            income.setText(String.format("$%s", format.format(budgetArrayList.get(position).getIncomeBudget())));
            allowance.setText(String.format("$%s", format.format(budgetArrayList.get(position).getAllowanceBudget())));
            bill.setText(String.format("$%s", format.format(budgetArrayList.get(position).getBillsBudget())));
            cashDeficit.setText(String.format("$%s", format.format(budgetArrayList.get(position).getCashDeficit())));
            actualHeader.setText(months[(actualArrayList.get(position).getMonths()) - 1] + " Budget");

            alertDialog.show();

        }

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
