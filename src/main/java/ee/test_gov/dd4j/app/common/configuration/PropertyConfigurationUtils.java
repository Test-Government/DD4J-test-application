package ee.test_gov.dd4j.app.common.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertyConfigurationUtils {

    public static BeanInfo getBeanInfo(Class<?> beanType) {
        try {
            return Introspector.getBeanInfo(beanType);
        } catch (IntrospectionException e) {
            throw new IllegalStateException(String.format(
                    "Failed to inspect type '%s'",
                    beanType
            ), e);
        }
    }

    public static PropertyDescriptor findProperty(BeanInfo beanInfo, String propertyName) {
        return Stream.of(beanInfo.getPropertyDescriptors())
                .filter(bi -> StringUtils.equals(bi.getName(), propertyName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format(
                        "Cannot find property '%s' in type '%s'",
                        propertyName,
                        beanInfo.getBeanDescriptor().getBeanClass()
                )));
    }

    public static boolean isPropertyAssignable(PropertyDescriptor propertyDescriptor, Class<?> typeToAssign) {
        return propertyDescriptor.getPropertyType().isAssignableFrom(typeToAssign);
    }

    public static void assignProperty(PropertyDescriptor propertyDescriptor, Object bean, Object propertyToAssign) {
        if (!isPropertyAssignable(propertyDescriptor, propertyToAssign.getClass())) {
            throw new IllegalArgumentException(String.format(
                    "Cannot assign type '%s' to property '%s'",
                    propertyToAssign.getClass(),
                    propertyDescriptor.getName()
            ));
        }
        Method assignMethod = propertyDescriptor.getWriteMethod();
        if (!assignMethod.canAccess(bean)) {
            assignMethod.setAccessible(true);
        }
        try {
            assignMethod.invoke(bean, propertyToAssign);
        } catch (Exception e) {
            throw new IllegalStateException(String.format(
                    "Failed to call '%s' method on '%s' with parameter '%s'",
                    assignMethod.getName(),
                    bean,
                    propertyToAssign
            ));
        }
    }

    public static <T> T createInstance(Class<T> type, Object... constructorParameters) {
        Constructor<T> constructor = getConstructor(type, Stream
                .of(constructorParameters)
                .map(Object::getClass)
                .toArray(Class[]::new)
        );
        if (!constructor.canAccess(null)) {
            constructor.setAccessible(true);
        }
        try {
            return constructor.newInstance(constructorParameters);
        } catch (Exception e) {
            throw new IllegalStateException(String.format(
                    "Failed to create an instance of '%s' with arguments: %s",
                    type,
                    List.of(constructorParameters)
            ));
        }
    }

    private static <T> Constructor<T> getConstructor(Class<T> type, Class<?>... parameterTypes) {
        try {
            return type.getConstructor(parameterTypes);
        } catch (Exception e) {
            throw new IllegalStateException(String.format(
                    "Failed to find constructor of '%s' with parameter types: %s",
                    type,
                    List.of(parameterTypes)
            ));
        }
    }

}
