package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EHS系统API文档")
                        .version("1.0")
                        .description("环境健康安全(EHS)系统的API接口文档，包含报警管理和WebSocket实时通信接口。")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}