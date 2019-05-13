package net.Ken365X.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mac on 2018/7/8.
 */

@Entity
@Table(name = "Designer")
public class Designer{
    @Id
    @Column(name = "designer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer designer_id;

    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "state",nullable = false)
    private int state = 0;

    @Column(name = "real_name")
    private String real_name;

    @Column(name = "phone_number",nullable = false)
    private String phone_number;

    @Column(name = "nick_name")
    private String nick_name;

    @Column(name = "sex")
    private int sex;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "citi_account")
    private String citi_account;

    @Column(name = "citi_num")
    private String citi_num;

    @Column(name = "citi_display")
    private String citi_display;

    @Column(name = "qq")
    private String qq;

    @Column(name = "weixin")
    private String weixin;

    @Column(name = "realm")
    private String realm;

    @Column(name = "email")
    private String email;

    @Column(name = "sign")
    private String sign;

    @Column(name = "designer_ornot",nullable = false)
    private boolean designer_ornot = false;

    @Column(name = "exp_work")
    private String exp_work;

    @Column(name = "exp_edu")
    private String exp_edu;

    @Column(name = "exp_prise")
    private String exp_prise;

    @Column(name = "money",nullable = false)
    private double money = 0;

    @Column(name = "exp",nullable = false)
    private int exp = 0;

    @Column(name = "credit",nullable = false)
    private int credit = 0;

    @OneToMany(targetEntity = Message.class,mappedBy = "designer",
            cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(targetEntity = Masterprise.class,mappedBy = "designer",
            cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Masterprise> masterprises = new HashSet<>();

    // Constants
    public static final int UNLOGIN = 0;
    public static final int LOGIN = 1;
    public static final int FROZEN = 2;
    public static final int NOTADESIGNER = 0;
    public static final int ISADESIGNER = 1;

    public static final int MAN = 1;
    public static final int WOMAN = 2;
    public static final int UNKNOWN = 3;

    // Constructor
    public Designer() {
        // void
    }

    public Designer(String username, String password, int state, String real_name, String email) {
        this.username = username;
        this.password = password;
        this.state = state;
        this.real_name = real_name;
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Designer designer = (Designer) o;

        return designer_id.equals(designer.designer_id);
    }

    @Override
    public int hashCode() {
        return designer_id.hashCode();
    }

    public Integer getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(Integer designer_id) {
        this.designer_id = designer_id;
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

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean isDesigner_ornot() {
        return designer_ornot;
    }

    public void setDesigner_ornot(boolean designer_ornot) {
        this.designer_ornot = designer_ornot;
    }

    public String getExp_work() {
        return exp_work;
    }

    public void setExp_work(String exp_work) {
        this.exp_work = exp_work;
    }

    public String getExp_edu() {
        return exp_edu;
    }

    public void setExp_edu(String exp_edu) {
        this.exp_edu = exp_edu;
    }

    public String getExp_prise() {
        return exp_prise;
    }

    public void setExp_prise(String exp_prise) {
        this.exp_prise = exp_prise;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Set<Masterprise> getMasterprises() {
        return masterprises;
    }

    public void setMasterprises(Set<Masterprise> masterprises) {
        this.masterprises = masterprises;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
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
