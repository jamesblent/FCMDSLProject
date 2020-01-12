package abc.docker.services.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryAddress {
    @JsonProperty("AddressLine")
    private List<String> addressLine;
    @JsonProperty("StreetName")
    private String streetName;
    @JsonProperty("BuildingNumber")
    private String buildingNumber;
    @JsonProperty("PostCode")
    private String postCode;
    @JsonProperty("TownName")
    private String townName;
    @JsonProperty("CountySubDivision")
    private  List<String>  countySubDivision;
    @JsonProperty("Country")
    private String country;

    public List<String> getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(List<String> addressLine) {
        this.addressLine = addressLine;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public List<String> getCountySubDivision() {
        return countySubDivision;
    }

    public void setCountySubDivision(List<String> countySubDivision) {
        this.countySubDivision = countySubDivision;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
