package uk.gov.companieshouse.web.emergencyauthcodeweb;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;

@Configuration
public class EmergencyAuthCodeWebApplicationConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public EnvironmentReader createEnvironmentReader() {

        return new EnvironmentReaderImpl();
    }
}
