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
public class EACStartControllerTest {
    private static final String EAC_START_PATH = "/request-an-authcode";
    private static final String EAC_START_VIEW = "eac/startPage";
    private static final String MOCK_CONTROLLER_PATH = UrlBasedViewResolver.REDIRECT_URL_PREFIX + "mockControllerPath";

    private MockMvc mockMvc;

    @Mock
    private NavigatorService navigatorService;

    @InjectMocks
    private EACStartController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Get start page view - successful")
    void getRequestSuccessful() throws Exception {
        this.mockMvc.perform(get(EAC_START_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_START_VIEW));
    }

    @Test
    @DisplayName("Post to company lookup page - successful")
    void postRequestSuccessful() throws Exception {
        when(navigatorService.getNextControllerRedirect(any(), any()))
                .thenReturn(MOCK_CONTROLLER_PATH);

        this.mockMvc.perform(post(EAC_START_PATH))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(MOCK_CONTROLLER_PATH));
    }
}
