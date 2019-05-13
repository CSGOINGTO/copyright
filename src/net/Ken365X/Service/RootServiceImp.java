package net.Ken365X.Service;


import net.Ken365X.Dao.RootDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.omg.PortableInterceptor.SUCCESSFUL;


/**
 * Created by mac on 2018/7/6.
 */
public class RootServiceImp implements RootService {

    RootDao rootDao;

    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAIL_NOROOT = 1;
    public static final int LOGIN_FAIL_PASSWORDWRONG = 2;

    public static final int CHANGER_ERROR = 1;
    public static final int CHANGE_SUCCESS = 0;

    public int Login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        try {
            subject.login(usernamePasswordToken);
            return LOGIN_SUCCESS;
        } catch (UnknownAccountException e) {
            return LOGIN_FAIL_NOROOT;
        } catch (IncorrectCredentialsException e) {
            return LOGIN_FAIL_PASSWORDWRONG;
        }
    }

    public int ChangePassword(String username, String password, String newPassword) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken(username,password);
        try{
            subject.login(usernamePasswordToken);
        }catch(Exception e){
            e.printStackTrace();
            return CHANGER_ERROR;
        }
        rootDao.rePasswordRoot(username,newPassword);
        return CHANGER_ERROR;
    }

    public int AddRoot(String username, String password) {
        return 0;
    }

    public int Logout(String username) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return 0;
    }

    public RootDao getRootDao() {
        return rootDao;
    }

    public void setRootDao(RootDao rootDao) {
        this.rootDao = rootDao;
    }
}
