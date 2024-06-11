package ee.test_gov.dd4j.app.common.configuration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.digidoc4j.Configuration;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ConfigurationImplementationConfigurator {

    private final @NonNull Configuration configuration;

    public void configureImplementations(@NonNull Map<String, Class<?>> implementationMappings) {
        implementationMappings.forEach(this::configureImplementation);
    }

    private void configureImplementation(String name, Class<?> implementationType) {
            BeanInfo beanInfo = PropertyConfigurationUtils.getBeanInfo(configuration.getClass());
            PropertyDescriptor propertyDescriptor = PropertyConfigurationUtils.findProperty(beanInfo, name);

            if (!PropertyConfigurationUtils.isPropertyAssignable(propertyDescriptor, implementationType)) {
                throw new IllegalStateException(String.format(
                        "Implementation type '%s' is not assignable to '%s'",
                        implementationType,
                        propertyDescriptor.getPropertyType()
                ));
            }

            Object implementation = PropertyConfigurationUtils.createInstance(implementationType, configuration);
            PropertyConfigurationUtils.assignProperty(propertyDescriptor, configuration, implementation);

            log.info(
                    "An instance of '{}' was configured as '{}' of DD4J Configuration '{}'",
                    implementationType, name, configuration
            );
    }

}
