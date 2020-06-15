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
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerList;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.navigation.NavigatorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ListOfOfficersControllerTest {
    private static final String REQUEST_ID = "abc123";
    private static final String OFFICER_ID_PARAM = "id";
    private static final String VALID_OFFICER_ID = "123abc";
    private static final String COMPANY_NUMBER = "12345678";
    private static final String EAC_LIST_OF_OFFICERS_PATH =
            "/auth-code-requests/requests/" + REQUEST_ID + "/officers";
    private static final String EAC_LIST_OF_OFFICERS_VIEW = "eac/listOfOfficers";
    private static final String ERROR_VIEW = "error";
    private static final String MOCK_CONTROLLER_PATH = UrlBasedViewResolver.REDIRECT_URL_PREFIX + "mockControllerPath";
    private static final String TEMPLATE_OFFICER_LIST_MODEL = "eacOfficerList";
    private static final String TEMPLATE_INDIVIDUAL_OFFICER_MODEL = "eacOfficer";

    private MockMvc mockMvc;

    private EACRequest eacRequest = new EACRequest();
    private EACOfficerList eacOfficerList = new EACOfficerList();

    @Mock
    private EmergencyAuthCodeService emergencyAuthCodeService;

    @Mock
    private NavigatorService navigatorService;

    @InjectMocks
    private ListOfOfficersController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Get list of officers view - successful")
    void getRequestSuccessful() throws Exception {
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);
        when(emergencyAuthCodeService.getListOfOfficers(eacRequest.getCompanyNumber())).thenReturn(eacOfficerList);

        this.mockMvc.perform(get(EAC_LIST_OF_OFFICERS_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_LIST_OF_OFFICERS_VIEW))
                .andExpect(model().attributeExists(TEMPLATE_OFFICER_LIST_MODEL))
                .andExpect(model().attributeExists(TEMPLATE_INDIVIDUAL_OFFICER_MODEL));
    }

    @Test
    @DisplayName("Get list of officers view - unsuccessful - emergencyAuthCodeService returns ServiceException for getEACRequest")
    void getRequestUnsuccessful_ServiceException_GetEACRequest() throws Exception {
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenThrow(ServiceException.class);

        this.mockMvc.perform(get(EAC_LIST_OF_OFFICERS_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_VIEW));
    }

    @Test
    @DisplayName("Get list of officers view - unsuccessful - emergencyAuthCodeService returns ServiceException for getListOfOfficers")
    void getRequestUnsuccessful_ServiceException_GetListOfOfficers() throws Exception {
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);
        when(emergencyAuthCodeService.getListOfOfficers(eacRequest.getCompanyNumber())).thenThrow(ServiceException.class);

        this.mockMvc.perform(get(EAC_LIST_OF_OFFICERS_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_VIEW));
    }

    @Test
    @DisplayName("Post to confirmation page - unsuccessful - null officer id from user not selecting radio button")
    void postRequestUnsuccessful_NullOfficerId() throws Exception {
        String officerId = null;

        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);
        when(emergencyAuthCodeService.getListOfOfficers(eacRequest.getCompanyNumber())).thenReturn(eacOfficerList);

        this.mockMvc.perform(post(EAC_LIST_OF_OFFICERS_PATH)
                .param(OFFICER_ID_PARAM, officerId))
                .andExpect(status().isOk())
                .andExpect(view().name(EAC_LIST_OF_OFFICERS_VIEW))
                .andExpect(model().attributeErrorCount(TEMPLATE_INDIVIDUAL_OFFICER_MODEL, 1));
    }

    @Test
    @DisplayName("Post to confirmation page - unsuccessful - emergencyAuthCodeService returns ServiceException for getEACRequest")
    void postRequestUnsuccessful_ServiceException_GetEACRequest() throws Exception {
        String officerId = null;

        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenThrow(ServiceException.class);

        this.mockMvc.perform(post(EAC_LIST_OF_OFFICERS_PATH)
                .param(OFFICER_ID_PARAM, officerId))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_VIEW));
    }

    @Test
    @DisplayName("Post to confirmation page - unsuccessful - emergencyAuthCodeService returns ServiceException for getListOfOfficers")
    void postRequestUnsuccessful_ServiceException_GetListOfOfficers() throws Exception {
        String officerId = null;

        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);
        when(emergencyAuthCodeService.getListOfOfficers(eacRequest.getCompanyNumber())).thenThrow(ServiceException.class);

        this.mockMvc.perform(post(EAC_LIST_OF_OFFICERS_PATH)
                .param(OFFICER_ID_PARAM, officerId))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_VIEW));
    }

    @Test
    @DisplayName("Post to confirmation page - error returning eac request")
    void postRequestErrorReturningEacRequest() throws Exception {
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenThrow(ServiceException.class);

        this.mockMvc.perform(post(EAC_LIST_OF_OFFICERS_PATH)
                .param(OFFICER_ID_PARAM, VALID_OFFICER_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(ERROR_VIEW));
    }

    @Test
    @DisplayName("Post to confirmation page - error updating eac request")
    void postRequestErrorUpdatingEacRequest() throws Exception {
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);
        when(emergencyAuthCodeService.updateEACRequest(any(), any())).thenThrow(ServiceException.class);

        this.mockMvc.perform(post(EAC_LIST_OF_OFFICERS_PATH)
                .param(OFFICER_ID_PARAM, VALID_OFFICER_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(ERROR_VIEW));
    }

    @Test
    @DisplayName("Post to confirmation page - successful")
    void postRequestSuccessful() throws Exception {
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        when(emergencyAuthCodeService.getEACRequest(REQUEST_ID)).thenReturn(eacRequest);
        when(navigatorService.getNextControllerRedirect(any(), any())).thenReturn(MOCK_CONTROLLER_PATH);

        this.mockMvc.perform(post(EAC_LIST_OF_OFFICERS_PATH)
                .param(OFFICER_ID_PARAM, VALID_OFFICER_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(MOCK_CONTROLLER_PATH))
                .andExpect(model().attributeErrorCount(TEMPLATE_INDIVIDUAL_OFFICER_MODEL, 0));
    }
}
