package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;

import java.text.ParseException;

@Controller
@PreviousController(CompanyLookupPlaceholderController.class)
@NextController(ListOfDirectorsController.class)
@RequestMapping("/request-an-authcode/company-information")
public class CompanyInformationPageController extends BaseController {

    private static String COMPANY_INFO = "eac/companyInformation";

    @Override
    protected String getTemplateName() {
        return COMPANY_INFO;
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
