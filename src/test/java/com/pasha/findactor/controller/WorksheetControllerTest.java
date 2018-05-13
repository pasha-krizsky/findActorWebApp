package com.pasha.findactor.controller;

import com.pasha.findactor.model.User;
import com.pasha.findactor.model.Worksheet;
import com.pasha.findactor.model.constants.WorksheetStatus;
import com.pasha.findactor.service.UserService;
import com.pasha.findactor.service.WorksheetService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.View;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Matchers.anyInt;
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

public class WorksheetControllerTest {

    @InjectMocks
    @Spy
    private WorksheetController controller;

    @Mock
    private View mockView;

    @Mock
    private WorksheetService worksheetService;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).setSingleView(mockView).build();
        doReturn("").when(controller).getPrincipal();
    }

    @Test
    public void testFindWorksheetForUserGet() throws Exception {
        final String WORKSHEETS_USER_URL = "/worksheetsUser";

        when(userService.findBySSO(anyString())).thenReturn(new User());

        Worksheet worksheet = new Worksheet();
        worksheet.setStatus("R");
        worksheet.setSubmissionDate(new Date());
        when(worksheetService.findByUserId(anyInt())).thenReturn(worksheet);

        String expectedViewName = "list_worksheets_user";
        mockMvc.perform(get(WORKSHEETS_USER_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testFindWorksheetForUserNoWorksheetsGet() throws Exception {
        final String WORKSHEETS_USER_URL = "/worksheetsUser";

        when(userService.findBySSO(anyString())).thenReturn(new User());
        String expectedViewName = "redirect:/submitWorksheet";
        mockMvc.perform(get(WORKSHEETS_USER_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testListWorksheetsForAgentGet() throws Exception {
        final String WORKSHEETS_AGENT_URL = "/worksheetsAgent";

        when(worksheetService.findWorksheetsReviewed()).thenReturn(Collections.emptyList());
        String expectedViewName = "list_worksheets_agent";
        mockMvc.perform(get(WORKSHEETS_AGENT_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testListWorksheetsForDirectorGet() throws Exception {
        final String WORKSHEETS_DIRECTOR_URL = "/worksheetsDirector";

        when(worksheetService.findWorksheetsCasting()).thenReturn(Collections.emptyList());
        String expectedViewName = "list_worksheets_director";
        mockMvc.perform(get(WORKSHEETS_DIRECTOR_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testSubmitWorksheetGet() throws Exception {
        final String SUBMIT_URL = "/submitWorksheet";
        final String WORKSHEET_ATTR = "worksheet";

        User user = new User();
        user.setId(1);
        when(userService.findBySSO(anyString())).thenReturn(user);
        when(worksheetService.findByUserId(user.getId())).thenReturn(null);

        String expectedViewName = "submit_new_worksheet_user";
        mockMvc.perform(get(SUBMIT_URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute(WORKSHEET_ATTR, new Worksheet()))
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testSubmitWorksheetPost() throws Exception {
        final String SUBMIT_URL = "/submitWorksheet";

        BindingResult bindingResult = mock(BindingResult.class);

        String expectedViewName = "worksheet_submitted_user";
        mockMvc.perform(post(SUBMIT_URL, new Worksheet(), bindingResult))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testEditWorksheetGet() throws Exception {
        final String EDIT_WORKSHEET = "/editWorksheet-1";
        final String WORKSHEET_ATTR = "worksheet";

        when(worksheetService.findById(1)).thenReturn(new Worksheet());
        String expectedViewName = "worksheet_edit";
        mockMvc.perform(get(EDIT_WORKSHEET))
                .andExpect(status().isOk())
                .andExpect(model().attribute(WORKSHEET_ATTR, new Worksheet()))
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testEditWorksheetPost() throws Exception {
        final String EDIT_WORKSHEET = "/editWorksheet-1";

        BindingResult bindingResult = mock(BindingResult.class);
        when(userService.findBySSO(anyString())).thenReturn(new User());

        String expectedViewName = "redirect:/worksheetsUser";
        mockMvc.perform(post(EDIT_WORKSHEET, new Worksheet(), bindingResult))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testViewWorksheetGet() throws Exception {
        final String EDIT_WORKSHEET = "/viewWorksheet-1";
        final String WORKSHEET_ATTR = "worksheet";

        when(worksheetService.findById(1)).thenReturn(new Worksheet());
        String expectedViewName = "worksheet_view";
        mockMvc.perform(get(EDIT_WORKSHEET))
                .andExpect(status().isOk())
                .andExpect(model().attribute(WORKSHEET_ATTR, new Worksheet()))
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void testDeclineWorksheetAgentGet() throws Exception {
        final String DECLINE_WORKSHEET_AGENT = "/declineWorksheetAgent-1";

        Worksheet worksheet = new Worksheet();
        when(worksheetService.findById(1)).thenReturn(worksheet);
        String expectedViewName = "redirect:/worksheetsAgent";
        String expectedStatus = WorksheetStatus.DECLINED.getStatus();
        mockMvc.perform(get(DECLINE_WORKSHEET_AGENT))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));

        Assert.assertEquals("Wrong status", expectedStatus, worksheet.getStatus());
    }

    @Test
    public void testCastingWorksheetAgentGet() throws Exception {
        final String CASTING_WORKSHEET = "/castingWorksheet-1";

        Worksheet worksheet = new Worksheet();
        when(worksheetService.findById(1)).thenReturn(worksheet);
        String expectedViewName = "redirect:/worksheetsAgent";
        String expectedStatus = WorksheetStatus.CASTING.getStatus();
        mockMvc.perform(get(CASTING_WORKSHEET))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));

        Assert.assertEquals("Wrong status", expectedStatus, worksheet.getStatus());
    }

    @Test
    public void testDeclineWorksheetDirectorGet() throws Exception {
        final String DECLINE_WORKSHEET_DIRECTOR = "/declineWorksheetDirector-1";

        Worksheet worksheet = new Worksheet();
        when(worksheetService.findById(1)).thenReturn(worksheet);

        String expectedViewName = "redirect:/worksheetsDirector";
        String expectedStatus = WorksheetStatus.DECLINED.getStatus();

        mockMvc.perform(get(DECLINE_WORKSHEET_DIRECTOR))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));

        Assert.assertEquals("Wrong status", expectedStatus, worksheet.getStatus());
    }

    @Test
    public void testCastingWorksheetDirectorGet() throws Exception {
        final String OFFER_WORKSHEET = "/offerWorksheet-1";

        Worksheet worksheet = new Worksheet();
        when(worksheetService.findById(1)).thenReturn(worksheet);
        String expectedViewName = "redirect:/worksheetsDirector";
        String expectedStatus = WorksheetStatus.OFFER.getStatus();
        mockMvc.perform(get(OFFER_WORKSHEET))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));

        Assert.assertEquals("Wrong status", expectedStatus, worksheet.getStatus());
    }
}