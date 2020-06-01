package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;

@Controller
@PreviousController(CompanyConfirmationPageController.class)
@RequestMapping("/auth-code-requests/company/{companyNumber}/cannot-use-this-service")
public class CannotUseThisServiceController extends BaseController {

    private static final String CANNOT_USE_THIS_SERVICE = "eac/cannotUseThisService";

    @Override
    protected String getTemplateName() {
        return CANNOT_USE_THIS_SERVICE;
    }

    @GetMapping
    public String getCompanyInformation(@PathVariable String companyNumber, Model model) {

        addBackPageAttributeToModel(model, companyNumber);

        return getTemplateName();
    }
}
