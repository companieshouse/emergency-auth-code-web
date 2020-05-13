package uk.gov.companieshouse.web.emergencyauthcodeweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.interceptor.LoggingInterceptor;

@SpringBootApplication
public class EmergencyAuthCodeWebApplication implements WebMvcConfigurer {

    public static final String APPLICATION_NAME_SPACE = "emergency-auth-code-web";

    private LoggingInterceptor loggingInterceptor;

    @Autowired
    public EmergencyAuthCodeWebApplication(LoggingInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmergencyAuthCodeWebApplication.class, args);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }
}
