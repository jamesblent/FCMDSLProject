package abc.docker.services.util;



import abc.docker.services.database.DatabaseManager;
import abc.docker.services.exception.BadRequestException;
import abc.docker.services.exception.ForbiddenRequestException;
import abc.docker.services.model.*;
import abc.docker.services.exception.InternalServerException;
import abc.docker.services.model.response.ResponseMeta;
import abc.docker.services.model.response.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsentUtil {

    public static void updateConsentStatusIfExpired(Consent consent) throws SQLException, IOException, ParseException {
        boolean isConsentUpdated = false;
      /* if (consent.getStatus().equals(CustomConstants.STATUS_AUTHORIZED) ||
                consent.getStatus().equalsIgnoreCase(CustomConstants.STATUS_AWAITING_AUTH)) {
            Date previousDate = getISO8601DateForString(consent.getCreationDateTime());
            Date currentDate = new Date();
            if (currentDate.after(previousDate)) {
                long diff = currentDate.getTime() - previousDate.getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                if (days > CustomConstants.CONSENT_EXPIRY_DAYS) {
                    consent.setStatus(CustomConstants.STATUS_EXPIRED);
                    isConsentUpdated = DatabaseManager.getInstance().saveConsent(consent, "update");
                }
            }
        }*/
        //if (isConsentUpdated)
        //    throw new BadRequestException(CustomConstants.ERROR_CONSENT_INVALID_CONSENT_STATUS);

    }

    public static void validateConsent(Consent consent)   {

        if (consent.getStatus().equals(CustomConstants.STATUS_REVOKED)||
                consent.getStatus() .equals( CustomConstants.STATUS_REJECTED)||
                consent.getStatus() .equals( CustomConstants.STATUS_CONSUMED) ||
                consent.getStatus() .equals( CustomConstants.STATUS_AWAITING_AUTH)) {
            throw new BadRequestException(CustomConstants.ERROR_CONSENT_INVALID_CONSENT_STATUS);

        }

    }

    public static void validateConsentForAuthorization(Consent consent)   {

        if (consent.getStatus().equals(CustomConstants.STATUS_REVOKED)||
                consent.getStatus() .equals( CustomConstants.STATUS_REJECTED)||
                consent.getStatus().equals( CustomConstants.STATUS_CONSUMED )) {
            throw new BadRequestException(CustomConstants.ERROR_CONSENT_INVALID_CONSENT_STATUS);

        }

    }

    public static String getGeneratedConsentId() {
        int min = 2, max = 7;
        double random = new Random().nextInt(max - min) + min;
        return CustomConstants.CONSENT_ID_IDENTIFIER + Math.floor(new Date().getTime() * random);

    }

    public static ArrayList<String> getPermissionsArrayFromString(String consentPermission) {
        if (consentPermission != null)
            return new ArrayList<String>(Arrays.asList(
                    consentPermission.replace("[", "").
                            replace("]", "").
                            split(",")));
        else return null;
    }


    public static Customer getCustomerbyId(String customerId) throws SQLException {
        Customer foundCustomer = DatabaseManager.getInstance().getCustomerbyId(customerId);
        if (foundCustomer == null)
            throw new BadRequestException(CustomConstants.ERROR_CUSTOMER_NOT_FOUND);
        return foundCustomer;
    }

    public static Customer getCustomerbyEmail(String customerEmail) throws SQLException {
        Customer foundCustomer = DatabaseManager.getInstance().getCustomerbyId(customerEmail);
        if (foundCustomer == null)
            throw new BadRequestException(CustomConstants.ERROR_CUSTOMER_NOT_FOUND);
        return foundCustomer;
    }




    public static void validateConsentForPermission(Consent foundConsent, String permissionType) throws  InternalServerException {
        String dbPermissions=foundConsent.getPersistedPermission();
        if(dbPermissions==null)
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);

        switch (permissionType)
        {  //403 Forbidden errors
            case "balances":
                if(!dbPermissions.contains("ReadBalances") )
                    throw new ForbiddenRequestException("Missing permission: required [ReadBalances]");

                break;
            case "transactions":
                if(!dbPermissions.contains("ReadTransactionsBasic") &&
                        !dbPermissions.contains("ReadTransactionsDetail"))
                    throw new ForbiddenRequestException("Missing permissions: required one of [ReadTransactionsBasic, ReadTransactionsDetail]");

                break;
            case "beneficiaries":
                if(!dbPermissions.contains("ReadBeneficiariesBasic") &&
                        !dbPermissions.contains("ReadBeneficiariesDetail"))
                    throw new ForbiddenRequestException("Missing permissions: required one of [ReadBeneficiariesBasic, ReadBeneficiariesDetail]");
                break;
        }
    }

    public static void validateConsentForDomesticPayment(Consent foundConsent, Consent domesticPaymentRequest) throws IOException {
        Gson gson = new GsonBuilder().create();
        Initiation requestInitiation=domesticPaymentRequest.getInitiation();
        Initiation dbInitiation=  gson.fromJson(foundConsent.getPersistedInitiation(),Initiation.class);

        //initiation validations start
        if(requestInitiation.getInstructionIdentification()!=null && dbInitiation.getInstructionIdentification()!=null
                && !requestInitiation.getInstructionIdentification().equalsIgnoreCase(dbInitiation.getInstructionIdentification())){
            throw new BadRequestException(CustomConstants.ERROR_INSTRUCTION_IDENTIFICATION_MISMATCHED);

        }

        if(requestInitiation.getEndToEndIdentification()!=null && dbInitiation.getEndToEndIdentification()!=null &&
                !requestInitiation.getEndToEndIdentification().equalsIgnoreCase(dbInitiation.getEndToEndIdentification())){
            throw new BadRequestException(CustomConstants.ERROR_ENDTOEND_IDENTIFICATION_MISMATCHED);

        }

        if((requestInitiation.getLocalInstrument()==null && dbInitiation.getLocalInstrument()==null) ||
                (requestInitiation.getLocalInstrument()!=null && dbInitiation.getLocalInstrument()!=null && requestInitiation.getLocalInstrument().equalsIgnoreCase(dbInitiation.getLocalInstrument()))){ }else{
            //show Error
            throw new BadRequestException(CustomConstants.ERROR_LOCALINSTRUMENT_MISMATCHED);
        }

        if(requestInitiation.getInstructedAmount()!=null && dbInitiation.getInstructedAmount()!=null){
            if(requestInitiation.getInstructedAmount().getAmount()!=null && dbInitiation.getInstructedAmount().getAmount()!=null &&
                    !requestInitiation.getInstructedAmount().getAmount().equalsIgnoreCase(dbInitiation.getInstructedAmount().getAmount())){
                throw new BadRequestException(CustomConstants.ERROR_INSTRUCTEDAMOUNT_MISMATCHED);

            }
            if(requestInitiation.getInstructedAmount().getCurrency()!=null && dbInitiation.getInstructedAmount().getCurrency()!=null &&
                    !requestInitiation.getInstructedAmount().getCurrency().equalsIgnoreCase(dbInitiation.getInstructedAmount().getCurrency())){
                throw new BadRequestException(CustomConstants.ERROR_INSTRUCTEDCURRENCY_MISMATCHED);


            }
        }

        if(requestInitiation.getDebtorAccount()!=null && dbInitiation.getDebtorAccount()!=null) {
            if(requestInitiation.getDebtorAccount().getSchemeName()!=null && dbInitiation.getDebtorAccount().getSchemeName()!=null &&
                    !requestInitiation.getDebtorAccount().getSchemeName().equalsIgnoreCase(dbInitiation.getDebtorAccount().getSchemeName())){
                throw new BadRequestException(CustomConstants.ERROR_DEBTORACCOUNT_SCHEMENAME_MISMATCHED);

            }
            if(requestInitiation.getDebtorAccount().getIdentification()!=null && dbInitiation.getDebtorAccount().getIdentification()!=null &&
                    !requestInitiation.getDebtorAccount().getIdentification().equalsIgnoreCase(dbInitiation.getDebtorAccount().getIdentification())){
                throw new BadRequestException(CustomConstants.ERROR_DEBTORACCOUNT_IDENTIFICATION_MISMATCHED);

            }
            if( (requestInitiation.getDebtorAccount().getName()==null && dbInitiation.getDebtorAccount().getName()==null) ||(requestInitiation.getDebtorAccount().getName()!=null && dbInitiation.getDebtorAccount().getName()!=null && requestInitiation.getDebtorAccount().getName().equalsIgnoreCase(dbInitiation.getDebtorAccount().getName()))){ }else{
                //show Error
                throw new BadRequestException(CustomConstants.ERROR_DEBTORACCOUNT_NAME_MISMATCHED);

            }
            if((requestInitiation.getDebtorAccount().getSecondaryIdentification()==null && dbInitiation.getDebtorAccount().getSecondaryIdentification()==null) ||
                    (requestInitiation.getDebtorAccount().getSecondaryIdentification()!=null && dbInitiation.getDebtorAccount().getSecondaryIdentification()!=null && requestInitiation.getDebtorAccount().getSecondaryIdentification().equalsIgnoreCase(dbInitiation.getDebtorAccount().getSecondaryIdentification()))){ }else{
                //show Error
                throw new BadRequestException(CustomConstants.ERROR_DEBTORACCOUNT_SECONDARY_IDENTIFICATION_MISMATCHED);

            }
        }else{
            if((requestInitiation.getDebtorAccount()==null && dbInitiation.getDebtorAccount()!=null)||
                    (requestInitiation.getDebtorAccount()!=null && dbInitiation.getDebtorAccount()==null)){
                throw new BadRequestException(CustomConstants.ERROR_DEBTORACCOUNT_MISMATCHED);

            }
        }
        if(requestInitiation.getCreditorAccount()!=null && dbInitiation.getCreditorAccount()!=null){
            if(requestInitiation.getCreditorAccount().getSchemeName()!=null && dbInitiation.getCreditorAccount().getSchemeName()!=null &&
                    !requestInitiation.getCreditorAccount().getSchemeName().equalsIgnoreCase(dbInitiation.getCreditorAccount().getSchemeName())){
                throw new BadRequestException(CustomConstants.ERROR_CREDITORACCOUNT_SCHEMENAME_MISMATCHED);

            }
            if(requestInitiation.getCreditorAccount().getIdentification()!=null && dbInitiation.getCreditorAccount().getIdentification()!=null &&
                    !requestInitiation.getCreditorAccount().getIdentification().equalsIgnoreCase(dbInitiation.getCreditorAccount().getIdentification())){
                throw new BadRequestException(CustomConstants.ERROR_CREDITORACCOUNT_IDENTIFICATION_MISMATCHED);

            }
            if( (requestInitiation.getCreditorAccount().getName()==null && dbInitiation.getCreditorAccount().getName()==null) ||
                    (requestInitiation.getCreditorAccount().getName()!=null && dbInitiation.getCreditorAccount().getName()!=null && requestInitiation.getCreditorAccount().getName().equalsIgnoreCase(dbInitiation.getCreditorAccount().getName()))){ }else{
                //show Error
                throw new BadRequestException(CustomConstants.ERROR_CREDITORACCOUNT_NAME_MISMATCHED);

            }
            if((requestInitiation.getCreditorAccount().getSecondaryIdentification()==null && dbInitiation.getCreditorAccount().getSecondaryIdentification()==null) ||
                    (requestInitiation.getCreditorAccount().getSecondaryIdentification()!=null && dbInitiation.getCreditorAccount().getSecondaryIdentification()!=null && requestInitiation.getCreditorAccount().getSecondaryIdentification().equalsIgnoreCase(dbInitiation.getCreditorAccount().getSecondaryIdentification()))){ }else{
                //show Error
                throw new BadRequestException(CustomConstants.ERROR_CREDITORACCOUNT_SECONDARY_IDENTIFICATION_MISMATCHED);

            }
        }

        if(requestInitiation.getCreditorPostalAddress()!=null && dbInitiation.getCreditorPostalAddress()!=null) {
            if((requestInitiation.getCreditorPostalAddress().getAddressType()==null && dbInitiation.getCreditorPostalAddress().getAddressType()==null) ||
                    (requestInitiation.getCreditorPostalAddress().getAddressType()!=null && dbInitiation.getCreditorPostalAddress().getAddressType()!=null &&
                            requestInitiation.getCreditorPostalAddress().getAddressType().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getAddressType()))){}else{

                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_ADDRESSTYPE_MISMATCHED);

            }

            if((requestInitiation.getCreditorPostalAddress().getDepartment()==null && dbInitiation.getCreditorPostalAddress().getDepartment()==null) ||(requestInitiation.getCreditorPostalAddress().getDepartment()!=null && dbInitiation.getCreditorPostalAddress().getDepartment()!=null &&
                    requestInitiation.getCreditorPostalAddress().getDepartment().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getDepartment()))){}else{
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_DEPARTMENT_MISMATCHED);

            }

            if((requestInitiation.getCreditorPostalAddress().getSubDepartment()==null && dbInitiation.getCreditorPostalAddress().getSubDepartment()==null)||
                    (requestInitiation.getCreditorPostalAddress().getSubDepartment()!=null && dbInitiation.getCreditorPostalAddress().getSubDepartment()!=null &&
                            requestInitiation.getCreditorPostalAddress().getSubDepartment().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getSubDepartment()))){}else{
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_SUBDEPARTMENT_MISMATCHED);

            }

            if((requestInitiation.getCreditorPostalAddress().getStreetName()==null && dbInitiation.getCreditorPostalAddress().getStreetName()==null)||
                    (requestInitiation.getCreditorPostalAddress().getStreetName()!=null && dbInitiation.getCreditorPostalAddress().getStreetName()!=null &&
                            requestInitiation.getCreditorPostalAddress().getStreetName().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getStreetName()))){}else{
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_STREETNAME_MISMATCHED);

            }
            if((requestInitiation.getCreditorPostalAddress().getBuildingNumber()==null && dbInitiation.getCreditorPostalAddress().getBuildingNumber()==null)||(requestInitiation.getCreditorPostalAddress().getBuildingNumber()!=null && dbInitiation.getCreditorPostalAddress().getBuildingNumber()!=null &&
                    requestInitiation.getCreditorPostalAddress().getBuildingNumber().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getBuildingNumber()))){}else{
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_BUILDINGNUMBER_MISMATCHED);

            }
            if((requestInitiation.getCreditorPostalAddress().getPostCode()==null && dbInitiation.getCreditorPostalAddress().getPostCode()==null)||(requestInitiation.getCreditorPostalAddress().getPostCode()!=null && dbInitiation.getCreditorPostalAddress().getPostCode()!=null &&
                    requestInitiation.getCreditorPostalAddress().getPostCode().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getPostCode()))){}else{
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_POSTCODE_MISMATCHED);

            }
            if((requestInitiation.getCreditorPostalAddress().getTownName()==null && dbInitiation.getCreditorPostalAddress().getTownName()==null)||(requestInitiation.getCreditorPostalAddress().getTownName()!=null && dbInitiation.getCreditorPostalAddress().getTownName()!=null &&
                    requestInitiation.getCreditorPostalAddress().getTownName().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getTownName()))){}else{
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_TOWNNAME_MISMATCHED);

            }
            if((requestInitiation.getCreditorPostalAddress().getCountrySubDivision()==null && dbInitiation.getCreditorPostalAddress().getCountrySubDivision()==null)||(requestInitiation.getCreditorPostalAddress().getCountrySubDivision()!=null && dbInitiation.getCreditorPostalAddress().getCountrySubDivision()!=null &&
                    requestInitiation.getCreditorPostalAddress().getCountrySubDivision().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getCountrySubDivision()))){}else {
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_COUNTRYSUBDIVISION_MISMATCHED);

            }
            if((requestInitiation.getCreditorPostalAddress().getCountry()==null && dbInitiation.getCreditorPostalAddress().getCountry()==null)||(requestInitiation.getCreditorPostalAddress().getCountry()!=null && dbInitiation.getCreditorPostalAddress().getCountry()!=null &&
                    requestInitiation.getCreditorPostalAddress().getCountry().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getCountry()))){}else {
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_COUNTRY_MISMATCHED);

            }
            if((requestInitiation.getCreditorPostalAddress().getAddressLine()==null && dbInitiation.getCreditorPostalAddress().getAddressLine()==null)||(requestInitiation.getCreditorPostalAddress().getAddressLine()!=null && dbInitiation.getCreditorPostalAddress().getAddressLine()!=null &&
                    requestInitiation.getCreditorPostalAddress().getAddressLine().equalsIgnoreCase(dbInitiation.getCreditorPostalAddress().getAddressLine()))){}else {
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_ADDRESSLINE_MISMATCHED);

            }
        }else{
            if((requestInitiation.getCreditorPostalAddress()==null && dbInitiation.getCreditorPostalAddress()!=null)||
                    (requestInitiation.getCreditorPostalAddress()!=null && dbInitiation.getCreditorPostalAddress()==null)){
                throw new BadRequestException(CustomConstants.ERROR_CREDITORPOSTALADDRESS_MISMATCHED);

            }
        }

        if(requestInitiation.getRemittanceInformation()!=null && dbInitiation.getRemittanceInformation()!=null){
            if((requestInitiation.getRemittanceInformation().getReference()==null && dbInitiation.getRemittanceInformation().getReference()==null)||(requestInitiation.getRemittanceInformation().getReference()!=null && dbInitiation.getRemittanceInformation().getReference()!=null &&
                    requestInitiation.getRemittanceInformation().getReference().equalsIgnoreCase(dbInitiation.getRemittanceInformation().getReference()))){}else {
                throw new BadRequestException(CustomConstants.ERROR_REMITTANCEINFO_REFERENCE_MISMATCHED);

            }
            if((requestInitiation.getRemittanceInformation().getUnstructured()==null && dbInitiation.getRemittanceInformation().getUnstructured()==null)||(requestInitiation.getRemittanceInformation().getUnstructured()!=null && dbInitiation.getRemittanceInformation().getUnstructured()!=null &&
                    requestInitiation.getRemittanceInformation().getUnstructured().equalsIgnoreCase(dbInitiation.getRemittanceInformation().getUnstructured()))){}else {
                throw new BadRequestException(CustomConstants.ERROR_REMITTANCEINFO_UNSTRUCTURED_MISMATCHED);
            }
        }else{
            if((requestInitiation.getRemittanceInformation()==null && dbInitiation.getRemittanceInformation()!=null)||
                    (requestInitiation.getRemittanceInformation()!=null && dbInitiation.getRemittanceInformation()==null)){
                throw new BadRequestException(CustomConstants.ERROR_REMITTANCEINFO_MISMATCHED);

            }
        }
        //initiation validation End


        //Risk Validations Start
        Risk requestRisk=domesticPaymentRequest.getRisk();
        Risk dbRisk=  gson.fromJson(foundConsent.getPersistedRisk(),Risk.class);
        if((requestRisk.getPaymentContextCode()==null && dbRisk.getPaymentContextCode()==null)||(requestRisk.getPaymentContextCode()!=null && dbRisk.getPaymentContextCode()!=null &&
                requestRisk.getPaymentContextCode().equalsIgnoreCase(dbRisk.getPaymentContextCode()))){}else {
            throw new BadRequestException(CustomConstants.ERROR_RISK_PAYMENT_CONTEXT_CODE_MISMATCHED);

        }
        if((requestRisk.getMerchantCategoryCode()==null && dbRisk.getMerchantCategoryCode()==null)||(requestRisk.getMerchantCategoryCode()!=null && dbRisk.getMerchantCategoryCode()!=null &&
                requestRisk.getMerchantCategoryCode().equalsIgnoreCase(dbRisk.getMerchantCategoryCode()))){}else{
            throw new BadRequestException(CustomConstants.ERROR_RISK_MERCHANT_CATEGORY_CODE_MISMATCHED);

        }

        if((requestRisk.getMerchantCustomerIdentification()==null && dbRisk.getMerchantCustomerIdentification()==null)||(requestRisk.getMerchantCustomerIdentification()!=null && dbRisk.getMerchantCustomerIdentification()!=null &&
                requestRisk.getMerchantCustomerIdentification().equalsIgnoreCase(dbRisk.getMerchantCustomerIdentification()))){}else{
            throw new BadRequestException(CustomConstants.ERROR_RISK_MERCHANT_CUSTOMER_IDNET_MISMATCHED);

        }
        if(requestRisk.getDeliveryAddress()!=null &&dbRisk.getDeliveryAddress()!=null){
            if((requestRisk.getDeliveryAddress().getAddressLine()==null && dbRisk.getDeliveryAddress().getAddressLine()==null)||(requestRisk.getDeliveryAddress().getAddressLine()!=null && dbRisk.getDeliveryAddress().getAddressLine()!=null) ) {
                if( !requestRisk.getDeliveryAddress().getAddressLine().toString().equalsIgnoreCase(dbRisk.getDeliveryAddress().getAddressLine().toString())){
                    throw new BadRequestException(CustomConstants.ERROR_RISK_DELIVERY_ADDRESS_MISMATCHED);
                }
            }else{
                throw new BadRequestException(CustomConstants.ERROR_RISK_DELIVERY_ADDRESS_MISMATCHED);
            }

            if((requestRisk.getDeliveryAddress().getStreetName()==null && dbRisk.getDeliveryAddress().getStreetName()==null)||(requestRisk.getDeliveryAddress().getStreetName()!=null && dbRisk.getDeliveryAddress().getStreetName()!=null &&
                    requestRisk.getDeliveryAddress().getStreetName().equalsIgnoreCase(dbRisk.getDeliveryAddress().getStreetName()))){}else{
                throw new BadRequestException(CustomConstants.ERROR_RISK_STREET_NAME_MISMATCHED);
            }
            if((requestRisk.getDeliveryAddress().getBuildingNumber()==null && dbRisk.getDeliveryAddress().getBuildingNumber()==null)||(requestRisk.getDeliveryAddress().getBuildingNumber()!=null && dbRisk.getDeliveryAddress().getBuildingNumber()!=null &&
                    requestRisk.getDeliveryAddress().getBuildingNumber().equalsIgnoreCase(dbRisk.getDeliveryAddress().getBuildingNumber()))){}else {
                throw new BadRequestException(CustomConstants.ERROR_RISK_BUILDING_NUMBER_MISMATCHED);
            }
            if((requestRisk.getDeliveryAddress().getPostCode()==null && dbRisk.getDeliveryAddress().getPostCode()==null)||(requestRisk.getDeliveryAddress().getPostCode()!=null && dbRisk.getDeliveryAddress().getPostCode()!=null &&
                    requestRisk.getDeliveryAddress().getPostCode().equalsIgnoreCase(dbRisk.getDeliveryAddress().getPostCode()))){}else {
                throw new BadRequestException(CustomConstants.ERROR_RISK_POST_CODE_MISMATCHED);

            }

            if(requestRisk.getDeliveryAddress().getTownName()!=null && dbRisk.getDeliveryAddress().getTownName()!=null &&
                    !requestRisk.getDeliveryAddress().getTownName().equalsIgnoreCase(dbRisk.getDeliveryAddress().getTownName())){
                throw new BadRequestException(CustomConstants.ERROR_RISK_TOWN_NAME_MISMATCHED);

            }

            if((requestRisk.getDeliveryAddress().getCountySubDivision()==null && dbRisk.getDeliveryAddress().getCountySubDivision()==null)||(requestRisk.getDeliveryAddress().getCountySubDivision()!=null && dbRisk.getDeliveryAddress().getCountySubDivision()!=null) ) {
                if( !requestRisk.getDeliveryAddress().getCountySubDivision().toString().equalsIgnoreCase(dbRisk.getDeliveryAddress().getCountySubDivision().toString())){
                    throw new BadRequestException(CustomConstants.ERROR_RISK_COUNTRY_SUBDIVISION_MISMATCHED);

                }
            }else{
                throw new BadRequestException(CustomConstants.ERROR_RISK_COUNTRY_SUBDIVISION_MISMATCHED);
            }
            if(requestRisk.getDeliveryAddress().getCountry()!=null && dbRisk.getDeliveryAddress().getCountry()!=null &&
                    !requestRisk.getDeliveryAddress().getCountry().equalsIgnoreCase(dbRisk.getDeliveryAddress().getCountry())){
                throw new BadRequestException(CustomConstants.ERROR_RISK_COUNTRY_MISMATCHED);

            }
        }else {
            if ((requestRisk.getDeliveryAddress() == null && dbRisk.getDeliveryAddress() != null) ||
                    (requestRisk.getDeliveryAddress() != null && dbRisk.getDeliveryAddress() == null)) {
                throw new BadRequestException(CustomConstants.ERROR_RISK_DELIVERYADRESS_MISMATCHED);

            }
        }
        //Risk Validations END



    }

    public static boolean checkPISPFundsConfirmation(Consent consent) throws IOException, SQLException {
        Gson gson = new GsonBuilder().create();
        Initiation dbInitiation=  gson.fromJson(consent.getPersistedInitiation(),Initiation.class);

        UserAccount userAccount=DatabaseManager.getInstance().getAccountFromConsent(consent);
        if(Double.valueOf(userAccount.getAvailableBalance())>Double.valueOf(dbInitiation.getInstructedAmount().getAmount())){
            return true;
        }
        return false;

    }


    public static Consent createConsentBean(Consent consent) throws InternalServerException, JsonProcessingException, ParseException {
        ObjectMapper objectMapper=new ObjectMapper();
        Gson gson = new GsonBuilder().create();

        //generate consent id
        consent.setConsentId(ConsentUtil.getGeneratedConsentId());
        consent.setCreationDateTime(ConsentUtil.getISO8601StringForDate(new Date()));
        consent.setStatus(CustomConstants.STATUS_AWAITING_AUTH);
        consent.setStatusUpdateDateTime(ConsentUtil.getISO8601StringForDate(new Date()));

        switch (consent.getConsentType()) {
            case CustomConstants.CONSENT_TYPE_AISP:
                //convert bean for database
                consent.setPersistedPermission(consent.getPermissions().toString());
                break;
            case CustomConstants.CONSENT_TYPE_PISP:
                //validation checks
                if (consent.getInitiation() == null)
                    throw new InternalServerException(CustomConstants.ERROR_PISP_INITITATION_NOT_FOUND);

                //convert bean for database
                consent.setPersistedInitiation(gson.toJson(consent.getInitiation()));
                if(consent.getScaSupportData()!=null )
                    if(consent.getScaSupportData().getRequestedSCAExemptionType()!=null
                            || consent.getScaSupportData().getReferencePaymentOrderId()!=null || consent.getScaSupportData().getAppliedAuthenticationApproach()!=null){
                        consent.setPersistedSCASupportData(gson.toJson(consent.getScaSupportData()));
                    }
                if(consent.getAuthorisation()!=null)
                    if( consent.getAuthorisation().getAuthorisationType()!=null){
                        consent.setPersistedAuthorisation(gson.toJson(consent.getAuthorisation()));
                    }


                break;
        }

        if (consent.getLinks() != null)
            consent.setPersistedLinks(objectMapper.writeValueAsString(consent.getLinks()));

        if (consent.getMeta() != null)
            consent.setPersistedMeta(objectMapper.writeValueAsString(consent.getMeta()));

        if (consent.getRisk() != null)
            consent.setPersistedRisk(gson.toJson(consent.getRisk()));

        return consent;
    }




    public static SuccessResponse createSuccessResponse(Object data) {
        SuccessResponse successResponse = new SuccessResponse();
        ResponseMeta responseMeta=new ResponseMeta();
        responseMeta.setCode("000000");
        responseMeta.setMessage("success");
        successResponse.setMeta(responseMeta);
        if(data!=null)
        successResponse.setData(data);

        return successResponse;
    }
    public static String getISO8601StringForDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String[] acceptedFormats = {"yyyy-MM-dd'T'HH:mm:ss","yyyy-MM-dd","yyyy-MM-dd'T'HH:mm:ssXXX"};
        TimeZone tz = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(tz);
        Date text = DateUtils.parseDate(date, acceptedFormats);
        return sdf.format(text);
    }
    public static String getISO8601StringForDate(Date date)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(tz);
        String text = sdf.format(date);
        return  text;
    }

    public static Date getISO8601DateForString(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date = sdf.parse(strDate);
        return  date;
    }
}
