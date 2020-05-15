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
import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac.util.EACTestUtility;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.CompanyService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class CompanyInformationPageControllerTest {

    private static final String COMPANY_NUMBER = "12345678";
    private static final String EAC_COMPANY_INFORMATION_PATH = "/request-an-authcode/company/" + COMPANY_NUMBER + "/company-information";
    private static final String EAC_COMPANY_INFORMATION_VIEW = "eac/companyInformation";
    private static final String MOCK_CONTROLLER_PATH = UrlBasedViewResolver.REDIRECT_URL_PREFIX + "mockControllerPath";

    private MockMvc mockMvc;

    @Mock
    private CompanyService mockCompanyService;

    @Mock
    private NavigatorService navigatorService;

    @InjectMocks
    private CompanyInformationPageController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Get company information view - successful")
    void getRequestSuccessful() throws Exception {

        configureValidCompanyProfile(COMPANY_NUMBER);

        this.mockMvc.perform(get(EAC_COMPANY_INFORMATION_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_COMPANY_INFORMATION_VIEW));

        verify(mockCompanyService, times(1)).getCompanyProfile(COMPANY_NUMBER);

    }

    @Test
    @DisplayName("Post to list of directors page - successful")
    void postRequestSuccessful() throws Exception {
        when(navigatorService.getNextControllerRedirect(any(), any()))
                .thenReturn(MOCK_CONTROLLER_PATH);

        this.mockMvc.perform(post(EAC_COMPANY_INFORMATION_PATH))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(MOCK_CONTROLLER_PATH));

    }


    private void configureValidCompanyProfile(String companyNumber) throws ServiceException {
        when(mockCompanyService.getCompanyProfile(companyNumber))
                .thenReturn(EACTestUtility.validCompanyProfile(companyNumber));
    }
}
