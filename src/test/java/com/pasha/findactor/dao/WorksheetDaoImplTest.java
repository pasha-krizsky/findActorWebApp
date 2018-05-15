package com.pasha.findactor.dao;

import com.pasha.findactor.configuration.HibernateConfiguration;
import com.pasha.findactor.model.User;
import com.pasha.findactor.model.Worksheet;
import com.pasha.findactor.model.constants.WorksheetStatus;
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfiguration.class)
@WebAppConfiguration
@Transactional
public class WorksheetDaoImplTest {

    private static final String USER_SSO_ID = "3333";

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private WorksheetDao worksheetDao;

    @Test
    public void testSaveAndFindWorksheetByUserId() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();

            userDao.save(user);
            User actualUser = userDao.findBySso(USER_SSO_ID);
            Integer userId = actualUser.getId();

            worksheet.setUser(actualUser);
            worksheetDao.saveWorksheet(worksheet);

            Worksheet actualWorksheet = worksheetDao.findByUserId(userId);

            Assert.assertEquals(worksheet, actualWorksheet);
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindAllWorksheets() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();

            userDao.save(user);
            User actualUser = userDao.findBySso(USER_SSO_ID);

            worksheet.setUser(actualUser);
            worksheetDao.saveWorksheet(worksheet);

            List<Worksheet> worksheets = worksheetDao.findAllWorksheets();

            Assert.assertTrue(worksheets.contains(worksheet));
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindCastingWorksheetsPositive() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();
            worksheet.setStatus(WorksheetStatus.CASTING.getStatus());

            userDao.save(user);
            User actualUser = userDao.findBySso(USER_SSO_ID);

            worksheet.setUser(actualUser);
            worksheetDao.saveWorksheet(worksheet);

            List<Worksheet> worksheets = worksheetDao.findWorksheetsCasting();

            Assert.assertTrue(worksheets.contains(worksheet));
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindCastingWorksheetsNegative() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();
            worksheet.setStatus(WorksheetStatus.REVIEWED.getStatus());

            userDao.save(user);
            User actualUser = userDao.findBySso(USER_SSO_ID);

            worksheet.setUser(actualUser);
            worksheetDao.saveWorksheet(worksheet);

            List<Worksheet> worksheets = worksheetDao.findWorksheetsCasting();

            Assert.assertFalse(worksheets.contains(worksheet));
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindReviewedWorksheetsPositive() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();
            worksheet.setStatus(WorksheetStatus.REVIEWED.getStatus());

            userDao.save(user);
            User actualUser = userDao.findBySso(USER_SSO_ID);

            worksheet.setUser(actualUser);
            worksheetDao.saveWorksheet(worksheet);

            List<Worksheet> worksheets = worksheetDao.findWorksheetsReviewed();

            Assert.assertTrue(worksheets.contains(worksheet));
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindReviewedWorksheetsNegative() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();
            worksheet.setStatus(WorksheetStatus.CASTING.getStatus());

            userDao.save(user);
            User actualUser = userDao.findBySso(USER_SSO_ID);

            worksheet.setUser(actualUser);
            worksheetDao.saveWorksheet(worksheet);

            List<Worksheet> worksheets = worksheetDao.findWorksheetsReviewed();

            Assert.assertFalse(worksheets.contains(worksheet));
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindOfferWorksheetsPositive() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();
            worksheet.setStatus(WorksheetStatus.OFFER.getStatus());

            userDao.save(user);
            User actualUser = userDao.findBySso(USER_SSO_ID);

            worksheet.setUser(actualUser);
            worksheetDao.saveWorksheet(worksheet);

            List<Worksheet> worksheets = worksheetDao.findWorksheetsOffer();

            Assert.assertTrue(worksheets.contains(worksheet));
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testFindOfferWorksheetsNegative() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();
            worksheet.setStatus(WorksheetStatus.CASTING.getStatus());

            userDao.save(user);
            User actualUser = userDao.findBySso(USER_SSO_ID);

            worksheet.setUser(actualUser);
            worksheetDao.saveWorksheet(worksheet);

            List<Worksheet> worksheets = worksheetDao.findWorksheetsOffer();

            Assert.assertFalse(worksheets.contains(worksheet));
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void testUpdateWorksheet() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions()
                .getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();
            userDao.save(user);
            User actualUser = userDao.findBySso(USER_SSO_ID);
            worksheet.setUser(actualUser);
            worksheetDao.saveWorksheet(worksheet);
            worksheet.setSex("bla-bla-bla");
            worksheetDao.updateWorksheet(worksheet);
            Worksheet actualWorksheet = worksheetDao.findByUserId(actualUser.getId());

            Assert.assertEquals("bla-bla-bla", actualWorksheet.getSex());
        } finally {
            connection.rollback();
            connection.close();
        }
    }


    private Worksheet prepareWorksheet() {
        Worksheet worksheet = new Worksheet();

        worksheet.setSubmissionDate(new Date());
        worksheet.setStatus(WorksheetStatus.REVIEWED.getStatus());
        worksheet.setAge(new Short("15"));
        worksheet.setExperience("ex");
        worksheet.setReason("res");
        worksheet.setEyeColor("blue");
        worksheet.setHairColor("black");
        worksheet.setHeight(100.0F);
        worksheet.setWeight(100.0F);
        worksheet.setNationality("a");
        worksheet.setSkinColor("white");
        worksheet.setSex("m");

        return worksheet;
    }

    private User prepareUser() {
        User user = new User();

        user.setEmail("email");
        user.setSsoId(USER_SSO_ID);
        user.setUserProfiles(new HashSet<>());
        user.setPassword("a");
        user.setFirstName("aa");
        user.setLastName("aaa");

        return user;
    }

}