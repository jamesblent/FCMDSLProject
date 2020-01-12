package abc.docker.services.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Initiation {
    @JsonProperty("InstructionIdentification")
    private String instructionIdentification;
    @JsonProperty("EndToEndIdentification")
    private String endToEndIdentification;
    @JsonProperty("LocalInstrument")
    private String localInstrument;
    @JsonProperty("InstructedAmount")
    private InstructedAmount instructedAmount;
    @JsonProperty("CreditorAccount")
    private CreditorDebtorAccount creditorAccount;
    @JsonProperty("DebtorAccount")
    private CreditorDebtorAccount debtorAccount;
    @JsonProperty("CreditorPostalAddress")
    private CreditorPostalAddress creditorPostalAddress;
    @JsonProperty("RemittanceInformation")
    private RemittanceInformation remittanceInformation;

    public String getInstructionIdentification() {
        return instructionIdentification;
    }

    public void setInstructionIdentification(String instructionIdentification) {
        this.instructionIdentification = instructionIdentification;
    }

    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    public void setEndToEndIdentification(String endToEndIdentification) {
        this.endToEndIdentification = endToEndIdentification;
    }

    public String getLocalInstrument() {
        return localInstrument;
    }

    public void setLocalInstrument(String localInstrument) {
        this.localInstrument = localInstrument;
    }

    public InstructedAmount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(InstructedAmount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public CreditorDebtorAccount getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(CreditorDebtorAccount creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public CreditorDebtorAccount getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(CreditorDebtorAccount debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public CreditorPostalAddress getCreditorPostalAddress() {
        return creditorPostalAddress;
    }

    public void setCreditorPostalAddress(CreditorPostalAddress creditorPostalAddress) {
        this.creditorPostalAddress = creditorPostalAddress;
    }

    public RemittanceInformation getRemittanceInformation() {
        return remittanceInformation;
    }

    public void setRemittanceInformation(RemittanceInformation remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }
}



