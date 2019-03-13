package io.microsamples.cloud.csdemo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Data
public class CustomProperties {
    private String location;
    private String clientId;
    private String secret;
}
