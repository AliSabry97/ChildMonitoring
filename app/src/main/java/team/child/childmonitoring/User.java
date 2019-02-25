package team.child.childmonitoring;

/**
 * Created by alisa on 2/24/2019.
 */

public class User {
    private String phone , name ;

    public User ()
    {}
    public User(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
