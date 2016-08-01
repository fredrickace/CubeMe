package com.cube_me.cubeme.Accounts;

/**
 * Created by Fredrick on 14-Jul-16.
 */

public class Accounts {

    String accountName;
    String accountContactName;

    public Accounts() {
    }

    public Accounts(String accountName, String accounttContactName) {
        this.accountName = accountName;
        this.accountContactName = accounttContactName;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountContactName() {
        return accountContactName;
    }
}
