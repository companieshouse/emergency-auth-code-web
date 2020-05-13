package uk.gov.companieshouse.web.emergencyauthcodeweb.annotation;

import uk.gov.companieshouse.web.emergencyauthcodeweb.controller.BaseController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Defines the previous controller in the linear journey to support the 'back' page
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PreviousController {
    Class<? extends BaseController>[] value();
}
