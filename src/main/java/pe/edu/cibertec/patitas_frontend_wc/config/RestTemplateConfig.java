package pe.edu.cibertec.patitas_frontend_wc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    //DEFINIR Bean
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder
                .rootUri("http://localhost:8081/autenticacion")
                .setConnectTimeout(Duration.ofSeconds(10))//TIMEOUT DE ESTABLECIMIENTO ESPERA DE CONEXIÓN
                .setReadTimeout(Duration.ofSeconds(10))//TIMEOUT DE LECTURA MAXIMO DE LA RESPUESTA
                .build();
    }







}
