package ee.test_gov.dd4j.app.validation.controller;

import ee.test_gov.dd4j.app.validation.request.ContainerValidationRequest;
import ee.test_gov.dd4j.app.validation.request.ContainerValidationRequestParser;
import ee.test_gov.dd4j.app.validation.response.ContainerValidationResponse;
import ee.test_gov.dd4j.app.validation.response.ContainerValidationResponseBuilder;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.digidoc4j.Container;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ee.test_gov.dd4j.app.common.CommonConstants.STRING_ENABLED;
import static ee.test_gov.dd4j.app.common.CommonConstants.STRING_TRUE;
import static ee.test_gov.dd4j.app.validation.ValidationConstants.VALIDATION_BASE_PATH;
import static ee.test_gov.dd4j.app.validation.ValidationConstants.VALIDATION_PROPERTIES_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = VALIDATION_BASE_PATH)
@ConditionalOnProperty(
        prefix = VALIDATION_PROPERTIES_PREFIX,
        name = STRING_ENABLED,
        havingValue = STRING_TRUE
)
class ContainerValidationController {

    private final @NonNull ContainerValidationRequestParser containerValidationRequestParser;
    private final @NonNull ContainerValidationResponseBuilder containerValidationResponseBuilder;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ContainerValidationResponse requestValidation(@RequestBody @Valid ContainerValidationRequest requestBody) {
        Container container = containerValidationRequestParser.parse(requestBody);
        return containerValidationResponseBuilder.build(container.validate());
    }

}
