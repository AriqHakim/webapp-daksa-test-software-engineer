package com.daksa.career;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.daksa.career.parser.StringParser;

/**
 * @author Muhammad Rizki
 *         This class is used to perform logic of account domain in the apps
 */
@Dependent
public class AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
    @Inject
    private AccountRepository accountRepository;

    @Inject
    private StringParser stringParser;

    /**
     * This method is used to register the account
     *
     * @param account
     */
    public void register(Account account) {
        LOG.info("register");
        accountRepository.save(account);
    }

    public boolean isIdExisting(String id) {
        return accountRepository.isIdExisting(id);
    }

    /**
     * This method is used for parsing file batch registration
     *
     * @param batchRegisStream
     * @throws IOException
     */
    public void parseBatch(InputStream batchRegisStream) throws IOException {
        LOG.info("parseBatch");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(batchRegisStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Account account = null;

                // parse line to account
                try {
                    account = stringParser.parseDataToAccount(line);
                } catch (Exception e) {
                    LOG.warn("Skipping malformed line: {}", line, e);
                    continue;
                }

                // write to log if account is null
                if (account == null) {
                    LOG.warn("Parser returned null for line: {}", line);
                    continue;
                }

                // skip if id already exists
                if (accountRepository.isIdExisting(account.getId())) {
                    LOG.info("Skipping existing id: {}", account.getId());
                    continue;
                }

                try {
                    accountRepository.save(account);
                } catch (Exception e) {
                    LOG.error("Failed to save account from line: {}", line, e);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
