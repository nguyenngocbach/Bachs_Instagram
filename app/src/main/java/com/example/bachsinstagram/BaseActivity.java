package com.example.bachsinstagram;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindView();
    }

    protected void bindView() {
        ButterKnife.bind(this);
        setToolbar();
    }

    public void setToolbar() {
        if (toolbar!=null){
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
        }
    }

    public void setContentViewWithoutInject(int layoutResId) {
        // super.setContentView(Id_layout) IdLayout above . this method is using to set layout for it's father class.
        super.setContentView(layoutResId);
    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting,menu);
        return true;
    }
}
