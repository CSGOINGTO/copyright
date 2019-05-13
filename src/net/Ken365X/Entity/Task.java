package net.Ken365X.Entity;

import javax.persistence.*;
import java.util.*;

/**
 * Created by mac on 2018/7/8.
 */
@Entity
@Table(name = "Task")
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer task_id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "task_info_title",nullable = false)
    private String task_info_title;

    @Column(name = "task_info",nullable = false)
    private String task_info;

    @Column(name = "give_money",nullable = false)
    private double give_money = 0;

    @Column(name = "stage",nullable = false)
    private int stage = 0;

    @Column(name = "property",nullable = false)
    private double property = 0.5;

    @Column(name = "num_of_picture",nullable = false)
    private int num_of_picture = 0;

    @Column(name = "info_class",nullable = false)
    private String info_class;

    @Column(name = "num_view",nullable = false)
    private int num_view = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_day",nullable = false)
    private Date start_day;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_day",nullable = false)
    private Date end_day;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_day2")
    private Date start_day2;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_day2")
    private Date end_day2;

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "sub_task_info_title",joinColumns =
    @JoinColumn(name = "task_id",nullable = false))
    @Column(name = "sub_task_info_title")
    @OrderColumn(name = "order_sub_task_info_title")
    private List<String> sub_task_info_title = new ArrayList<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "sub_task_info",joinColumns =
    @JoinColumn(name = "task_id",nullable = false))
    @Column(name = "sub_task_info")
    @OrderColumn(name = "order_sub_task_info")
    private List<String> sub_task_info = new ArrayList<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "sub_task_img_info",joinColumns =
    @JoinColumn(name = "task_id",nullable = false))
    @Column(name = "sub_task_img_info")
    @OrderColumn(name = "order_sub_task_img_info")
    private List<String> sub_task_img_info = new ArrayList<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "task_picture_prefix",joinColumns =
    @JoinColumn(name = "task_id",nullable = false))
    @Column(name = "picture_prefix")
    @OrderColumn(name = "order_picture_prefix")
    private List<String> picture_prefix = new ArrayList<>();

    @ManyToOne(targetEntity = Enterprise.class)
    @JoinColumn(name = "enterprise_id",referencedColumnName = "enterprise_id",nullable = false)
    private Enterprise enterprise;

    @OneToMany(targetEntity = Masterprise.class,mappedBy = "task",
            cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Masterprise> masterprises = new HashSet<>();

    // constants
    public static final int UNKNOW = 0;
    public static final int STAGE_ONE = 1;
    public static final int STAGE_TWO = 2;
    public static final int STAGE_THREE = 3;
    public static final int STAGE_FOUR = 4;
    public static final int STAGE_FIVE = 5;
    public static final int STAGE_SIX = 6;

    // constructor
    public Task() {
        // VOID
    }

    public Task(String name, String task_info_title, String task_info,
                double give_money, int stage, Date start_day, Date end_day,
                Enterprise enterprise) {
        this.name = name;
        this.task_info_title = task_info_title;
        this.task_info = task_info;
        this.give_money = give_money;
        this.stage = stage;
        this.start_day = start_day;
        this.end_day = end_day;
        this.enterprise = enterprise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return task_id.equals(task.task_id);
    }

    @Override
    public int hashCode() {
        return task_id.hashCode();
    }

//    @Override
//    public String toString() {
//        return "Task{" +
//                "task_id=" + task_id +
//                ", name='" + name + '\'' +
//                ", task_info_title='" + task_info_title + '\'' +
//                ", task_info='" + task_info + '\'' +
//                ", give_money=" + give_money +
//                ", stage=" + stage +
//                ", property=" + property +
//                ", num_of_picture=" + num_of_picture +
//                ", info_class='" + info_class + '\'' +
//                ", num_view=" + num_view +
//                ", start_day=" + start_day +
//                ", end_day=" + end_day +
//                ", sub_task_info_title=" + sub_task_info_title +
//                ", sub_task_info=" + sub_task_info +
//                ", sub_task_img_info=" + sub_task_img_info +
//                ", picture_prefix=" + picture_prefix +
//                ", enterprise=" + enterprise +
//                ", masterprises=" + masterprises +
//                '}';
//    }

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTask_info_title() {
        return task_info_title;
    }

    public void setTask_info_title(String task_info_title) {
        this.task_info_title = task_info_title;
    }

    public String getTask_info() {
        return task_info;
    }

    public void setTask_info(String task_info) {
        this.task_info = task_info;
    }

    public double getGive_money() {
        return give_money;
    }

    public void setGive_money(double give_money) {
        this.give_money = give_money;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Date getStart_day() {
        return start_day;
    }

    public void setStart_day(Date start_day) {
        this.start_day = start_day;
    }

    public Date getEnd_day() {
        return end_day;
    }

    public void setEnd_day(Date end_day) {
        this.end_day = end_day;
    }

    public List<String> getSub_task_info_title() {
        return sub_task_info_title;
    }

    public void setSub_task_info_title(List<String> sub_task_info_title) {
        this.sub_task_info_title = sub_task_info_title;
    }

    public String getInfo_class() {
        return info_class;
    }

    public void setInfo_class(String info_class) {
        this.info_class = info_class;
    }

    public List<String> getSub_task_info() {
        return sub_task_info;
    }

    public void setSub_task_info(List<String> sub_task_info) {
        this.sub_task_info = sub_task_info;
    }

    public int getNum_of_picture() {
        return num_of_picture;
    }

    public void setNum_of_picture(int num_of_picture) {
        this.num_of_picture = num_of_picture;
    }

    public List<String> getSub_task_img_info() {
        return sub_task_img_info;
    }

    public void setSub_task_img_info(List<String> sub_task_img_info) {
        this.sub_task_img_info = sub_task_img_info;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public int getNum_view() {
        return num_view;
    }

    public void setNum_view(int num_view) {
        this.num_view = num_view;
    }

    public Set<Masterprise> getMasterprises() {
        return masterprises;
    }

    public void setMasterprises(Set<Masterprise> masterprises) {
        this.masterprises = masterprises;
    }

    public double getProperty() {
        return property;
    }

    public void setProperty(double property) {
        this.property = property;
    }

    public List<String> getPicture_prefix() {
        return picture_prefix;
    }

    public void setPicture_prefix(List<String> picture_prefix) {
        this.picture_prefix = picture_prefix;
    }

    public Date getStart_day2() {
        return start_day2;
    }

    public void setStart_day2(Date start_day2) {
        this.start_day2 = start_day2;
    }

    public Date getEnd_day2() {
        return end_day2;
    }

    public void setEnd_day2(Date end_day2) {
        this.end_day2 = end_day2;
    }
}
