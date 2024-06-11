package ee.test_gov.dd4j.app.common.dataloader;

import eu.europa.esig.dss.spi.client.http.DataLoader;
import eu.europa.esig.dss.spi.exception.DSSDataLoaderMultipleException;
import eu.europa.esig.dss.spi.exception.DSSExternalResourceException;
import eu.europa.esig.dss.utils.Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RequiredArgsConstructor
public class InMemoryCachingGetDataLoader implements DataLoader {

    private final ConcurrentMap<String, byte[]> responseCache = new ConcurrentHashMap<>();
    private final @NonNull DataLoader dataLoader;

    @Override
    public byte[] get(String url) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("Url cannot be blank");
        }

        byte[] response = responseCache.get(url);
        if (response != null) {
            log.debug("Using cached response for URL: {}", url);
            return response.clone();
        }

        response = dataLoader.get(url);
        if (response != null) {
            log.info("Caching fetched response for URL: {}", url);
            responseCache.putIfAbsent(url, response.clone());
        }

        log.debug("Using fetched response for URL: {}", url);
        return response;
    }

    @Override
    public DataAndUrl get(List<String> urlStrings) {
        if (Utils.isCollectionEmpty(urlStrings)) {
            throw new DSSExternalResourceException("Cannot process the GET call. List of URLs is empty!");
        }

        final Map<String, Throwable> exceptions = new HashMap<>(); // store map of exception thrown for urls
        for (String urlString : urlStrings) {
            log.debug("Processing a GET call to URL [{}]...", urlString);
            try {
                final byte[] bytes = get(urlString);
                if (Utils.isArrayEmpty(bytes)) {
                    log.debug("The retrieved content from URL [{}] is empty. Continue with other URLs...", urlString);
                    continue;
                }
                return new DataAndUrl(urlString, bytes);
            } catch (Exception e) {
                log.warn("Cannot obtain data using '{}' : {}", urlString, e.getMessage());
                exceptions.put(urlString, e);
            }
        }
        throw new DSSDataLoaderMultipleException(exceptions);
    }

    @Override
    public byte[] post(String url, byte[] content) {
        throw new UnsupportedOperationException("POST method is not supported");
    }

    @Override
    public void setContentType(String contentType) {
        dataLoader.setContentType(contentType);
    }

}
