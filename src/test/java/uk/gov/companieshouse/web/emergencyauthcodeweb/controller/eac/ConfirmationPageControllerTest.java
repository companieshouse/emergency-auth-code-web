package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class ConfirmationPageControllerTest {
    private static final String EAC_CONFIRMATION_PATH = "/auth-code-requests/requests/request_id_placeholder/confirmation";
    private static final String EAC_CONFIRMATION_VIEW = "eac/confirmationPage";

    private MockMvc mockMvc;

    @InjectMocks
    private ConfirmationPageController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Get confirmation view - successful")
    void getRequestSuccessful() throws Exception {
        this.mockMvc.perform(get(EAC_CONFIRMATION_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_CONFIRMATION_VIEW));
    }
}
