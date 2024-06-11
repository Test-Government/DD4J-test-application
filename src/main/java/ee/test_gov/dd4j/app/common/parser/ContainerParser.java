package ee.test_gov.dd4j.app.common.parser;

import org.digidoc4j.Container;

import java.io.InputStream;

@FunctionalInterface
public interface ContainerParser {

    Container parse(InputStream inputStream);

}
