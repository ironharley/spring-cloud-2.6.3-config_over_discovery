package com.example.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

}

/**
 routes:
 - id: test-rest-route
 uri: http://localhost:28888
 predicates:
 - Path=/test-rest/{segment}
 - id: cloud-config-route
 uri: http://localhost:18888
 predicates:
 - Path=/config/{segment}
 - id: eureka-route
 uri: http://localhost:8761
 predicates:
 - Path=/discovery

 */
