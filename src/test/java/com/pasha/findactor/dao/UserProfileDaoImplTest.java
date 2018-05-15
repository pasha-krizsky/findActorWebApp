package com.pasha.findactor.dao;

import com.pasha.findactor.configuration.HibernateConfiguration;
import com.pasha.findactor.model.UserProfile;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfiguration.class)
@WebAppConfiguration
@Transactional
public class UserProfileDaoImplTest {

    @Autowired
    UserProfileDaoImpl userProfileDao;

    @Before
    public void setUp() throws Exception {
        Connection connection = userProfileDao.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        Statement st = connection.createStatement();
        try {
            st.execute("INSERT INTO user_profile (type) VALUES ('USER')");
        } catch (SQLIntegrityConstraintViolationException ignored) {}
        try {
            st.execute("INSERT INTO user_profile (type) VALUES ('ADMIN')");
        } catch (SQLIntegrityConstraintViolationException ignored) {}
        try {
            st.execute("INSERT INTO user_profile (type) VALUES ('AGENT')");
        } catch (SQLIntegrityConstraintViolationException ignored) {}
        try {
            st.execute("INSERT INTO user_profile (type) VALUES ('DIRECTOR')");
        } catch (SQLIntegrityConstraintViolationException ignored) {}
    }

    @Test
    public void testFindById() throws Exception {

        Connection connection = userProfileDao.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {
            List<UserProfile> userProfiles = new ArrayList<>();

            userProfiles.add(userProfileDao.findById(1));
            userProfiles.add(userProfileDao.findById(2));
            userProfiles.add(userProfileDao.findById(3));
            userProfiles.add(userProfileDao.findById(4));

            commonCheck(userProfiles);

        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindAll() throws Exception {

        Connection connection = userProfileDao.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {
            List<UserProfile> userProfiles = userProfileDao.findAll();

            commonCheck(userProfiles);

        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindByType() throws Exception {

        Connection connection = userProfileDao.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {
            UserProfile userProfile = userProfileDao.findByType("USER");
            Assert.assertTrue(userProfile.getType().equals("USER"));
            userProfile = userProfileDao.findByType("ADMIN");
            Assert.assertTrue(userProfile.getType().equals("ADMIN"));
            userProfile = userProfileDao.findByType("AGENT");
            Assert.assertTrue(userProfile.getType().equals("AGENT"));
            userProfile = userProfileDao.findByType("DIRECTOR");
            Assert.assertTrue(userProfile.getType().equals("DIRECTOR"));
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    private void commonCheck(List<UserProfile> userProfiles) {
        boolean typeUser = false;
        boolean typeAdmin = false;
        boolean typeAgent = false;
        boolean typeDirector = false;
        for (UserProfile userProfile: userProfiles) {
            if (userProfile.getType().equals("USER")) typeUser = true;
            if (userProfile.getType().equals("ADMIN")) typeAdmin = true;
            if (userProfile.getType().equals("AGENT")) typeAgent = true;
            if (userProfile.getType().equals("DIRECTOR")) typeDirector = true;
        }

        Assert.assertTrue("User profile with type USER not found", typeUser);
        Assert.assertTrue("User profile with type ADMIN not found", typeAdmin);
        Assert.assertTrue("User profile with type AGENT not found", typeAgent);
        Assert.assertTrue("User profile with type DIRECTOR not found",typeDirector);
    }
}