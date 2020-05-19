package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.CompanyService;

import javax.servlet.http.HttpServletRequest;

@Controller
@NextController(ListOfDirectorsController.class)
@RequestMapping("/request-an-authcode/company/{companyNumber}/company-information")
public class CompanyInformationPageController extends BaseController {

    @Autowired
    private CompanyService companyService;

    private static String COMPANY_INFO = "eac/companyInformation";

    private static final String TEMPLATE_HEADING = "Confirm company details";

    private static final boolean SHOW_CONTINUE = true;
    private static final String MODEL_ATTR_SHOW_CONTINUE = "showContinue";

    @Override
    protected String getTemplateName() {
        return COMPANY_INFO;
    }

    @GetMapping
    public String getCompanyInformation(@PathVariable String companyNumber, Model model, HttpServletRequest request) {

        try {
            model.addAttribute("companyProfile", companyService.getCompanyProfile(companyNumber));
        } catch (ServiceException e) {
            LOGGER.errorRequest(request, e.getMessage(), e);
            return ERROR_VIEW;
        }

        model.addAttribute(MODEL_ATTR_SHOW_CONTINUE, SHOW_CONTINUE);
        model.addAttribute("templateHeading", TEMPLATE_HEADING);

        model.addAttribute("backButton", "/company-lookup/search?forward=%2Frequest-an-authcode%2Fcompany%2F%7BcompanyNumber%7D%2Fcompany-information");

        return getTemplateName();
    }

    @PostMapping
    public String postListOfDirectors() {

        return navigatorService.getNextControllerRedirect(this.getClass());
    }

}
