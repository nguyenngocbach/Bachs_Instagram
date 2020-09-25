package com.example.bachsinstagram.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bachsinstagram.R;
import com.example.bachsinstagram.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIAnimator extends DefaultItemAnimator {

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimationsMap = new HashMap<>();
    Map<RecyclerView.ViewHolder, AnimatorSet> heartAnimationsMap = new HashMap<>();

    private int lastAddAnimatedItem = -2;


    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        Log.d("bachdz","okkk");
        return true;
    }


    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state,
                                                     @NonNull RecyclerView.ViewHolder viewHolder,
                                                     int changeFlags, @NonNull List<Object> payloads) {

        if(changeFlags==FLAG_CHANGED){
            for (Object payload : payloads)
                if (payload instanceof String){
                    return new FeedItemHolderInfo((String) payload);
                }
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {

        if (holder.getAdapterPosition()>lastAddAnimatedItem){
            lastAddAnimatedItem++;
            runEnterAnimation((UIadapter.ViewHolder)holder);
            return false;
        }
        //dispatchAddFinished(holder);
        dispatchAddFinished(holder);
        return false;
    }

    private void runEnterAnimation(UIadapter.ViewHolder holder) {

        int screenHeight= Utils.getScreenHeight(holder.itemView.getContext());
        holder.itemView.setTranslationY(screenHeight);
        holder.itemView.animate()
                .translationY(0)
                .setDuration(700)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dispatchAddFinished(holder);
                    }
                })
                .start();

    }


    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo, @NonNull ItemHolderInfo postInfo) {
        cancelCurrentAnimationIfExists(newHolder);

        if (preInfo instanceof FeedItemHolderInfo){
            FeedItemHolderInfo info= (FeedItemHolderInfo) preInfo;
            UIadapter.ViewHolder holder= (UIadapter.ViewHolder) newHolder;
            animateHeartButton(holder);
            updateLikesCounter(holder);
            if (UIadapter.ACTION_LIKE_IMAGE_CLICKED.equals(info.updateAction)) {
                animatePhotoLike(holder);
            }
        }
        return false;
    }



    private void cancelCurrentAnimationIfExists(RecyclerView.ViewHolder item) {
        if (likeAnimationsMap.containsKey(item)) {
            likeAnimationsMap.get(item).cancel();
        }
        if (heartAnimationsMap.containsKey(item)) {
            heartAnimationsMap.get(item).cancel();
        }
    }

    private void animateHeartButton(UIadapter.ViewHolder holder) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(holder.btnLike, "rotation", 0f, 360f);
        rotationAnim.setDuration(300);
        rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.btnLike, "scaleX", 0.2f, 1f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.btnLike, "scaleY", 0.2f, 1f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);

        bounceAnimY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                holder.btnLike.setImageResource(R.drawable.ic_favorite_black_24dp);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                heartAnimationsMap.remove(holder);
                dispatchChangeFinishedIfAllAnimationsEnded(holder);
            }
        });

        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
        animatorSet.start();

        heartAnimationsMap.put(holder, animatorSet);
    }


    // working after.
    private void updateLikesCounter(UIadapter.ViewHolder holder) {
    }


    private void animatePhotoLike(UIadapter.ViewHolder holder) {
        holder.viewMain.setVisibility(View.VISIBLE);
        holder.iconMain.setVisibility(View.VISIBLE);

        holder.viewMain.setScaleY(0.1f);
        holder.viewMain.setScaleX(0.1f);
        holder.viewMain.setAlpha(0.7f);
        holder.iconMain.setScaleY(0.1f);
        holder.iconMain.setScaleX(0.1f);

        AnimatorSet set= new AnimatorSet();

        ObjectAnimator slXViewMain= ObjectAnimator.ofFloat(holder.viewMain,"scaleX",0.1f,1.0f);
        slXViewMain.setDuration(200);
        slXViewMain.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator slYViewMain= ObjectAnimator.ofFloat(holder.viewMain,"scaleY",0.1f,1.0f);
        slYViewMain.setDuration(200);
        slYViewMain.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator alphaViewMain= ObjectAnimator.ofFloat(holder.viewMain,"alpha",0.7f,0.0f);
        alphaViewMain.setDuration(200);
        alphaViewMain.setStartDelay(150);
        alphaViewMain.setInterpolator(DECCELERATE_INTERPOLATOR);



        ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(holder.iconMain, "scaleY", 0.1f, 1f);
        imgScaleUpYAnim.setDuration(300);
        imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(holder.iconMain, "scaleX", 0.1f, 1f);
        imgScaleUpXAnim.setDuration(300);
        imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);


        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(holder.iconMain, "scaleY", 1f, 0f);
        imgScaleDownYAnim.setDuration(300);
        imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(holder.iconMain, "scaleX", 1f, 0f);
        imgScaleDownXAnim.setDuration(300);
        imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        set.playTogether(slYViewMain,slXViewMain,alphaViewMain,imgScaleUpYAnim,imgScaleUpXAnim);
        set.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                likeAnimationsMap.remove(holder);
                resetLikeAnimationState(holder);
                dispatchChangeFinishedIfAllAnimationsEnded(holder);
            }
        });
        set.start();
    }

    private void resetLikeAnimationState(UIadapter.ViewHolder holder) {
        holder.iconMain.setVisibility(View.INVISIBLE);
        holder.viewMain.setVisibility(View.INVISIBLE);
    }

    private void dispatchChangeFinishedIfAllAnimationsEnded(UIadapter.ViewHolder holder) {
        if (likeAnimationsMap.containsKey(holder) || heartAnimationsMap.containsKey(holder)) {
            return;
        }

        dispatchAnimationFinished(holder);
    }



    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {

        super.endAnimation(item);
        cancelCurrentAnimationIfExists(item);
    }

    @Override
    public void endAnimations() {
        super.endAnimations();
        for (AnimatorSet animatorSet : likeAnimationsMap.values()) {
            animatorSet.cancel();
        }
    }

    public static class FeedItemHolderInfo extends ItemHolderInfo {
        public String updateAction;

        public FeedItemHolderInfo(String updateAction) {
            this.updateAction = updateAction;
        }
    }
}
