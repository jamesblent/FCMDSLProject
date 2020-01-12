package abc.docker.services.controller;

import abc.docker.services.database.DatabaseManager;
import abc.docker.services.exception.BadRequestException;
import abc.docker.services.exception.InternalServerException;
import abc.docker.services.model.Consent;
import abc.docker.services.model.UserAccount;
import abc.docker.services.model.request.AuthorizeConsentRequest;
import abc.docker.services.model.response.FundsConfirmationResponse;
import abc.docker.services.model.response.PostConsentResponse;
import abc.docker.services.model.response.SuccessResponse;
import abc.docker.services.util.ConsentUtil;
import abc.docker.services.util.CustomConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Usman Shafi on 29-July-19.
 */

@RestController
@RequestMapping("v1/access-consent")
public class ConsentStoreController {

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createConsent(@RequestBody Consent consentRequest) throws InternalServerException {
        boolean isConsentCreated = false;

        PostConsentResponse postConsentResponse = new PostConsentResponse();

        try {
            Consent consent=ConsentUtil.createConsentBean(consentRequest);
            isConsentCreated = DatabaseManager.getInstance().saveConsent(consent,CustomConstants.CONSENT_ACTION_CREATE);
            if(isConsentCreated) {
                postConsentResponse.setConsent_id(consent.getConsentId());
                postConsentResponse.setCreationDateTime(ConsentUtil.getISO8601StringForDate(consent.getCreationDateTime()));
                postConsentResponse.setStatusUpdateDateTime(ConsentUtil.getISO8601StringForDate(consent.getStatusUpdateDateTime()));
                if(consent.getTransactionFromDateTime()!=null && !consent.getTransactionFromDateTime().isEmpty())
                    postConsentResponse.setTransactionFromDateTime(ConsentUtil.getISO8601StringForDate(consent.getTransactionFromDateTime()));
                if(consent.getTransactionToDateTime()!=null && !consent.getTransactionToDateTime().isEmpty())
                    postConsentResponse.setTransactionToDateTime(ConsentUtil.getISO8601StringForDate(consent.getTransactionToDateTime()));
                if(consent.getExpirationDateTime()!=null && !consent.getExpirationDateTime().isEmpty())
                    postConsentResponse.setExpirationDateTime(ConsentUtil.getISO8601StringForDate(consent.getExpirationDateTime()));
                postConsentResponse.setStatus(consent.getStatus());
                postConsentResponse.setMeta(consent.getMeta());
            }
            else
                throw new InternalServerException(CustomConstants.ERROR_CONSENT_NOT_SAVED);

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        } catch (IOException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        } catch (ParseException e) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }

        return new ResponseEntity<>((ConsentUtil.createSuccessResponse(postConsentResponse)),HttpStatus.CREATED);
    }

    @GetMapping("/{consentId}")
    public ResponseEntity<SuccessResponse<Consent>> getConsent(@PathVariable("consentId") String consentId) throws InternalServerException {
        try {

            Consent responseConsent=ConsentUtil.getAndConvertResponseConsentBean(consentId);
            return ResponseEntity.ok(ConsentUtil.createSuccessResponse(responseConsent));

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        }catch (IOException | ParseException ex) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }
    }

    @GetMapping("/{consentId}/accounts")
    public ResponseEntity<UserAccount> getConsentAccount(@PathVariable("consentId") String consentId) throws InternalServerException {
        try {

            UserAccount userAccount=ConsentUtil.getAccountFromConsent(consentId);
            return ResponseEntity.ok(userAccount);

        } catch (SQLException  ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        }catch (IOException|ParseException ex){
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }
    }


    @PutMapping("/{consentId}")
    public ResponseEntity updateConsent(@RequestBody Consent inputConsent, @PathVariable("consentId") String consentId) throws InternalServerException {
        try {
            if(inputConsent==null )
                throw new InternalServerException(CustomConstants.ERROR_REQUEST_NOT_CORRECT);
            inputConsent.setConsentId(consentId);
            ConsentUtil.updateConsentStatusIfExpired(inputConsent);
            Consent consent=ConsentUtil.validateAndConvertUpdateConsentBean(inputConsent);

            boolean isConsentUpdated = DatabaseManager.getInstance().saveConsent(consent,CustomConstants.CONSENT_ACTION_UPDATE);
            if(isConsentUpdated)
                return new ResponseEntity( HttpStatus.OK );
            else
                throw new InternalServerException(CustomConstants.ERROR_CONSENT_NOT_UPDATED);


        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        } catch (IOException |ParseException ex) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }

    }

    @DeleteMapping("/{consentId}")
    public ResponseEntity deleteConsent( @PathVariable("consentId") String consentId) throws InternalServerException {
        try {
            Consent consent = ConsentUtil.getConsentFromStore(consentId);
            ConsentUtil.updateConsentStatusIfExpired(consent);
            if(consent.getConsentType().equalsIgnoreCase(CustomConstants.CONSENT_TYPE_PISP))
                throw new BadRequestException(CustomConstants.ERROR_CONSENT_NOT_FOUND
                );
            if(consent.getStatus().equalsIgnoreCase(CustomConstants.STATUS_REVOKED))
                throw new BadRequestException(CustomConstants.ERROR_CONSENT_NOT_FOUND);

            consent.setStatus(CustomConstants.STATUS_REVOKED);
            boolean isConsentDeleted = DatabaseManager.getInstance().saveConsent(consent,CustomConstants.CONSENT_ACTION_UPDATE);
            if(isConsentDeleted)
                return new ResponseEntity( HttpStatus.NO_CONTENT );
            else
                throw new InternalServerException(CustomConstants.ERROR_CONSENT_NOT_DELETED);


        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        } catch (IOException | ParseException ex) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }

    }


    @PostMapping(value="/{consentId}/authorization",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity authorizeConsent(@RequestBody AuthorizeConsentRequest authorizeConsentRequest, @PathVariable("consentId") String consentId) throws InternalServerException {
        boolean isConsentUpdated = false;
        boolean isAccountCreated = false;
        try {
            if (authorizeConsentRequest.getAccounts() == null || authorizeConsentRequest.getAccounts().size() == 0) {
                throw new InternalServerException(CustomConstants.ERROR_CONSENT_ACCOUNTS_NOT_FOUND);
            }
            Consent foundConsent = ConsentUtil.getConsentFromStore(consentId);
            ConsentUtil.updateConsentStatusIfExpired(foundConsent);

            ConsentUtil.validateConsentForAuthorization(foundConsent);
            if (foundConsent.getStatus().equalsIgnoreCase(CustomConstants.STATUS_AUTHORIZED)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            foundConsent.setStatus(CustomConstants.STATUS_AUTHORIZED);
            foundConsent.setStatusUpdateDateTime(ConsentUtil.getISO8601StringForDate(new Date()));
            //save accounts
            for (UserAccount userAccount : authorizeConsentRequest.getAccounts() ) {
                userAccount.setConsent(foundConsent);
                userAccount.setDatCreation(new Date());
                isAccountCreated = DatabaseManager.getInstance().saveUserAccounts(userAccount);
                if (!isAccountCreated)
                    break;
            }
            if (isAccountCreated) {
                isConsentUpdated = DatabaseManager.getInstance().saveConsent(foundConsent, "update");
            }
            //update consent status

            if (isAccountCreated && isConsentUpdated)
                return new ResponseEntity(HttpStatus.OK);
            else
                throw new InternalServerException(CustomConstants.ERROR_CONSENT_NOT_AUTHORIZED);

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        } catch (IOException | ParseException ex) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }
    }

    @GetMapping("/{consentId}/validation")
    public ResponseEntity validateConsent(@PathVariable("consentId") String consentId) throws InternalServerException {
        try {
            Consent foundConsent = ConsentUtil.getConsentFromStore(consentId);
            ConsentUtil.updateConsentStatusIfExpired(foundConsent);
            ConsentUtil.validateConsent(foundConsent);

            return new ResponseEntity( HttpStatus.OK );

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        } catch (IOException | ParseException ex) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }
    }

    @GetMapping("/{consentId}/account-validation/{accountId}")
    public ResponseEntity validateConsentForAccount(@PathVariable("consentId") String consentId, @PathVariable("accountId") String accountId) throws InternalServerException {
        try {
            Consent foundConsent = ConsentUtil.getConsentFromStore(consentId);
            ConsentUtil.updateConsentStatusIfExpired(foundConsent);
            if (accountId == null || accountId.isEmpty())
                throw new BadRequestException(CustomConstants.ERROR_CONSENT_ACCOUNT_NOT_VALIDATED);
            if(foundConsent.getConsentType().equalsIgnoreCase(CustomConstants.CONSENT_TYPE_PISP))
                throw new BadRequestException(CustomConstants.ERROR_CONSENT_NOT_FOUND
                );
            ConsentUtil.validateConsent(foundConsent);
            ConsentUtil.validateConsentForAccount(foundConsent.getConsentId(),accountId);
            return new ResponseEntity( HttpStatus.OK );

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        }catch (IOException | ParseException ex) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }
    }

    @GetMapping("/{consentId}/permission-validation/{accountendpoint}")
    public ResponseEntity validateConsentForPermission(@PathVariable("consentId") String consentId,@PathVariable("accountendpoint") String accountendpoint ) throws InternalServerException {
        try {
            Consent foundConsent = ConsentUtil.getConsentFromStore(consentId);
            ConsentUtil.updateConsentStatusIfExpired(foundConsent);
            if(foundConsent.getConsentType().equalsIgnoreCase(CustomConstants.CONSENT_TYPE_PISP))
                throw new BadRequestException(CustomConstants.ERROR_CONSENT_NOT_FOUND);
            ConsentUtil.validateConsent(foundConsent);
            ConsentUtil.validateConsentForPermission(foundConsent,accountendpoint);
            return new ResponseEntity( HttpStatus.OK );

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        }catch (IOException | ParseException ex) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }
    }

    @PostMapping("/{consentId}/domesticpayment-validation")
    public ResponseEntity validateConsentForDomesticPayment(@PathVariable("consentId") String consentId, @RequestBody Consent domesticPaymentRequest) throws InternalServerException, IOException {
        try {
            Consent foundConsent = ConsentUtil.getConsentFromStore(consentId);
            ConsentUtil.updateConsentStatusIfExpired(foundConsent);
            ConsentUtil.validateConsent(foundConsent);
            if(foundConsent.getConsentType().equalsIgnoreCase(CustomConstants.CONSENT_TYPE_AISP))
                throw new BadRequestException(CustomConstants.ERROR_CONSENT_NOT_FOUND
                );
            ConsentUtil.validateConsentForDomesticPayment(foundConsent,domesticPaymentRequest);
            return new ResponseEntity( HttpStatus.OK );

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        }catch (IOException | ParseException ex) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }
    }


    @GetMapping("/{ConsentId}/funds-confirmation")
    public ResponseEntity<?> performFundsConfirmation(@PathVariable("ConsentId") String consentId) throws InternalServerException {
        try {
            Consent foundConsent = ConsentUtil.getConsentFromStore(consentId);
            ConsentUtil.updateConsentStatusIfExpired(foundConsent);

            if(foundConsent.getConsentType().equalsIgnoreCase(CustomConstants.CONSENT_TYPE_AISP))
                throw new BadRequestException(CustomConstants.ERROR_CONSENT_NOT_FOUND);
            ConsentUtil.validateConsent(foundConsent);


            FundsConfirmationResponse fundsConfirmationResponse=new FundsConfirmationResponse();
            fundsConfirmationResponse.setFundsAvailable(ConsentUtil.checkPISPFundsConfirmation(foundConsent));
            fundsConfirmationResponse.setFundsAvailableDateTime(ConsentUtil.getISO8601StringForDate(new Date()));
            return new ResponseEntity<>(fundsConfirmationResponse,HttpStatus.OK);

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        }
        catch (IOException  | ParseException e) {
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);
        }
    }
}

