package uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure;

import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.ConditionalController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorServiceTests;

/**
 * Mock conditional controller class for testing missing expected number of
 * path variables.
 *
 * @see NavigatorServiceTests
 */
@NextController(MockControllerFive.class)
@PreviousController(MockControllerThree.class)
public class MockControllerFour extends BaseController implements ConditionalController {

    @Override
    protected String getTemplateName() {
        return null;
    }

    @Override
    public boolean willRender(String companyNumber, String transactionId, String companylfpId) {
        return false;
    }
}
