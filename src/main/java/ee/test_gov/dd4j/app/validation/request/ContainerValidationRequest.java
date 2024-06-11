package ee.test_gov.dd4j.app.validation.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class ContainerValidationRequest {

    @NotNull
    private byte[] container;

}
