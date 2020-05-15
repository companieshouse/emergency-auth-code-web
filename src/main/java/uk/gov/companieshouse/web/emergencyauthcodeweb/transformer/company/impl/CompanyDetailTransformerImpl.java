package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.impl;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.CompanyDetailTransformer;

@Component
public class CompanyDetailTransformerImpl implements CompanyDetailTransformer {

    @Override
    public CompanyDetail getCompanyDetail(CompanyProfileApi companyProfile) {

        CompanyDetail companyDetail = new CompanyDetail();

        companyDetail.setCompanyName(companyProfile.getCompanyName());
        companyDetail.setCompanyNumber(companyProfile.getCompanyNumber());

        return companyDetail;
    }
}
