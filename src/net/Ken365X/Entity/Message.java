package net.Ken365X.Entity;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by mac on 2018/7/11.
 */
@Entity
@Table(name = "Message")
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer message_id;

    @Column(name = "message",nullable = false)
    private String message;

    @Column(name = "title",nullable = false)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sendtime")
    private Date sendtime;

    @Column(name = "readornot",nullable = false)
    private boolean readornot = false;

    @Column(name = "orientation",nullable = false)
    private boolean orientation = false;

    @ManyToOne(targetEntity = Designer.class)
    @JoinColumn(name = "designer_id",referencedColumnName = "designer_id",nullable = false)
    private Designer designer;

    @ManyToOne(targetEntity = Enterprise.class)
    @JoinColumn(name = "enterprise_id",referencedColumnName = "enterprise_id",nullable = false)
    private Enterprise enterprise;

    public static final boolean READED = true;
    public static final boolean UNREADED = false;
    public static final boolean E2D = true;
    public static final boolean D2E = false;

    public Message() {
        // VOID
    }

    public Message(String message, String title, Date sendtime, boolean readornot,
                   boolean orientation, Designer designer, Enterprise enterprise) {
        this.message = message;
        this.title = title;
        this.sendtime = sendtime;
        this.readornot = readornot;
        this.orientation = orientation;
        this.designer = designer;
        this.enterprise = enterprise;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", message='" + message + '\'' +
                ", title='" + title + '\'' +
                ", sendtime=" + sendtime +
                ", readornot=" + readornot +
                ", orientation=" + orientation +
                ", designer=" + designer +
                ", enterprise=" + enterprise +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return message_id.equals(message.message_id);
    }

    @Override
    public int hashCode() {
        return message_id.hashCode();
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public boolean isReadornot() {
        return readornot;
    }

    public void setReadornot(boolean readornot) {
        this.readornot = readornot;
    }

    public boolean isOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
