package abc.docker.services.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

@DatabaseTable(tableName = "CUSTOMER")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {
    public Customer() {
    }

    @DatabaseField(columnName="CUSTOMER_ID" ,id = true)
    private String customerId;

    @DatabaseField(columnName = "FirstName", canBeNull = false)
    private String firstName;

    @DatabaseField( columnName = "LastName", canBeNull = false)
    private String lastName;

    @DatabaseField(columnName = "DOB")
    private String dob;

    @DatabaseField(columnName = "Email")
    private String email;

    @DatabaseField(columnName = "Phone")
    private String phone;

    @DatabaseField( columnName = "City")
    private String city;

    @DatabaseField( columnName = "REGISTRATION_ID")
    private String registrationId;

    @DatabaseField( columnName = "is_active")
    private String isActive;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
