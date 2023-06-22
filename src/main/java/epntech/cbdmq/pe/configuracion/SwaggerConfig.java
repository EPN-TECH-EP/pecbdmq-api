package epntech.cbdmq.pe.configuracion;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.PathItem;

import static epntech.cbdmq.pe.constante.SeguridadConst.URLS_PUBLICAS;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))

                .paths(getPaths());
    }

    private Paths getPaths() {
        Paths paths = new Paths();

        // Añade todos los métodos públicos sin autenticación
        for (String url : URLS_PUBLICAS) {
            paths.addPathItem(url, new PathItem()
                            .get(new Operation()
                                    //Se utiliza SecurityRequirement con una instancia vacía para indicar que no se requiere ningún esquema de seguridad.
                                    .addSecurityItem(new SecurityRequirement())
                            )
                            .post(new Operation()
                                    .addSecurityItem(new SecurityRequirement())
                            )
                    //put,delete que no necesiten token
            );
        }

        return paths;
    }
}



