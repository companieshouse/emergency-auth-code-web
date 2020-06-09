package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerList;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@PreviousController(CompanyConfirmationPageController.class)
@NextController(OfficerConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/{requestId}/officers")
public class ListOfOfficersController extends BaseController {

    private static final String LIST_OF_OFFICERS = "eac/listOfOfficers";

    @Autowired
    private EmergencyAuthCodeService emergencyAuthCodeService;

    @Override
    protected String getTemplateName() {
        return LIST_OF_OFFICERS;
    }

    @GetMapping
    public String getListOfOfficers(@PathVariable String requestId, Model model, HttpServletRequest request) {

        try {
            EACRequest eacRequest = emergencyAuthCodeService.getEACRequest(requestId);
            EACOfficerList eacOfficerList = emergencyAuthCodeService.getListOfOfficers(eacRequest.getCompanyNumber());

            //Add back button with company number as path variable
            addBackPageAttributeToModel(model, eacRequest.getCompanyNumber());

            model.addAttribute("eacOfficerList", eacOfficerList);
            model.addAttribute("eacOfficer", new EACOfficer());
        } catch (ServiceException ex) {
            LOGGER.errorRequest(request, ex.getMessage(), ex);
            return ERROR_VIEW;
        }

        return getTemplateName();
    }


    @PostMapping
    public String postListOfDirectors(@PathVariable String requestId,
            @ModelAttribute("eacOfficer") @Valid EACOfficer eacOfficer, BindingResult bindingResult,
            HttpServletRequest request, Model model) {
        String id = eacOfficer.getId();

        if(bindingResult.hasErrors()) {
            try {
                EACRequest eacRequest = emergencyAuthCodeService.getEACRequest(requestId);
                EACOfficerList eacOfficerList = emergencyAuthCodeService.getListOfOfficers(eacRequest.getCompanyNumber());

                //Add back button with company number as path variable
                addBackPageAttributeToModel(model, eacRequest.getCompanyNumber());

                model.addAttribute("eacOfficerList", eacOfficerList);
            } catch (ServiceException ex) {
                LOGGER.errorRequest(request, ex.getMessage(), ex);
                return ERROR_VIEW;
            }

            return getTemplateName();
        }

        return navigatorService.getNextControllerRedirect(this.getClass(), requestId);
    }
}
