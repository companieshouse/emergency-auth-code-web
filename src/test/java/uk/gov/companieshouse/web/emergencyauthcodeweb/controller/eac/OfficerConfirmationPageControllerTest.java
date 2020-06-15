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
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerDOB;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class OfficerConfirmationPageControllerTest {
    private static final String REQUEST_ID = "abc123";
    private static final String COMPANY_NUMBER = "12345678";
    private static final String OFFICER_ID = "87654321";
    private static final String EAC_OFFICER_CONFIRMATION_PATH = "/auth-code-requests/requests/" + REQUEST_ID + "/confirm-officer";
    private static final String EAC_OFFICER_CONFIRMATION_VIEW = "eac/officerConfirmation";
    private static final String ERROR = "error";
    private static final String MOCK_CONTROLLER_PATH = UrlBasedViewResolver.REDIRECT_URL_PREFIX + "mockControllerPath";

    private static final String BACK_BUTTON_MODEL_ATTR = "backButton";
    private static final String EAC_OFFICER_MODEL_ATTR = "eacOfficer";
    private static final String EAC_REQUEST_MODEL_ATTR = "eacRequest";
    private static final String EAC_OFFICER_DOB_MONTH_MODEL_ATTR = "eacOfficerDOBMonth";
    private static final String EAC_OFFICER_APPOINTMENT_MODEL_ATTR = "eacOfficerAppointment";


    private static final String OFFICER_CONFIRMATION_PARAM = "confirm";
    private static final String VALID_CONFIRMATION = "true";

    private MockMvc mockMvc;
    private EACRequest eacRequest = new EACRequest();
    private EACOfficer eacOfficer = new EACOfficer();
    private EACOfficerDOB eacOfficerDOB = new EACOfficerDOB();

    @Mock
    private EmergencyAuthCodeService emergencyAuthCodeService;

    @Mock
    private NavigatorService navigatorService;

    @InjectMocks
    private OfficerConfirmationPageController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Get list of officers view - request already marked as submitted")
    void getRequestEACRequestAlreadySent() throws Exception {
        eacRequest.setStatus("submitted");
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        eacRequest.setOfficerId(OFFICER_ID);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);

        this.mockMvc.perform(get(EAC_OFFICER_CONFIRMATION_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR));
    }

    @Test
    @DisplayName("Get list of officers view - error returning request from API")
    void getRequestErrorReturningEACRequest() throws Exception {
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenThrow(ServiceException.class);

        this.mockMvc.perform(get(EAC_OFFICER_CONFIRMATION_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR));
    }

    @Test
    @DisplayName("Get list of officers view - successful")
    void getRequestSuccessful() throws Exception {
        eacRequest.setStatus("pending");
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        eacRequest.setOfficerId(OFFICER_ID);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);

        eacOfficerDOB.setMonth("1");
        eacOfficer.setDateOfBirth(eacOfficerDOB);
        eacOfficer.setAppointedOn(LocalDate.of(2020, 1, 1));
        when(emergencyAuthCodeService.getOfficer(COMPANY_NUMBER, OFFICER_ID)).thenReturn(eacOfficer);

        when(navigatorService.getPreviousControllerPath(any(), any())).thenReturn(MOCK_CONTROLLER_PATH);

        this.mockMvc.perform(get(EAC_OFFICER_CONFIRMATION_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_OFFICER_CONFIRMATION_VIEW))
                .andExpect(model().attributeExists(BACK_BUTTON_MODEL_ATTR))
                .andExpect(model().attributeExists(EAC_OFFICER_MODEL_ATTR))
                .andExpect(model().attributeExists(EAC_REQUEST_MODEL_ATTR))
                .andExpect(model().attributeExists(EAC_OFFICER_DOB_MONTH_MODEL_ATTR))
                .andExpect(model().attributeExists(EAC_OFFICER_APPOINTMENT_MODEL_ATTR));
    }

    @Test
    @DisplayName("Post to confirmation page - successful")
    void postRequestSuccessful() throws Exception {
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        eacRequest.setOfficerId(OFFICER_ID);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);


        when(navigatorService.getNextControllerRedirect(any(), any()))
                .thenReturn(MOCK_CONTROLLER_PATH);

        this.mockMvc.perform(post(EAC_OFFICER_CONFIRMATION_PATH)
                    .param(OFFICER_CONFIRMATION_PARAM, VALID_CONFIRMATION))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name(MOCK_CONTROLLER_PATH));
    }

    @Test
    @DisplayName("Post to confirmation page - unsuccessful - ServiceException thrown by Service")
    void postRequestUnsuccessful_ServiceException() throws Exception {
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenThrow(ServiceException.class);

        this.mockMvc.perform(post(EAC_OFFICER_CONFIRMATION_PATH)
                .param(OFFICER_CONFIRMATION_PARAM, VALID_CONFIRMATION))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR));
    }

    @Test
    @DisplayName("Post to confirmation page - unsuccessful - checkbox not checked, which means the param returns false")
    void postRequestSuccessful_Unsuccessful_CheckboxNotChecked() throws Exception {
        eacRequest.setStatus("pending");
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        eacRequest.setOfficerId(OFFICER_ID);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);

        eacOfficerDOB.setMonth("1");
        eacOfficer.setDateOfBirth(eacOfficerDOB);
        eacOfficer.setAppointedOn(LocalDate.of(2020, 1, 1));
        when(emergencyAuthCodeService.getOfficer(COMPANY_NUMBER, OFFICER_ID)).thenReturn(eacOfficer);

        when(navigatorService.getPreviousControllerPath(any(), any())).thenReturn(MOCK_CONTROLLER_PATH);

        this.mockMvc.perform(post(EAC_OFFICER_CONFIRMATION_PATH)
                .param(OFFICER_CONFIRMATION_PARAM, "false"))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_OFFICER_CONFIRMATION_VIEW));
    }
}
