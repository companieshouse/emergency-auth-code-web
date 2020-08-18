package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.handler.company.request.CompanyGet;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.CompanyDetailTransformer;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyDetailTransformerImplTest {

    @Mock
    private CompanyGet companyGet;

    @Mock
    private ApiResponse<CompanyProfileApi> responseWithData;

    @Mock
    private CompanyDetail companyDetail;

    private CompanyDetailTransformer companyDetailTransformer = new CompanyDetailTransformerImpl();

    private static final String COMPANY_NAME = "company";

    private static final String COMPANY_NUMBER = "number";

    private static final String INVALID_COMPANY_STATUS = "status";

    private static final String ACTIVE_COMPANY_STATUS = "active";
    private static final String FORMATTED_ACTIVE_COMPANY_STATUS = "Active";

    private static final LocalDate DATE_OF_CREATION = LocalDate.of(2000, 01, 01);

    private static final String COMPANY_TYPE = "company-type";

    private CompanyProfileApi createMockCompanyProfileApi(String companyName, String companyNumber, String companyStatus, LocalDate dateOfCreation, String companyType) {

        CompanyProfileApi companyProfile = new CompanyProfileApi();

        companyProfile.setCompanyName(companyName);
        companyProfile.setCompanyNumber(companyNumber);
        companyProfile.setCompanyStatus(companyStatus);
        companyProfile.setDateOfCreation(dateOfCreation);
        companyProfile.setType(companyType);

        return companyProfile;
    }

    @Test
    @DisplayName("Get Company Detail - All fields Populated Path")
    void getCompanyDetailAllPopulated() {

        CompanyDetail companyDetailReturned = companyDetailTransformer.getCompanyDetail(createMockCompanyProfileApi(COMPANY_NAME, COMPANY_NUMBER, INVALID_COMPANY_STATUS, DATE_OF_CREATION, COMPANY_TYPE));

        assertEquals(COMPANY_NAME, companyDetailReturned.getCompanyName());
        assertEquals(COMPANY_NUMBER, companyDetailReturned.getCompanyNumber());
        assertEquals(INVALID_COMPANY_STATUS, companyDetailReturned.getCompanyStatus());
        assertEquals(DATE_OF_CREATION.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")), companyDetailReturned.getDateOfCreation());
        assertEquals(COMPANY_TYPE, companyDetailReturned.getType());
    }

    @Test
    @DisplayName("Get Company Detail - Active status")
    void getCompanyDetailActiveStatus() {

        CompanyDetail companyDetailReturned = companyDetailTransformer.getCompanyDetail(createMockCompanyProfileApi(COMPANY_NAME, COMPANY_NUMBER, ACTIVE_COMPANY_STATUS, DATE_OF_CREATION, COMPANY_TYPE));


        assertEquals(COMPANY_NAME, companyDetailReturned.getCompanyName());
        assertEquals(COMPANY_NUMBER, companyDetailReturned.getCompanyNumber());
        assertEquals(DATE_OF_CREATION.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")), companyDetailReturned.getDateOfCreation());
        assertEquals(COMPANY_TYPE, companyDetailReturned.getType());
        assertEquals(FORMATTED_ACTIVE_COMPANY_STATUS, companyDetailReturned.getCompanyStatus());
    }
}
