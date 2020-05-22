package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.impl;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.CompanyDetailTransformer;

import java.time.format.DateTimeFormatter;

@Component
public class CompanyDetailTransformerImpl implements CompanyDetailTransformer {

    @Override
    public CompanyDetail getCompanyDetail(CompanyProfileApi companyProfile) {

        CompanyDetail companyDetail = new CompanyDetail();

        companyDetail.setCompanyName(companyProfile.getCompanyName());

        companyDetail.setCompanyNumber(companyProfile.getCompanyNumber());

        String companyStatus = companyProfile.getCompanyStatus();
        companyDetail.setCompanyStatus(
                companyStatus.substring(0, 1).toUpperCase() + companyStatus.substring(1));

        companyDetail.setDateOfCreation(companyProfile.getDateOfCreation()
                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));

        return companyDetail;
    }
}
