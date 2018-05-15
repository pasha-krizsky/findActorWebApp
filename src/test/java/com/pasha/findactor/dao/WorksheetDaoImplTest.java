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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfiguration.class)
@WebAppConfiguration
@Transactional
public class WorksheetDaoImplTest {

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private WorksheetDao worksheetDao;

    @Test
    public void testSaveAndFindWorksheetByUserId() throws Exception {

        Connection connection = worksheetDao.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
        connection.setAutoCommit(false);
        try {

            User user = prepareUser();
            Worksheet worksheet = prepareWorksheet();

            userDao.save(user);
            User actualUser = userDao.findBySso("3333");
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
        user.setSsoId("3333");
        user.setUserProfiles(new HashSet<>());
        user.setPassword("a");
        user.setFirstName("aa");
        user.setLastName("aaa");

        return user;
    }

}