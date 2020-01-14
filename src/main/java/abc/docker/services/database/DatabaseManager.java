package abc.docker.services.database;

import abc.docker.services.model.Consent;
import abc.docker.services.model.Customer;
import abc.docker.services.model.UserAccount;
import abc.docker.services.util.CustomConstants;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {
    DatabaseHelper dbOpenHelper = null;
    static final String TAG = "DatabaseManager";
    static DatabaseManager dbManager = null;
    private DatabaseManager() throws SQLException {
        dbOpenHelper = new DatabaseHelper();
    }

    public static DatabaseManager getInstance() throws SQLException {
        if (dbManager == null) {
            dbManager = new DatabaseManager();
        }
        return dbManager;
    }


    public boolean saveCustomer(Customer consent, String action) throws SQLException, IOException {
        Dao<Customer, Integer> customerDao = dbOpenHelper.getCustomerDao();
        Dao.CreateOrUpdateStatus i=customerDao.createOrUpdate(consent);
        dbOpenHelper.connectionSource.close();
        if(action.equalsIgnoreCase(CustomConstants.CONSENT_ACTION_UPDATE))
            return i.isUpdated();
        else
            return i.isCreated();
    }

    public boolean saveUserAccounts(UserAccount userAccount) throws SQLException {
        Dao<UserAccount, Integer> userAccountsDao = dbOpenHelper.getUserAccountsDao();
        Dao.CreateOrUpdateStatus i=userAccountsDao.createOrUpdate(userAccount);
        return i.isCreated();
    }


    public UserAccount getConsentForAccount(Consent  foundConsent, String accountId) throws SQLException {
        List<UserAccount> userAccounts = null;
        userAccounts = dbOpenHelper.getUserAccountsDao().queryForEq("CONSENT_ID",foundConsent);
        if(userAccounts!=null&&userAccounts.size()>0) {
            for(UserAccount userAccount:userAccounts){
                if(userAccount.getAccountId().equalsIgnoreCase(accountId)){
                    return userAccount;
                }
            }
        }
        return null;
    }

    public Customer getCustomerbyId(String customerId) throws SQLException {
        List<Customer> customers = null;
        customers = dbOpenHelper.getCustomerDao().queryForEq("CUSTOMER_ID",customerId);
        if(customers!=null&&customers.size()>0) {
            return customers.get(0);
        }
        return null;
    }

    public List<Customer> getCustomerbyEmail(String customerId) throws SQLException {
        List<Customer> customers = null;
        customers = dbOpenHelper.getCustomerDao().queryForEq("Email",customerId);
        if(customers!=null&&customers.size()>0) {
            return customers;
        }
        return null;
    }

    public UserAccount getAccountFromConsent(Consent  foundConsent) throws SQLException {
        List<UserAccount> userAccounts = null;
        userAccounts = dbOpenHelper.getUserAccountsDao().queryForEq("CONSENT_ID",foundConsent);
        if(userAccounts!=null&&userAccounts.size()>0) {
            return userAccounts.get(0);
        }
        return null;
    }
}
