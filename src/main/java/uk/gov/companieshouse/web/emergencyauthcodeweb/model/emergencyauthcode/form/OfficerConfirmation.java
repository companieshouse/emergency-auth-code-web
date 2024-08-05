package uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.form;

import jakarta.validation.constraints.AssertTrue;

public class OfficerConfirmation {
    @AssertTrue(message = "{officer.confirmationNotMade}")
    private boolean confirm;

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
}
