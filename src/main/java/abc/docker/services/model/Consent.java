package abc.docker.services.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;

@DatabaseTable(tableName = "CONSENT")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Consent {
    public Consent() {
    }

    @DatabaseField(columnName="CONSENT_ID" ,id = true)
    private String consentId;

    @DatabaseField(columnName = "CONSENT_TYPE", canBeNull = false)
    private String consentType;

    @DatabaseField( columnName = "CONSENT_STATUS", canBeNull = false)
    private String status;

    @DatabaseField(columnName = "DAT_STATUS_UPDATE")
    private String statusUpdateDateTime;

    @DatabaseField(columnName = "DAT_CREATION")
    private String creationDateTime;

    @DatabaseField(columnName = "DAT_EXPIRATION")
    private String expirationDateTime;

    @DatabaseField( columnName = "USER_ID")
    private String userId;

    @DatabaseField( columnName = "TPP_REDIRECT_URI")
    private String tppRedirectURI;

    @DatabaseField( columnName = "TPP_STATE")
    private String tppState;

    @DatabaseField( columnName = "TPP_KEY")
    private String tppKey;


    /*AISP Consents column start */
    @DatabaseField(columnName = "AI_DAT_TXN_FROM")
    private String transactionFromDateTime;

    @DatabaseField(columnName = "AI_DAT_TXN_TO")
    private String transactionToDateTime;

    transient private ArrayList<String> permissions;

    @JsonIgnore //ignored in json because it's persisted permission object as String
    @DatabaseField( columnName = "AI_CONSENT_PERMISSION", columnDefinition = "VARCHAR(3000)")
    private String persistedPermission;
    /*AISP Consents column end */

    /*PISP Consents column start */


    transient private Initiation initiation;

    @JsonIgnore //ignored in json because it's persisted object as String
    @DatabaseField( columnName = "PI_INITIATION", columnDefinition = "VARCHAR(3000)")
    private String persistedInitiation;

    transient private Authorisation authorisation;

    @JsonIgnore //ignored in json because it's persisted object as String
    @DatabaseField( columnName = "PI_AUTHORISATION", columnDefinition = "VARCHAR(3000)")
    private String persistedAuthorisation;

    transient private SCASupportData scaSupportData;

    @JsonIgnore //ignored in json because it's persisted object as String
    @DatabaseField( columnName = "PI_SCA_SUPPORT_DATA", columnDefinition = "VARCHAR(3000)")
    private String persistedSCASupportData;

    /*PISP Consents column ends */

    transient private Risk risk;

    @JsonIgnore //ignored in json because it's persisted  object as String
    @DatabaseField(columnName = "RISK", columnDefinition = "VARCHAR(3000)")
    private String persistedRisk;

    transient private Object links;

    @JsonIgnore //ignored in json because it's persisted  object as String
    @DatabaseField(columnName = "LINKS", columnDefinition = "VARCHAR(3000)")
    private String persistedLinks;

    transient private Object meta;

    @JsonIgnore //ignored in json because it's persisted  object as String
    @DatabaseField(columnName = "META", columnDefinition = "VARCHAR(3000)")
    private String persistedMeta;

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public String getConsentType() {
        return consentType;
    }

    public void setConsentType(String consentType) {
        this.consentType = consentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusUpdateDateTime() {
        return statusUpdateDateTime;
    }

    public void setStatusUpdateDateTime(String statusUpdateDateTime) {
        this.statusUpdateDateTime = statusUpdateDateTime;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(String expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public void setTransactionFromDateTime(String transactionFromDateTime) {
        this.transactionFromDateTime = transactionFromDateTime;
    }

    public void setTransactionToDateTime(String transactionToDateTime) {
        this.transactionToDateTime = transactionToDateTime;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTppRedirectURI() {
        return tppRedirectURI;
    }

    public void setTppRedirectURI(String tppRedirectURI) {
        this.tppRedirectURI = tppRedirectURI;
    }

    public String getTppState() {
        return tppState;
    }

    public void setTppState(String tppState) {
        this.tppState = tppState;
    }

    public String getTppKey() {
        return tppKey;
    }

    public void setTppKey(String tppKey) {
        this.tppKey = tppKey;
    }

    public String getTransactionFromDateTime() {
        return transactionFromDateTime;
    }

    public String getTransactionToDateTime() {
        return transactionToDateTime;
    }

    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
    }

    public String getPersistedPermission() {
        return persistedPermission;
    }

    public void setPersistedPermission(String persistedPermission) {
        this.persistedPermission = persistedPermission;
    }




    public String getPersistedInitiation() {
        return persistedInitiation;
    }

    public void setPersistedInitiation(String persistedInitiation) {
        this.persistedInitiation = persistedInitiation;
    }



    public String getPersistedAuthorisation() {
        return persistedAuthorisation;
    }

    public void setPersistedAuthorisation(String persistedAuthorisation) {
        this.persistedAuthorisation = persistedAuthorisation;
    }


    public String getPersistedSCASupportData() {
        return persistedSCASupportData;
    }

    public void setPersistedSCASupportData(String persistedSCASupportData) {
        this.persistedSCASupportData = persistedSCASupportData;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public String getPersistedRisk() {
        return persistedRisk;
    }

    public void setPersistedRisk(String persistedRisk) {
        this.persistedRisk = persistedRisk;
    }

    public Object getLinks() {
        return links;
    }

    public void setLinks(Object links) {
        this.links = links;
    }

    public String getPersistedLinks() {
        return persistedLinks;
    }

    public void setPersistedLinks(String persistedLinks) {
        this.persistedLinks = persistedLinks;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public String getPersistedMeta() {
        return persistedMeta;
    }

    public void setPersistedMeta(String persistedMeta) {
        this.persistedMeta = persistedMeta;
    }

    public Initiation getInitiation() {
        return initiation;
    }

    public void setInitiation(Initiation initiation) {
        this.initiation = initiation;
    }

    public Authorisation getAuthorisation() {
        return authorisation;
    }

    public void setAuthorisation(Authorisation authorisation) {
        this.authorisation = authorisation;
    }

    public SCASupportData getScaSupportData() {
        return scaSupportData;
    }

    public void setScaSupportData(SCASupportData scaSupportData) {
        this.scaSupportData = scaSupportData;
    }
}
