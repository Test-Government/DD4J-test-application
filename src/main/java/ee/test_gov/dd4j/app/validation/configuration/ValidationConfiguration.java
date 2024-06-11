package ee.test_gov.dd4j.app.validation.configuration;

import ee.test_gov.dd4j.app.common.configuration.ConfigurationImplementationConfigurator;
import ee.test_gov.dd4j.app.common.parser.ConfiguredContainerParser;
import ee.test_gov.dd4j.app.common.parser.ContainerParser;
import ee.test_gov.dd4j.app.validation.configuration.provider.ValidationConfigurationProvider;
import ee.test_gov.dd4j.app.validation.configuration.provider.ValidationThreadExecutorConfigurationProvider;
import ee.test_gov.dd4j.app.validation.impl.SimpleContainerValidationRequestParser;
import ee.test_gov.dd4j.app.validation.impl.SimpleContainerValidationResponseBuilder;
import ee.test_gov.dd4j.app.validation.request.ContainerValidationRequestParser;
import ee.test_gov.dd4j.app.validation.response.ContainerValidationResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static ee.test_gov.dd4j.app.common.CommonConstants.STRING_ENABLED;
import static ee.test_gov.dd4j.app.common.CommonConstants.STRING_TRUE;
import static ee.test_gov.dd4j.app.validation.ValidationConstants.VALIDATION_PROPERTIES_PREFIX;

@Configuration
@ConditionalOnProperty(
        prefix = VALIDATION_PROPERTIES_PREFIX,
        name = STRING_ENABLED,
        havingValue = STRING_TRUE
)
@Slf4j
class ValidationConfiguration {

    @Bean
    ExecutorService dd4jValidationTaskExecutor(
            ValidationThreadExecutorConfigurationProvider configurationProvider
    ) {
        log.info(
                "Creating DD4J validation task executor with core pool size {}, maximum pool size {}, and keep alive time {}",
                configurationProvider.getCorePoolSize(),
                configurationProvider.getMaximumPoolSize(),
                configurationProvider.getKeepAliveTime()
        );
        return new ThreadPoolExecutor(
                configurationProvider.getCorePoolSize(),
                configurationProvider.getMaximumPoolSize(),
                configurationProvider.getKeepAliveTime().toSeconds(),
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );
    }

    @Bean
    org.digidoc4j.Configuration dd4jValidationConfiguration(
            ValidationConfigurationProvider configurationProvider,
            ExecutorService dd4jValidationTaskExecutor
    ) {
        log.info("Initializing DD4J configuration for validation in {} mode", configurationProvider.getMode());
        org.digidoc4j.Configuration configuration = org.digidoc4j.Configuration.of(configurationProvider.getMode());
        configuration.setThreadExecutor(dd4jValidationTaskExecutor);

        Optional
                .ofNullable(configurationProvider.getImplementations())
                .ifPresent(f -> new ConfigurationImplementationConfigurator(configuration).configureImplementations(f));

        configuration.getTSL().refresh();

        return configuration;
    }

    @Bean
    ContainerParser validationContainerParser(org.digidoc4j.Configuration dd4jValidationConfiguration) {
        return new ConfiguredContainerParser(dd4jValidationConfiguration);
    }

    @Bean
    ContainerValidationRequestParser containerValidationRequestParser(ContainerParser validationContainerParser) {
        return new SimpleContainerValidationRequestParser(validationContainerParser);
    }

    @Bean
    ContainerValidationResponseBuilder containerValidationResponseBuilder() {
        return new SimpleContainerValidationResponseBuilder();
    }

}
