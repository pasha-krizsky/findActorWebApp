package com.pasha.findactor.dao;

import com.pasha.findactor.model.PersistentLogin;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * This class is aimed to work with tokens to organize support of
 * RememberMe functionality.
 *
 * @author Pavel.Krizskiy
 * @see PersistentTokenRepository
 * @since 1.0.0
 */
@Slf4j
@Repository("tokenRepositoryDao")
@Transactional
public class HibernateTokenRepositoryImpl extends AbstractDao<String, PersistentLogin>
        implements PersistentTokenRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        log.info("Creating Token for user : {}", token.getUsername());
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        persist(persistentLogin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        log.info("Fetch token if any for seriesId : {}", seriesId);
        try {
            Criteria criteria = createEntityCriteria();
            criteria.add(Restrictions.eq("series", seriesId));
            PersistentLogin persistentLogin = (PersistentLogin) criteria.uniqueResult();

            return new PersistentRememberMeToken(
                    persistentLogin.getUsername(),
                    persistentLogin.getSeries(),
                    persistentLogin.getToken(),
                    persistentLogin.getLastUsed()
            );
        } catch (Exception e) {
            log.info("Token not found");
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUserTokens(String username) {
        log.info("Removing token if any for user : {}", username);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("username", username));
        PersistentLogin persistentLogin = (PersistentLogin) criteria.uniqueResult();
        if (persistentLogin != null) {
            log.info("RememberMe was selected");
            delete(persistentLogin);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        log.info("Updating token for seriesId : {}", seriesId);
        PersistentLogin persistentLogin = getByKey(seriesId);
        persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUsed(lastUsed);
        update(persistentLogin);
    }
}
