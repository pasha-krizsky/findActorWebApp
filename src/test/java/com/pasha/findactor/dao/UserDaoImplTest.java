package com.pasha.findactor.dao;

import com.pasha.findactor.configuration.HibernateConfiguration;
import com.pasha.findactor.model.User;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfiguration.class)
@WebAppConfiguration
@Transactional
public class UserDaoImplTest {

    private static final String SSO_ID = "667";

    @Autowired
    UserDaoImpl userDao;

    @Test
    public void testSaveUser() throws Exception {

        Connection connection = userDao.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            userDao.save(user);

            User actualUser = userDao.findBySso(SSO_ID);

            System.out.println("Get user:");
            System.out.println(actualUser);
            Assert.assertEquals(user, actualUser);
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindAllUsers() throws Exception {

        Connection connection = userDao.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user1 = prepareUser();
            User user2 = prepareUser();
            user2.setSsoId("668");

            userDao.save(user1);
            userDao.save(user2);

            List<User> users = userDao.findAll();

            Assert.assertTrue(users.contains(user1));
            Assert.assertTrue(users.contains(user2));
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testDeleteBySso() throws Exception {

        Connection connection = userDao.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();

            userDao.save(user);
            User actualUser = userDao.findBySso(SSO_ID);
            Assert.assertNotNull(actualUser);
            userDao.deleteBySso(SSO_ID);
            User actualUserAfterDelete = userDao.findBySso(SSO_ID);
            Assert.assertNull(actualUserAfterDelete);
        } finally {
            connection.rollback();
            connection.close();
        }
    }


    private User prepareUser() {
        User user = new User();

        user.setEmail("str");
        user.setSsoId(SSO_ID);
        user.setUserProfiles(new HashSet<>());
        user.setPassword("aaa");
        user.setFirstName("aaa");
        user.setLastName("aaa");

        return user;
    }

}