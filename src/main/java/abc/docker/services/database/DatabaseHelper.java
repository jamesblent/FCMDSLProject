package abc.docker.services.database;

import abc.docker.services.model.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper {
    JdbcConnectionSource connectionSource;
    private static final Class<?>[] TABLES = new Class<?>[] {Customer.class,
            Group.class, Topic.class
    };

    private Dao<Consent, Integer> consentDao = null;
    private Dao<Customer, Integer> customerDao = null;
    private Dao<UserAccount, Integer> userAccountsDao = null;

    public  DatabaseHelper() throws SQLException{
        //use any dbconnection url db2/sqlserver/mysql
        connectionSource = new JdbcConnectionSource(
                "jdbc:mysql://remotemysql.com:3306/Y9suwvShtg",
                "Y9suwvShtg","zbZQP2mtVl" );

            /*Uncomment if you want to create tables using code,
            (just for testing& quick practice)
            */
            setupDatabase();
            //   deleteDatabase();
    }

    private void setupDatabase(){
        try {
            for (Class<?> tableClass : TABLES) {
                TableUtils.createTableIfNotExists(connectionSource, tableClass);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteDatabase() throws SQLException{

        TableUtils.dropTable(connectionSource, Consent.class,true);
        TableUtils.dropTable(connectionSource, UserAccount.class,true);

    }



     public Dao<Customer, Integer> getCustomerDao() throws SQLException {
        if (customerDao == null) {
            customerDao = DaoManager.createDao(connectionSource,Customer.class);
        }
        return customerDao;
    }




    public Dao<UserAccount, Integer> getUserAccountsDao() throws SQLException {
        if (userAccountsDao == null) {
            userAccountsDao = DaoManager.createDao(connectionSource,UserAccount.class);
        }
        return userAccountsDao;
    }

}
