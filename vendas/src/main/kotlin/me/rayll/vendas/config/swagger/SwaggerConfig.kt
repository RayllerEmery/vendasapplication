package me.rayll.vendas.config.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun docket() = Docket(DocumentationType.SWAGGER_2)
        .host("localhost:8080")
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("me.rayll.vendas.controller"))
        .paths(PathSelectors.any())
        .build()
        .securityContexts(arrayListOf(securityContext()))
        .securitySchemes(arrayListOf(apiKey()))
        .apiInfo(apiInfo())


    private fun contact() = springfox.documentation.service.Contact(
        "Rayller Emery",
        "",
        "rayller.emery@gmail.com")

    private fun apiInfo() =
        ApiInfoBuilder().title("Vendas API")
            .description("Api construida no curso de Spring")
            .version("1.0")
            .contact(contact())
            .build()


    private fun apiKey() = ApiKey("JWT", "Authorization","header")

    private fun securityContext() =
        SecurityContext.builder()
        .securityReferences(defaultAuth())
            .forPaths(PathSelectors.any())
            .build()

    private fun defaultAuth(): List<SecurityReference>{
        var authorizationScope = AuthorizationScope("global", "accessEverithing")
        var scope = arrayOf<AuthorizationScope>()
        var reference = SecurityReference("JWT", scope)
        var auths = arrayListOf<SecurityReference>()
        auths.add(reference)

        return auths
    }
}