package ee.test_gov.dd4j.app.common.configuration.provider;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter(AccessLevel.PACKAGE)
public class ThreadPoolExecutorConfigurationProvider {

    @Min(0)
    private int corePoolSize = 0;
    @Min(1)
    private int maximumPoolSize = 1;
    @NotNull
    private Duration keepAliveTime = Duration.ofMinutes(1L);

}
