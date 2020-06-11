package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;

@Controller
@RequestMapping("/auth-code-requests/accessibility-statement")
public class AccessibilityStatementController extends BaseController {

    private static String EAC_ACCESSIBILITY = "eac/accessibilityStatement";

    @Override protected String getTemplateName() {
        return EAC_ACCESSIBILITY;
    }

    @GetMapping
    public String getEACAccessibilityStatement() {

        return getTemplateName();
    }
}