package net.Ken365X.Service;

import net.Ken365X.Dao.DesignerDao;
import net.Ken365X.Dao.MasterpriseDao;
import net.Ken365X.Entity.Designer;
import net.Ken365X.Entity.Enterprise;
import net.Ken365X.Entity.Masterprise;
import net.Ken365X.Entity.Message;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by mac on 2018/7/9.
 */
public class DesignerServiceImp implements DesignerService{
    private DesignerDao designerDao;
    private MasterpriseDao masterpriseDao;

    public static final int SAME_USERNAME_USED = -1;
    public static final int ADD_USER_SUCCESS = 0;

    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAIL_NOUSER = -1;
    public static final int LOGIN_FAIL_PASSWORDWRONG = -2;

    public static final boolean CHANGE_PSD_SUCCESS = true;
    public static final boolean CHANGE_PSD_FAIL = false;

    public static final boolean UPDATE_USER_INFO_FALI = false;
    public static final boolean UPDATE_USER_INFO_SUCCESS = true;

    public static final int SHIFT_DESIGNER_FAIL_NO_USERNAME = -1;
    public static final int SHIFT_DESIGNER_FAIL_INFO_NOT_COMPLETE = -2;
    public static final int SHIFT_DESIGNER_FAIL_HAS_SHIFTED = -3;
    public static final int SHIFT_SUCCESS = 0;

    @Override
    public int addNewDesigner(String username, String password, String phonenum) {
        boolean res = designerDao.addNewDesigner(username, password, phonenum);
        if (res == true){
            return ADD_USER_SUCCESS;
        }else {
            return SAME_USERNAME_USED;
        }
    }

    @Override
    public String loginDesignerByEmail(String email, String password) {
        Designer designer = null;
        try{
            designer = designerDao.getDesignerByEmail(email);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        if (designer == null){
            return "none";
        }
        if(designer.getPassword().equals(password)){
            return designer.getUsername();
        }else {
            return "wrong";
        }
    }

    @Override
    public boolean updateBasicInfoOfUserByUsername(String username,String realname, String nickname,
                                                   int sex, Date birthday, String qq,
                                                   String weixin,String email, String realm,String phonenum) {
        Designer designer = designerDao.getDesignerByName(username);
        if (designer == null){
            // No record of this user
            return false;
        }
        if (realname != null){
            designer.setReal_name(realname);
        }
        if (nickname != null){
            designer.setNick_name(nickname);
        }
        if (sex == Designer.MAN || sex == Designer.WOMAN || sex == Designer.UNKNOWN){
            designer.setSex(sex);
        }
        if (birthday != null){
            designer.setBirthday(birthday);
        }
        if (qq != null){
            designer.setQq(qq);
        }
        if (weixin != null){
            designer.setWeixin(weixin);
        }
        if (realm != null){
            designer.setRealm(realm);
        }
        if (phonenum != null){
            designer.setPhone_number(phonenum);
        }
        if (email != null){
            designer.setEmail(email);
        }
        // update success!
        designerDao.updateDesigner(designer);
        return true;
    }

    @Override
    public boolean updateExtendInfoOfUserByUsername(String username, String exp_edu,
                                                    String exp_prise, String exp_work, String sign) {
        Designer designer = designerDao.getDesignerByName(username);
        if (designer == null){
            // No record of user
            return false;
        }
        if(exp_edu != null){
            designer.setExp_edu(exp_edu);
        }
        if (exp_prise != null){
            designer.setExp_prise(exp_prise);
        }
        if (exp_work != null){
            designer.setExp_work(exp_work);
        }
        if (sign != null){
            designer.setSign(sign);
        }
        designerDao.updateDesigner(designer);
        return UPDATE_USER_INFO_SUCCESS;
    }

    @Override
    public int loginDesigner(String username, String password) {
        Designer designer = null;
        try{
            designer = designerDao.getDesignerByName(username);
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
        if (designer == null){
            return LOGIN_FAIL_NOUSER;
        }
        if(designer.getPassword().equals(password)){
            return LOGIN_SUCCESS;
        }else {
            return LOGIN_FAIL_PASSWORDWRONG;
        }
    }

    @Override
    public boolean updatePasswordOfDesignerByUsername(String username,String originpassword, String password) {
        Designer designer = designerDao.getDesignerByName(username);
        if (designer == null){
            return CHANGE_PSD_FAIL;
        }
        if (!designer.getPassword().equals(originpassword)){
            return CHANGE_PSD_FAIL;
        }
        designer.setPassword(password);
        designerDao.updateDesigner(designer);
        return CHANGE_PSD_SUCCESS;
    }

    @Override
    public boolean setMoneyOfUser(String username, double money) {
        Designer designer = designerDao.getDesignerByName(username);
        if(designer == null){
            return UPDATE_USER_INFO_FALI;
        }
        designer.setMoney(money);
        designerDao.updateDesigner(designer);
        return UPDATE_USER_INFO_SUCCESS;
    }

    @Override
    public boolean addMoneyOfUser(String username, double moneyadded) {
        Designer designer = designerDao.getDesignerByName(username);
        if(designer == null){
            return UPDATE_USER_INFO_FALI;
        }
        designer.setMoney(designer.getMoney() + moneyadded);
        designerDao.updateDesigner(designer);
        return UPDATE_USER_INFO_SUCCESS;
    }

    @Override
    public double getMoneyByUsername(String usernmae) {
        Designer designer = designerDao.getDesignerByName(usernmae);
        if(designer == null){
            return -1;
        }
        return designer.getMoney();
    }

    @Override
    public boolean setExpOfUser(String usernmae, int exp) {
        Designer designer =
                designerDao.getDesignerByName(usernmae);
        if(designer == null){
            return UPDATE_USER_INFO_FALI;
        }
        designer.setExp(exp);
        designerDao.updateDesigner(designer);
        return UPDATE_USER_INFO_SUCCESS;
    }

    @Override
    public boolean addExpOfUser(String username, int exp) {
        Designer designer =
                designerDao.getDesignerByName(username);
        if(designer == null){
            return UPDATE_USER_INFO_FALI;
        }
        designer.setExp(designer.getExp() + exp);
        designerDao.updateDesigner(designer);
        return UPDATE_USER_INFO_SUCCESS;
    }

    @Override
    public int getExpByName(String username) {
        Designer designer = designerDao.getDesignerByName(username);
        if(designer == null){
            return -1;
        }
        return designer.getExp();
    }

    @Override
    public Designer getBasicInfoOfUser(String username) {
        Designer designer = designerDao.getDesignerByName(username);
        return designer;
    }

    @Override
    public int shiftToDesigner(String username) {
        Designer designer = designerDao.getDesignerByName(username);
        if (designer == null){
            return SHIFT_DESIGNER_FAIL_NO_USERNAME;
        }
        if (designer.isDesigner_ornot() == true){
            return SHIFT_DESIGNER_FAIL_HAS_SHIFTED;
        }
        // The Conditions for shift into designer
        if (designer.getPhone_number() == null || designer.getNick_name() == null ||
                designer.getEmail() == null || designer.getRealm() == null){
            return SHIFT_DESIGNER_FAIL_INFO_NOT_COMPLETE;
        }
        // shift to designer
        System.out.println(designer.isDesigner_ornot());
        designer.setDesigner_ornot(true);
        designerDao.updateDesigner(designer);
        return SHIFT_SUCCESS;
    }

    @Override
    public boolean userShareMasterprice(String username, int masterprice_id) {
        Designer designer = designerDao.getDesignerByName(username);
        if(designer == null){
            return false;
        }
        Masterprise masterprise = masterpriseDao.getMasterpriseById(masterprice_id);
        if (masterprise == null){
            return false;
        }
        // add the credit of user
        designer.setCredit(designer.getCredit() + 1);
        // add money of credit
        // TODO
        masterprise.getShare_designer_username().add(username);
        designerDao.updateDesigner(designer);
        masterpriseDao.updateMasterprise(masterprise);
        return true;

    }

    @Override
    public boolean userLikeMasterprice(String username, int masterprice_id) {
        Designer designer = designerDao.getDesignerByName(username);
        if(designer == null){
            return false;
        }
        Masterprise masterprise = masterpriseDao.getMasterpriseById(masterprice_id);
        if (masterprise == null){
            return false;
        }

        masterprise.getLike_designer_username().add(username);
        designerDao.updateDesigner(designer);
        masterpriseDao.updateMasterprise(masterprise);
        return true;
    }

    @Override
    public List<Designer> getAllDesigners() {
        return designerDao.getAllDesigner();
    }

    public DesignerDao getDesignerDao() {
        return designerDao;
    }

    public void setDesignerDao(DesignerDao designerDao) {
        this.designerDao = designerDao;
    }

    public MasterpriseDao getMasterpriseDao() {
        return masterpriseDao;
    }

    public void setMasterpriseDao(MasterpriseDao masterpriseDao) {
        this.masterpriseDao = masterpriseDao;
    }
}
