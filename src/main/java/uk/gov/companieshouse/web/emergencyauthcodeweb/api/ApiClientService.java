package uk.gov.companieshouse.web.emergencyauthcodeweb.api;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.InternalApiClient;

/**
 * The {@code ApiClientService} interface provides an abstraction that can be
 * used when testing {@code ApiClientManager} static methods, without imposing
 * the use of a test framework that supports mocking of static methods.
 */
public interface ApiClientService {

    ApiClient getApiClient();
    InternalApiClient getInternalApiClient();
}
