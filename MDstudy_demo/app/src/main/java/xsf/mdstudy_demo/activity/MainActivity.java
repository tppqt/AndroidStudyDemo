package xsf.mdstudy_demo.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import xsf.mdstudy_demo.R;
import xsf.mdstudy_demo.book.BooksActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout drawer_Layout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigation_view;
    private ImageView iv_profile;

    //private BackHandledFragment selectedFragment;
    private static final int ANIM_DURATION_TOOLBAR = 300;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        NavigationItemclick();

    }

    private void NavigationItemclick() {
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){

                }
                return true;//表示是否处于选中状态
            }
        });




    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer_Layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawer_Layout, R.string.drawer_open, R.string.drawer_close);
        drawer_Layout.addDrawerListener(drawerToggle);

        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        /*iv_profile = (ImageView) findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(this);*/
        findViewById(R.id.btn_click1).setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;//返回true表示显示选项按钮
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_click1:
                Toast.makeText(MainActivity.this, "clcik", Toast.LENGTH_SHORT).show();
                BooksActivity.launch(this);
                break;
            case R.id.iv_profile:

                break;

        }

    }
}
