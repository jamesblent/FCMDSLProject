package abc.docker.services.util;

public class CustomConstants {

    public static final String STATUS_AWAITING_AUTH="AwaitingAuthorisation";
    public static final String STATUS_REVOKED="Revoked";
    public static final String STATUS_AUTHORIZED="Authorised";
    public static final String STATUS_REJECTED="Rejected";
    public static final String STATUS_CONSUMED="Consumed";

    public static final String CONSENT_TYPE_AISP="AISP";
    public static final String CONSENT_TYPE_PISP="PISP";


    //error message format is "errorcode||errormessage"
    public static final String ERROR_CUSTOMER_NOT_SAVED="GENE-1000@Customer not saved.";
    public static final String ERROR_CUSTOMER_NOT_FOUND="Resource.NotFound@Customer with the specified id does not exist";
    public static final String ERROR_CONSENT_INVALID_CONSENT_STATUS="Resource.InvalidConsentStatus@Consent with the specified id is invalid";
    public static final String ERROR_CONSENT_ACCOUNTS_NOT_FOUND="Resource.NotFound@Accounts do not exist";
    public static final String ERROR_CONSENT_ACCOUNT_NOT_VALIDATED="Resource.NotFound@Account with the specified id does not exist";
    public static final String ERROR_DB_CONNECTION="GENE-1008@Please check DB connection and try again";
    public static final String ERROR_EXCEPTION="GENE-1009@There is some exception in the code";
    public static final String ERROR_PISP_INITITATION_NOT_FOUND="GENE-1012@'Initiation' is invalid/missing";
    public static final String ERROR_REQUEST_NOT_CORRECT="GENE-1014@Request is not in is correct format.";
    public static final String ERROR_CONSENT_NOT_UPDATED="GENE-1017@Consent has not been updated.";
    public static final String ERROR_CONSENT_NOT_AUTHORIZED="GENE-1018@The access consent has been successfully authorised.";
    public static final String ERROR_CONSENT_NOT_DELETED="GENE-1025@Consent has not been deleted.";
    public static final String ERROR_INSTRUCTION_IDENTIFICATION_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.InstructionIdentification' is missing or values are not matching";
    public static final String ERROR_ENDTOEND_IDENTIFICATION_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.EndToEndIdentification' is missing or values are not matching";
    public static final String ERROR_LOCALINSTRUMENT_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.LocalInstrument' is missing or values are not matching";
    public static final String ERROR_INSTRUCTEDAMOUNT_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.InstructedAmount.Amount' is missing or values are not matching";
    public static final String ERROR_INSTRUCTEDCURRENCY_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.InstructedAmount.Currency' is missing or values are not matching";
    public static final String ERROR_DEBTORACCOUNT_SCHEMENAME_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.DebtorAccount.SchemeName' is missing or values are not matching";
    public static final String ERROR_DEBTORACCOUNT_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.DebtorAccount' is missing or values are not matching";
    public static final String ERROR_DEBTORACCOUNT_IDENTIFICATION_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.DebtorAccount.Identification' is missing or values are not matching";
    public static final String ERROR_DEBTORACCOUNT_NAME_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.DebtorAccount.Name' is missing or values are not matching";
    public static final String ERROR_DEBTORACCOUNT_SECONDARY_IDENTIFICATION_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.DebtorAccount.SecondaryIdentification' is missing or values are not matching";
    public static final String ERROR_CREDITORACCOUNT_SCHEMENAME_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorAccount.SchemeName' is missing or values are not matching";
    public static final String ERROR_CREDITORACCOUNT_IDENTIFICATION_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorAccount.Identification' is missing or values are not matching";
    public static final String ERROR_CREDITORACCOUNT_NAME_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorAccount.Name' is missing or values are not matching";
    public static final String ERROR_CREDITORACCOUNT_SECONDARY_IDENTIFICATION_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorAccount.SecondaryIdentification' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_ADDRESSTYPE_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.AddressType' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_DEPARTMENT_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.Department' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_SUBDEPARTMENT_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.SubDepartment' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_STREETNAME_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.StreetName' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_BUILDINGNUMBER_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.BuildingNumber' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_POSTCODE_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.PostCode' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_TOWNNAME_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.TownName' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_COUNTRYSUBDIVISION_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.CountrySubDivision' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_COUNTRY_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.Country' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_ADDRESSLINE_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress.AddressLine' is missing or values are not matching";
    public static final String ERROR_CREDITORPOSTALADDRESS_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.CreditorPostalAddress' is missing or values are not matching";
    public static final String ERROR_REMITTANCEINFO_REFERENCE_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.RemittanceInformation.Reference' is missing or values are not matching";
    public static final String ERROR_REMITTANCEINFO_UNSTRUCTURED_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.RemittanceInformation.Unstructured' is missing or values are not matching";
    public static final String ERROR_REMITTANCEINFO_MISMATCHED="Resource.ConsentMismatch@Field 'Data.Initiation.RemittanceInformation' is missing or values are not matching";
    public static final String ERROR_RISK_PAYMENT_CONTEXT_CODE_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.PaymentContextCode' is missing or values are not matching";
    public static final String ERROR_RISK_MERCHANT_CATEGORY_CODE_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.MerchantCategoryCode' is missing or values are not matching";
    public static final String ERROR_RISK_MERCHANT_CUSTOMER_IDNET_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.MerchantCustomerIdentification' is missing or values are not matching";
    public static final String ERROR_RISK_DELIVERY_ADDRESS_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.DeliveryAddress.AddressLine' is missing or values are not matching";
    public static final String ERROR_RISK_STREET_NAME_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.DeliveryAddress.StreetName' is missing or values are not matching";
    public static final String ERROR_RISK_BUILDING_NUMBER_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.DeliveryAddress.BuildingNumber' is missing or values are not matching";
    public static final String ERROR_RISK_POST_CODE_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.DeliveryAddress.PostCode' is missing or values are not matching";
    public static final String ERROR_RISK_TOWN_NAME_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.DeliveryAddress.TownName' is missing or values are not matching";
    public static final String ERROR_RISK_COUNTRY_SUBDIVISION_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.DeliveryAddress.CountySubDivision' is missing or values are not matching";
    public static final String ERROR_RISK_COUNTRY_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.DeliveryAddress.Country' is missing or values are not matching";
    public static final String ERROR_RISK_DELIVERYADRESS_MISMATCHED="Resource.ConsentMismatch@Field 'Risk.DeliveryAddress' is missing or values are not matching";


    public static final String SUCCESS_CONSENT_SAVED="CONSENT-1000:Consent has been saved successfully.";
    public static final String SUCCESS_CONSENT_RETREIVED="CONSENT-1001:Consent details has been retrieved successfully.";


    public static final int CONSENT_EXPIRY_DAYS=1;
    public static final String CONSENT_ID_IDENTIFIER="urn-abcbank-intent-";
    public static final String CONSENT_ACTION_UPDATE="update";
    public static final String CONSENT_ACTION_CREATE="create";



}
