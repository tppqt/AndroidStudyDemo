package xsf.mdstudy_demo.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import xsf.mdstudy_demo.R;
import xsf.mdstudy_demo.adapter.RecyViewAdapter;
import xsf.mdstudy_demo.common.RecyclerItemClickListener;
import xsf.mdstudy_demo.net.AsyHttputil;

public class BooksActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyViewAdapter reAdapter;

    private ProgressBar progressBar;
    private static final int ANIM_DURATION_FAB = 400;
    private FloatingActionButton fab_normal;


    public static void launch(Context context) {
        Intent intent = new Intent(context, BooksActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        initView();
        doSearch("Android");
    }


    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, onItemClickListener));
        reAdapter = new RecyViewAdapter(this);


        recyclerView.setAdapter(reAdapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        fab_normal = (FloatingActionButton) findViewById(R.id.fab_normal);
        fab_normal.setOnClickListener(this);


    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            BookBean book = reAdapter.getBook(position);
            Intent intent = new Intent(BooksActivity.this, BooksDetailActivity.class);
            intent.putExtra("book", book);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(BooksActivity.this,
                    view.findViewById(R.id.ivBook), "transition_book_img");

            ActivityCompat.startActivity(BooksActivity.this, intent, options.toBundle());

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_normal:
                setUpFab();

                break;
        }

    }

    private void setUpFab() {
        new MaterialDialog.Builder(this)
                .title("找一找")
                .input("请输入关键字", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (!TextUtils.isEmpty(input)) {
                            doSearch(input.toString());
                        }
                    }
                }).show();
    }

    private void doSearch(String keyword) {
        progressBar.setVisibility(View.VISIBLE);
        reAdapter.clearItems();
        AsyHttputil.search(keyword, new AsyHttputil.ISearchResponse<List<BookBean>>() {
            @Override
            public void onData(List<BookBean> books) {
                progressBar.setVisibility(View.GONE);
                startFABAnimation();
                reAdapter.updateItems(books, true);
            }

        });


    }

    private void startFABAnimation() {
        fab_normal.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(500)
                .setDuration(ANIM_DURATION_FAB)
                .start();
    }
}
