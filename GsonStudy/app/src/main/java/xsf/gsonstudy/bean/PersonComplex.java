package xsf.gsonstudy.bean;

/**
 * @author xushangfei
 * @time Created at 2016/4/6.
 * @email xsf_uestc_ncl@163.com
 */
public class PersonComplex {
    private String name;
    private int age;

    public PersonComplex(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public PersonComplex(String name, int age, Contact contact) {
        this.name = name;
        this.age = age;
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    private Contact contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


   /* public class Contact {
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
    }*/
}
