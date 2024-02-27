package io.biker.management.openApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SecurityScheme(name = "Authorization", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@OpenAPIDefinition(info = @Info(title = "Biker Management", description = "OpenAPI documentation for biker management project."
                + "\n\n Authorization is done through Jwt bearer tokens."
                + "\n\n NOTE: Admins have the same authorization as back office users.", version = "1.0"), servers = @Server(url = "http://localhost:8080", description = "Dev ENV"))
public class OpenApiConfig {

}
