package abc.docker.services.database;

import abc.docker.services.model.Consent;
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


    public boolean saveConsent(Consent consent,String action) throws SQLException, IOException {
        Dao<Consent, Integer> consentDao = dbOpenHelper.getConsentDao();
        Dao.CreateOrUpdateStatus i=consentDao.createOrUpdate(consent);
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

    public Consent getConsent(String  consentId) throws SQLException {
        List<Consent> consent = null;
        consent = dbOpenHelper.getConsentDao().queryForEq("CONSENT_ID",consentId);
        if(consent!=null&&consent.size()>0)
            return consent.get(0);
        return null;
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

    public UserAccount getConsentAccount(Consent  foundConsent) throws SQLException {
        List<UserAccount> userAccounts = null;
        userAccounts = dbOpenHelper.getUserAccountsDao().queryForEq("CONSENT_ID",foundConsent);
        if(userAccounts!=null&&userAccounts.size()>0) {
            return userAccounts.get(0);
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
