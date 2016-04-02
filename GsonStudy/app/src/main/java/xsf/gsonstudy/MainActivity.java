package xsf.gsonstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;


import xsf.gsonstudy.bean.Person;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1, btn2, btn3;
    private TextView tv_test;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();
    }

    private void initviews() {
        gson = new Gson();
        btn1 = (Button) findViewById(R.id.btn_test1);
        btn2 = (Button) findViewById(R.id.btn_test2);
        btn3 = (Button) findViewById(R.id.btn_test3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        tv_test = (TextView) findViewById(R.id.tv_test);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test1:
                testPerson1();
                break;
            case R.id.btn_test2:
                testPerson2();
                break;
            case R.id.btn_test3:
                break;

            case R.id.tv_test:
                break;

        }

    }

    private void testPerson2() {
        Person person1 = new Person();
        person1.setAge(13);
        person1.setName("xsf");
        Person person2 = new Person();
        person2.setAge(13);
        person2.setName("xsf");

        final String personStr = gson.toJson(new Person[]{person1, person2});
        Logger.d(personStr);
        final Person[] personout = gson.fromJson(personStr, Person[].class);

        for (final Person person:personout){
            System.out.println("person=" + person);
        }

        //tv_test.setText();
    }

    private void testPerson1() {
        Person person = new Person();
        person.setAge(13);
        person.setName("xsf");
        final String personjsonStr = gson.toJson(person);

        final Person personout = gson.fromJson(personjsonStr, Person.class);
        final Person personout1 =GsonUtil.GsonToBean(personjsonStr,Person.class);

        tv_test.setText(personout1.getName() +"\n"+ personout1.getAge());
        Logger.d(personjsonStr);

    }
}
