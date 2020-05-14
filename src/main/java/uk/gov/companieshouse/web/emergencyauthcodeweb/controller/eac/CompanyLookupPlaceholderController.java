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
@PreviousController(EACStartController.class)
@NextController(CompanyInformationPageController.class)
@RequestMapping("/request-an-authcode/companylookup")
public class CompanyLookupPlaceholderController extends BaseController {

    private static String COMPANY_LOOKUP = "eac/placeholder";

    @Override
    protected String getTemplateName() {
        return COMPANY_LOOKUP;
    }

    @GetMapping
    public String getCompanyLookup(Model model) {

        return getTemplateName();
    }

    @PostMapping
    public String postCompanyInfo() {

        return navigatorService.getNextControllerRedirect(this.getClass());
    }
}
