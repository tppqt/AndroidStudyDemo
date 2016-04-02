package xsf.bmobtest.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author xushangfei
 * @time Created at 2016/3/27.
 * @email xsf_uestc_ncl@163.com
 */
public class Bean extends BmobObject {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
