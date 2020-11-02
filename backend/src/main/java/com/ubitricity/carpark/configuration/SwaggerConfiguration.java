package com.ubitricity.carpark.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Carpark Service")
                .description("This is the ubitricity carpark service API")
                .license("")
                .licenseUrl("http://ubitricity.com")
                .termsOfServiceUrl("")
                .version("0.3.0")
                .contact(new Contact("", "", ""))
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ubitricity.carpark.api"))
                .build()
                .apiInfo(apiInfo());
    }
}
