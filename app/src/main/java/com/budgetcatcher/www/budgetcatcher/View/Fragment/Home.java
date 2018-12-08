package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Home extends Fragment {

    @BindView(R.id.projected_balance_layout)
    CoordinatorLayout projectedBalanceLayoutBottomSheet;
    @BindView(R.id.picker)
    NumberPicker picker;

    private BottomSheetBehavior projectedBalanceBottomSheetBehavior;
    private String[] date = {"8/13/18 - 8/19/18", "8/20/18 - 8/26/18", "8/27/17 - 9/2/18", "9/3/18 - 9/9/19", "9/10/18 - 9/16/18", "9/17/18 - 9/23/18", "9/24/19 - 9/30/18"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null)
            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Home");

        hideSoftKeyboard();
        projectedBalanceBottomSheetBehavior = BottomSheetBehavior.from(projectedBalanceLayoutBottomSheet);
        projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        picker.setMinValue(0);
        picker.setMaxValue(date.length - 1);
        picker.setDisplayedValues(date);

        return rootView;
    }

    @OnClick({R.id.projected_balance, R.id.done_bottom_sheet})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.projected_balance: {

                projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
            }

            case R.id.done_bottom_sheet: {

                final Activity activity = getActivity();

                final AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setCancelable(true);

                LayoutInflater inflater = this.getLayoutInflater();
                View alertView = inflater.inflate(R.layout.projected_balance_alert_box, null);
                builder1.setView(alertView);

                TextView weekSum = alertView.findViewById(R.id.week_sum);
                TextView weekNumber = alertView.findViewById(R.id.week_number);

                weekNumber.setText(date[picker.getValue()]);

                final AlertDialog alert11 = builder1.create();

                alert11.show();

                break;
            }

        }

    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getActivity() != null) {
            if (getActivity().getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) (getActivity()).getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager != null)
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        }

    }
}
