package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;

import javax.servlet.http.HttpServletRequest;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@PreviousController(ListOfOfficersController.class)
@NextController(ConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/{requestId}/confirm-officer")
public class OfficerConfirmationPageController extends BaseController {
    private static final String OFFICER_INFORMATION = "eac/officerConfirmation";

    @Autowired
    private EmergencyAuthCodeService emergencyAuthCodeService;

    @Override
    protected String getTemplateName() {
        return OFFICER_INFORMATION;
    }

    @GetMapping
    public String getOfficerConfirmation(@PathVariable String requestId, Model model, HttpServletRequest request) {

        try {
            // Retrieve details for the selected officer from the API
            EACRequest eacRequest = emergencyAuthCodeService.getEACRequest(requestId);
            if (eacRequest.getStatus().equals("sent")) {
                LOGGER.errorRequest(request, "Emergency Auth Code request has already been sent for this session");
                return ERROR_VIEW;
            }
            EACOfficer eacOfficer = emergencyAuthCodeService.getOfficer(eacRequest.getCompanyNumber(), eacRequest.getOfficerId());

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
    public String postOfficerConfirmation(@PathVariable String requestId) {

        return navigatorService.getNextControllerRedirect(this.getClass(), requestId);
    }
}
