package ee.test_gov.dd4j.app.validation.request;

import org.digidoc4j.Container;

@FunctionalInterface
public interface ContainerValidationRequestParser {

    Container parse(ContainerValidationRequest containerValidationRequest);

}
