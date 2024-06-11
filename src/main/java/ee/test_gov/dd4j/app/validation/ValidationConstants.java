package ee.test_gov.dd4j.app.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static ee.test_gov.dd4j.app.common.CommonConstants.PROPERTIES_INFIX_CONFIGURATION;
import static ee.test_gov.dd4j.app.common.CommonConstants.PROPERTIES_INFIX_THREAD_EXECUTOR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationConstants {

    public static final String VALIDATION_BASE_PATH = "/validate";

    public static final String VALIDATION_PROPERTIES_PREFIX = "validation";

    public static final String VALIDATION_CONFIGURATION_PROPERTIES_PREFIX =
            VALIDATION_PROPERTIES_PREFIX + PROPERTIES_INFIX_CONFIGURATION;

    public static final String VALIDATION_THREAD_EXECUTOR_PROPERTIES_PREFIX =
            VALIDATION_CONFIGURATION_PROPERTIES_PREFIX + PROPERTIES_INFIX_THREAD_EXECUTOR;

}
