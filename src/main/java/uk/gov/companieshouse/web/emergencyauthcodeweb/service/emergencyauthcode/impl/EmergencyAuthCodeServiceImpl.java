package uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.emergencyauthcode.authcoderequest.PrivateEACRequestApi;
import uk.gov.companieshouse.api.model.emergencyauthcode.officer.PrivateEACOfficersListApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.api.ApiClientService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerList;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode.EmergencyAuthCodeService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode.officer.EACOfficerListTransformer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode.request.EACRequestTransformer;

@Service
public class EmergencyAuthCodeServiceImpl implements EmergencyAuthCodeService {

    private static final UriTemplate GET_OFFICERS_URI = new UriTemplate("/emergency-auth-code-service/company/{companyNumber}/officers");
    private static final UriTemplate EMERGENCY_AUTH_CODE_REQUEST_URI = new UriTemplate("/emergency-auth-code-service/auth-code-requests");
    private static final UriTemplate GET_EMERGENCY_AUTH_CODE_REQUEST_URI = new UriTemplate("/emergency-auth-code-service/auth-code-requests/{requestId}");

    @Autowired
    private ApiClientService apiClientService;

    @Autowired
    private EACRequestTransformer eacRequestTransformer;

    @Autowired
    private EACOfficerListTransformer eacOfficerListTransformer;

    public EACRequest createAuthCodeRequest(EACRequest eacRequest) throws ServiceException {
        PrivateEACRequestApi privateEACRequestApi = eacRequestTransformer.clientToApi(eacRequest);

        InternalApiClient internalApiClient = apiClientService.getInternalApiClient();
        ApiResponse<PrivateEACRequestApi> apiResponse;
        try {
            String uri = EMERGENCY_AUTH_CODE_REQUEST_URI.toString();
            apiResponse = internalApiClient.privateEacResourceHandler()
                    .createAuthCode(uri, privateEACRequestApi).execute();
            return eacRequestTransformer.apiToClient(apiResponse.getData());
        } catch (ApiErrorResponseException ex) {
            throw new ServiceException("Error creating emergency auth code request", ex);
        } catch (URIValidationException ex) {
            throw new ServiceException("Invalid URI for emergency auth code request", ex);
        }
    }

    public EACOfficerList getListOfOfficers(String companyNumber) throws ServiceException {
        InternalApiClient internalApiClient = apiClientService.getInternalApiClient();
        ApiResponse<PrivateEACOfficersListApi> apiResponse;

        try {
            String uri = GET_OFFICERS_URI.expand(companyNumber).toString();
            apiResponse = internalApiClient.privateEacResourceHandler().listOfficers(uri).execute();
            return eacOfficerListTransformer.apiToClient(apiResponse.getData());
        } catch (ApiErrorResponseException ex) {
            throw new ServiceException("Error retrieving the list of eligible officers", ex);
        } catch (URIValidationException ex) {
            throw new ServiceException("Invalid URI for retrieving list of eligible officers", ex);
        }
    }

    public EACRequest getEACRequest(String requestId) throws ServiceException {
        InternalApiClient internalApiClient = apiClientService.getInternalApiClient();
        ApiResponse<PrivateEACRequestApi> apiResponse;

        try {
            String uri = GET_EMERGENCY_AUTH_CODE_REQUEST_URI.expand(requestId).toString();
            apiResponse = internalApiClient.privateEacResourceHandler().getAuthCode(uri).execute();
            return eacRequestTransformer.apiToClient(apiResponse.getData());
        } catch (ApiErrorResponseException ex) {
            throw new ServiceException("Error retrieving emergency auth code request", ex);
        } catch (URIValidationException ex) {
            throw new ServiceException("Invalid URI for retrieving emergency auth code request", ex);
        }
    }
}
