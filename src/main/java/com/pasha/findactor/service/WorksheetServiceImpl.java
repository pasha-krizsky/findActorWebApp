package com.pasha.findactor.service;

import com.pasha.findactor.dao.UserDao;
import com.pasha.findactor.dao.WorksheetDao;
import com.pasha.findactor.model.User;
import com.pasha.findactor.model.Worksheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Service
@Transactional
public class WorksheetServiceImpl implements WorksheetService {

    @Autowired
    private WorksheetDao worksheetDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Worksheet> findAllWorksheets() {
        return worksheetDao.findAllWorksheets();
    }

    @Override
    public List<Worksheet> findWorksheetsReviewed() {
        return worksheetDao.findWorksheetsReviewed();
    }

    @Override
    public List<Worksheet> findWorksheetsCasting() {
        return worksheetDao.findWorksheetsCasting();
    }

    @Override
    public List<Worksheet> findWorksheetsOffer() {
        return worksheetDao.findWorksheetsOffer();
    }

    @Override
    public void saveWorksheet(Worksheet worksheet) {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        worksheet.setSubmissionDate(date);
        worksheetDao.saveWorksheet(worksheet);
    }

    @Override
    public void saveWorksheet(Worksheet worksheet, String ssoId) {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        worksheet.setSubmissionDate(date);
        User user = userDao.findBySso(ssoId);
        worksheet.setUser(user);
        worksheetDao.saveWorksheet(worksheet);
    }

    @Override
    public Worksheet findById(Integer worksheetId) {
        return worksheetDao.findById(worksheetId);
    }

    @Override
    public Worksheet findByUserId(Integer userId) {
        return worksheetDao.findByUserId(userId);
    }

    @Override
    public void updateWorksheet(Worksheet worksheet) {
        worksheetDao.updateWorksheet(worksheet);
    }

    @Override
    public void deleteById(Integer id) {
        worksheetDao.deleteById(id);
    }
}
