package uk.gov.companieshouse.web.emergencyauthcodeweb.service.emergencyauthcode;

import uk.gov.companieshouse.web.emergencyauthcodeweb.exception.ServiceException;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerList;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;

public interface EmergencyAuthCodeService {
    EACRequest createAuthCodeRequest(EACRequest eacRequest) throws
            ServiceException;

    EACOfficerList getListOfOfficers(String companyNumber) throws ServiceException;

    EACRequest getEACRequest(String requestId) throws ServiceException;
}
