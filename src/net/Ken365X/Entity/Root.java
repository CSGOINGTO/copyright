package net.Ken365X.Entity;

import javax.persistence.*;

/**
 * Created by mac on 2018/7/6.
 */

@Entity
@Table(name = "Root")
public class Root {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Root_Username",length = 32,nullable = false,unique = true)
    private String Root_Username;

    @Column(name = "Root_Password",length = 32,nullable = false)
    private String Root_Password;

    public Root(String root_Username, String root_Password) {
        Root_Username = root_Username;
        Root_Password = root_Password;
    }

    public Root() {
        // VOID
    }

    @Override
    public String toString() {
        return "Root{" +
                "id=" + id +
                ", Root_Username='" + Root_Username + '\'' +
                ", Root_Password='" + Root_Password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Root root = (Root) o;

        return id.equals(root.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoot_Username() {
        return Root_Username;
    }

    public void setRoot_Username(String root_Username) {
        Root_Username = root_Username;
    }

    public String getRoot_Password() {
        return Root_Password;
    }

    public void setRoot_Password(String root_Password) {
        Root_Password = root_Password;
    }
}
