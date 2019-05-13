package net.Ken365X.Service;

import net.Ken365X.Entity.Designer;
import net.Ken365X.Entity.Message;

import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/7/9.
 */
public interface DesignerService {
    int addNewDesigner(String username,String password,String realname);
    boolean updateBasicInfoOfUserByUsername(String username,String realname, String nickname,
                                            int sex, Date birthday,String qq,String weixin,
                                            String email,String realm,String phonenumber);
    boolean updateExtendInfoOfUserByUsername(String username,String exp_edu,
                                             String exp_prise,String exp_work,String sign);
    int loginDesigner(String username,String password);
    String loginDesignerByEmail(String email,String password);
    boolean setMoneyOfUser(String username,double money);
    boolean addMoneyOfUser(String username,double moneyadded);
    double getMoneyByUsername(String usernmae);
    boolean setExpOfUser(String usernmae,int exp);
    boolean addExpOfUser(String username,int exp);
    int getExpByName(String username);
    Designer getBasicInfoOfUser(String username);
    int shiftToDesigner(String username);
    boolean updatePasswordOfDesignerByUsername(String username,String originpassword,String password);
    boolean userShareMasterprice(String username,int masterprice_id);
    boolean userLikeMasterprice(String username,int masterprice_id);
    List<Designer> getAllDesigners();
}
