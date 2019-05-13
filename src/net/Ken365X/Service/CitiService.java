package net.Ken365X.Service;

/**
 * Created by mac on 2018/8/25.
 */
public interface CitiService {
    String bindCitiBankAccount(String username,String username_citi, String password);
    boolean bindCitiBankAccount2(String username, String account,String account_display);
    String bindCitiBankAccountE(String username,String username_citi, String password);
    boolean bindCitiBankAccountE2(String username, String account,String account_display);

    int checkCitiBankE(String username,String password);
}
