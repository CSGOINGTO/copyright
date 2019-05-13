package net.Ken365X.Entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/7/8.
 */
@Entity
@Table(name = "Masterprise")
public class Masterprise {
    @Id
    @Column(name = "masterprise_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masterprise_id;

    @Column(name = "info",nullable = false)
    private String info;

    @Column(name = "stage",nullable = false)
    private int stage = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date",nullable = false)
    private Date create_date;

    @Column(name = "number_of_picture",nullable = false)
    private int number_of_picture = 0;

    @Column(name = "number_of_video",nullable = false)
    private int number_of_video = 0;

    @Column(name = "view",nullable = false)
    private int view = 0;

    @Column(name = "moneyA",nullable = false)
    private double money_A = 0;

    @Column(name = "moneyB",nullable = false)
    private double money_B = 0;

    @Column(name = "moneyC",nullable = false)
    private double money_C = 0;

    @Column(name = "video_info")
    private String video_info;

    @Column(name = "video_prefix")
    private String video_prefix;

    @Column(name = "score_enterprise",nullable = false)
    private int score_enterprise = 0;

    @Column(name = "final_score",nullable = false)
    private int final_score = 0;

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "picture_annotation",joinColumns =
    @JoinColumn(name = "masterprise_id",nullable = false))
    @Column(name = "picture_annotation")
    @OrderColumn(name = "order_picture_annotation")
    private List<String> picture_annotation = new ArrayList<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "picture_prefix",joinColumns =
    @JoinColumn(name = "masterprise_id",nullable = false))
    @Column(name = "picture_prefix")
    @OrderColumn(name = "order_picture_prefix")
    private List<String> picture_prefix = new ArrayList<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "like_designer_username",joinColumns =
    @JoinColumn(name = "masterprise_id",nullable = false))
    @Column(name = "like_designer_username")
    @OrderColumn(name = "order_desiner_username")
    private List<String> like_designer_username = new ArrayList<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "share_designer_username",joinColumns =
    @JoinColumn(name = "masterprise_id",nullable = false))
    @Column(name = "share_designer_username")
    @OrderColumn(name = "order_desiner_username")
    private List<String> share_designer_username = new ArrayList<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "comments_designer_username",joinColumns =
    @JoinColumn(name = "masterprise_id",nullable = false))
    @Column(name = "comments_designer_username")
    @OrderColumn(name = "order_comments_designer_username")
    private List<String> comments_designer_username = new ArrayList<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "comments",joinColumns =
    @JoinColumn(name = "masterprise_id",nullable = false))
    @Column(name = "comments")
    @OrderColumn(name = "order_comments")
    private List<String> comments = new ArrayList<>();

    @ManyToOne(targetEntity = Designer.class)
    @JoinColumn(name = "designer_id",referencedColumnName = "designer_id",nullable = false)
    private Designer designer;

    @ManyToOne(targetEntity = Task.class)
    @JoinColumn(name = "task_id",referencedColumnName = "task_id",nullable = false)
    private Task task;

    // constants
    public static final int UNKNOW = 0;
    public static final int STAGE_ONE = 1;
    public static final int STAGE_TWO = 2;
    public static final int STAGE_THREE = 3;
    public static final int STAGE_FOUR = 4;
    public static final int STAGE_FIVE = 5;

    // constructor
    public Masterprise() {
        // VOID
    }

    public Masterprise(String info, int stage, Date create_date, Designer designer) {
        this.info = info;
        this.stage = stage;
        this.create_date = create_date;
        this.designer = designer;
    }

//    @Override
//    public String toString() {
//        return "Masterprise{" +
//                "masterprise_id=" + masterprise_id +
//                ", info='" + info + '\'' +
//                ", stage=" + stage +
//                ", create_date=" + create_date +
//                ", number_of_picture=" + number_of_picture +
//                ", number_of_video=" + number_of_video +
//                ", view=" + view +
//                ", money_A=" + money_A +
//                ", money_B=" + money_B +
//                ", money_C=" + money_C +
//                ", video_info='" + video_info + '\'' +
//                ", video_prefix='" + video_prefix + '\'' +
//                ", score_enterprise=" + score_enterprise +
//                ", final_score=" + final_score +
//                ", picture_annotation=" + picture_annotation +
//                ", picture_prefix=" + picture_prefix +
//                ", like_designer_username=" + like_designer_username +
//                ", share_designer_username=" + share_designer_username +
//                ", comments_designer_username=" + comments_designer_username +
//                ", comments=" + comments +
//                ", designer=" + designer +
//                ", task=" + task +
//                '}';
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Masterprise that = (Masterprise) o;

        return masterprise_id.equals(that.masterprise_id);
    }

    @Override
    public int hashCode() {
        return masterprise_id.hashCode();
    }

    public Integer getMasterprise_id() {
        return masterprise_id;
    }

    public void setMasterprise_id(Integer masterprise_id) {
        this.masterprise_id = masterprise_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public int getNumber_of_picture() {
        return number_of_picture;
    }

    public void setNumber_of_picture(int number_of_picture) {
        this.number_of_picture = number_of_picture;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public double getMoney_A() {
        return money_A;
    }

    public void setMoney_A(double money_A) {
        this.money_A = money_A;
    }

    public double getMoney_B() {
        return money_B;
    }

    public void setMoney_B(double money_B) {
        this.money_B = money_B;
    }

    public double getMoney_C() {
        return money_C;
    }

    public void setMoney_C(double money_C) {
        this.money_C = money_C;
    }

    public String getVideo_info() {
        return video_info;
    }

    public void setVideo_info(String video_info) {
        this.video_info = video_info;
    }

    public int getScore_enterprise() {
        return score_enterprise;
    }

    public void setScore_enterprise(int score_enterprise) {
        this.score_enterprise = score_enterprise;
    }

    public int getFinal_score() {
        return final_score;
    }

    public void setFinal_score(int final_score) {
        this.final_score = final_score;
    }

    public List<String> getPicture_annotation() {
        return picture_annotation;
    }

    public void setPicture_annotation(List<String> picture_annotation) {
        this.picture_annotation = picture_annotation;
    }

    public List<String> getComments_designer_username() {
        return comments_designer_username;
    }

    public void setComments_designer_username(List<String> comments_designer_username) {
        this.comments_designer_username = comments_designer_username;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getNumber_of_video() {
        return number_of_video;
    }

    public void setNumber_of_video(int number_of_video) {
        this.number_of_video = number_of_video;
    }

    public List<String> getLike_designer_username() {
        return like_designer_username;
    }

    public void setLike_designer_username(List<String> like_designer_username) {
        this.like_designer_username = like_designer_username;
    }

    public List<String> getShare_designer_username() {
        return share_designer_username;
    }

    public void setShare_designer_username(List<String> share_designer_username) {
        this.share_designer_username = share_designer_username;
    }

    public String getVideo_prefix() {
        return video_prefix;
    }

    public void setVideo_prefix(String video_prefix) {
        this.video_prefix = video_prefix;
    }

    public List<String> getPicture_prefix() {
        return picture_prefix;
    }

    public void setPicture_prefix(List<String> picture_prefix) {
        this.picture_prefix = picture_prefix;
    }
}
