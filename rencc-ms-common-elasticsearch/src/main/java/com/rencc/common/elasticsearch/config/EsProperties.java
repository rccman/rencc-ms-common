package com.rencc.common.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "elastic.cluster")
public class EsProperties {

    private String url;
    /**
     * 集群名（配置不使用）
     */
    private String clusterName;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "EsConfig{" +
                "url='" + url + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
