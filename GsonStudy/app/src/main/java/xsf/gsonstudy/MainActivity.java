package xsf.gsonstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import xsf.gsonstudy.bean.Contact;
import xsf.gsonstudy.bean.Person;
import xsf.gsonstudy.bean.PersonComplex;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1, btn2, btn3, btn4, btn5, btn6;
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
        btn4 = (Button) findViewById(R.id.btn_test4);
        btn5 = (Button) findViewById(R.id.btn_test5);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        tv_test = (TextView) findViewById(R.id.tv_test);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test1:
                testPerson1();
                break;
            case R.id.btn_test2:
                testListPersons();
                break;
            case R.id.btn_test3:
                testPersonComlex();
                break;
            case R.id.btn_test4:
                testListPersonComplex();
                break;


        }

    }


    private void testListPersonComplex() {
        final Gson gson = new Gson();
        List<PersonComplex> cplPersons = new ArrayList<>();

        final PersonComplex p1 = new PersonComplex("piter", 22, new Contact("126", "172636356"));
        cplPersons.add(p1);
        final PersonComplex p2 = new PersonComplex("lilei", 22, new Contact("163", "1892636535345"));
        cplPersons.add(p2);

        final String listCplpergsonStr = gson.toJson(cplPersons);
        System.out.println(listCplpergsonStr);
        List<PersonComplex> outcplPersons = new ArrayList<>();
        outcplPersons = gson.fromJson(listCplpergsonStr, new TypeToken<List<PersonComplex>>() {
        }.getType());
        tv_test.setText("name:" + outcplPersons.get(1).getName() + "\n"
                        + "age:" + outcplPersons.get(1).getAge() + "\n"
                        + "Contact:" + "\n"
                        + "email:" + outcplPersons.get(1).getContact().getEmail() + "\n"
                        + "phone:" + outcplPersons.get(1).getContact().getPhone() + "\n"
        );


    }

    private void testPersonComlex() {
        final Gson gson = new Gson();
        final PersonComplex p1 = new PersonComplex("piter", 22, new Contact("126", "172636356"));
        final String personComplexjsonStr = gson.toJson(p1);

        final PersonComplex outPer = gson.fromJson(personComplexjsonStr, PersonComplex.class);
        tv_test.setText("name:" + outPer.getName() + "\n"
                        + "age:" + outPer.getAge() + "\n"
                        + "Contact:" + "\n"
                        + "email:" + outPer.getContact().getEmail() + "\n"
                        + "phone:" + outPer.getContact().getPhone() + "\n"
        );


    }

    private void testListPersons() {
        List<Person> persons = new ArrayList<>();
        Person person1 = new Person("xsf", 13);

        persons.add(person1);
        Person person2 = new Person("mdx", 12);

        persons.add(person2);

        /*final String personStr = gson.toJson(new Person[]{person1, person2});
        Logger.d(personStr);
        final Person[] personout = gson.fromJson(personStr, Person[].class);

        for (final Person person : personout) {
            System.out.println("person=" + person);
        }*/
        final String personsStr = GsonUtil.GsonString(persons);
        Logger.d(personsStr);
        Gson gson = new Gson();
        final List<Person> outPersons = gson.fromJson(personsStr, new TypeToken<List<Person>>() {
        }.getType());

        final List<Person> outPersons2 = GsonUtil.GsonToList(personsStr, Person.class);

        System.out.println("outPersons2" + outPersons2);
        System.out.println("outPersons" + outPersons);


        tv_test.setText(outPersons.get(0).getName());
    }

    private void testPerson1() {
        Person person = new Person("xsf", 13);
        final String personjsonStr = gson.toJson(person);

        final Person personout = gson.fromJson(personjsonStr, Person.class);
        final Person personout1 = GsonUtil.GsonToBean(personjsonStr, Person.class);

        tv_test.setText(personout1.getName() + "\n" + personout1.getAge());
        Logger.d(personjsonStr);

    }
}
