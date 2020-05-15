package uk.gov.companieshouse.web.emergencyauthcodeweb.service.company;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;

public interface CompanyService {

    CompanyProfileApi getCompanyProfile(String companyNumber) throws ServiceException;

    CompanyDetail getCompanyDetail(String companyNumber) throws ServiceException;
}
