package com.starling.roundup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RESTConfiguration
{

    @Bean
    public RestTemplate createRestTemplate()
    {
        return new RestTemplate();
    }

}
