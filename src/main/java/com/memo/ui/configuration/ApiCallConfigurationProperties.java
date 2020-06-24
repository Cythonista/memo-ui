package com.memo.ui.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "workshop.api.call")
public class ApiCallConfigurationProperties {

    private final String host;
    private final int port;
    private final String pathName;

    public ApiCallConfigurationProperties(String host, int port, String pathName) {
        this.host = host;
        this.port = port;
        this.pathName = pathName;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getPathName() {
        return this.pathName;
    }

}
