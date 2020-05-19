package uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.api.ApiClientService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;
import uk.gov.companieshouse.web.emergencyauthcodeweb.service.company.CompanyService;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.CompanyDetailTransformer;



@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private ApiClientService apiClientService;

    @Autowired
    private CompanyDetailTransformer companyDetailTransformer;

    private static final UriTemplate GET_COMPANY_URI = new UriTemplate("/company/{companyNumber}");

    @Override
    public CompanyProfileApi getCompanyProfile(String companyNumber) throws ServiceException {

        ApiClient apiClient = apiClientService.getApiClient();

        CompanyProfileApi companyProfileApi;

        String uri = GET_COMPANY_URI.expand(companyNumber).toString();

        try {
            companyProfileApi = apiClient.company().get(uri).execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving company profile", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for company resource", e);
        }

        return companyProfileApi;
    }

    @Override
    public CompanyDetail getCompanyDetail(String companyNumber) throws ServiceException {

        return companyDetailTransformer.getCompanyDetail(getCompanyProfile(companyNumber));
    }
}
