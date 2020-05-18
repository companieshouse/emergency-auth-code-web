package uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.MissingAnnotationException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerFive;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerOne;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerSeven;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerThree;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerTwo;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.success.MockSuccessJourneyControllerOne;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.success.MockSuccessJourneyControllerThree;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.success.MockSuccessJourneyControllerTwo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class NavigatorServiceTests {

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private NavigatorService navigatorService;

    @Test
    public void missingNextControllerAnnotation() {
        Throwable exception = assertThrows(MissingAnnotationException.class, () ->
                navigatorService.getNextControllerRedirect(MockControllerThree.class));

        assertEquals("Missing @NextController annotation on class uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerThree", exception.getMessage());
    }

    @Test
    public void missingPreviousControllerAnnotation() {
        Throwable exception = assertThrows(MissingAnnotationException.class, () ->
                navigatorService.getPreviousControllerPath(MockControllerThree.class));

        assertEquals("Missing @PreviousController annotation on class uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerThree", exception.getMessage());
    }

    @Test
    public void missingRequestMappingAnnotationOnNextController() {
        Throwable exception = assertThrows(MissingAnnotationException.class, () ->
                navigatorService.getNextControllerRedirect(MockControllerOne.class));

        assertEquals("Missing @RequestMapping annotation on class uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerTwo", exception.getMessage());
    }

    @Test
    public void missingRequestMappingAnnotationOnPreviousController() {
        Throwable exception = assertThrows(MissingAnnotationException.class, () ->
                navigatorService.getPreviousControllerPath(MockControllerTwo.class));

        assertEquals("Missing @RequestMapping annotation on class uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerOne", exception.getMessage());
    }

    @Test
    public void missingRequestMappingValueOnNextController() {
        Throwable exception = assertThrows(MissingAnnotationException.class, () ->
                navigatorService.getNextControllerRedirect(MockControllerFive.class));

        assertEquals("Missing @RequestMapping value on class uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerSix", exception.getMessage());
    }

    @Test
    public void missingRequestMappingValueOnPreviousController() {
        Throwable exception = assertThrows(MissingAnnotationException.class, () ->
                navigatorService.getPreviousControllerPath(MockControllerSeven.class));

        assertEquals("Missing @RequestMapping value on class uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.failure.MockControllerSix", exception.getMessage());
    }

    @Test
    public void successfulRedirectStartingFromMandatoryControllerWithExpectedNumberOfPathVariables() {
        when(applicationContext.getBean(MockSuccessJourneyControllerTwo.class)).thenReturn(new MockSuccessJourneyControllerTwo());

        String redirect = navigatorService.getNextControllerRedirect(
                MockSuccessJourneyControllerOne.class);

        assertEquals(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/mock-success-journey-controller-two", redirect);
    }

    @Test
    public void successfulRedirectStartingFromConditionalControllerWithExpectedNumberOfPathVariables() {
        when(applicationContext.getBean(MockSuccessJourneyControllerThree.class)).thenReturn(new MockSuccessJourneyControllerThree());

        String redirect = navigatorService.getNextControllerRedirect(MockSuccessJourneyControllerTwo.class);

        assertEquals(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/mock-success-journey-controller-three", redirect);
    }

    @Test
    public void successfulPathReturnedWithSingleConditionalControllerInChain() {
        when(applicationContext.getBean(MockSuccessJourneyControllerTwo.class)).thenReturn(new MockSuccessJourneyControllerTwo());
        when(applicationContext.getBean(MockSuccessJourneyControllerThree.class)).thenReturn(new MockSuccessJourneyControllerThree());

        String redirect = navigatorService.getPreviousControllerPath(MockSuccessJourneyControllerThree.class);

        assertEquals("/mock-success-journey-controller-two", redirect);
    }

}
