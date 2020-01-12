package abc.docker.services.model.response;

import java.util.Date;

public class FundsConfirmationResponse {
    private boolean fundsAvailable;
    private String fundsAvailableDateTime;


    public boolean isFundsAvailable() {
        return fundsAvailable;
    }

    public void setFundsAvailable(boolean fundsAvailable) {
        this.fundsAvailable = fundsAvailable;
    }

    public String getFundsAvailableDateTime() {
        return fundsAvailableDateTime;
    }

    public void setFundsAvailableDateTime(String fundsAvailableDateTime) {
        this.fundsAvailableDateTime = fundsAvailableDateTime;
    }
}
