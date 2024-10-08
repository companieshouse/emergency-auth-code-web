package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
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
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.CompanyService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;

@Controller
@NextController(ListOfOfficersController.class)
@RequestMapping("/auth-code-requests/company/{companyNumber}/confirm")
public class CompanyConfirmationPageController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmergencyAuthCodeService emergencyAuthCodeService;

    private static final String COMPANY_INFO = "eac/companyConfirmation";
    private static final String CANNOT_USE_THIS_SERVICE = "/cannot-use-this-service";

    private static final String TEMPLATE_HEADING = "Confirm company details";

    private static final boolean SHOW_CONTINUE = true;
    private static final String MODEL_ATTR_SHOW_CONTINUE = "showContinue";
    private static final String SELF_KEY = "self";

    private static final String ACCEPTED_STATUS = "Active";

    private static final List<String> ACCEPTED_TYPES = new ArrayList<>(
            Arrays.asList("ltd", "private-limited-guarant-nsc-limited-exemption", "plc",
                    "private-limited-guarant-nsc", "private-limited-shares-section-30-exemption",
                    "llp"));

    @Override
    protected String getTemplateName() {
        return COMPANY_INFO;
    }

    @GetMapping
    public String getCompanyInformation(@PathVariable("companyNumber") String companyNumber, Model model, HttpServletRequest request) {
        try {
            CompanyDetail companyDetail = companyService.getCompanyDetail(companyNumber);

            model.addAttribute("companyDetail", companyDetail);

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
    public String postCompanyInformation(@PathVariable("companyNumber") String companyNumber, HttpServletRequest request) {
        EACRequest eacRequest = new EACRequest();
        EACRequest returnedRequest;
        String requestId;
        eacRequest.setCompanyNumber(companyNumber);

        try {
            CompanyDetail companyDetail = companyService.getCompanyDetail(companyNumber);

            if(!ACCEPTED_STATUS.equals(companyDetail.getCompanyStatus()) || !ACCEPTED_TYPES.contains(companyDetail.getType())){
                return getCannotUseThisServiceView(companyNumber);
            }

            returnedRequest = emergencyAuthCodeService.createAuthCodeRequest(eacRequest);

            if (returnedRequest == null) {
                return getCannotUseThisServiceView(companyNumber);
            }

            requestId = extractIdFromSelfLink(returnedRequest.getLinks());

            return navigatorService.getNextControllerRedirect(this.getClass(), requestId);

        } catch (ServiceException error) {
            LOGGER.errorRequest(request, error.getMessage(), error);
            return ERROR_VIEW;
        }
    }

    private String getCannotUseThisServiceView(final String companyNumber) {
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/auth-code-requests/company/" + companyNumber + CANNOT_USE_THIS_SERVICE;
    }

    private String extractIdFromSelfLink(Map<String, String> links) {
        String requestSelfLink = links.get(SELF_KEY);
        int index = requestSelfLink.lastIndexOf('/');
        return requestSelfLink.substring(index + 1);
    }
}
