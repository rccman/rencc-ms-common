package com.rencc.common.elasticsearch.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(EsProperties.class)
@EnableConfigurationProperties(EsProperties.class)
public class ElasticConfig implements DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ElasticConfig.class);
    private static final int CONNECT_TIME_OUT = 5000;
    private static final int SOCKET_TIME_OUT = 30000;
    private static final int CONNECTION_REQUEST_TIME_OUT = 5000;
    private static final int MAX_CONNECT_NUM = 1000;
    private static final int MAX_CONNECT_PER_ROUTE = 500;
    private RestHighLevelClient restHighLevelClient;
    private final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    @Autowired
    private EsProperties esProperties;


    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient initRestHighLevelClient() {
        if (null != restHighLevelClient) {
            return restHighLevelClient;
        }
        logger.info("msg1=es配置信息,,esProperties=", esProperties.toString());
        RestClientBuilder builder = RestClient.builder(this.getHttpHost("http", esProperties.getUrl()));
        //设置回调
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIME_OUT);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIME_OUT);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
            return requestConfigBuilder;
        });
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
//            httpClientBuilder.setMaxConnTotal(MAX_CONNECT_NUM);
//            httpClientBuilder.setMaxConnPerRoute(MAX_CONNECT_PER_ROUTE);
            if (!StringUtils.isEmpty(esProperties.getUsername()) && !StringUtils.isEmpty(esProperties.getPassword())) {
                if (esProperties.getUsername().length() > 3 && esProperties.getPassword().length() > 3) {
                    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(esProperties.getUsername(), esProperties.getPassword()));
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
            }
            return httpClientBuilder;
        });
        restHighLevelClient = new RestHighLevelClient(builder);
        logger.info("restHighLevelClient初始化完成...");
        return restHighLevelClient;
    }

    /**
     * 销毁 es 连接
     * @author renchaochao
     * @date 2021/2/23 15:16
     */
    @Override
    public void destroy() {
        try {
            if (null != restHighLevelClient) {
                restHighLevelClient.close();
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    /**
     * 获取HttpHost
     * @param schema URL前缀 http / https
     * @return HttpHost[] hostname, port, schema
     * @author renchaochao
     * @date 2021/2/23 15:13
     */
    private HttpHost[] getHttpHost(String schema, String hosts) {
        String[] urlArray = hosts.split(",");
        int nodeSize = urlArray.length;
        HttpHost[] httpHosts = new HttpHost[nodeSize];
        for (int i = 0; i < nodeSize; i++) {
            String[] urlInfo = urlArray[i].split(":");
            httpHosts[i] = new HttpHost(urlInfo[0], Integer.parseInt(urlInfo[1]), schema);
        }
        return httpHosts;
    }

}
