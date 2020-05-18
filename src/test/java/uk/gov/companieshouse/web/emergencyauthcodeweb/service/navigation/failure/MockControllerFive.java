package uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure;

import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.NextController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorServiceTests;

/**
 * Mock controller class.
 *
 * @see NavigatorServiceTests
 */
@NextController(MockControllerSix.class)
@PreviousController(MockControllerFour.class)
@RequestMapping("/mock-controller-five")
public class MockControllerFive extends BaseController {

    @Override
    protected String getTemplateName() {
        return null;
    }
}
