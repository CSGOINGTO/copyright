package net.Ken365X.Service;

import net.Ken365X.Dao.DesignerDao;
import net.Ken365X.Dao.EnterpriseDao;
import net.Ken365X.Entity.Designer;
import net.Ken365X.Entity.Enterprise;
import net.Ken365X.Utils.CitiApiUtils;

/**
 * Created by mac on 2018/8/25.
 */
public class CitiServiceImp implements CitiService{
    private DesignerDao designerDao;
    private EnterpriseDao enterpriseDao;

    @Override
    public String bindCitiBankAccount(String username, String username_citi, String password) {
        CitiApiUtils citiApiUtil = new CitiApiUtils();
        citiApiUtil.setUsername(username_citi);
        citiApiUtil.setPassword(password);
        String result = null;
        try{
            result = citiApiUtil.checkAccount();
        }catch(Exception e){
            e.printStackTrace();
        }
        if (result == null){
            return null;
        }
        Designer designer = null;
        try{
            designer = designerDao.getDesignerByName(username);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (designer == null){
            return null;
        }
        designer.setCiti_account(username_citi);
        designerDao.updateDesigner(designer);
        return result;
    }

    @Override
    public boolean bindCitiBankAccount2(String username, String account,String account_display) {
        Designer designer = null;
        try{
            designer = designerDao.getDesignerByName(username);
        }catch(Exception e){
            // e.printStackTrace();
        }
        if (designer == null){
            return false;
        }
        designer.setCiti_num(account);
        designer.setCiti_display(account_display);
        return designerDao.updateDesigner(designer);
    }

    @Override
    public String bindCitiBankAccountE(String username, String username_citi, String password) {
        CitiApiUtils citiApiUtil = new CitiApiUtils();
        citiApiUtil.setUsername(username_citi);
        citiApiUtil.setPassword(password);
        String result = null;
        try{
            result = citiApiUtil.checkAccount();
        }catch(Exception e){
            e.printStackTrace();
        }
        if (result == null){
            return null;
        }
        Enterprise enterprise = null;
        try{
            enterprise = enterpriseDao.getEnterpriseByName(username);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (enterprise == null){
            return null;
        }
        enterprise.setCiti_account(username_citi);
        enterpriseDao.updateEnterprise(enterprise);
        return result;
    }

    @Override
    public boolean bindCitiBankAccountE2(String username, String account, String account_display) {
        Enterprise enterprise = null;
        try{
            enterprise = enterpriseDao.getEnterpriseByName(username);
        }catch(Exception e){
            // e.printStackTrace();
        }
        if (enterprise == null){
            return false;
        }
        enterprise.setCiti_num(account);
        enterprise.setCiti_display(account_display);
        return enterpriseDao.updateEnterprise(enterprise);
    }

    @Override
    public int checkCitiBankE(String username, String password) {
        Enterprise enterprise = null;
        try{
            enterprise = enterpriseDao.getEnterpriseByName(username);
        }catch(Exception e){
            // e.printStackTrace();
        }
        CitiApiUtils citiApiUtil = new CitiApiUtils();
        citiApiUtil.setUsername(enterprise.getCiti_account());
        citiApiUtil.setPassword(password);
        if (citiApiUtil.checkAccount() != null){
            return 0;
        } else {
            return -1;
        }
    }

    public DesignerDao getDesignerDao() {
        return designerDao;
    }

    public void setDesignerDao(DesignerDao designerDao) {
        this.designerDao = designerDao;
    }

    public EnterpriseDao getEnterpriseDao() {
        return enterpriseDao;
    }

    public void setEnterpriseDao(EnterpriseDao enterpriseDao) {
        this.enterpriseDao = enterpriseDao;
    }
}
