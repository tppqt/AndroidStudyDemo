package xsf.mdstudy_demo.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import xsf.mdstudy_demo.R;
import xsf.mdstudy_demo.adapter.PageAapter;

public class BooksDetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BookBean book;
    private ImageView ivImage;
    private CollapsingToolbarLayout collapsingToolbar;

    public static void launch(Context context) {
        Intent intent = new Intent(context, BooksDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_detail);
        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        book = (BookBean) getIntent().getSerializableExtra("book");
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(book.getTitle());


        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
        Glide.with(ivImage.getContext())
                .load(book.getImages().getLarge())
                .fitCenter()
                .into(ivImage);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setUpViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("内容简介"));
        tabLayout.addTab(tabLayout.newTab().setText("作者简介"));
        tabLayout.addTab(tabLayout.newTab().setText("目录"));
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setUpViewPager(ViewPager viewPager) {
        PageAapter adapter = new PageAapter(getSupportFragmentManager());
        adapter.addFragment(DetailFragment.newInstance(book.getSummary()), "内容简介");
        adapter.addFragment(DetailFragment.newInstance(book.getAuthor_intro()), "作者简介");
        adapter.addFragment(DetailFragment.newInstance(book.getCatalog()), "作品目录");
        viewPager.setAdapter(adapter);
    }
}
