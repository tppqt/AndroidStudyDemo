package xsf.gsonstudy.bean;

/**
 * @author xushangfei
 * @time Created at 2016/4/6.
 * @email xsf_uestc_ncl@163.com
 */
public class Contact {
    public Contact(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    private String email;
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
