package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.company.CompanyDetail;
import uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.company.CompanyDetailTransformer;

@Component
public class CompanyDetailTransformerImpl implements CompanyDetailTransformer {

    @Override
    public CompanyDetail getCompanyDetail(CompanyProfileApi companyProfile) {
        CompanyDetail companyDetail = new CompanyDetail();
        companyDetail.setCompanyName(companyProfile.getCompanyName());
        companyDetail.setCompanyNumber(companyProfile.getCompanyNumber());

        String companyStatus = companyProfile.getCompanyStatus();
        companyDetail.setCompanyStatus(formatCompanyStatus(companyStatus));

        // Incorporation date isn't always present, so set default and handle accordingly.
        if(companyProfile.getDateOfCreation() != null) {
            LocalDate dateOfCreation = companyProfile.getDateOfCreation();
            companyDetail.setDateOfCreation(dateOfCreation.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        }

        companyDetail.setType(companyProfile.getType());

        return companyDetail;
    }

    private String formatCompanyStatus(String companyStatus) {
        Map<String, String> statuses = new HashMap<>();
        statuses.put("active", "Active");
        statuses.put("dissolved", "Dissolved");
        statuses.put("liquidation", "Liquidation");
        statuses.put("receivership", "Receiver Action");
        statuses.put("converted-closed", "Converted / Closed");
        statuses.put("voluntary-arrangement", "Voluntary Arrangement");
        statuses.put("insolvency-proceedings", "Insolvency Proceedings");
        statuses.put("administration", "In Administration");
        statuses.put("open", "Open");
        statuses.put("closed", "Closed");

        if(statuses.containsKey(companyStatus)){
            return statuses.get(companyStatus);
        }

        return companyStatus;
    }

}
