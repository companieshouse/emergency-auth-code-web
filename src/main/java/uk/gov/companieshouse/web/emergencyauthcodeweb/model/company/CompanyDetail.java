package uk.gov.companieshouse.web.emergencyauthcodeweb.model.company;

import java.util.Optional;
import javax.validation.constraints.NotNull;

public class CompanyDetail {

    private String companyName;

    @NotNull
    private String companyNumber;

    private String companyStatus;

    private String dateOfCreation;

    private String type;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyStatus() { return companyStatus; }

    public void setCompanyStatus(String companyStatus) { this.companyStatus = companyStatus; }

    public String getDateOfCreation() { return dateOfCreation; }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
