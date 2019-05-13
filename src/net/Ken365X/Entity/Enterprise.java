package net.Ken365X.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mac on 2018/7/8.
 */
@Entity
@Table(name = "Enterprise")
public class Enterprise {
    @Id
    @Column(name = "enterprise_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enterprise_id;

    @Column(name = "username",nullable = false,unique = true)
    private String username ;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "phonenum",nullable = false)
    private String phonenum;

    @Column(name = "state",nullable = false)
    private int state = 0;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "realm",nullable = false)
    private String realm;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "info",nullable = false)
    private String info;

    @Column(name = "citi_account")
    private String citi_account;

    @Column(name = "citi_num")
    private String citi_num;

    @Column(name = "citi_display")
    private String citi_display;

    @OneToMany(targetEntity = Message.class,mappedBy = "enterprise",cascade = CascadeType.ALL)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(targetEntity = Task.class,mappedBy = "enterprise",cascade = CascadeType.ALL)
    private Set<Task> tasks = new HashSet<>();

    // constants
    public static final int UNLOGIN = 0;
    public static final int LOGIN = 1;
    public static final int FROZEN = 2;

    // constructor


    public Enterprise() {
        // VOID
    }

    public Enterprise(String username, String password, int state,
                      String name, String email) {
        this.username = username;
        this.password = password;
        this.state = state;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enterprise that = (Enterprise) o;

        return enterprise_id.equals(that.enterprise_id);
    }

    @Override
    public int hashCode() {
        return enterprise_id.hashCode();
    }

    public Integer getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(Integer enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getCiti_account() {
        return citi_account;
    }

    public void setCiti_account(String citi_account) {
        this.citi_account = citi_account;
    }

    public String getCiti_num() {
        return citi_num;
    }

    public void setCiti_num(String citi_num) {
        this.citi_num = citi_num;
    }

    public String getCiti_display() {
        return citi_display;
    }

    public void setCiti_display(String citi_display) {
        this.citi_display = citi_display;
    }
}
