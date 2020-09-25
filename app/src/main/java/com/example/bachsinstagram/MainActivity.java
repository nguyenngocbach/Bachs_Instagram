package com.example.bachsinstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.bachsinstagram.adapter.UIAnimator;
import com.example.bachsinstagram.adapter.UIadapter;
import com.example.bachsinstagram.model.News;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseDrawerActivity implements UIadapter.OnNewsClickListener {

    @BindView(R.id.main_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.fbt_btn)
    FloatingActionButton fabCreate;
    @BindView(R.id.content)
    CoordinatorLayout clContent;

    private List<News> mNews;
    private UIadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
    }

    private void setUI() {
        addItem();
        adapter= new UIadapter(this,mNews,this);
        LinearLayoutManager linear= new LinearLayoutManager(this);
        linear.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new UIAnimator());
    }

    private void addItem() {
        mNews= new ArrayList<>();
        mNews.add(new News("Nguyễn Ngọc Bách đã thay đôi anh đại diện của anh ấy",
                "Ảnh đại diên !",R.drawable.xe_em,11));
        mNews.add(new News("Nguyễn Ngọc Bách đã thay đôi anh đại diện của anh ấy",
                "Ảnh đại diên !",R.drawable.avater_ngoc_em,11));
        mNews.add(new News("Nguyễn Ngọc Bách đã thay đôi anh đại diện của anh ấy",
                "Ảnh đại diên !",R.drawable.ngoc_em,11));
        mNews.add(new News("Nguyễn Ngọc Bách đã thay đôi anh đại diện của anh ấy",
                "Ảnh đại diên !",R.drawable.img_ngoc_trinh,11));
        mNews.add(new News("Nguyễn Ngọc Bách đã thay đôi anh đại diện của anh ấy",
                "Ảnh đại diên !",R.drawable.xe_em,11));
    }


    @Override
    public void onCommentsClick(View v, int position) {

    }

    @Override
    public void onMoreClick(View v, int position) {

    }

    @Override
    public void onProfileClick(View v) {

    }
}
