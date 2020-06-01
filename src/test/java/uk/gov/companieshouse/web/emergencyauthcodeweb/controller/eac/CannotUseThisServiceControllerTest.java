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
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class CannotUseThisServiceControllerTest {
    private static final String COMPANY_NUMBER = "123456789";
    private static final String EAC_CANNOT_USE_SERVICE_PATH = "/auth-code-requests/company/" + COMPANY_NUMBER + "/cannot-use-this-service";
    private static final String EAC_CANNOT_USE_SERVICE_VIEW = "eac/cannotUseThisService";
    private static final String MOCK_CONTROLLER_PATH = UrlBasedViewResolver.REDIRECT_URL_PREFIX + "mockControllerPath";


    private static final String BACK_BUTTON_MODEL_ATTR = "backButton";

    private MockMvc mockMvc;

    @Mock
    private NavigatorService navigatorService;

    @InjectMocks
    private CannotUseThisServiceController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Get Cannot use this service view - successful")
    void getRequestSuccessful() throws Exception {

        when(navigatorService.getPreviousControllerPath(any(), any()))
                .thenReturn(MOCK_CONTROLLER_PATH);

        this.mockMvc.perform(get(EAC_CANNOT_USE_SERVICE_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_CANNOT_USE_SERVICE_VIEW))
                .andExpect(model().attributeExists(BACK_BUTTON_MODEL_ATTR));
    }
}
