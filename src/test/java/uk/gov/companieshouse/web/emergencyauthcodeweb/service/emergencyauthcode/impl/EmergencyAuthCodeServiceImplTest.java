package uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.emergencyauthcode.PrivateEACResourceHandler;
import uk.gov.companieshouse.api.handler.emergencyauthcode.request.PrivateEACOfficerList;
import uk.gov.companieshouse.api.handler.emergencyauthcode.request.PrivateEACRequestCreate;
import uk.gov.companieshouse.api.handler.emergencyauthcode.request.PrivateEACRequestGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.emergencyauthcode.authcoderequest.PrivateEACRequestApi;
import uk.gov.companieshouse.api.model.emergencyauthcode.officer.PrivateEACOfficersListApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.api.ApiClientService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerList;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode.officer.EACOfficerListTransformer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode.request.EACRequestTransformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmergencyAuthCodeServiceImplTest {

    private static final String EAC_REQUEST_ID = "abc123";
    private static final String EMERGENCY_AUTH_CODE_REQUEST_URI = "/emergency-auth-code-service/auth-code-requests";
    private static final String GET_EMERGENCY_AUTH_CODE_REQUEST_URI = EMERGENCY_AUTH_CODE_REQUEST_URI + "/" + EAC_REQUEST_ID;

    private static final String COMPANY_NUMBER = "12345678";
    private static final String LIST_OFFICERS_URI = "/emergency-auth-code-service/company/" + COMPANY_NUMBER + "/officers";

    private EACRequest eacRequest = new EACRequest();
    private PrivateEACRequestApi eacRequestApi = new PrivateEACRequestApi();

    private EACOfficerList eacOfficerList = new EACOfficerList();
    private PrivateEACOfficersListApi eacOfficersListApi = new PrivateEACOfficersListApi();

    @Mock
    private InternalApiClient internalApiClient;

    @Mock
    private PrivateEACResourceHandler privateEACResourceHandler;

    @Mock
    private PrivateEACRequestCreate privateEACRequestCreate;

    @Mock
    private ApiResponse<PrivateEACRequestApi> eacRequestApiResponse;

    @Mock
    private PrivateEACRequestGet privateEACRequestGet;

    @Mock
    private PrivateEACOfficerList privateEACOfficerList;

    @Mock
    private ApiResponse<PrivateEACOfficersListApi> eacOfficersListApiResponse;

    @Mock
    private ApiClientService apiClientService;

    @Mock
    private EACRequestTransformer eacRequestTransformer;

    @Mock
    private EACOfficerListTransformer eacOfficerListTransformer;

    @InjectMocks
    private EmergencyAuthCodeServiceImpl eacService;

    @Test
    @DisplayName("Test post request to create an EmergencyAuthCodeRequest is successful")
    void testEACPostRequest_Successful()
            throws ApiErrorResponseException, URIValidationException, ServiceException {
        when(eacRequestTransformer.clientToApi(eacRequest)).thenReturn(eacRequestApi);
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler
                .createAuthCode(EMERGENCY_AUTH_CODE_REQUEST_URI, eacRequestApi))
                .thenReturn(privateEACRequestCreate);
        when(privateEACRequestCreate.execute()).thenReturn(eacRequestApiResponse);
        when(eacRequestApiResponse.getData()).thenReturn(eacRequestApi);
        when(eacRequestTransformer.apiToClient(eacRequestApi)).thenReturn(eacRequest);

        EACRequest result = eacService.createAuthCodeRequest(eacRequest);
        assertEquals(eacRequest, result);
    }

    @Test
    @DisplayName("Test post request to create an EmergencyAuthCodeRequest is unsuccessful - ApiErrorResponseException")
    void testPostEACRequest_Unsuccessful_ApiErrorResponseException()
            throws ApiErrorResponseException, URIValidationException {
        when(eacRequestTransformer.clientToApi(eacRequest)).thenReturn(eacRequestApi);
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler.createAuthCode(EMERGENCY_AUTH_CODE_REQUEST_URI, eacRequestApi))
                .thenReturn(privateEACRequestCreate);
        when(privateEACRequestCreate.execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ServiceException.class, () -> eacService.createAuthCodeRequest(eacRequest));
    }

    @Test
    @DisplayName("Test post request to create an EmergencyAuthCodeRequest is unsuccessful - URIValidationException")
    void testPostEACRequest_Unsuccessful_URIValidationException()
            throws ApiErrorResponseException, URIValidationException {
        when(eacRequestTransformer.clientToApi(eacRequest)).thenReturn(eacRequestApi);
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler.createAuthCode(EMERGENCY_AUTH_CODE_REQUEST_URI, eacRequestApi))
                .thenReturn(privateEACRequestCreate);
        when(privateEACRequestCreate.execute()).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () -> eacService.createAuthCodeRequest(eacRequest));
    }

    @Test
    @DisplayName("Test get request for list of officers is successful")
    void testGetOfficerList_Successful()
            throws ApiErrorResponseException, URIValidationException, ServiceException {
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler.listOfficers(LIST_OFFICERS_URI)).thenReturn(privateEACOfficerList);
        when(privateEACOfficerList.execute()).thenReturn(eacOfficersListApiResponse);
        when(eacOfficersListApiResponse.getData()).thenReturn(eacOfficersListApi);
        when(eacOfficerListTransformer.apiToClient(eacOfficersListApi)).thenReturn(eacOfficerList);

        EACOfficerList result = eacService.getListOfOfficers(COMPANY_NUMBER);
        assertEquals(eacOfficerList, result);
    }

    @Test
    @DisplayName("Test get request for list of officers is unsuccessful - ApiErrorResponseException")
    void testGetOfficerList_Unsuccessful_ApiErrorResponseException()
            throws ApiErrorResponseException, URIValidationException {
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler.listOfficers(LIST_OFFICERS_URI)).thenReturn(privateEACOfficerList);
        when(privateEACOfficerList.execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ServiceException.class, () -> eacService.getListOfOfficers(COMPANY_NUMBER));
    }

    @Test
    @DisplayName("Test get request for list of officers is unsuccessful - URIValidationException")
    void testGetOfficerList_Unsuccessful_URIValidationException()
            throws ApiErrorResponseException, URIValidationException {
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler.listOfficers(LIST_OFFICERS_URI)).thenReturn(privateEACOfficerList);
        when(privateEACOfficerList.execute()).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () -> eacService.getListOfOfficers(COMPANY_NUMBER));
    }

    @Test
    @DisplayName("Test get request for an emergency auth code request is successful")
    void testGetEACRequest_Successful()
            throws ApiErrorResponseException, URIValidationException, ServiceException {
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler.getAuthCode(GET_EMERGENCY_AUTH_CODE_REQUEST_URI)).thenReturn(privateEACRequestGet);
        when(privateEACRequestGet.execute()).thenReturn(eacRequestApiResponse);
        when(eacRequestApiResponse.getData()).thenReturn(eacRequestApi);
        when(eacRequestTransformer.apiToClient(eacRequestApi)).thenReturn(eacRequest);

        EACRequest result = eacService.getEACRequest(EAC_REQUEST_ID);
        assertEquals(eacRequest, result);
    }

    @Test
    @DisplayName("Test get request for an emergency auth code request is unsuccessful - ApiErrorResponseException")
    void testGetEACRequest_Unsuccessful_ApiErrorResponseException()
            throws ApiErrorResponseException, URIValidationException {
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler.getAuthCode(GET_EMERGENCY_AUTH_CODE_REQUEST_URI)).thenReturn(privateEACRequestGet);
        when(privateEACRequestGet.execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ServiceException.class, () -> eacService.getEACRequest(EAC_REQUEST_ID));
    }

    @Test
    @DisplayName("Test get request for an emergency auth code request is unsuccessful - URIValidationException")
    void testGetEACRequest_Unsuccessful_URIValidationException()
            throws ApiErrorResponseException, URIValidationException {
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler.getAuthCode(GET_EMERGENCY_AUTH_CODE_REQUEST_URI)).thenReturn(privateEACRequestGet);
        when(privateEACRequestGet.execute()).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () -> eacService.getEACRequest(EAC_REQUEST_ID));
    }
}
