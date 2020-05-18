package uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.company.CompanyResourceHandler;
import uk.gov.companieshouse.api.handler.company.request.CompanyGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.api.ApiClientService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.CompanyService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyServiceImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private ApiClientService apiClientService;

    @Mock
    private CompanyResourceHandler companyResourceHandler;

    @Mock
    private CompanyGet companyGet;

    @Mock
    private ApiResponse<CompanyProfileApi> responseWithData;

    @Mock
    private CompanyProfileApi companyProfile;

    @InjectMocks
    private CompanyService mockCompanyService = new CompanyServiceImpl();

    private static final String COMPANY_NUMBER_WITH_LETTERS = "SE123456";

    private static final String COMPANY_NUMBER_WITH_EIGHT_DIGITS = "12345678";

    private static final String COMPANY_URI = "/company/" + COMPANY_NUMBER_WITH_EIGHT_DIGITS;



    private void initGetCompany() {

        when(apiClientService.getApiClient()).thenReturn(apiClient);

        when(apiClient.company()).thenReturn(companyResourceHandler);

        when(companyResourceHandler.get(COMPANY_URI)).thenReturn(companyGet);
    }

    @Test
    @DisplayName("Get Company Profile - Success Path")
    void getCompanyProfileSuccess() throws ServiceException, ApiErrorResponseException, URIValidationException {

        initGetCompany();

        when(companyGet.execute()).thenReturn(responseWithData);

        when(responseWithData.getData()).thenReturn(companyProfile);

        CompanyProfileApi returnedCompanyProfile = mockCompanyService.getCompanyProfile(COMPANY_NUMBER_WITH_EIGHT_DIGITS);

        assertEquals(companyProfile, returnedCompanyProfile);
    }

    @Test
    @DisplayName("Get Company Profile - Throws ApiErrorResponseException")
    void getCompanyProfileThrowsApiErrorResponseException() throws ApiErrorResponseException, URIValidationException {

        initGetCompany();

        when(companyGet.execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ServiceException.class, () ->
                mockCompanyService.getCompanyProfile(COMPANY_NUMBER_WITH_EIGHT_DIGITS));
    }

    @Test
    @DisplayName("Get Company Profile - Throws URIValidationException")
    void getCompanyProfileThrowsURIValidationException() throws ApiErrorResponseException, URIValidationException {

        initGetCompany();

        when(companyGet.execute()).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () ->
                mockCompanyService.getCompanyProfile(COMPANY_NUMBER_WITH_EIGHT_DIGITS));
    }
}
