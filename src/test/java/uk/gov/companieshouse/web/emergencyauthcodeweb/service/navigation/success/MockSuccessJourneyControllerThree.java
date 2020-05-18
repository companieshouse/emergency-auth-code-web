package uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.success;

import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.ConditionalController;

/**
 * Mock controller class for success scenario testing of navigation.
 */
@PreviousController(MockSuccessJourneyControllerTwo.class)
@RequestMapping("/mock-success-journey-controller-three")
public class MockSuccessJourneyControllerThree extends BaseController implements
        ConditionalController {

    @Override
    protected String getTemplateName() {
        return null;
    }

    @Override
    public boolean willRender(String companyNumber, String transactionId, String companylfpId) {
        return true;
    }
}
