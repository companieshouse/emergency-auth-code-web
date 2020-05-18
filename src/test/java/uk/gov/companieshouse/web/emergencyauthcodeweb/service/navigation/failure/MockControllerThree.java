package uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure;

import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorServiceTests;

/**
 * Mock controller class for testing missing navigation annotations {@code NextController}
 * and {@code PreviousController}.
 *
 * @see NavigatorServiceTests
 */
public class MockControllerThree extends BaseController {

    @Override
    protected String getTemplateName() {
        return null;
    }
}
