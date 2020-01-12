package abc.docker.services.model.request;

import abc.docker.services.model.UserAccount;

import java.util.ArrayList;

public class AuthorizeConsentRequest {
    ArrayList<UserAccount> accounts;

    public ArrayList<UserAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<UserAccount> accounts) {
        this.accounts = accounts;
    }
}
