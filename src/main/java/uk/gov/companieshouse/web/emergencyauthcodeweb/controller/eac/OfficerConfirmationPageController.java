package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;

@Controller
@PreviousController(ListOfOfficersController.class)
@NextController(ConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/{requestId}/confirm-officer")
public class OfficerConfirmationPageController extends BaseController {
    private static final String OFFICER_INFORMATION = "eac/officerConfirmation";

    @Override
    protected String getTemplateName() {
        return OFFICER_INFORMATION;
    }

    @GetMapping
    public String getOfficerConfirmation(@PathVariable String requestId, Model model) {

        addBackPageAttributeToModel(model, requestId);

        return getTemplateName();
    }


    @PostMapping
    public String postOfficerConfirmation(@PathVariable String requestId) {

        return navigatorService.getNextControllerRedirect(this.getClass(), requestId);
    }
}
