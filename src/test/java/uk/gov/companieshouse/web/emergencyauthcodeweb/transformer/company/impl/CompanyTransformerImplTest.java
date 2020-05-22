package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.handler.company.request.CompanyGet;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.RegisteredOfficeAddressApi;
import uk.gov.companieshouse.api.model.company.account.CompanyAccountApi;
import uk.gov.companieshouse.api.model.company.account.LastAccountsApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.CompanyDetailTransformer;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyTransformerImplTest {

    @Mock
    private CompanyGet companyGet;

    @Mock
    private ApiResponse<CompanyProfileApi> responseWithData;

    @Mock
    private CompanyDetail companyDetail;

    private CompanyDetailTransformer companyDetailTransformer = new CompanyDetailTransformerImpl();

    private static final String COMPANY_NAME = "company";
    private static final String COMPANY_NUMBER = "number";
    private static final String COMPANY_STATUS = "status";
    private static final LocalDate DATE_OF_CREATION = LocalDate.of(2000, 01, 01);;

    private CompanyProfileApi createMockCompanyProfileApi() {

        CompanyProfileApi companyProfile = new CompanyProfileApi();

        companyProfile.setCompanyName(COMPANY_NAME);
        companyProfile.setCompanyNumber(COMPANY_NUMBER);
        companyProfile.setCompanyStatus(COMPANY_STATUS);
        companyProfile.setDateOfCreation(DATE_OF_CREATION);

        return companyProfile;
    }

    @Test
    @DisplayName("Get Company Detail - All fields Populated Path")
    void getCompanyDetailAllPopulated() {

        CompanyDetail companyDetailReturned = companyDetailTransformer.getCompanyDetail(createMockCompanyProfileApi());

        assertEquals(COMPANY_NAME, companyDetailReturned.getCompanyName());
        assertEquals(COMPANY_NUMBER, companyDetailReturned.getCompanyNumber());
    }
}
