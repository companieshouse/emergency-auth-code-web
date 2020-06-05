package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode.officer;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.emergencyauthcode.officer.PrivateEACOfficersListApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerList;

@RequestScope
@Mapper(componentModel = "spring", uses = { EACOfficerTransformer.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EACOfficerListTransformer {

    @Mapping(source = "eacOfficerList.itemsPerPage", target = "itemsPerPage")
    @Mapping(source = "eacOfficerList.startIndex", target = "startIndex")
    @Mapping(source = "eacOfficerList.totalResults", target = "totalResults")
    @Mapping(source = "eacOfficerList.items", target = "items")

    EACOfficerList apiToClient(PrivateEACOfficersListApi eacOfficerList);
}
