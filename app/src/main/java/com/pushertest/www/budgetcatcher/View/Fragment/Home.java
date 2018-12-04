package com.pushertest.www.budgetcatcher.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.pushertest.www.budgetcatcher.R;
import com.pushertest.www.budgetcatcher.View.Activity.MainActivity;

import java.util.Objects;

import butterknife.ButterKnife;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Home extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null)
            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Home");

        hideSoftKeyboard();

        return rootView;
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
