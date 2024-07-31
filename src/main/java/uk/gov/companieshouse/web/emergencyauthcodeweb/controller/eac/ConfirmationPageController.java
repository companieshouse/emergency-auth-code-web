package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@PreviousController(OfficerConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/{requestId}/confirmation")
public class ConfirmationPageController extends BaseController {

    private static final String CONFIRMATION_PAGE = "eac/confirmationPage";

    @Autowired
    private EmergencyAuthCodeService emergencyAuthCodeService;

    @Override
    protected String getTemplateName() {
        return CONFIRMATION_PAGE;
    }

    @GetMapping
    public String getConfirmationPage(@PathVariable String requestId, Model model, HttpServletRequest request) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mma", Locale.UK);

        try {
            LocalDateTime submittedAt = emergencyAuthCodeService.getEACRequest(requestId).getSubmittedAt();

            //Declare timezone of submittedAt in Mongo to be GMT
            ZonedDateTime gmt = ZonedDateTime.of(submittedAt, ZoneId.of("GMT"));

            //Convert GMT to British time
            LocalDateTime uk = gmt.withZoneSameInstant(ZoneId.of("Europe/London")).toLocalDateTime();

            String submittedTime = uk.toLocalTime().format(dtf);
            model.addAttribute("submittedAt", submittedAt);
            model.addAttribute("submittedMonth",
                    StringUtils.capitalize(submittedAt.getMonth().name().toLowerCase()));
            model.addAttribute("submittedTime", submittedTime.toLowerCase());
        } catch (ServiceException e) {
            LOGGER.errorRequest(request, e.getMessage(), e);
            return ERROR_VIEW;
        }

        return getTemplateName();
    }
}
