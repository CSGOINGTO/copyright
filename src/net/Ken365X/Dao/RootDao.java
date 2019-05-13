package net.Ken365X.Dao;

/**
 * Created by mac on 2018/7/6.
 */
public interface RootDao {
    String getPasswordByName(String name);
    boolean deleteRootByName(String name);
    boolean addRoot(String username,String password);
    boolean rePasswordRoot(String username,String newPassword);
}
