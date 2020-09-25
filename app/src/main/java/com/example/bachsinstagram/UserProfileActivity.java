package com.example.bachsinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bachsinstagram.adapter.UserProfileAdapter;
import com.example.bachsinstagram.view.RevealBackgroundView;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class UserProfileActivity extends BaseDrawerActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    @BindView(R.id.revealbackgound_view)
    RevealBackgroundView revealBackgroundView;
    @BindView(R.id.rvUserProfile)
    RecyclerView recyclerView;
    @BindView(R.id.tlUserProfileTabs)
    TabLayout tabLayout;

    @BindView(R.id.ivUserProfilePhoto)
    ImageView ivUserProfilePhoto;
    @BindView(R.id.vUserDetails)
    View vUserDetails;
    @BindView(R.id.btnFollow)
    Button btnFollow;
    @BindView(R.id.vUserStats)
    View vUserStats;
    @BindView(R.id.vUserProfileRoot)
    View vUserProfileRoot;

    private UserProfileAdapter userPhotosAdapter;



    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, UserProfileActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        
        setTablayout();
        setupUserProfileGrid();
        setupRevealBackground(savedInstanceState);
    }



    private void setTablayout() {
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_grid_on_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_list_black));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_place_black));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_label_black));
    }

    private void setupUserProfileGrid() {
        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                userPhotosAdapter.setLockedAnimations(true);
            }
        });
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        revealBackgroundView.setOnStateChangeListener(this);
        if (savedInstanceState==null){
            int[] startingLocation= getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            revealBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    revealBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                    revealBackgroundView.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            revealBackgroundView.setToFinishedFrame();
            userPhotosAdapter.setLockedAnimations(true);
        }
    }


    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED==state){
            recyclerView.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
            userPhotosAdapter= new UserProfileAdapter(this);
            recyclerView.setAdapter(userPhotosAdapter);
            animateUserProfileOptions();
            animateUserProfileHeader();
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            tabLayout.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }


    private void animateUserProfileOptions() {
        tabLayout.setTranslationY(-vUserProfileRoot.getHeight());
        tabLayout.animate().translationY(0).setDuration(300).setStartDelay(300).setInterpolator(INTERPOLATOR);
    }

    private void animateUserProfileHeader() {
        vUserProfileRoot.setTranslationY(-vUserProfileRoot.getHeight());
        ivUserProfilePhoto.setTranslationY(-ivUserProfilePhoto.getHeight());
        vUserDetails.setTranslationY(-vUserDetails.getHeight());
        vUserStats.setAlpha(0);

        vUserProfileRoot.animate().translationY(0).setDuration(300).setInterpolator(INTERPOLATOR);
        ivUserProfilePhoto.animate().translationY(0).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR);
        vUserDetails.animate().translationY(0).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR);
        vUserStats.animate().alpha(1).setDuration(200).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
    }
}
