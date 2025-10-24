package com.daksa.career;

import java.time.LocalDate;

/**
 * @author Muhammad Rizki
 *         This class represent the model of account
 */
public class Account {
    private String id;
    private String name;
    private String address;
    private LocalDate birthDate;
    private boolean allowNegativeBalance;

    public Account() {
    }

    public Account(String id, String name, String address, LocalDate birthDate, boolean allowNegativeBalance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.birthDate = birthDate;
        this.allowNegativeBalance = allowNegativeBalance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isAllowNegativeBalance() {
        return allowNegativeBalance;
    }

    public void setAllowNegativeBalance(boolean allowNegativeBalance) {
        this.allowNegativeBalance = allowNegativeBalance;
    }
}
