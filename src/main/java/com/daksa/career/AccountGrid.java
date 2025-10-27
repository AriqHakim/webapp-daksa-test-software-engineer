package com.daksa.career;

import com.vaadin.flow.component.grid.Grid;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Muhammad Rizki
 *         This class represents Account Table
 */
public class AccountGrid extends Grid<Account> {
    private List<Account> accounts; // reflect list of account in the AccountRepository

    private final Supplier<List<Account>> accountSupplier;

    /**
     * Create Account Table here
     */
    public AccountGrid(Supplier<List<Account>> accountSupplier) {
        this.accountSupplier = accountSupplier;
        accounts = accountSupplier.get();
        addColumn(Account::getId).setHeader("ID").setAutoWidth(true);
        addColumn(Account::getName).setHeader("Name").setAutoWidth(true);
        addColumn(Account::getAddress).setHeader("Address").setAutoWidth(true);
        addColumn(Account::getBirthDate).setHeader("Birth Date").setAutoWidth(true);
        addColumn(account -> account.isAllowNegativeBalance() ? "Yes" : "No")
                .setHeader("Allow Negative Balance").setAutoWidth(true);

        setItems(accounts);
    }

    /**
     * Create refresh table here
     */
    public void refreshAll() {
        accounts = accountSupplier.get();
        setItems(accounts);
    }
}
