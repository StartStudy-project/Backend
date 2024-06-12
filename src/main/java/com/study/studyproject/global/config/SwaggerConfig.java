package com.study.studyproject.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import java.util.Arrays;

@Component
public class SwaggerConfig implements WebMvcOpenApiTransformationFilter {


    @Override
    public boolean supports(DocumentationType documentationType) {
        return documentationType.equals(DocumentationType.OAS_30);
    }

    @Override
    public OpenAPI transform(OpenApiTransformationContext context) {
        OpenAPI openApi = context.getSpecification();
        Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8080");

        Server testServer = new Server();
        testServer.setDescription("test");
        testServer.setUrl("http://ec2-15-165-250-19.ap-northeast-2.compute.amazonaws.com:8080/");
        openApi.setServers(Arrays.asList(localServer, testServer));
        return openApi;
    }
}
