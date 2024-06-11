package ee.test_gov.dd4j.app.common.factory;

import ee.test_gov.dd4j.app.common.dataloader.InMemoryCachingGetDataLoader;
import eu.europa.esig.dss.spi.client.http.DataLoader;
import eu.europa.esig.dss.spi.x509.aia.AIASource;
import eu.europa.esig.dss.spi.x509.aia.DefaultAIASource;
import org.digidoc4j.AIASourceFactory;
import org.digidoc4j.Configuration;
import org.digidoc4j.impl.AiaDataLoaderFactory;

public class InMemoryCachingAiaSourceFactory implements AIASourceFactory {

    private final DataLoader cachingAiaDataLoader;

    public InMemoryCachingAiaSourceFactory(Configuration configuration) {
        DataLoader aiaDataLoader = new AiaDataLoaderFactory(configuration).create();
        cachingAiaDataLoader = new InMemoryCachingGetDataLoader(aiaDataLoader);
    }

    @Override
    public AIASource create() {
        return new DefaultAIASource(cachingAiaDataLoader);
    }

}
