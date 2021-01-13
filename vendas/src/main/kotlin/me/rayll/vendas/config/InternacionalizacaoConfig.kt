package me.rayll.vendas.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import java.util.*

@Configuration
@ComponentScan
class InternacionalizacaoConfig {

    @Bean
    fun messageSource(): MessageSource{
        var messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages")
        //messageSource.setBasenames("classpath:messages")
        messageSource.setDefaultEncoding("ISO-8859-1")
        messageSource.setDefaultLocale(Locale.getDefault())
        return messageSource
    }

    @Bean
    fun localValidatorBean(): LocalValidatorFactoryBean{
        var localBean = LocalValidatorFactoryBean()
        localBean.setValidationMessageSource(messageSource())
        return localBean
    }
}