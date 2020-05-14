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
@PreviousController(CompanyInformationPageController.class)
@NextController(ConfirmationPageController.class)
@RequestMapping("/request-an-authcode/list-of-directors")
public class ListOfDirectorsController extends BaseController {

    private static String LIST_OF_DIRECTORS = "eac/listOfDirectors";

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
