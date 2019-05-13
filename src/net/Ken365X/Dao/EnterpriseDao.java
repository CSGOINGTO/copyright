package net.Ken365X.Dao;

import net.Ken365X.Entity.Enterprise;

/**
 * Created by mac on 2018/7/10.
 */
public interface EnterpriseDao {
    boolean addNewEnterprise(String username,String password,String phonenum,String name,
                             String realm,String email,String info);
    Enterprise getEnterpriseByName(String username);
    Enterprise getEnterpriseByEmail(String email);
    Enterprise getEnterpriseById(int id);
    boolean updateEnterprise(Enterprise enterprise);
    boolean deleteEnterpriseByName(String username);
}
