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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class OfficerConfirmationPageControllerTest {
    private static final String EAC_OFFICER_CONFIRMATION_PATH = "/auth-code-requests/requests/request_id_placeholder/confirm-officer";
    private static final String EAC_OFFICER_CONFIRMATION_VIEW = "eac/officerConfirmation";
    private static final String MOCK_CONTROLLER_PATH = UrlBasedViewResolver.REDIRECT_URL_PREFIX + "mockControllerPath";

    private MockMvc mockMvc;

    @Mock
    private NavigatorService navigatorService;

    @InjectMocks
    private OfficerConfirmationPageController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Get list of directors view - successful")
    void getRequestSuccessful() throws Exception {
        this.mockMvc.perform(get(EAC_OFFICER_CONFIRMATION_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_OFFICER_CONFIRMATION_VIEW));
    }

    @Test
    @DisplayName("Post to confirmation page - successful")
    void postRequestSuccessful() throws Exception {
        when(navigatorService.getNextControllerRedirect(any(), any()))
                .thenReturn(MOCK_CONTROLLER_PATH);

        this.mockMvc.perform(post(EAC_OFFICER_CONFIRMATION_PATH))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(MOCK_CONTROLLER_PATH));
    }
}