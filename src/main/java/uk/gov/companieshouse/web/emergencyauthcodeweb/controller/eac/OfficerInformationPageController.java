package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;

@Controller
@PreviousController(ListOfDirectorsController.class)
@NextController(ConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/request_id_placeholder/confirm-officer")
public class OfficerInformationPageController extends BaseController {
    private static final String OFFICER_INFORMATION = "eac/officerInformation";

    @Override
    protected String getTemplateName() {
        return OFFICER_INFORMATION;
    }

    @GetMapping
    public String getCompanyInformation(Model model) {

        return getTemplateName();
    }


    @PostMapping
    public String postListOfDirectors() {

        return navigatorService.getNextControllerRedirect(this.getClass());
    }
}
