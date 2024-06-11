package ee.test_gov.dd4j.app.validation.configuration.provider;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

import static ee.test_gov.dd4j.app.common.CommonConstants.STRING_ENABLED;
import static ee.test_gov.dd4j.app.common.CommonConstants.STRING_TRUE;
import static ee.test_gov.dd4j.app.validation.ValidationConstants.VALIDATION_CONFIGURATION_PROPERTIES_PREFIX;
import static ee.test_gov.dd4j.app.validation.ValidationConstants.VALIDATION_PROPERTIES_PREFIX;

@Getter
@Setter(AccessLevel.PACKAGE)
@Validated
@Configuration
@ConditionalOnProperty(
        prefix = VALIDATION_PROPERTIES_PREFIX,
        name = STRING_ENABLED,
        havingValue = STRING_TRUE
)
@ConfigurationProperties(prefix = VALIDATION_CONFIGURATION_PROPERTIES_PREFIX)
public class ValidationConfigurationProvider {

    @NotNull
    private org.digidoc4j.Configuration.Mode mode;

    private Map<@NotBlank String, @NotNull Class<?>> implementations;

}
