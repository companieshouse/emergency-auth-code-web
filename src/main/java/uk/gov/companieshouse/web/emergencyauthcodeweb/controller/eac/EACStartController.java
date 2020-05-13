package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;

import java.text.ParseException;

@Controller
@NextController(CompanyLookupPlaceholderController.class)
@RequestMapping("/request-an-authcode")
public class EACStartController extends BaseController {

    private static String EAC_HOME = "eac/startPage";

    @Override
    protected String getTemplateName() {
        return EAC_HOME;
    }

    @GetMapping
    public String getEacHome(Model model) {

        return getTemplateName();
    }


    @PostMapping
    public String postCompanyLookup() {

        return navigatorService.getNextControllerRedirect(this.getClass());
    }
}
