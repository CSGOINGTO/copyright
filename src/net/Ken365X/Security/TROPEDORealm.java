package net.Ken365X.Security;

import net.Ken365X.Dao.RootDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * Created by mac on 2018/7/6.
 */
public class TROPEDORealm extends AuthenticatingRealm {
    private RootDao rootDao;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = rootDao.getPasswordByName(username);
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(username,password,"TROPEDORealm");
        return simpleAuthenticationInfo;
    }

    public RootDao getRootDao() {
        return rootDao;
    }

    public void setRootDao(RootDao rootDao) {
        this.rootDao = rootDao;
    }
}
