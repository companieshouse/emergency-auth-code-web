package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode.officer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.emergencyauthcode.officer.PrivateEACOfficerApi;
import uk.gov.companieshouse.api.model.emergencyauthcode.officer.PrivateEACOfficerDOBApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerDOB;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EACOfficerTransformerTest {
    private static final String ID = "abc123";
    private static final String FULL_NAME = "FIRSTNAME MIDDLENAME LASTNAME";
    private static final String TRANSFORMED_NAME = "LASTNAME, Firstname Middlename";
    private static final String OFFICER_ROLE = "role";
    private static final String MONTH_OF_BIRTH = "month";
    private static final String YEAR_OF_BIRTH = "year";
    private static final LocalDate APPOINTED_ON = LocalDate.of(2020, 1, 1);
    private static final String NATIONALITY = "nationality";
    private static final String COUNTRY_OF_RESIDENCE = "country";
    private static final String OCCUPATION = "job";

    private EACOfficer eacOfficer;
    private EACOfficerDOB eacOfficerDOB;

    private PrivateEACOfficerApi eacOfficerApi;
    private PrivateEACOfficerDOBApi eacOfficerDOBApi;

    private EACOfficerTransformer eacOfficerTransformer;

    @BeforeEach
    void setUp() {
        eacOfficerTransformer = Mappers.getMapper(EACOfficerTransformer.class);

        eacOfficerDOB = new EACOfficerDOB();
        eacOfficerDOB.setMonth(MONTH_OF_BIRTH);
        eacOfficerDOB.setYear(YEAR_OF_BIRTH);

        eacOfficerDOBApi = new PrivateEACOfficerDOBApi();
        eacOfficerDOBApi.setMonth(MONTH_OF_BIRTH);
        eacOfficerDOBApi.setYear(YEAR_OF_BIRTH);

        eacOfficer = new EACOfficer();
        eacOfficer.setId(ID);
        eacOfficer.setName(TRANSFORMED_NAME);
        eacOfficer.setOfficerRole(OFFICER_ROLE);
        eacOfficer.setDateOfBirth(eacOfficerDOB);
        eacOfficer.setAppointedOn(APPOINTED_ON);
        eacOfficer.setNationality(NATIONALITY);
        eacOfficer.setCountryOfResidence(COUNTRY_OF_RESIDENCE);
        eacOfficer.setOccupation(OCCUPATION);

        eacOfficerApi = new PrivateEACOfficerApi();
        eacOfficerApi.setId(ID);
        eacOfficerApi.setName(FULL_NAME);
        eacOfficerApi.setOfficerRole(OFFICER_ROLE);
        eacOfficerApi.setDateOfBirth(eacOfficerDOBApi);
        eacOfficerApi.setAppointedOn(APPOINTED_ON);
        eacOfficerApi.setNationality(NATIONALITY);
        eacOfficerApi.setCountryOfResidence(COUNTRY_OF_RESIDENCE);
        eacOfficerApi.setOccupation(OCCUPATION);
    }

    @Test
    @DisplayName("Test mapping of PrivateEACOfficerApi to EACOfficer")
    void validateApiToClient() {
        EACOfficer result = eacOfficerTransformer.apiToClient(eacOfficerApi);

        assertEquals(eacOfficer.getId(), result.getId());
        assertEquals(eacOfficer.getName(), result.getName());
        assertEquals(eacOfficer.getOfficerRole(), result.getOfficerRole());
        assertEquals(eacOfficer.getDateOfBirth().getMonth(), result.getDateOfBirth().getMonth());
        assertEquals(eacOfficer.getDateOfBirth().getYear(), result.getDateOfBirth().getYear());
        assertEquals(eacOfficer.getAppointedOn(), result.getAppointedOn());
        assertEquals(eacOfficer.getNationality(), result.getNationality());
        assertEquals(eacOfficer.getCountryOfResidence(), result.getCountryOfResidence());
        assertEquals(eacOfficer.getOccupation(), result.getOccupation());
    }
}
