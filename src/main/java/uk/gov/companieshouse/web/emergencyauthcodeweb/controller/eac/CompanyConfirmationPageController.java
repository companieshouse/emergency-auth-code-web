package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.CompanyService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@NextController(ListOfDirectorsController.class)
@RequestMapping("/auth-code-requests/company/{companyNumber}/confirm")
public class CompanyConfirmationPageController extends BaseController {

    @Autowired
    private CompanyService companyService;

    private static final String COMPANY_INFO = "eac/companyConfirmation";
    private static final String CANNOT_USE_THIS_SERVICE = "/cannot-use-this-service";

    private static final String TEMPLATE_HEADING = "Confirm company details";

    private static final boolean SHOW_CONTINUE = true;
    private static final String MODEL_ATTR_SHOW_CONTINUE = "showContinue";

    private static final List<String> ACCEPTED_TYPES = new ArrayList<>(
            Arrays.asList("ltd", "private-limited-guarant-nsc-limited-exemption", "plc",
                    "private-limited-guarant-nsc", "private-limited-shares-section-30-exemption",
                    "llp"));
    private static final String ACCEPTED_STATUS = "Active";

    @Override
    protected String getTemplateName() {
        return COMPANY_INFO;
    }

    @GetMapping
    public String getCompanyInformation(@PathVariable("companyNumber") String companyNumber, Model model, HttpServletRequest request) {

        try {
            model.addAttribute("companyDetail", getCompanyDetails(companyNumber));
        } catch (ServiceException e) {
            LOGGER.errorRequest(request, e.getMessage(), e);
            return ERROR_VIEW;
        }

        model.addAttribute(MODEL_ATTR_SHOW_CONTINUE, SHOW_CONTINUE);
        model.addAttribute("templateHeading", TEMPLATE_HEADING);

        model.addAttribute("backButton", "/company-lookup/search?forward=%2Fauth-code-requests%2Fcompany%2F%7BcompanyNumber%7D%2Fconfirm");

        return getTemplateName();
    }

    @PostMapping
    public String postListOfDirectors(@PathVariable("companyNumber") String companyNumber, HttpServletRequest request) {

        try {
            CompanyDetail companyDetail = getCompanyDetails(companyNumber);
            if( !ACCEPTED_STATUS.equals(companyDetail.getCompanyStatus()) || !ACCEPTED_TYPES.contains(companyDetail.getType())){
                return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/auth-code-requests/company/" + companyNumber + CANNOT_USE_THIS_SERVICE;
            }

            return navigatorService.getNextControllerRedirect(this.getClass());

        } catch (ServiceException error) {

            LOGGER.errorRequest(request, error.getMessage(), error);
            return ERROR_VIEW;
        }
    }

    private CompanyDetail getCompanyDetails(String companyNumber) throws ServiceException {
        return companyService.getCompanyDetail(companyNumber);
    }
}