package xsf.adapterstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xsf.adapterstudy.R;
import xsf.adapterstudy.bean.TestBean;

/**
 * @author xushangfei
 * @time Created at 2016/4/2.
 * @email xsf_uestc_ncl@163.com
 */
public class MultiAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<TestBean> datas;

    private static final int TYPE_MAIN = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_COUNT = 2;
    private int index = 1;


    public MultiAdapter(Context context, List<TestBean> datas) {
        layoutInflater = LayoutInflater.from(context);
        this.datas = datas;

    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? TYPE_SEPARATOR : TYPE_MAIN;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int viewType = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (viewType) {
                case TYPE_MAIN:
                    convertView = layoutInflater.inflate(R.layout.item_list, parent, false);
                    holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                    holder.tv_des = (TextView) convertView.findViewById(R.id.tv_desc);
                    holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                    holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                    break;
                case TYPE_SEPARATOR:
                    convertView = layoutInflater.inflate(R.layout.item_list2, parent, false);
                    holder.tv_separetore = (TextView) convertView.findViewById(R.id.tv_separetore);

            }


            convertView.setTag(holder);
        } else {
            //说明convertview已经被复用，已经设置过tag
            holder = (ViewHolder) convertView.getTag();
        }

        switch (viewType) {
            case TYPE_MAIN:
                TestBean testBean = datas.get(position);
                holder.tv_title.setText(testBean.getTitle());
                holder.tv_des.setText(testBean.getDesc());
                holder.tv_time.setText(testBean.getTime());
                holder.tv_name.setText(testBean.getName());
                break;
            case TYPE_SEPARATOR:
                holder.tv_separetore.setText("分割线");
                break;

        }


        return convertView;
    }

    /**
     * viewHoder服务于特定的Adapter，根据对应的item_layout书写
     */
    private static class ViewHolder {
        TextView tv_title;
        TextView tv_des;
        TextView tv_time;
        TextView tv_name;
        TextView tv_separetore;

    }
}
