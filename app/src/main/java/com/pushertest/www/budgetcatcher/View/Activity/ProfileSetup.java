package com.pushertest.www.budgetcatcher.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.pushertest.www.budgetcatcher.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileSetup extends AppCompatActivity {

    @BindView(R.id.financial_goal)
    Spinner financialGoalSpinner;
    @BindView(R.id.risk_level)
    Spinner riskLevelSpinner;
    @BindView(R.id.skill_level)
    Spinner skillLevelSpinner;

    ArrayList<String> financialGoal;
    ArrayList<String> riskLevel;
    ArrayList<String> skillLevel;

    ArrayAdapter<String> financialGoalAdapter;
    ArrayAdapter<String> riskLevelAdapter;
    ArrayAdapter<String> skillLevelAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        ButterKnife.bind(this);

        financialGoalList();
        riskLevelList();
        skillLevelList();

        financialGoalAdapter = new ArrayAdapter<String>(ProfileSetup.this, R.layout.spinner_item, R.id.spinner_item_text, financialGoal);
        financialGoalSpinner.setAdapter(financialGoalAdapter);

        riskLevelAdapter = new ArrayAdapter<String>(ProfileSetup.this, R.layout.spinner_item, R.id.spinner_item_text, riskLevel);
        riskLevelSpinner.setAdapter(riskLevelAdapter);

        skillLevelAdapter = new ArrayAdapter<String>(ProfileSetup.this, R.layout.spinner_item, R.id.spinner_item_text, skillLevel);
        skillLevelSpinner.setAdapter(skillLevelAdapter);

    }

    public void skillLevelList() {

        financialGoal = new ArrayList<>();
        financialGoal.add("SET Financial Goals");
        financialGoal.add("Grow Your Saving");
        financialGoal.add("Increase Spendable Cash");
        financialGoal.add("Reduce Your Debt");

    }

    public void financialGoalList() {

        riskLevel = new ArrayList<>();
        riskLevel.add("What is Your Risk Level?");
        riskLevel.add("Grow Your Saving");
        riskLevel.add("Increase Spendable Cash");
        riskLevel.add("Reduce Your Debt");

    }

    public void riskLevelList() {

        skillLevel = new ArrayList<>();
        skillLevel.add("What is Your Financial Skill Level");
        skillLevel.add("Grow Your Saving");
        skillLevel.add("Increase Spendable Cash");
        skillLevel.add("Reduce Your Debt");

    }

    @OnClick({R.id.save})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.save: {

                startActivity(new Intent(ProfileSetup.this, MainActivity.class));
                finish();

                break;
            }

        }

    }

}
