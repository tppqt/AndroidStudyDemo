package xsf.reboundstudy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import xsf.reboundstudy.popView.PopMenuItem;
import xsf.reboundstudy.popView.PopMenuItemListener;
import xsf.reboundstudy.popView.PopView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private PopView popView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        inintPopView();

    }

    private void inintPopView() {
        popView = new PopView.Builder().attachToactvity(MainActivity.this)
                .addMenuItem(new PopMenuItem("文字", getResources().getDrawable(R.mipmap.tabbar_compose_idea)))
                .addMenuItem(new PopMenuItem("照片/视频", getResources().getDrawable(R.mipmap.tabbar_compose_photo)))
                .addMenuItem(new PopMenuItem("头条文章", getResources().getDrawable(R.mipmap.tabbar_compose_headlines)))
                .addMenuItem(new PopMenuItem("签到", getResources().getDrawable(R.mipmap.tabbar_compose_lbs)))
                .addMenuItem(new PopMenuItem("点评", getResources().getDrawable(R.mipmap.tabbar_compose_review)))
                .addMenuItem(new PopMenuItem("更多", getResources().getDrawable(R.mipmap.tabbar_compose_more)))
                .setOnItemClickListener(new PopMenuItemListener() {
                    @Override
                    public void onPopItemClick(PopView popView, int position) {
                        Toast.makeText(MainActivity.this, "点击了第" + (position+1) + "个位置", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("弹性动画");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        findViewById(R.id.btn_bottom).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:


                break;
            case R.id.btn_bottom:
                if(!popView.isShowing()){
                    popView.show();
                }

                break;
        }

    }
}
