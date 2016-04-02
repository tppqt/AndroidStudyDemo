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
public class NormalAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<TestBean> datas;

    //NormalAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public NormalAdapter(Context context, List<TestBean> datas) {
        layoutInflater = LayoutInflater.from(context);
        this.datas = datas;
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
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();

            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_des = (TextView) convertView.findViewById(R.id.tv_desc);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

            convertView.setTag(holder);
        } else {
            //说明convertview已经被复用，已经设置过tag
            holder = (ViewHolder) convertView.getTag();
        }

        TestBean testBean = datas.get(position);
        holder.tv_title.setText(testBean.getTitle());
        holder.tv_des.setText(testBean.getDesc());
        holder.tv_time.setText(testBean.getTime());
        holder.tv_name.setText(testBean.getName());

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

    }
}
