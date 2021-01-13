package me.rayll.vendas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@RestController
class VendasApplication: SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<VendasApplication>(*args)
}
