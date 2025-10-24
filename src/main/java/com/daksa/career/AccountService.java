package com.daksa.career;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Muhammad Rizki
 *         This class is used to perform logic of account domain in the apps
 */
@Dependent
public class AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
    @Inject
    private AccountRepository accountRepository;

    /**
     * This method is used to register the account
     *
     * @param account
     */
    public void register(Account account) {
        LOG.info("register");
        // TODO : code here
    }

    /**
     * This method is used for parsing file batch registration
     *
     * @param batchRegisStream
     * @throws IOException
     */
    public void parseBatch(InputStream batchRegisStream) throws IOException {
        LOG.info("parseBatch");
        // TODO : code here
    }

}
