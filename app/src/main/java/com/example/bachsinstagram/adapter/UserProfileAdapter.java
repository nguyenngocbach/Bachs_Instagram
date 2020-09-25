package com.example.bachsinstagram.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.bachsinstagram.R;
import com.example.bachsinstagram.util.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.ViewHolderUser> {

    private static final int PHOTO_ANIMATION_DELAY = 600;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    private Context mContext;
    private int sizenhe=0;
    private List<String> mFicture= new ArrayList<>();

    private boolean lockedAnimations = false;
    private int lastAnimatedItem = -1;

    public UserProfileAdapter(Context mContext) {
        this.mContext = mContext;
        sizenhe= Utils.getScreenWidth(mContext)/3;
        Log.d("bachdzcheck"," "+sizenhe);
        mFicture= Arrays.asList(mContext.getResources().getStringArray(R.array.user_photos));
    }

    @NonNull
    @Override
    public UserProfileAdapter.ViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_user,parent,false);
        StaggeredGridLayoutManager.LayoutParams params=
                (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        params.height=sizenhe;
        params.width=sizenhe;
        params.setFullSpan(false);
        view.setLayoutParams(params);
        return new ViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileAdapter.ViewHolderUser holder, int position) {
        //holder.bind(mFicture.get(position));
        Log.d("bachdzcheck"," "+sizenhe);

        Picasso.with(mContext)
                .load(mFicture.get(position))
                .resize(sizenhe, sizenhe)
                .centerCrop()
                .into(holder.img, new Callback() {
                    @Override
                    public void onSuccess() {
                        animatePhoto(holder);
                    }

                    @Override
                    public void onError() {

                    }
                });
        if (lastAnimatedItem < position) lastAnimatedItem = position;

    }

    private void animatePhoto(ViewHolderUser holder) {
        if (!lockedAnimations) {
            if (lastAnimatedItem== holder.getPosition()){
                setLockedAnimations(true);
            }

            long animationDelay = PHOTO_ANIMATION_DELAY + holder.getPosition() * 30;
            holder.frameLayout.setScaleY(0);
            holder.frameLayout.setScaleX(0);

            holder.frameLayout.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setDuration(1000)
                    .setStartDelay(animationDelay)
                    .setInterpolator(INTERPOLATOR)
                    .start();

        }
    }

    @Override
    public int getItemCount() {
        return mFicture.size();
    }



    public class ViewHolderUser extends RecyclerView.ViewHolder {
        @BindView(R.id.frame_user)
        FrameLayout frameLayout;
        @BindView(R.id.img_item_user)
        ImageView img;
        public ViewHolderUser(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    public void setLockedAnimations(boolean lockedAnimations) {
        this.lockedAnimations = lockedAnimations;
    }

}
