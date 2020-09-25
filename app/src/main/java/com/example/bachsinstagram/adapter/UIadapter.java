package com.example.bachsinstagram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bachsinstagram.MainActivity;
import com.example.bachsinstagram.R;
import com.example.bachsinstagram.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UIadapter extends RecyclerView.Adapter<UIadapter.ViewHolder> {

    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";

    private Context mContext;
    private List<News> mNews;
    private OnNewsClickListener mOnNewsClickListener;

    public UIadapter(Context mContext, List<News> mNews, OnNewsClickListener mOnNewsClickListener) {
        this.mContext = mContext;
        this.mNews = mNews;
        this.mOnNewsClickListener = mOnNewsClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.news_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news= mNews.get(position);
        holder.bind(news);
        setupClickableViews(holder);
    }

    private void setupClickableViews(ViewHolder holder) {
        holder.imgMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position= holder.getAdapterPosition();
                notifyItemChanged(position,ACTION_LIKE_IMAGE_CLICKED);
                if (mContext instanceof MainActivity) {
                    //((MainActivity) mContext).showLikedSnackbar();
                }
            }
        });

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positon= holder.getAdapterPosition();
                notifyItemChanged(positon,ACTION_LIKE_BUTTON_CLICKED);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avater_adapter)
        ImageView avater;
        @BindView(R.id.title_adapter)
        TextView titleUI;
        @BindView(R.id.img_main)
        ImageView imgMain;
        @BindView(R.id.view_main)
        View viewMain;
        @BindView(R.id.icon_main)
        ImageView iconMain;
        @BindView(R.id.btnline)
        ImageView btnLike;
        @BindView(R.id.btncomment)
        ImageView btnComment;
        @BindView(R.id.btnmore)
        ImageView btnMore;
        @BindView(R.id.title_like)
        TextView titleLike;
        @BindView(R.id.status_main)
        TextView statusMain;

        private News news;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(News news) {
            this.news= news;
            avater.setImageResource(news.getImage());
            titleUI.setText(news.getTitle());
            imgMain.setImageResource(news.getImage());
            statusMain.setText(news.getStatus());
            titleLike.setText(news.getLike()+" Like");
        }
    }

    public interface OnNewsClickListener {

        void onCommentsClick(View v, int position);

        void onMoreClick(View v, int position);

        void onProfileClick(View v);
    }
}
