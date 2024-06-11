package ee.test_gov.dd4j.app.common.parser;

import ee.test_gov.dd4j.app.common.exception.ContainerParseException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.digidoc4j.Configuration;
import org.digidoc4j.Container;
import org.digidoc4j.ContainerOpener;

import java.io.InputStream;

@RequiredArgsConstructor
public class ConfiguredContainerParser implements ContainerParser {

    private final @NonNull Configuration configuration;

    @Override
    public Container parse(InputStream inputStream) {
        try {
            return ContainerOpener.open(inputStream, configuration);
        } catch (Exception e) {
            throw new ContainerParseException("Failed to parse container", e);
        }
    }

}
