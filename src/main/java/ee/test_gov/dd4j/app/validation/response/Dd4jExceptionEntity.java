package ee.test_gov.dd4j.app.validation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dd4jExceptionEntity {

    private String message;
    private String signatureId;
    private Integer errorCode;

}
