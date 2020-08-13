package com.fin.best.bestfin.api.config.adapter

import com.fin.best.bestfin.api.component.model.RequestBuffer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfigure : WebMvcConfigurer {
    @Value("\${temporary-file-path}")
    lateinit var temporaryFilePath: String

    @Bean(name = ["requestDataBuffer"])
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun requestDataVO(): RequestBuffer = RequestBuffer()

    @Bean(name = ["interceptor"])
    fun applicationInterceptor() = ApplicationInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(applicationInterceptor())
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedOrigins("*")
    }

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON)
    }
}