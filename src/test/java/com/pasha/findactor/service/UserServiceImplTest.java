package com.pasha.findactor.service;

import com.pasha.findactor.dao.UserDao;
import com.pasha.findactor.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao dao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testFindBySso() {
        userService.findBySSO("sso");
        verify(dao, times(1)).findBySso("sso");
    }

    @Test
    public void testDeleteBySso() {
        userService.deleteUserBySSO("sso");
        verify(dao, times(1)).deleteBySso("sso");
    }

    @Test
    public void testFindAll() {
        userService.findAllUsers();
        verify(dao, times(1)).findAll();
    }

    @Test
    public void testUpdateNotFound() {
        User user = new User();
        user.setId(1);
        userService.updateUser(user);
        verify(dao, times(1)).findById(user.getId());
    }

    @Test
    public void testUpdateFound() {
        User userUpdated = new User();
        userUpdated.setSsoId("user");
        userUpdated.setFirstName("first");
        userUpdated.setLastName("last");
        userUpdated.setEmail("mail");
        userUpdated.setId(1);
        userUpdated.setPassword("pass");

        User userFromDB = new User();

        when(dao.findById(anyInt())).thenReturn(userFromDB);
        when(passwordEncoder.encode(anyString())).thenReturn("password");

        userService.updateUser(userUpdated);

        Assert.assertEquals(userUpdated.getSsoId(), userFromDB.getSsoId());
        Assert.assertEquals(userUpdated.getFirstName(), userFromDB.getFirstName());
        Assert.assertEquals(userUpdated.getLastName(), userFromDB.getLastName());
        Assert.assertEquals("password", userFromDB.getPassword());
    }

    @Test
    public void testSaveUser() {
        User userToSave = new User();
        userToSave.setPassword("pass");
        userService.saveUser(userToSave);
        verify(passwordEncoder).encode("pass");
        verify(dao, times(1)).save(userToSave);
    }
}