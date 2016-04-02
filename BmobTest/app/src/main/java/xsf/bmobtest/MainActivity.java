package xsf.bmobtest;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import xsf.bmobtest.bean.Bean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name, et_feedback, et_queryname;
    private Button btn_submmit, btn_queryAll, btn_queryOne, btn_push;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBmob();

        initViews();
    }

    private void initBmob() {
        Bmob.initialize(this, "8a56b39388498990d46b9b2cd06e673b");
        //启动推送服务
        BmobInstallation.getCurrentInstallation(this).save();
        BmobPush.startWork(this, "8a56b39388498990d46b9b2cd06e673b");
    }

    private void initViews() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_feedback = (EditText) findViewById(R.id.et_back);
        btn_submmit = (Button) findViewById(R.id.btn_commit);
        btn_submmit.setOnClickListener(this);
        btn_queryAll = (Button) findViewById(R.id.btn_qureyAll);
        btn_queryAll.setOnClickListener(this);
        btn_queryOne = (Button) findViewById(R.id.btn_qureyOne);
        btn_queryOne.setOnClickListener(this);
        et_queryname = (EditText) findViewById(R.id.et_queryname);
        btn_push = (Button) findViewById(R.id.btn_push);
        btn_push.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                submmit();
                break;
            case R.id.btn_qureyAll:
                queryAll();
                break;
            case R.id.btn_qureyOne:
                qureyOne();
                break;
            case R.id.btn_push:
                push();
                break;
        }
    }

    private void push() {
        BmobPushManager pushManager = new BmobPushManager(MainActivity.this);
        pushManager.pushMessageAll("test");
    }

    private void qureyOne() {
        String str = et_queryname.getText().toString();
        if (str.equals("")) {
            return;
        }
        BmobQuery<Bean> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("name", str);
        bmobQuery.findObjects(MainActivity.this, new FindListener<Bean>() {
            @Override
            public void onSuccess(List<Bean> beans) {
                AlertDialog.Builder diagbuilder = new AlertDialog.Builder(MainActivity.this);
                diagbuilder.setTitle("查询");
                //StringBuffer stringBuffer = new StringBuffer("");
                String str = "";
                if (beans.size() == 0) {
                    Toast.makeText(MainActivity.this, "暂无查询结构，请核对后输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Bean bean : beans) {
                    str += "name: " + bean.getName() + "      age: " + bean.getAge() + "\n";
                }
                diagbuilder.setMessage(str);
                diagbuilder.create().show();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this, "query_fail", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void queryAll() {
        BmobQuery<Bean> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(MainActivity.this, new FindListener<Bean>() {
            @Override
            public void onSuccess(List<Bean> beans) {
                AlertDialog.Builder diagbuilder = new AlertDialog.Builder(MainActivity.this);
                diagbuilder.setTitle("查询");
                //StringBuffer stringBuffer = new StringBuffer("");
                String str = "";
                for (Bean bean : beans) {
                    str += "name: " + bean.getName() + "      age: " + bean.getAge() + "\n";
                }
                diagbuilder.setMessage(str);
                diagbuilder.create().show();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this, "query_fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void submmit() {
        String name = et_name.getText().toString();
        String feedback = et_feedback.getText().toString();
        if (name.equals("") || feedback.equals("")) {
            Logger.d("输入为空");
            return;
        }
        Bean testbean = new Bean();
        testbean.setName(name);
        testbean.setAge(feedback);
        testbean.save(MainActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "submmit_sucess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "submmit_fail", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
