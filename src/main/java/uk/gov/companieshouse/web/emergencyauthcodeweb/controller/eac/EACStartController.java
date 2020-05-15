package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.Criteria;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
@NextController(EACStartController.class)
@RequestMapping("/request-an-authcode")
public class EACStartController extends BaseController {

    private static String EAC_HOME = "eac/startPage";

    @Override
    protected String getTemplateName() {
        return EAC_HOME;
    }

    @GetMapping
    public String getEacHome(Model model) {

        model.addAttribute("criteria", new Criteria());

        return getTemplateName();
    }

    @PostMapping
    public String postCompanyLookup(@ModelAttribute("criteria") @Valid Criteria criteria,
            BindingResult bindingResult, Model model, RedirectAttributes attributes) {

        attributes.addAttribute("forward", "/request-an-authcode/company/{companyNumber}/company-information");
        return UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/company-lookup/search";
    }
}
