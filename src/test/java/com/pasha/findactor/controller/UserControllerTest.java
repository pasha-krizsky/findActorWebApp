package com.pasha.findactor.controller;

import com.pasha.findactor.model.User;
import com.pasha.findactor.service.UserProfileService;
import com.pasha.findactor.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.View;

import java.util.Collections;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class UserControllerTest {

    @InjectMocks
    @Spy
    private UserController controller;

    @Mock
    private View mockView;

    @Mock
    private UserService userService;

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private AuthenticationTrustResolver authenticationTrustResolver;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).setSingleView(mockView).build();
        doReturn(Collections.emptyList()).when(controller).initializeProfiles();
        doReturn("").when(controller).getPrincipal();
    }

    @Test
    public void testRegisterUserGet() throws Exception {
        final String REGISTER_USER_URL = "/registerUser";
        final String USER_ATTR = "user";
        final String EDIT_ATTR = "edit";

        String expectedViewName = "registration";
        mockMvc.perform(get(REGISTER_USER_URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute(USER_ATTR, new User()))
                .andExpect(model().attribute(EDIT_ATTR, false))
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testRegisterUserPost() throws Exception {
        final String REGISTER_USER_URL = "/registerUser";

        BindingResult bindingResult = mock(BindingResult.class);

        when(userProfileService.findAll()).thenReturn(Collections.emptyList());
        when(userService.isUserSSOUnique(anyInt(), anyString())).thenReturn(true);

        String expectedViewName = "registration_success";
        mockMvc.perform(post(REGISTER_USER_URL, new User(), bindingResult))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testListAllUsersGet() throws Exception {
        final String LIST_USERS_URL = "/listUsers";

        String expectedViewName = "users_list_admin";
        mockMvc.perform(get(LIST_USERS_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testEditUserGet() throws Exception {
        final String EDIT_USER_URL = "/editUser-ssoId";
        final String EDIT_ATTR = "edit";

        String expectedViewName = "registration";
        mockMvc.perform(get(EDIT_USER_URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute(EDIT_ATTR, true))
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testUpdateUserPost() throws Exception {
        final String EDIT_USER_URL = "/editUser-ssoId";

        BindingResult bindingResult = mock(BindingResult.class);

        when(userProfileService.findAll()).thenReturn(Collections.emptyList());
        when(userService.isUserSSOUnique(anyInt(), anyString())).thenReturn(true);

        String expectedViewName = "registration_success";
        mockMvc.perform(post(EDIT_USER_URL, new User(), bindingResult))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testDeleteUserGet() throws Exception {
        final String DELETE_USER_URL = "/deleteUser-ssoId";

        String expectedViewName = "redirect:/listUsers";
        mockMvc.perform(get(DELETE_USER_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testLogoutPageGet() throws Exception {
        final String LOGOUT_URL = "/logout";

        String expectedViewName = "redirect:/login?logout";
        mockMvc.perform(get(LOGOUT_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testLoginPageGet() throws Exception {
        final String LOGOUT_URL = "/login";

        when(authenticationTrustResolver.isAnonymous(anyObject())).thenReturn(true);

        String expectedViewName = "login";
        mockMvc.perform(get(LOGOUT_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }
}