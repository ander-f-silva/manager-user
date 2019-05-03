package br.com.pp.user.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.config.EnableWebFlux


@Configuration
@EnableWebFlux
class CorsConfig : WebFluxConfigurer {
    override fun addCorsMappings(corsRegistry: CorsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost")
                .allowedMethods("GET", "OPTION")
                .maxAge(3600)
    }
}