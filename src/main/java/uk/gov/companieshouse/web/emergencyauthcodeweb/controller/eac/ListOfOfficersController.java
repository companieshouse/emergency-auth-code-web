package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerList;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@PreviousController(CompanyConfirmationPageController.class)
@NextController(OfficerConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/{requestId}/officers")
public class ListOfOfficersController extends BaseController {

    private static final String LIST_OF_OFFICERS = "eac/listOfOfficers";
    private static final String EAC_OFFICER_ATTR = "eacOfficer";

    @Autowired
    private EmergencyAuthCodeService emergencyAuthCodeService;

    @Override
    protected String getTemplateName() {
        return LIST_OF_OFFICERS;
    }

    @GetMapping
    public String getListOfOfficers(@PathVariable String requestId,
            @RequestParam(name = "page", defaultValue = "1") int page, Model model,
            HttpServletRequest request) {

        try {
            EACRequest eacRequest = emergencyAuthCodeService.getEACRequest(requestId);
            page -= 1;
            EACOfficerList eacOfficerList = emergencyAuthCodeService.getListOfOfficers(eacRequest.getCompanyNumber(), page);
            int totalPages = (int) Math.ceil((double) eacOfficerList.getTotalResults() / (double) eacOfficerList.getItemsPerPage());

            if(totalPages > 1) {
                List<Integer> pageNumbers =
                        IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("currentPage", page);
            }

            addBackPageAttributeToModel(model, eacRequest.getCompanyNumber());

            model.addAttribute("eacOfficerList", eacOfficerList);
            if(!model.containsAttribute(EAC_OFFICER_ATTR)) {
                model.addAttribute(EAC_OFFICER_ATTR, new EACOfficer());
            }
        } catch (ServiceException ex) {
            LOGGER.errorRequest(request, ex.getMessage(), ex);
            return ERROR_VIEW;
        }

        return getTemplateName();
    }


    @PostMapping
    public String postListOfOfficers(@PathVariable String requestId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @ModelAttribute("eacOfficer") @Valid EACOfficer eacOfficer, BindingResult bindingResult,
            HttpServletRequest request, Model model) {

        // If an officer is not selected, reload page and show error
        if(bindingResult.hasErrors()) {
            model.addAttribute(EAC_OFFICER_ATTR, eacOfficer);
            return getListOfOfficers(requestId, page, model, request);
        }

        // Retrieve company number from API for later request
        String companyNumber;
        try {
            companyNumber = emergencyAuthCodeService.getEACRequest(requestId).getCompanyNumber();
        } catch (ServiceException ex) {
            LOGGER.errorRequest(request, ex.getMessage(), ex);
            return ERROR_VIEW;
        }

        EACRequest eacRequest = new EACRequest();
        eacRequest.setOfficerId(eacOfficer.getId());
        eacRequest.setCompanyNumber(companyNumber);

        // Update API with selected officer
        try {
            emergencyAuthCodeService.updateEACRequest(requestId, eacRequest);
        } catch (ServiceException ex) {
            LOGGER.errorRequest(request, ex.getMessage(), ex);
            return ERROR_VIEW;
        }

        return navigatorService.getNextControllerRedirect(this.getClass(), requestId);
    }
}
