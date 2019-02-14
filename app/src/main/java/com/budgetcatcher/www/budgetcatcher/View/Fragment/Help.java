package com.budgetcatcher.www.budgetcatcher.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Activity.MainActivity;

import java.util.Objects;

import butterknife.ButterKnife;

public class Help extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.help, container, false);
        ButterKnife.bind(this, rootView);

        if (getActivity() != null) {

            Objects.requireNonNull(((MainActivity) getActivity()).getSupportActionBar()).setTitle("Help");
            ((MainActivity) getActivity()).navigationView.setCheckedItem(R.id.nav_help);

        }

        return rootView;
    }

}
