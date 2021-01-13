package me.rayll.vendas.config

import me.rayll.vendas.security.jwt.JWTService
import me.rayll.vendas.service.impl.UsuarioServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter(){

    @Autowired
    lateinit var usuarioServiceImpl : UsuarioServiceImpl

    @Autowired
    lateinit var jwtService: JWTService

    @Bean
    fun jwtFilter(): OncePerRequestFilter = JWTAuthFilter(jwtService, usuarioServiceImpl)


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(usuarioServiceImpl).passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/clientes/**")
            .hasAuthority("USER")
            .antMatchers("/api/produtos/**")
            .hasRole("ADMIN")
            .antMatchers("/api/pedidos/**")
            .hasRole("USER")
            .antMatchers(HttpMethod.POST, "/api/usuarios/**")
            .permitAll()
//            .antMatchers(*matchers())
//            .permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/v2/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/favicon.ico"
        )
    }

    private fun matchers() = arrayOf("/v2/api-docs",
        "/configuration/ui",
        "/swagger/resources/**",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/favicon.ico")

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}