package abc.docker.services.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SCASupportData {
    @JsonProperty("RequestedSCAExemptionType")
    private String requestedSCAExemptionType;
    @JsonProperty("AppliedAuthenticationApproach")
    private String appliedAuthenticationApproach;
    @JsonProperty("ReferencePaymentOrderId")
    private String referencePaymentOrderId;


    public String getRequestedSCAExemptionType() {
        return requestedSCAExemptionType;
    }

    public void setRequestedSCAExemptionType(String requestedSCAExemptionType) {
        this.requestedSCAExemptionType = requestedSCAExemptionType;
    }

    public String getAppliedAuthenticationApproach() {
        return appliedAuthenticationApproach;
    }

    public void setAppliedAuthenticationApproach(String appliedAuthenticationApproach) {
        this.appliedAuthenticationApproach = appliedAuthenticationApproach;
    }

    public String getReferencePaymentOrderId() {
        return referencePaymentOrderId;
    }

    public void setReferencePaymentOrderId(String referencePaymentOrderId) {
        this.referencePaymentOrderId = referencePaymentOrderId;
    }
}
