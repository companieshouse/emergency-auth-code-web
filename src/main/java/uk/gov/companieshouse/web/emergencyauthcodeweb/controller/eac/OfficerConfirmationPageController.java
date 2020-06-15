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
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.form.OfficerConfirmation;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@PreviousController(ListOfOfficersController.class)
@NextController(ConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/{requestId}/confirm-officer")
public class OfficerConfirmationPageController extends BaseController {
    private static final String OFFICER_INFORMATION = "eac/officerConfirmation";
    private static final String OFFICER_CONFIRMATION_MODEL_ATTR = "officerConfirmation";

    private static final String SUBMITTED_STATUS = "submitted";

    @Autowired
    private EmergencyAuthCodeService emergencyAuthCodeService;

    @Override
    protected String getTemplateName() {
        return OFFICER_INFORMATION;
    }

    @GetMapping
    public String getOfficerConfirmation(@PathVariable String requestId,
                                         Model model,
                                         HttpServletRequest request) {

        try {
            // Retrieve details for the selected officer from the API
            EACRequest eacRequest = emergencyAuthCodeService.getEACRequest(requestId);
            if (eacRequest.getStatus().equals(SUBMITTED_STATUS)) {
                LOGGER.errorRequest(request, "Emergency Auth Code request has already been sent for this session");
                return ERROR_VIEW;
            }
            EACOfficer eacOfficer = emergencyAuthCodeService.getOfficer(eacRequest.getCompanyNumber(), eacRequest.getOfficerId());

            if (!model.containsAttribute(OFFICER_CONFIRMATION_MODEL_ATTR)) {
                model.addAttribute(OFFICER_CONFIRMATION_MODEL_ATTR, new OfficerConfirmation());
            }

            addBackPageAttributeToModel(model, requestId);

            model.addAttribute("eacOfficer", eacOfficer);
            model.addAttribute("eacRequest", eacRequest);
            model.addAttribute("eacOfficerDOBMonth", Month.of(Integer.parseInt(eacOfficer.getDateOfBirth().getMonth())));
            model.addAttribute("eacOfficerAppointment", DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH).format(eacOfficer.getAppointedOn()));
        } catch (ServiceException ex) {
            LOGGER.errorRequest(request, ex.getMessage(), ex);
            return ERROR_VIEW;
        }
        return getTemplateName();
    }


    @PostMapping
    public String postOfficerConfirmation(@PathVariable String requestId,
            @ModelAttribute @Valid OfficerConfirmation officerConfirmation,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            model.addAttribute(OFFICER_CONFIRMATION_MODEL_ATTR, officerConfirmation);
            return getOfficerConfirmation(requestId, model, request);
        }

        try {
            EACRequest eacRequest = emergencyAuthCodeService.getEACRequest(requestId);

            String companyNumber = eacRequest.getCompanyNumber();
            String officerId = eacRequest.getOfficerId();

            EACRequest updatedRequest = new EACRequest();
            updatedRequest.setCompanyNumber(companyNumber);
            updatedRequest.setOfficerId(officerId);
            updatedRequest.setStatus(SUBMITTED_STATUS);

            emergencyAuthCodeService.updateEACRequest(requestId, updatedRequest);
        } catch (ServiceException e) {
            LOGGER.errorRequest(request, e.getMessage(), e);
            return ERROR_VIEW;
        }


        return navigatorService.getNextControllerRedirect(this.getClass(), requestId);
    }
}
