package abc.docker.services.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "USER_ACCOUNT")
public class UserAccount {

    public UserAccount() {
    }
    @JsonIgnore
    @DatabaseField(generatedId = true,columnName = "USER_ACCOUNT_ID")
    int id;

    @DatabaseField(columnName = "ACCOUNT_NO", canBeNull = false)
    String accountId;

    @DatabaseField(columnName = "ACCOUNT_ALIAS", canBeNull = false)
    String accountAlias;

    @DatabaseField(columnName = "CUSTOMER_ID", canBeNull = false)
    String customerId;


    @DatabaseField(columnName = "PRODUCT_TYPE", canBeNull = false)
    String productType;

    @DatabaseField(columnName = "PRODUCT_NAME", canBeNull = false)
    String productName;


    @DatabaseField(columnName = "CATEGORY1")
    String category1;


    @DatabaseField(columnName = "CATEGORY2")
    String category2;

    @DatabaseField(columnName = "IS_DETAIL_EXIST", canBeNull = false)
    boolean detailsExist;

    @JsonIgnore
    @DatabaseField(columnName = "DAT_CREATION", canBeNull = false)
    Date datCreation;


    @DatabaseField(columnName = "AVAILABLE_BALANCE", canBeNull = true)
    String availableBalance;


    @JsonIgnore
    @DatabaseField(foreign = true, columnName = "CONSENT_ID")
    Consent consent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountAlias() {
        return accountAlias;
    }

    public void setAccountAlias(String accountAlias) {
        this.accountAlias = accountAlias;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public boolean isDetailsExist() {
        return detailsExist;
    }

    public void setDetailsExist(boolean detailsExist) {
        this.detailsExist = detailsExist;
    }

    public Date getDatCreation() {
        return datCreation;
    }

    public void setDatCreation(Date datCreation) {
        this.datCreation = datCreation;
    }

    public Consent getConsent() {
        return consent;
    }

    public void setConsent(Consent consent) {
        this.consent = consent;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }
}
