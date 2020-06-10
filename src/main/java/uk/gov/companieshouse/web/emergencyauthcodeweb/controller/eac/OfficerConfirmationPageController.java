package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.form.OfficerConfirmation;

import javax.validation.Valid;

@Controller
@PreviousController(ListOfOfficersController.class)
@NextController(ConfirmationPageController.class)
@RequestMapping("/auth-code-requests/requests/{requestId}/confirm-officer")
public class OfficerConfirmationPageController extends BaseController {
    private static final String OFFICER_INFORMATION = "eac/officerConfirmation";

    @Override
    protected String getTemplateName() {
        return OFFICER_INFORMATION;
    }

    @GetMapping
    public String getOfficerConfirmation(@PathVariable String requestId, Model model) {

        model.addAttribute("officerConfirmation", new OfficerConfirmation());

        addBackPageAttributeToModel(model, requestId);

        return getTemplateName();
    }


    @PostMapping
    public String postOfficerConfirmation(@PathVariable String requestId,
            @ModelAttribute @Valid OfficerConfirmation officerConfirmation,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return getTemplateName();
        }

        return navigatorService.getNextControllerRedirect(this.getClass(), requestId);
    }
}
