package com.daksa.career.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.daksa.career.Account;

public class StringParser {
    public Account parseDataToAccount(String lineData) {
        Account account = new Account();

        // linear index pointer
        int index = 0;

        // set ID
        account.setId(lineData.substring(index, index + 16).trim());
        index += 16;

        // set Name
        int nameLength = Integer.parseInt(lineData.substring(index, index + 2));
        index += 2;
        account.setName(lineData.substring(index, index + nameLength));
        index += nameLength;

        // set Address
        int addressLength = Integer.parseInt(lineData.substring(index, index + 2));
        index += 2;
        if (addressLength > 0) {
            account.setAddress(lineData.substring(index, index + addressLength));
            index += addressLength;
        }

        // set Birth Date
        String birthDateString = lineData.substring(index, index + 8);
        LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.BASIC_ISO_DATE);
        account.setBirthDate(birthDate);

        // --- Allow Negative Balance flag
        account.setAllowNegativeBalance(lineData.charAt(lineData.length() - 1) == 'Y');

        return account;
    }
}
