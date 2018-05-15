package com.pasha.findactor.service;

import com.pasha.findactor.dao.UserProfileDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceImplTest {

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Mock
    private UserProfileDaoImpl userProfileDao;

    @Test
    public void testFindAll() {
        userProfileService.findAll();
        verify(userProfileDao, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        userProfileService.findById(1);
        verify(userProfileDao, times(1)).findById(1);
    }

    @Test
    public void testFindByType() {
        userProfileService.findByType("type");
        verify(userProfileDao, times(1)).findByType("type");
    }
}