package uk.gov.companieshouse.web.emergencyauthcodeweb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import io.opentelemetry.api.OpenTelemetry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class OpenTelemetryAppenderInitializerTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withBean(OpenTelemetry.class, OpenTelemetry::noop)
            .withUserConfiguration(OpenTelemetryAppenderInitializer.class);

    @Test
    void afterPropertiesSetDelegatesToInstallAppender() {
        OpenTelemetry openTelemetry = OpenTelemetry.noop();
        OpenTelemetryAppenderInitializer initializer =
                spy(new OpenTelemetryAppenderInitializer(openTelemetry));

        // Stub out the seam so the real static
        // OpenTelemetryAppender.install(...) call, and its global
        // JVM logging side effect, is never invoked in this test.
        doNothing().when(initializer).installAppender(openTelemetry);

        initializer.afterPropertiesSet();

        verify(initializer).installAppender(openTelemetry);
    }

    @Test
    void initializerBeanAbsentByDefault() {
        contextRunner.run(context ->
                assertEquals(0, context.getBeansOfType(OpenTelemetryAppenderInitializer.class).size()));
    }

    @Test
    void initializerBeanAbsentWhenDisabled() {
        contextRunner.withPropertyValues("management.opentelemetry.enabled=false").run(context ->
                assertEquals(0, context.getBeansOfType(OpenTelemetryAppenderInitializer.class).size()));
    }

    @Test
    void initializerBeanPresentWhenEnabled() {
        contextRunner.withPropertyValues("management.opentelemetry.enabled=true").run(context ->
                assertEquals(1, context.getBeansOfType(OpenTelemetryAppenderInitializer.class).size()));
    }
}
