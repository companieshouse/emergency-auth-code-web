package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.emergencyauthcode.authcoderequest.PrivateEACRequestApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.request.EACRequest;

@RequestScope
@Mapper(componentModel = "spring")
public interface EmergencyAuthCodeTransformer {

    @Mapping(source = "eacRequest.kind", target = "kind")
    @Mapping(source = "eacRequest.status", target = "status")
    @Mapping(source = "eacRequest.companyNumber", target = "companyNumber")
    @Mapping(source = "eacRequest.companyName", target = "companyName")
    @Mapping(source = "eacRequest.userId", target = "userId")
    @Mapping(source = "eacRequest.userEmail", target = "userEmail")
    @Mapping(source = "eacRequest.officerId", target = "officerId")
    @Mapping(source = "eacRequest.officerUraId", target = "officerUraId")
    @Mapping(source = "eacRequest.officerName", target = "officerName")
    @Mapping(source = "eacRequest.createdAt", target = "createdAt")
    @Mapping(source = "eacRequest.submittedAt", target = "submittedAt")
    @Mapping(source = "eacRequest.etag", target = "etag")
    @Mapping(source = "eacRequest.links", target = "links")

    EACRequest apiToClient(PrivateEACRequestApi eacRequest);

    @InheritInverseConfiguration
    PrivateEACRequestApi clientToApi(EACRequest eacRequest);
}
