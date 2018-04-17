package com.pasha.findactor.controller;

import com.pasha.findactor.common.Urls;
import com.pasha.findactor.common.Views;
import com.pasha.findactor.model.User;
import com.pasha.findactor.model.Worksheet;
import com.pasha.findactor.model.constants.WorksheetStatus;
import com.pasha.findactor.service.UserService;
import com.pasha.findactor.service.WorksheetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This controller is responsible to handle requests related to
 * worksheets (finding worksheets, adding the new once, etc.)
 * <p>
 * This controller is using {@link UserService} to work with worksheets
 * because of one to one relationship between {@link User} and {@link Worksheet} entities.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Slf4j
@Controller
public class WorksheetController extends AbstractController {

    private static final String LOGGED_IN_USER_ATTR = "loggedinuser";
    private static final String WORKSHEET_ATTR = "worksheet";
    private static final String WORKSHEETS_ATTR = "worksheets";

    @Autowired
    private WorksheetService worksheetService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {Urls.WORKSHEETS_USER_URL}, method = RequestMethod.GET)
    public String findWorksheetForUser(Model model) {

        User user = userService.findBySSO(getPrincipal());
        Worksheet worksheet = worksheetService.findByUserId(user.getId());
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());

        if (worksheet != null) {
            changeWorksheetToView(worksheet);
            model.addAttribute(WORKSHEET_ATTR, worksheet);
        } else {
            return Views.REDIRECT_SUBMIT_WORKSHEET;
        }

        return Views.LIST_WORKSHEETS_USER;
    }

    @RequestMapping(value = {Urls.WORKSHEETS_AGENT_URL}, method = RequestMethod.GET)
    public String listWorksheetsForAgent(Model model) {

        List<Worksheet> worksheetsReviewed = worksheetService.findWorksheetsReviewed();

        model.addAttribute(WORKSHEETS_ATTR, worksheetsReviewed);
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());

        return Views.LIST_WORKSHEETS_AGENT;
    }

    @RequestMapping(value = {Urls.WORKSHEETS_DIRECTOR_URL}, method = RequestMethod.GET)
    public String listWorksheetsForDirector(Model model) {

        List<Worksheet> worksheetsCasting = worksheetService.findWorksheetsCasting();

        model.addAttribute(WORKSHEETS_ATTR, worksheetsCasting);
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());

        return Views.LIST_WORKSHEETS_DIRECTOR;
    }

    @RequestMapping(value = {Urls.SUBMIT_NEW_WORKSHEET_URL}, method = RequestMethod.GET)
    public String submitWorksheet(Model model) {

        User user = userService.findBySSO(getPrincipal());
        if (worksheetService.findByUserId(user.getId()) != null) {
            return Views.REDIRECT_WORKSHEETS_USER;
        }

        Worksheet worksheet = new Worksheet();

        model.addAttribute(WORKSHEET_ATTR, worksheet);
        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());

        return Views.SUBMIT_NEW_WORKSHEET;
    }

    @RequestMapping(value = {Urls.SUBMIT_NEW_WORKSHEET_URL}, method = RequestMethod.POST)
    public String submitWorksheet(@Valid Worksheet worksheet, BindingResult result) {
        if (result.hasErrors()) {
            return Views.SUBMIT_NEW_WORKSHEET;
        }
        worksheetService.saveWorksheet(worksheet, getPrincipal());
        return Views.WORKSHEET_SUBMITTED;
    }

    @RequestMapping(value = {Urls.EDIT_WORKSHEET}, method = RequestMethod.GET)
    public String editWorksheet(@PathVariable String worksheetId, ModelMap model) {
        Worksheet worksheet = worksheetService.findById(Integer.valueOf(worksheetId));

        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());
        model.addAttribute(WORKSHEET_ATTR, worksheet);

        return Views.WORKSHEET_EDIT;
    }

    @RequestMapping(value = {Urls.EDIT_WORKSHEET}, method = RequestMethod.POST)
    public String editWorksheet(@Valid Worksheet worksheet, BindingResult result) {

        if (result.hasErrors()) {
            return Views.REDIRECT_WORKSHEETS_USER;
        }

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        worksheet.setSubmissionDate(date);
        worksheet.setUser(userService.findBySSO(getPrincipal()));
        worksheet.setStatus(WorksheetStatus.REVIEWED.getStatus());

        worksheetService.updateWorksheet(worksheet);

        return Views.REDIRECT_WORKSHEETS_USER;
    }

    @RequestMapping(value = {Urls.VIEW_WORKSHEET}, method = RequestMethod.GET)
    public String viewWorksheet(@PathVariable String worksheetId, ModelMap model) {
        Worksheet worksheet = worksheetService.findById(Integer.valueOf(worksheetId));

        model.addAttribute(LOGGED_IN_USER_ATTR, getPrincipal());
        model.addAttribute(WORKSHEET_ATTR, worksheet);

        return Views.WORKSHEET;
    }

    @RequestMapping(value = {Urls.DECLINE_WORKSHEET_AGENT}, method = RequestMethod.GET)
    public String declineWorksheetAgent(@PathVariable String worksheetId) {

        Worksheet worksheet = worksheetService.findById(Integer.valueOf(worksheetId));
        worksheet.setStatus(WorksheetStatus.DECLINED.getStatus());
        worksheetService.updateWorksheet(worksheet);

        return Views.REDIRECT_WORKSHEETS_AGENT;
    }

    @RequestMapping(value = {Urls.CASTING_WORKSHEET}, method = RequestMethod.GET)
    public String castingWorksheetAgent(@PathVariable String worksheetId) {

        Worksheet worksheet = worksheetService.findById(Integer.valueOf(worksheetId));
        worksheet.setStatus(WorksheetStatus.CASTING.getStatus());
        worksheetService.updateWorksheet(worksheet);

        return Views.REDIRECT_WORKSHEETS_AGENT;
    }

    @RequestMapping(value = {Urls.DECLINE_WORKSHEET_DIRECTOR}, method = RequestMethod.GET)
    public String declineWorksheetDirector(@PathVariable String worksheetId) {

        Worksheet worksheet = worksheetService.findById(Integer.valueOf(worksheetId));
        worksheet.setStatus(WorksheetStatus.DECLINED.getStatus());

        worksheetService.updateWorksheet(worksheet);

        return Views.REDIRECT_WORKSHEETS_DIRECTOR;
    }

    @RequestMapping(value = {Urls.OFFER_WORKSHEET}, method = RequestMethod.GET)
    public String castingWorksheetDirector(@PathVariable String worksheetId) {

        Worksheet worksheet = worksheetService.findById(Integer.valueOf(worksheetId));
        worksheet.setStatus(WorksheetStatus.OFFER.getStatus());

        worksheetService.updateWorksheet(worksheet);

        return Views.REDIRECT_WORKSHEETS_DIRECTOR;
    }

    private void changeWorksheetToView(Worksheet worksheet) {
        changeWorksheetStatus(worksheet);
        changeWorksheetDateRepresentation(worksheet);
    }

    private void changeWorksheetStatus(Worksheet worksheet) {
        switch (worksheet.getStatus()) {
            case "C":
                worksheet.setStatus("Casting");
                break;
            case "D":
                worksheet.setStatus("Declined");
                break;
            case "O":
                worksheet.setStatus("Offer!");
                break;
            case "R":
                worksheet.setStatus("Reviewed");
                break;
            default:
                worksheet.setStatus("Unknown");
                break;
        }
    }

    private void changeWorksheetDateRepresentation(Worksheet worksheet) {
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(datePattern, Locale.getDefault());
        try {
            worksheet.setSubmissionDate(format.parse(worksheet.getSubmissionDate().toString()));
        } catch (ParseException e) {
            log.error("Cannot parse date", e);
        }
    }
}