package uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EACOfficerDOB {
    @JsonProperty("month")
    private String month;

    @JsonProperty("year")
    private String year;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
