package api.dashboard.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

  @Bean
  public OpenAPI customerOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("Dashboard Oficina")
        .description("API para a dashboard do sistema para a oficina mecânica")
        .version("1.0.0")
        .contact(new Contact()
          .name("Matheus Simeão dos Reis")
          .email("maatsimeao@gmail.com")
          .url("https://github.com/msimeaor")
        )
      );
  }

}
