package uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure;

import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorServiceTests;

/**
 * Mock controller class for testing missing {@code RequestMapping} value
 * when searching forwards or backwards in the controller chain.
 *
 * @see NavigatorServiceTests
 */
@PreviousController(MockControllerFive.class)
@RequestMapping()
public class MockControllerSix extends BaseController {

    @Override
    protected String getTemplateName() {
        return null;
    }
}

