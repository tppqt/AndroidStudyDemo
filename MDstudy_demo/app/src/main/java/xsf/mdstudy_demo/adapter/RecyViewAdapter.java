package xsf.mdstudy_demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xsf.mdstudy_demo.R;
import xsf.mdstudy_demo.book.BookBean;
import xsf.mdstudy_demo.util.Utils;

/**
 * @author xushangfei
 * @time Created at 2016/3/17.
 * @email xsf_uestc_ncl@163.com
 */
public class RecyViewAdapter extends RecyclerView.Adapter <RecyViewAdapter.ViewHolder>{
    private final int mBackground;
    private List<BookBean> books = new ArrayList<>();
    private final TypedValue typedValue = new TypedValue();
    private static final int ANIMATED_ITEMS_COUNT = 4;

    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;
    private Context context;

    public RecyViewAdapter(Context context) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        mBackground = typedValue.resourceId;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivBook;
        public TextView tvTitle;
        public TextView tvDesc;

        private int position;



        public ViewHolder(View itemView) {
            super(itemView);
            ivBook = (ImageView) itemView.findViewById(R.id.ivBook);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);

        }
    }

    private void runEnterAnimation(View itemView, int position) {
        if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            itemView.setTranslationY(Utils.getScreenHeight(context));
            itemView.animate()
                    .translationY(0)
                    .setStartDelay(150 * position)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(800)
                    .start();

        }
    }

    @Override
    public RecyViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        BookBean book = books.get(position);
        holder.tvTitle.setText(book.getTitle());
        String desc = "作者: " + book.getAuthor()[0] + "\n副标题：" + book.getSubtitle()
                + "\n初版年：" + book.getPubdate() + "\n页数：" + book.getPages() + "\n定价：" + book.getPrice();
        holder.tvDesc.setText(desc);
        holder.ivBook.setImageResource(R.mipmap.ic_launcher);
        Glide.with(holder.ivBook.getContext())
                .load(book.getImage())
                .fitCenter()
                .into(holder.ivBook);
    }



    public void updateItems(List<BookBean> books, boolean animated) {
        animateItems = animated;
        lastAnimatedPosition = -1;
        this.books.addAll(books);
        notifyDataSetChanged();

    }

    public void clearItems() {
        books.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public BookBean getBook(int pos) {
        return books.get(pos);
    }


}
