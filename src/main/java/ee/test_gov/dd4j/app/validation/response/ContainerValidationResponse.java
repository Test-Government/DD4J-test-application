package ee.test_gov.dd4j.app.validation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContainerValidationResponse {

    private Boolean valid;

    private List<Dd4jExceptionEntity> containerErrors;
    private List<Dd4jExceptionEntity> containerWarnings;

    private List<Dd4jExceptionEntity> errors;
    private List<Dd4jExceptionEntity> warnings;

    private byte[] validationReport;

}
