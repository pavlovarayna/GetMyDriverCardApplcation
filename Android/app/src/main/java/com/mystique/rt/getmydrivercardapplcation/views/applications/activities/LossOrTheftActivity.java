package com.mystique.rt.getmydrivercardapplcation.views.applications.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mystique.rt.getmydrivercardapplcation.R;
import com.mystique.rt.getmydrivercardapplcation.apputils.Navigator;
import com.mystique.rt.getmydrivercardapplcation.views.applications.adapters.LossOrTheftAdapter;


public class LossOrTheftActivity extends AppCompatActivity implements Navigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lossortheft);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        LossOrTheftAdapter lossOrTheftAdapter = new LossOrTheftAdapter(getSupportFragmentManager());
        viewPager.setAdapter(lossOrTheftAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void navigateToActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
