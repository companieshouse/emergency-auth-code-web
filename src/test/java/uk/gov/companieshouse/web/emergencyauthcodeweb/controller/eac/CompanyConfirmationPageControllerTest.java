package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.CompanyService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(MockitoExtension.class)
public class CompanyConfirmationPageControllerTest {

    private static final String COMPANY_NUMBER = "12345678";

    private static final String EAC_COMPANY_CONFIRMATION_PATH = "/auth-code-requests/company/" +
            COMPANY_NUMBER + "/confirm";

    private static final String EAC_COMPANY_CONFIRMATION_VIEW = "eac/companyConfirmation";

    private static final String COMPANY_DETAIL_MODEL_ATTR = "companyDetail";

    private static final String TEMPLATE_NAME_MODEL_ATTR = "templateName";

    private static final String TEMPLATE_HEADING_MODEL_ATTR = "templateHeading";

    private static final String TEMPLATE_SHOW_CONTINUE_MODEL_ATTR = "showContinue";

    private static final String ERROR_VIEW = "error";

    private static final String INVALID_COMPANY_TYPE = "invalid-company-type";

    private static final String VALID_COMPANY_TYPE = "ltd";

    private static final String INVALID_COMPANY_STATUS = "invalid status";

    private static final String VALID_COMPANY_STATUS = "Active";

    private static final String EAC_CANNOT_USE_SERVICE_PATH = "redirect:/auth-code-requests/company/" + COMPANY_NUMBER + "/cannot-use-this-service";

    private static final String MOCK_CONTROLLER_PATH = UrlBasedViewResolver.REDIRECT_URL_PREFIX + "mockControllerPath";


    private MockMvc mockMvc;

    private EACRequest eacRequest = new EACRequest();

    @Mock
    private CompanyService mockCompanyService;

    @Mock
    private EmergencyAuthCodeService mockEACService;

    @Mock
    private NavigatorService navigatorService;

    @Mock
    private CompanyDetail companyDetail;

    @InjectMocks
    private CompanyConfirmationPageController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Get company information view - successful")
    void getRequestSuccessful() throws Exception {

        when(mockCompanyService.getCompanyDetail(COMPANY_NUMBER)).thenReturn(companyDetail);

        this.mockMvc.perform(get(EAC_COMPANY_CONFIRMATION_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_COMPANY_CONFIRMATION_VIEW))
                .andExpect(model().attributeExists(COMPANY_DETAIL_MODEL_ATTR))
                .andExpect(model().attributeExists(TEMPLATE_HEADING_MODEL_ATTR))
                .andExpect(model().attributeExists(TEMPLATE_SHOW_CONTINUE_MODEL_ATTR))
                .andExpect(model().attributeExists(TEMPLATE_NAME_MODEL_ATTR));


        verify(mockCompanyService, times(1)).getCompanyDetail(COMPANY_NUMBER);

    }

    @Test
    @DisplayName("Post to list of directors page - successful")
    void postRequestSuccessful() throws Exception {
        Map<String, String> links = new HashMap<>();
        links.put("self", "/selfLink");
        eacRequest.setLinks(links);
        when(mockEACService.createAuthCodeRequest(any(EACRequest.class))).thenReturn(eacRequest);

        when(navigatorService.getNextControllerRedirect(any(), any()))
                .thenReturn(MOCK_CONTROLLER_PATH);

        CompanyDetail validCompanyTypeAndStatus= new CompanyDetail();
        validCompanyTypeAndStatus.setType(VALID_COMPANY_TYPE);
        validCompanyTypeAndStatus.setCompanyStatus(VALID_COMPANY_STATUS);

        when(mockCompanyService.getCompanyDetail(COMPANY_NUMBER)).thenReturn(validCompanyTypeAndStatus);

        this.mockMvc.perform(post(EAC_COMPANY_CONFIRMATION_PATH))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(MOCK_CONTROLLER_PATH));

    }

    @Test
    @DisplayName("Post to cannot use service page - invalid company status")
    void postRequestUnsuccessfulStatus() throws Exception {

        CompanyDetail invalidCompanyStatus = new CompanyDetail();
        invalidCompanyStatus.setType(INVALID_COMPANY_STATUS);
        when(mockCompanyService.getCompanyDetail(COMPANY_NUMBER)).thenReturn(invalidCompanyStatus);

        this.mockMvc.perform(post(EAC_COMPANY_CONFIRMATION_PATH))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(EAC_CANNOT_USE_SERVICE_PATH));
    }

    @Test
    @DisplayName("Post to cannot use service page - invalid company type")
    void postRequestUnsuccessfulType() throws Exception {

        CompanyDetail invalidCompanyType = new CompanyDetail();
        invalidCompanyType.setType(INVALID_COMPANY_TYPE);
        when(mockCompanyService.getCompanyDetail(COMPANY_NUMBER)).thenReturn(invalidCompanyType);

        this.mockMvc.perform(post(EAC_COMPANY_CONFIRMATION_PATH))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(EAC_CANNOT_USE_SERVICE_PATH));
    }

    @Test
    @DisplayName("Get company information view - Throws exception")
    void getRequestThrowsException() throws Exception {
        doThrow(ServiceException.class).when(mockCompanyService).getCompanyDetail(COMPANY_NUMBER);

        this.mockMvc.perform(get(EAC_COMPANY_CONFIRMATION_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_VIEW));

        verify(mockCompanyService, times(1)).getCompanyDetail(COMPANY_NUMBER);
    }
}
