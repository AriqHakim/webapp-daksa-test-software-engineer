package com.daksa.career;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Muhammad Rizki
 *         This class is used to perform store and get data of registered
 *         accounts
 */
@Dependent
public class AccountRepository {

    // this list represents memory storage instead of database
    private static final List<Account> ACCOUNTS = Collections.synchronizedList(new ArrayList<>());

    /**
     * Save an account to the accounts, this accounts saved on memory.
     * When the application stopped, accounts will be refreshed
     *
     * @param account
     */
    public void save(Account account) {
        ACCOUNTS.add(account);
    }

    /**
     * This method is used to get all the submitted accounts
     *
     * @return List of Account
     */
    public List<Account> getAccounts() {
        return ACCOUNTS;
    }

    public boolean isIdExisting(String id) {
        return ACCOUNTS.stream().anyMatch(acc -> acc.getId().equals(id));
    }
}
