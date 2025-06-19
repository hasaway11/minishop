package com.example.demo;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    // http://localhost:8080/swagger-ui/index.html
    Info info = new Info().title("미니샵 문서화").description("MINISHOP API 레퍼런스 문서화").version(("1.0"));
    return new OpenAPI().components(new Components()).info(info);
  }
}
