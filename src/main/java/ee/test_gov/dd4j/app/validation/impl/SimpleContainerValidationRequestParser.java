package ee.test_gov.dd4j.app.validation.impl;

import ee.test_gov.dd4j.app.common.exception.Dd4jApplicationException;
import ee.test_gov.dd4j.app.common.parser.ContainerParser;
import ee.test_gov.dd4j.app.validation.request.ContainerValidationRequest;
import ee.test_gov.dd4j.app.validation.request.ContainerValidationRequestParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.digidoc4j.Container;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class SimpleContainerValidationRequestParser implements ContainerValidationRequestParser {

    private final @NonNull ContainerParser containerParser;

    @Override
    public Container parse(ContainerValidationRequest containerValidationRequest) {
        byte[] containerBytes = containerValidationRequest.getContainer();

        try (InputStream inputStream = new ByteArrayInputStream(containerBytes)) {
            return containerParser.parse(inputStream);
        } catch (IOException e) {
            throw new Dd4jApplicationException("Failed to feed container parser", e);
        }
    }

}
