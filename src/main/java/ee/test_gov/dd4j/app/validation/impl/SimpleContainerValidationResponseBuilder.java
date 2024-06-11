package ee.test_gov.dd4j.app.validation.impl;

import ee.test_gov.dd4j.app.validation.response.ContainerValidationResponse;
import ee.test_gov.dd4j.app.validation.response.ContainerValidationResponseBuilder;
import ee.test_gov.dd4j.app.validation.response.Dd4jExceptionEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.digidoc4j.ContainerValidationResult;
import org.digidoc4j.exceptions.DigiDoc4JException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class SimpleContainerValidationResponseBuilder implements ContainerValidationResponseBuilder {

    @Override
    public ContainerValidationResponse build(ContainerValidationResult containerValidationResult) {
        ContainerValidationResponse containerValidationResponse = new ContainerValidationResponse();
        containerValidationResponse.setValid(containerValidationResult.isValid());

        containerValidationResponse.setContainerErrors(exceptionsToEntities(containerValidationResult.getContainerErrors()));
        containerValidationResponse.setContainerWarnings(exceptionsToEntities(containerValidationResult.getContainerWarnings()));

        containerValidationResponse.setErrors(exceptionsToEntities(containerValidationResult.getErrors()));
        containerValidationResponse.setWarnings(exceptionsToEntities(containerValidationResult.getWarnings()));

        containerValidationResponse.setValidationReport(stringToUtf8Bytes(containerValidationResult.getReport()));

        return containerValidationResponse;
    }

    private static List<Dd4jExceptionEntity> exceptionsToEntities(List<DigiDoc4JException> exceptions) {
        if (CollectionUtils.isEmpty(exceptions)) {
            return null;
        }
        return exceptions.stream()
                .filter(Objects::nonNull)
                .map(SimpleContainerValidationResponseBuilder::exceptionToEntity)
                .toList();
    }

    private static Dd4jExceptionEntity exceptionToEntity(DigiDoc4JException exception) {
        Dd4jExceptionEntity exceptionEntity = new Dd4jExceptionEntity();

        exceptionEntity.setMessage(exception.getMessage());
        exceptionEntity.setSignatureId(exception.getSignatureId());
        if (exception.getErrorCode() != 0) {
            exceptionEntity.setErrorCode(exception.getErrorCode());
        }

        return exceptionEntity;
    }

    private static byte[] stringToUtf8Bytes(String string) {
        if (StringUtils.isNotEmpty(string)) {
            return string.getBytes(StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }

}
