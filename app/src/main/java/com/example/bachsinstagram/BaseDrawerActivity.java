package com.example.bachsinstagram;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;

public class BaseDrawerActivity extends BaseActivity {

    @BindView(R.id.drawlayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nvgt_drawerlayout)
    NavigationView navigationView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.drawer_activity);
        ViewGroup group= findViewById(R.id.fdrawerlayout);
        LayoutInflater.from(this).inflate(layoutResID,group,true);
        bindView();
        setupHeader();
    }

    private void setupHeader() {
        View view=navigationView.getHeaderView(0);
        view.findViewById(R.id.head_view_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGlobalMenuHeaderClick(view);
            }
        });
    }

    private void onGlobalMenuHeaderClick(View view) {
        drawerLayout.closeDrawer(Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                view.getLocationOnScreen(startingLocation);
                startingLocation[0]+= view.getWidth();
                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                overridePendingTransition(0, 0);
            }
        },200);
    }

    @Override
    public void setToolbar() {
        super.setToolbar();
        if (getToolbar()!=null){
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(Gravity.LEFT);

                }
            });
        }
    }
}
