package uk.gov.companieshouse.web.emergencyauthcodeweb.model.company;

public class CompanyDetail {

    private String companyName;

    private String companyNumber;

    private String companyStatus;

    private String dateOfCreation;

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
}