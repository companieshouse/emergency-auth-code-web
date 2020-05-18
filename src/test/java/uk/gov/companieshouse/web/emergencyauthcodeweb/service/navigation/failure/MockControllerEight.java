package uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure;

import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.companieshouse.web.emergencyauthcodeweb.annotation.PreviousController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.ConditionalController;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorServiceTests;

/**
 * Mock conditional controller class for testing exception handling.
 *
 * @see NavigatorServiceTests
 * @see uk.gov.companieshouse.web.emergencyauthcodeweb.exception.NavigationException
 */
@RequestMapping("/mock-controller-eight")
@PreviousController(MockControllerSeven.class)
public class MockControllerEight extends BaseController implements ConditionalController {

    @Override
    protected String getTemplateName() {
        return null;
    }

    @Override
    public boolean willRender(String companyNumber, String transactionId, String companylfpId) throws
            ServiceException {
        throw new ServiceException("Test exception", null);
    }
}
