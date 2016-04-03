package xsf.adapterstudy;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xsf.adapterstudy.adapter.MultiAdapter;
import xsf.adapterstudy.base.BaseActvity;
import xsf.adapterstudy.bean.TestBean;

public class TestActivity2 extends BaseActvity {
    private ListView lv_test;
    private List<TestBean> datas;
    private MultiAdapter multiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        initData();
        initViews();
    }

    private void initData() {
        datas = new ArrayList<TestBean>();
        TestBean bean = new TestBean("学习Android", "Adnroid step1", "2016-03-27", "张三");
        datas.add(bean);
        bean = new TestBean("学习Android", "Adnroid step2", "2016-03-28", "李四");
        datas.add(bean);
        bean = new TestBean("学习Android", "Adnroid step3", "2016-03-28", "七大姑");
        datas.add(bean);
        bean = new TestBean("学习Android", "Adnroid step4", "2016-03-28", "八大姨");
        datas.add(bean);
        bean = new TestBean("学习Android", "Adnroid step5", "2016-03-28", "隔壁老王");
        datas.add(bean);
        bean = new TestBean("学习Android", "Adnroid step5", "2016-03-28", "王五");
        datas.add(bean);
        bean = new TestBean("学习Android", "Adnroid step5", "2016-03-28", "王六");
        datas.add(bean);
        bean = new TestBean("学习Android", "Adnroid step5", "2016-03-28", "王七");
        datas.add(bean);
    }

    private void initViews() {
        lv_test = (ListView) findViewById(R.id.lv_test2);
        multiAdapter = new MultiAdapter(this, datas);
        lv_test.setAdapter(multiAdapter);


    }
}
