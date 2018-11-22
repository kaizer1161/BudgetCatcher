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

        skillLevel = new ArrayList<>();
        skillLevel.add("Select your financial skill level");
        skillLevel.add("1 - Not good at numbers!");
        skillLevel.add("2 - I can count to 10!");
        skillLevel.add("3 - I'm as good as the next person");
        skillLevel.add("4 - I aced collage math");
        skillLevel.add("5 - Professional");

    }

    public void financialGoalList() {

        financialGoal = new ArrayList<>();
        financialGoal.add("Select financial goals");
        financialGoal.add("Add to Savings");
        financialGoal.add("Add to Cash");
        financialGoal.add("Reduce Your Debt");

    }

    public void riskLevelList() {

        riskLevel = new ArrayList<>();
        riskLevel.add("Select your risk level");
        riskLevel.add("Take No Risk");
        riskLevel.add("Mild Risk is Ok");
        riskLevel.add("Moderate & Ready to Play");
        riskLevel.add("All or NOTHING");


    }

    @OnClick({R.id.save})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.save: {

                startActivity(new Intent(ProfileSetup.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                break;
            }

        }

    }

}
