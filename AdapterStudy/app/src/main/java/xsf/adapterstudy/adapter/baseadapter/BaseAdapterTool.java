package xsf.adapterstudy.adapter.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author xushangfei
 * @time Created at 2016/4/2.
 * @email xsf_uestc_ncl@163.com
 */
public abstract class BaseAdapterTool<T> extends BaseAdapter {
    protected Context context;
    protected List<T> datas;
    protected LayoutInflater layoutInflater;
    private int layoutId;

    public BaseAdapterTool(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //使用通用vierholder
        BaseViewHolderTool holder = BaseViewHolderTool.get(context, convertView, parent, layoutId, position);
        convert(holder, getItem(position));
        return holder.getBaseConvertView();
    }

    /**
     * 将该方法公布出去
     *
     * @param holder
     * @param t
     */
    public abstract void convert(BaseViewHolderTool holder, T t);
}
