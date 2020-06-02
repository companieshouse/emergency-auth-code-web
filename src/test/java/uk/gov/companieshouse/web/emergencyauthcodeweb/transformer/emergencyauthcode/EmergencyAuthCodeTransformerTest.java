package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.emergencyauthcode.authcoderequest.PrivateEACRequestApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.EACRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EmergencyAuthCodeTransformerTest {

    private static final String KIND = "kind";
    private static final String STATUS = "status";
    private static final String COMPANY_NUMBER = "companyNumber";
    private static final String COMPANY_NAME = "companyName";
    private static final String USER_ID = "userId";
    private static final String USER_EMAIL = "userEmail";
    private static final String OFFICER_ID = "officerId";
    private static final String OFFICER_URA_ID = "officerUraId";
    private static final String OFFICER_NAME = "officerName";
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
    private static final LocalDateTime SUBMITTED_AT = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
    private static final String ETAG = "etag";
    private static final String SELF_KEY = "self";
    private static final String SELF_VALUE = "selfLink";
    private  Map<String, String> links = new HashMap<>();

    private EACRequest eacRequest;
    private PrivateEACRequestApi eacRequestApi;

    private EmergencyAuthCodeTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = Mappers.getMapper(EmergencyAuthCodeTransformer.class);
        links.put(SELF_KEY, SELF_VALUE);

        eacRequest = new EACRequest();
        eacRequest.setKind(KIND);
        eacRequest.setStatus(STATUS);
        eacRequest.setCompanyNumber(COMPANY_NUMBER);
        eacRequest.setCompanyName(COMPANY_NAME);
        eacRequest.setUserId(USER_ID);
        eacRequest.setUserEmail(USER_EMAIL);
        eacRequest.setOfficerId(OFFICER_ID);
        eacRequest.setOfficerUraId(OFFICER_URA_ID);
        eacRequest.setOfficerName(OFFICER_NAME);
        eacRequest.setCreatedAt(CREATED_AT);
        eacRequest.setSubmittedAt(SUBMITTED_AT);
        eacRequest.setEtag(ETAG);
        eacRequest.setLinks(links);

        eacRequestApi = new PrivateEACRequestApi();
        eacRequestApi.setKind(KIND);
        eacRequestApi.setStatus(STATUS);
        eacRequestApi.setCompanyNumber(COMPANY_NUMBER);
        eacRequestApi.setCompanyName(COMPANY_NAME);
        eacRequestApi.setUserId(USER_ID);
        eacRequestApi.setUserEmail(USER_EMAIL);
        eacRequestApi.setOfficerId(OFFICER_ID);
        eacRequestApi.setOfficerUraId(OFFICER_URA_ID);
        eacRequestApi.setOfficerName(OFFICER_NAME);
        eacRequestApi.setCreatedAt(CREATED_AT);
        eacRequestApi.setSubmittedAt(SUBMITTED_AT);
        eacRequestApi.setEtag(ETAG);
        eacRequestApi.setLinks(links);
    }

    @Test
    @DisplayName("Test mapping of EACRequest to PrivateEACRequestApi")
    void validateClientToApi() {
        PrivateEACRequestApi result = transformer.clientToApi(eacRequest);

        assertEquals(eacRequest.getKind(), result.getKind());
        assertEquals(eacRequest.getStatus(), result.getStatus());
        assertEquals(eacRequest.getCompanyNumber(), result.getCompanyNumber());
        assertEquals(eacRequest.getCompanyName(), result.getCompanyName());
        assertEquals(eacRequest.getUserId(), result.getUserId());
        assertEquals(eacRequest.getUserEmail(), result.getUserEmail());
        assertEquals(eacRequest.getOfficerId(), result.getOfficerId());
        assertEquals(eacRequest.getOfficerUraId(), result.getOfficerUraId());
        assertEquals(eacRequest.getOfficerName(), result.getOfficerName());
        assertEquals(eacRequest.getCreatedAt(), result.getCreatedAt());
        assertEquals(eacRequest.getSubmittedAt(), result.getSubmittedAt());
        assertEquals(eacRequest.getEtag(), result.getEtag());
        assertEquals(eacRequest.getLinks(), result.getLinks());
    }

    @Test
    @DisplayName("Test mapping of PrivateEACRequestApi to EACRequest")
    void validateApiToClient() {
        EACRequest result = transformer.apiToClient(eacRequestApi);

        assertEquals(eacRequestApi.getKind(), result.getKind());
        assertEquals(eacRequestApi.getStatus(), result.getStatus());
        assertEquals(eacRequestApi.getCompanyNumber(), result.getCompanyNumber());
        assertEquals(eacRequestApi.getCompanyName(), result.getCompanyName());
        assertEquals(eacRequestApi.getUserId(), result.getUserId());
        assertEquals(eacRequestApi.getUserEmail(), result.getUserEmail());
        assertEquals(eacRequestApi.getOfficerId(), result.getOfficerId());
        assertEquals(eacRequestApi.getOfficerUraId(), result.getOfficerUraId());
        assertEquals(eacRequestApi.getOfficerName(), result.getOfficerName());
        assertEquals(eacRequestApi.getCreatedAt(), result.getCreatedAt());
        assertEquals(eacRequestApi.getSubmittedAt(), result.getSubmittedAt());
        assertEquals(eacRequestApi.getEtag(), result.getEtag());
        assertEquals(eacRequestApi.getLinks(), result.getLinks());
    }
}
