package model;

import java.util.Hashtable;

public class MockAccountManager implements AccountManager{
    private Hashtable accounts = new Hashtable();

    public void addAccount(String userId, Account account){
        this.accounts.put(userId, account);
    }

    @Override
    public Account findAccountForUser(String userId) {
        return (Account) this.accounts.get(userId);

    }

    @Override
    public void updateAccount(Account account) {

    }
}
