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
public class AccessibilityStatementControllerTest {
    private static final String EAC_ACCESSIBILITY_STATEMENT_PATH = "/auth-code-requests/accessibility-statement";
    private static final String EAC_ACCESSIBILITY_STATEMENT_VIEW = "eac/accessibilityStatement";


    private MockMvc mockMvc;

    @InjectMocks
    private AccessibilityStatementController accessibilityStatementController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(accessibilityStatementController).build();
    }

    @Test
    @DisplayName("Get accessibility statement view - successful")
    void getRequestSuccessful() throws Exception {

        this.mockMvc.perform(get(EAC_ACCESSIBILITY_STATEMENT_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_ACCESSIBILITY_STATEMENT_VIEW));
    }
}
