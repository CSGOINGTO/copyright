package net.Ken365X.Service;

/**
 * Created by mac on 2018/7/6.
 */
public interface RootService {
    int Login(String username,String password);
    int ChangePassword(String username,String password, String newPassword);
    int AddRoot(String username,String password);
    int Logout(String username);
}
