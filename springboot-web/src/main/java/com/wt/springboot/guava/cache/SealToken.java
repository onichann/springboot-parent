package com.wt.springboot.guava.cache;

/**
 * @author wutong
 * @date 2019/9/25 16:12
 * PROJECT_NAME sand-demo
 */
//@Configuration
//@ConfigurationProperties(prefix = "seal.token")
public class SealToken {
    private String grantType;
    private String scope;
    private String clientId;
    private String clientSecret;
    private String url;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
