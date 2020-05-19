package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;

public interface CompanyDetailTransformer {

    CompanyDetail getCompanyDetail(CompanyProfileApi companyProfile);
}
