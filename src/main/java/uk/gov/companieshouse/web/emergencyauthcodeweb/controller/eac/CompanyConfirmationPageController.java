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
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.CompanyService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.impl.EmergencyAuthCodeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@NextController(ListOfDirectorsController.class)
@RequestMapping("/auth-code-requests/company/{company_number}/confirm")
public class CompanyConfirmationPageController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmergencyAuthCodeServiceImpl emergencyAuthCodeServiceImpl;

    private static final String COMPANY_INFO = "eac/companyConfirmation";

    private static final String TEMPLATE_HEADING = "Confirm company details";

    private static final boolean SHOW_CONTINUE = true;
    private static final String MODEL_ATTR_SHOW_CONTINUE = "showContinue";
    private static final String SELF_KEY = "self";

    @Override
    protected String getTemplateName() {
        return COMPANY_INFO;
    }

    @GetMapping
    public String getCompanyInformation(@PathVariable("company_number") String companyNumber, Model model, HttpServletRequest request) {

        try {
            model.addAttribute("companyDetail", companyService.getCompanyDetail(companyNumber));
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
    public String postListOfDirectors(@PathVariable("company_number") String companyNumber, HttpServletRequest request) {
        EACRequest eacRequest = new EACRequest();
        EACRequest returnedRequest;
        String requestId;
        eacRequest.setCompanyNumber(companyNumber);


        try {
            returnedRequest = emergencyAuthCodeServiceImpl.createAuthCodeRequest(eacRequest);
            requestId = extractIdFromSelfLink(returnedRequest.getLinks());
        } catch (ServiceException ex) {
            LOGGER.errorRequest(request, ex.getMessage(), ex);
            return ERROR_VIEW;
        }
        return navigatorService.getNextControllerRedirect(this.getClass(), requestId);
    }

    private String extractIdFromSelfLink(Map<String, String> links) {
        String requestSelfLink = links.get(SELF_KEY);
        int index = requestSelfLink.lastIndexOf('/');
        return requestSelfLink.substring(index+1);
    }

}
