package uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.form;

import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficer;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ListOfOfficersForm {
    @NotNull(message = "{officer.selectionNotMade}")
    private String id;

    private String companyNumber;

    private List<EACOfficer> eacOfficerList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public List<EACOfficer> getEacOfficerList() {
        return eacOfficerList;
    }

    public void setEacOfficerList(List<EACOfficer> eacOfficerList) {
        this.eacOfficerList = eacOfficerList;
    }
}
