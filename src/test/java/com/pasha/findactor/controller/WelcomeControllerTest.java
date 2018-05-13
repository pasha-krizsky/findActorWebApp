package com.pasha.findactor.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class WelcomeControllerTest {

    @InjectMocks
    @Spy
    private WelcomeController controller;

    @Mock
    private View mockView;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).setSingleView(mockView).build();
    }

    @Test
    public void testWelcomeRootUrlRequest() throws Exception {
        final String ROOT_URL = "/";

        String expectedViewName = "redirect:/listUsers";
        mockMvc.perform(get(ROOT_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testWelcomeWelcomeUrlRequest() throws Exception {
        final String ROOT_URL_WELCOME = "/welcome";

        String expectedViewName = "redirect:/listUsers";
        mockMvc.perform(get(ROOT_URL_WELCOME))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testAccessDeniedRequest() throws Exception {
        final String ACCESS_DENIED_URL = "/accessDenied";

        doReturn("").when(controller).getPrincipal();

        String expectedViewName = "access_denied";
        mockMvc.perform(get(ACCESS_DENIED_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }
}