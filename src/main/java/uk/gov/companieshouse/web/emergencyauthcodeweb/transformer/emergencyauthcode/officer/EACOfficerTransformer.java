package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode.officer;

import org.apache.commons.text.WordUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.emergencyauthcode.officer.PrivateEACOfficerApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficer;

@RequestScope
@Mapper(componentModel = "spring")
public interface EACOfficerTransformer {

    @Mapping(source = "eacOfficer.id", target = "id")
    @Mapping(source = "eacOfficer.name", target = "name", qualifiedByName = "formatFullName")
    @Mapping(source = "eacOfficer.officerRole", target = "officerRole")
    @Mapping(source = "eacOfficer.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "eacOfficer.appointedOn", target = "appointedOn")
    @Mapping(source = "eacOfficer.nationality", target = "nationality")
    @Mapping(source = "eacOfficer.countryOfResidence", target = "countryOfResidence")
    @Mapping(source = "eacOfficer.occupation", target = "occupation")

    EACOfficer apiToClient(PrivateEACOfficerApi eacOfficer);

    //Convert FIRSTNAME MIDDLENAME LASTNAME to LASTNAME, Firstname Middlename
    @Named("formatFullName")
    static String formatFullName(String name) {
        if(name.isEmpty()) {
            return "";
        }
        int finalSpaceIndex = name.lastIndexOf(' ');
        String lastName = name.substring(finalSpaceIndex + 1);
        String firstNameAndMiddleName = WordUtils
                .capitalizeFully(name.substring(0, finalSpaceIndex));
        return lastName + ", " + firstNameAndMiddleName;
    }
}
