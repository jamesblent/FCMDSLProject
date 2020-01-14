package abc.docker.services.controller;

import abc.docker.services.database.DatabaseManager;
import abc.docker.services.exception.BadRequestException;
import abc.docker.services.exception.InternalServerException;
import abc.docker.services.model.Consent;
import abc.docker.services.model.Customer;
import abc.docker.services.model.UserAccount;
import abc.docker.services.model.request.AuthorizeConsentRequest;
import abc.docker.services.model.response.CustomerResponse;
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
import java.util.List;

/**
 * Created by Usman Shafi on 29-July-19.
 */

@RestController
@RequestMapping("v1/fcm_access")
public class ConsentStoreController {

    @PostMapping("/registerCustomer")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) throws InternalServerException {
        boolean isCustomerCreated = false;

        try {
            customer.setIsActive("true");
            customer.setCreationDateTime(ConsentUtil.getISO8601StringForDate(new Date()));

            isCustomerCreated = DatabaseManager.getInstance().saveCustomer(customer,CustomConstants.CONSENT_ACTION_CREATE);
            if(!isCustomerCreated) {
                throw new InternalServerException(CustomConstants.ERROR_CUSTOMER_NOT_SAVED);
            }

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        } catch (IOException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        }

        return new ResponseEntity<>((ConsentUtil.createSuccessResponse(null)),HttpStatus.CREATED);
    }

    @GetMapping("/getCustomerById/{customerId}")
    public ResponseEntity<SuccessResponse<Customer>> getCustomerById(@PathVariable("customerId") String customerId) throws InternalServerException {
        try {

            Customer foundCustomer = DatabaseManager.getInstance().getCustomerbyId(customerId);
            if (foundCustomer==null || !foundCustomer.getIsActive().equals("true"))
                throw new BadRequestException(CustomConstants.ERROR_CUSTOMER_NOT_FOUND);
            return ResponseEntity.ok(ConsentUtil.createSuccessResponse(foundCustomer));

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        }
    }

    @GetMapping("/getCustomerByEmail/{customerEmail}")
    public ResponseEntity<SuccessResponse<Customer>> getCustomerByEmail(@PathVariable("customerEmail") String customerEmail) throws InternalServerException {
        try {

            List<Customer> foundCustomer = DatabaseManager.getInstance().getCustomerbyEmail(customerEmail);
            if (!foundCustomer.equals("true"))
                throw new BadRequestException(CustomConstants.ERROR_CUSTOMER_NOT_FOUND);

            return ResponseEntity.ok(ConsentUtil.createSuccessResponse(foundCustomer));

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        }
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<SuccessResponse<Customer>> deleteCustomer(@PathVariable("customerId") String customerId) throws InternalServerException {
        boolean isCustomerDeleted = false;

        try {

            Customer foundCustomer = DatabaseManager.getInstance().getCustomerbyId(customerId);
            if (foundCustomer==null || !foundCustomer.getIsActive().equals("true"))
                throw new BadRequestException(CustomConstants.ERROR_CUSTOMER_NOT_FOUND);
            foundCustomer.setIsActive("false");
            isCustomerDeleted = DatabaseManager.getInstance().saveCustomer(foundCustomer,CustomConstants.CONSENT_ACTION_UPDATE);
            if(!isCustomerDeleted) {
                throw new InternalServerException(CustomConstants.ERROR_CUSTOMER_NOT_SAVED);
            }

            return ResponseEntity.ok(ConsentUtil.createSuccessResponse(foundCustomer));

        } catch (SQLException ex) {
            throw new InternalServerException(CustomConstants.ERROR_DB_CONNECTION);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerException(CustomConstants.ERROR_EXCEPTION);

        }
    }



}

