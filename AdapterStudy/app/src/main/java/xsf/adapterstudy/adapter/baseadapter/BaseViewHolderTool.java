package xsf.adapterstudy.adapter.baseadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author xushangfei
 * @time Created at 2016/4/2.
 * @email xsf_uestc_ncl@163.com
 */
public class BaseViewHolderTool {
    private SparseArray<View> views;
    private int basePosition;
    private View baseConvertView;

    public BaseViewHolderTool(Context context, ViewGroup parent, int layoutId, int position) {

        this.basePosition = position;
        this.views = new SparseArray<View>();

        baseConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        baseConvertView.setTag(this);
    }

    /**
     * 获得convertview不同情况的holder
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static BaseViewHolderTool get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new BaseViewHolderTool(context, parent, layoutId, position);
        } else {
            BaseViewHolderTool holder = (BaseViewHolderTool) convertView.getTag();
            holder.basePosition = position;//更新position位置
            return holder;
        }
    }

    /**
     * 通过id获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = baseConvertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getBaseConvertView() {
        return baseConvertView;
    }
}
