package uk.gov.companieshouse.web.emergencyauthcodeweb.controller.eac.util;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

public class EACTestUtility {

    private EACTestUtility() {
        throw new IllegalAccessError("Utility class");
    }

    public static CompanyProfileApi validCompanyProfile(String ID) {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        companyProfileApi.setCompanyNumber(ID);
        companyProfileApi.setCompanyName("TEST_COMPANY");

        return companyProfileApi;
    }
}