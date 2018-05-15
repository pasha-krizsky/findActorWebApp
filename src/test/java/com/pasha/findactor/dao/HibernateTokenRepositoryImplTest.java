package com.pasha.findactor.dao;

import com.pasha.findactor.configuration.HibernateConfiguration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfiguration.class)
@WebAppConfiguration
@Transactional
public class HibernateTokenRepositoryImplTest {

    @Autowired
    @Qualifier("tokenRepositoryDao")
    private PersistentTokenRepository hibernateTokenRepository;

    @Autowired
    private WorksheetDao worksheetDao;

    @Test
    public void testCreateAndGetToken() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {
            PersistentRememberMeToken token = new PersistentRememberMeToken("username", "series", "someFakeValue", new Date());
            hibernateTokenRepository.createNewToken(token);
            PersistentRememberMeToken actualToken = hibernateTokenRepository.getTokenForSeries("series");
            Assert.assertEquals(token.getUsername(), actualToken.getUsername());
            Assert.assertEquals(token.getSeries(), actualToken.getSeries());
            Assert.assertEquals(token.getTokenValue(), actualToken.getTokenValue());
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testCreateAndUpdateToken() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {
            PersistentRememberMeToken token = new PersistentRememberMeToken("username", "series", "someFakeValue", new Date());
            hibernateTokenRepository.createNewToken(token);
            hibernateTokenRepository.updateToken("series", "newValue", new Date());
            PersistentRememberMeToken actualToken = hibernateTokenRepository.getTokenForSeries("series");
            Assert.assertEquals(token.getUsername(), actualToken.getUsername());
            Assert.assertEquals(token.getSeries(), actualToken.getSeries());
            Assert.assertEquals("newValue", actualToken.getTokenValue());
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testRemoveToken() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {
            PersistentRememberMeToken token = new PersistentRememberMeToken("username", "series", "someFakeValue", new Date());
            hibernateTokenRepository.createNewToken(token);
            hibernateTokenRepository.removeUserTokens("username");
            PersistentRememberMeToken actualToken = hibernateTokenRepository.getTokenForSeries("series");
            Assert.assertNull(actualToken);
        } finally {
            connection.rollback();
            connection.close();
        }
    }
}