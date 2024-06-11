package ee.test_gov.dd4j.app.validation.configuration.provider;

import ee.test_gov.dd4j.app.common.configuration.provider.ThreadPoolExecutorConfigurationProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import static ee.test_gov.dd4j.app.common.CommonConstants.STRING_ENABLED;
import static ee.test_gov.dd4j.app.common.CommonConstants.STRING_TRUE;
import static ee.test_gov.dd4j.app.validation.ValidationConstants.VALIDATION_PROPERTIES_PREFIX;
import static ee.test_gov.dd4j.app.validation.ValidationConstants.VALIDATION_THREAD_EXECUTOR_PROPERTIES_PREFIX;

@Validated
@Configuration
@ConditionalOnProperty(
        prefix = VALIDATION_PROPERTIES_PREFIX,
        name = STRING_ENABLED,
        havingValue = STRING_TRUE
)
@ConfigurationProperties(prefix = VALIDATION_THREAD_EXECUTOR_PROPERTIES_PREFIX)
public class ValidationThreadExecutorConfigurationProvider extends ThreadPoolExecutorConfigurationProvider {
}
