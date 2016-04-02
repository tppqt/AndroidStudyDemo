package xsf.adapterstudy.adapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import xsf.adapterstudy.R;
import xsf.adapterstudy.adapter.baseadapter.BaseAdapterTool;
import xsf.adapterstudy.adapter.baseadapter.BaseViewHolderTool;
import xsf.adapterstudy.bean.TestBean;

/**
 * @author xushangfei
 * @time Created at 2016/4/2.
 * @email xsf_uestc_ncl@163.com
 */
public class OptAdapter extends BaseAdapterTool<TestBean> {
    public OptAdapter(Context context, List<TestBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(BaseViewHolderTool holder, TestBean testBean) {
        TextView tv_title = holder.getView(R.id.tv_title);
        tv_title.setText(testBean.getTitle());
        TextView tv_desc = holder.getView(R.id.tv_desc);
        tv_desc.setText(testBean.getDesc());
        TextView tv_time = holder.getView(R.id.tv_time);
        tv_time.setText(testBean.getTime());
        TextView tv_name = holder.getView(R.id.tv_name);
        tv_name.setText(testBean.getName());

    }
}
