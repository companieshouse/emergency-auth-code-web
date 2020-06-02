package uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode;

import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.EACRequest;

public interface EmergencyAuthCodeService {
    EACRequest createAuthCodeRequest(EACRequest eacRequest) throws
            ServiceException;
}
