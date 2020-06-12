package uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class EACOfficer {
    @NotNull(message = "{officer.selectionNotMade}")
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("officer_role")
    private String officerRole;

    @JsonProperty("date_of_birth")
    private EACOfficerDOB dateOfBirth;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("appointed_on")
    private LocalDate appointedOn;

    @JsonProperty("nationality")
    private String nationality;

    @JsonProperty("country_of_residence")
    private String countryOfResidence;

    @JsonProperty("occupation")
    private String occupation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficerRole() {
        return officerRole;
    }

    public void setOfficerRole(String officerRole) {
        this.officerRole = officerRole;
    }

    public EACOfficerDOB getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(EACOfficerDOB dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getAppointedOn() {
        return appointedOn;
    }

    public void setAppointedOn(LocalDate appointedOn) {
        this.appointedOn = appointedOn;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
