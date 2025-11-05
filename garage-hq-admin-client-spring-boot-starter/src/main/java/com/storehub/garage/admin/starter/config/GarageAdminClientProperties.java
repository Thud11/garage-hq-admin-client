package com.storehub.garage.admin.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "garage.admin")
public class GarageAdminClientProperties {

    private String url;
    private String masterToken;
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getMasterToken() { return masterToken; }
    public void setMasterToken(String masterToken) { this.masterToken = masterToken; }
}