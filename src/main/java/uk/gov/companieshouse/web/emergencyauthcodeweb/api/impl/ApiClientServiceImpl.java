package uk.gov.companieshouse.web.emergencyauthcodeweb.api.impl;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.sdk.manager.ApiClientManager;
import uk.gov.companieshouse.web.emergencyauthcodeweb.api.ApiClientService;

@Component
public class ApiClientServiceImpl implements ApiClientService {

    @Override
    public ApiClient getApiClient() {
        return ApiClientManager.getSDK();
    }

    @Override
    public InternalApiClient getInternalApiClient() {
        return ApiClientManager.getPrivateSDK();
    }
}
