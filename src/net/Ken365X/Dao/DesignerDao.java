package net.Ken365X.Dao;

import net.Ken365X.Entity.Designer;
import net.Ken365X.Entity.Message;

import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/7/9.
 */
public interface DesignerDao {
    boolean addNewDesigner(String username,String password,String phonenum);
    boolean deleteDesignerById(int id);
    Designer getDesignerById(int id);
    Designer getDesignerByEmail(String email);
    Designer getDesignerByName(String name);
    boolean updateDesigner(Designer designer);
    int getCountOfDesigner();
    List<Designer> getAllDesigner();
}
