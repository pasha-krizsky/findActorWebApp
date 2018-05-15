package com.pasha.findactor.service;

import com.pasha.findactor.dao.UserDao;
import com.pasha.findactor.dao.WorksheetDao;
import com.pasha.findactor.model.Worksheet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WorksheetServiceImplTest {

    @InjectMocks
    private WorksheetServiceImpl worksheetService;

    @Mock
    private WorksheetDao worksheetDao;

    @Mock
    private UserDao userDao;

    @Test
    public void testFindAllWorksheets() {
        worksheetService.findAllWorksheets();
        verify(worksheetDao, times(1)).findAllWorksheets();
    }

    @Test
    public void testFindWorksheetsReviewed() {
        worksheetService.findWorksheetsReviewed();
        verify(worksheetDao, times(1)).findWorksheetsReviewed();
    }

    @Test
    public void testFindWorksheetsCasting() {
        worksheetService.findWorksheetsCasting();
        verify(worksheetDao, times(1)).findWorksheetsCasting();
    }

    @Test
    public void testFindWorksheetsOffer() {
        worksheetService.findWorksheetsOffer();
        verify(worksheetDao, times(1)).findWorksheetsOffer();
    }

    @Test
    public void testSaveWorksheet() {
        Worksheet worksheet = new Worksheet();
        worksheetService.saveWorksheet(worksheet);
        Assert.assertNotNull(worksheet.getSubmissionDate());
        verify(worksheetDao, times(1)).saveWorksheet(worksheet);
    }

    @Test
    public void testSaveWorksheetSso() {
        Worksheet worksheet = new Worksheet();
        worksheetService.saveWorksheet(worksheet, "sso");
        Assert.assertNotNull(worksheet.getSubmissionDate());
        verify(userDao, times(1)).findBySso("sso");
        verify(worksheetDao, times(1)).saveWorksheet(worksheet);
    }

    @Test
    public void testFindById() {
        worksheetService.findById(1);
        verify(worksheetDao, times(1)).findById(1);
    }

    @Test
    public void testFindByUserId() {
        worksheetService.findByUserId(1);
        verify(worksheetDao, times(1)).findByUserId(1);
    }

    @Test
    public void testUpdateWorksheet() {
        Worksheet worksheet = new Worksheet();
        worksheetService.updateWorksheet(worksheet);
        verify(worksheetDao, times(1)).updateWorksheet(worksheet);
    }
}