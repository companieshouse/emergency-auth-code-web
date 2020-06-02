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
@PreviousController(CompanyConfirmationPageController.class)
@NextController(OfficerConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/{requestId}/officers")
public class ListOfDirectorsController extends BaseController {

    private static final String LIST_OF_DIRECTORS = "eac/listOfDirectors";

    @Override
    protected String getTemplateName() {
        return LIST_OF_DIRECTORS;
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
