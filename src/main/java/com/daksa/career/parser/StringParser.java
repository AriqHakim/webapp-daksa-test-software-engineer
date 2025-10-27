package com.daksa.career.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.daksa.career.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringParser {
    private static final Logger LOG = LoggerFactory.getLogger(StringParser.class);

    public Account parseDataToAccount(String lineData) {
        // check if string is empty
        if (lineData == null || lineData.isEmpty()) {
            LOG.error("Cannot parse null or empty line");
            throw new IllegalArgumentException("Line data cannot be null or empty");
        }

        Account account = new Account();
        try {
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

            LOG.info("Successfully parsed account with ID: {}", account.getId());
            return account;

        } catch (Exception e) {
            LOG.error("Unexpected error parsing line: {}\n", lineData, e);
            throw new IllegalArgumentException("Failed to parse account data", e);
        }
    }
}
