package model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DefaultAccountManager implements AccountManager
    private Log logger;
    private Configuration configuration;

    public DefaultAccountManager() {
        this(LogFactory.getLog(DefaultAccountManager.class));
    }

    public DefaultAccountManager(Log logger) {
        this.logger = logger;
    }

    @Override
    public Account findAccountForUser(String userId) {
        this.logger.debug("Getting account for user [" + userId + "]");
        this.configuration.getSQL("FIND_ACCOUNT_FOR_USER");
        return null;
    }

    @Override
    public void updateAccount(Account account) {

    }
}
