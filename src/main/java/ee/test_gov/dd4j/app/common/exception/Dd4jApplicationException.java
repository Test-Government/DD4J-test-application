package ee.test_gov.dd4j.app.common.exception;

public class Dd4jApplicationException extends RuntimeException {

    public Dd4jApplicationException(String message) {
        super(message);
    }

    public Dd4jApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

}
