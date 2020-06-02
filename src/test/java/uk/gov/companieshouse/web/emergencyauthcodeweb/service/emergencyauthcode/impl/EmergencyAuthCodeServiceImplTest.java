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
import uk.gov.companieshouse.api.handler.emergencyauthcode.request.PrivateEACRequestCreate;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.emergencyauthcode.authcoderequest.PrivateEACRequestApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.api.ApiClientService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode.EmergencyAuthCodeTransformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmergencyAuthCodeServiceImplTest {

    private static final String EMERGENCY_AUTH_CODE_REQUEST_URI = "/emergency-auth-code-service/auth-code-requests";

    private EACRequest eacRequest = new EACRequest();
    private PrivateEACRequestApi eacRequestApi = new PrivateEACRequestApi();

    @Mock
    private InternalApiClient internalApiClient;

    @Mock
    private PrivateEACResourceHandler privateEACResourceHandler;

    @Mock
    private PrivateEACRequestCreate privateEACRequestCreate;

    @Mock
    private ApiResponse<PrivateEACRequestApi> apiResponse;

    @Mock
    private ApiClientService apiClientService;

    @Mock
    private EmergencyAuthCodeTransformer emergencyAuthCodeTransformer;

    @InjectMocks
    private EmergencyAuthCodeServiceImpl eacService;

    @Test
    @DisplayName("Test post request to create an EmergencyAuthCodeRequest is successful")
    void testEACPostRequest_Successful()
            throws ApiErrorResponseException, URIValidationException, ServiceException {
        when(emergencyAuthCodeTransformer.clientToApi(eacRequest)).thenReturn(eacRequestApi);
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler
                .createAuthCode(EMERGENCY_AUTH_CODE_REQUEST_URI, eacRequestApi))
                .thenReturn(privateEACRequestCreate);
        when(privateEACRequestCreate.execute()).thenReturn(apiResponse);
        when(apiResponse.getData()).thenReturn(eacRequestApi);
        when(emergencyAuthCodeTransformer.apiToClient(eacRequestApi)).thenReturn(eacRequest);

        EACRequest result = eacService.createAuthCodeRequest(eacRequest);
        assertEquals(eacRequest, result);
    }

    @Test
    @DisplayName("Test post request to create an EmergencyAuthCodeRequest is unsuccessful - ApiErrorResponseException")
    void testPostEACRequest_Unsuccessful_ApiErrorResponseException()
            throws ApiErrorResponseException, URIValidationException {
        when(emergencyAuthCodeTransformer.clientToApi(eacRequest)).thenReturn(eacRequestApi);
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
            throws ApiErrorResponseException, URIValidationException, ServiceException {
        when(emergencyAuthCodeTransformer.clientToApi(eacRequest)).thenReturn(eacRequestApi);
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.privateEacResourceHandler()).thenReturn(privateEACResourceHandler);
        when(privateEACResourceHandler.createAuthCode(EMERGENCY_AUTH_CODE_REQUEST_URI, eacRequestApi))
                .thenReturn(privateEACRequestCreate);
        when(privateEACRequestCreate.execute()).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () -> eacService.createAuthCodeRequest(eacRequest));
    }
}
