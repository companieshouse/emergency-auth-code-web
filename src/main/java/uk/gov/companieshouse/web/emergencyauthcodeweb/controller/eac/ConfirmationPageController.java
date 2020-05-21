package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;

@Controller
@PreviousController(OfficerConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/request_id_placeholder/confirmation")
public class ConfirmationPageController extends BaseController {

    private static final String CONFIRMATION_PAGE = "eac/confirmationPage";

    @Override
    protected String getTemplateName() {
        return CONFIRMATION_PAGE;
    }

    @GetMapping
    public String getCompanyInformation(Model model) {

        addBackPageAttributeToModel(model);

        return getTemplateName();
    }
}
