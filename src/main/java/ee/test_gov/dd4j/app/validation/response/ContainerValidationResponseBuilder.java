package ee.test_gov.dd4j.app.validation.response;

import org.digidoc4j.ContainerValidationResult;

@FunctionalInterface
public interface ContainerValidationResponseBuilder {

    ContainerValidationResponse build(ContainerValidationResult containerValidationResult);

}
