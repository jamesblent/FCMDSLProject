package abc.docker.services.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Risk {
    @JsonProperty("PaymentContextCode")
    private String paymentContextCode;
    @JsonProperty("MerchantCategoryCode")
    private String merchantCategoryCode;
    @JsonProperty("MerchantCustomerIdentification")
    private String merchantCustomerIdentification;
    @JsonProperty("DeliveryAddress")
    private DeliveryAddress deliveryAddress;

    public String getPaymentContextCode() {
        return paymentContextCode;
    }

    public void setPaymentContextCode(String paymentContextCode) {
        this.paymentContextCode = paymentContextCode;
    }

    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public String getMerchantCustomerIdentification() {
        return merchantCustomerIdentification;
    }

    public void setMerchantCustomerIdentification(String merchantCustomerIdentification) {
        this.merchantCustomerIdentification = merchantCustomerIdentification;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
