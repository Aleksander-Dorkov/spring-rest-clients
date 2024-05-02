package com.example.feign.integration.restclient;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class RestClientConfiguration {

    private static final MappingJackson2XmlHttpMessageConverter XML_HTTP_MESSAGE_CONVERTER = new MappingJackson2XmlHttpMessageConverter();

    @Resource(name = "custom_timeout_factory")
    private BufferingClientHttpRequestFactory timeoutFactory;
    @Resource(name = "custom_logging_interceptor")
    private ClientHttpRequestInterceptor loggingInterceptor;

    @Bean(name = "github_rest_client")
    public RestClient githubRestClient() {
        return restClientBuilder("https://api.github.com").build();
    }

    @Bean(name = "soap_client")
    public RestClient secondRestClient() {
        return restClientBuilder("https://www.dataaccess.com/webservicesserver/NumberConversion.wso")
                .defaultHeaders(defaultSoapHeaders())
                .build();
    }

    private RestClient.Builder restClientBuilder(String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(timeoutFactory)
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(XML_HTTP_MESSAGE_CONVERTER))
                .requestInterceptor(loggingInterceptor);
    }

    private Consumer<HttpHeaders> defaultSoapHeaders() {
        return headers -> {
            headers.setContentType(MediaType.TEXT_XML);
            headers.setAccept(List.of(MediaType.TEXT_XML, MediaType.APPLICATION_XML));
        };
    }
}
