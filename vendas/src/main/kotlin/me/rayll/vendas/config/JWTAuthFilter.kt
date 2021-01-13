package me.rayll.vendas.config

import me.rayll.vendas.security.jwt.JWTService
import me.rayll.vendas.service.impl.UsuarioServiceImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthFilter(var jwtService: JWTService, var usuarioService: UsuarioServiceImpl) : OncePerRequestFilter() {


    override fun doFilterInternal(p0: HttpServletRequest, p1: HttpServletResponse, p2: FilterChain) {
        var authorization = p0.getHeader("Authorization")

        if(authorization != null && authorization.startsWith("Bearer")){

            var token = authorization.split(" ")[1]
            var isValid = jwtService.tokenValido(token)

            if(isValid){
                var loginUsuario = jwtService.obterLoginUsuario(token)
                var usuario = usuarioService.loadUserByUsername(loginUsuario)
                var user = UsernamePasswordAuthenticationToken(usuario, null, usuario.authorities)
                user.details = WebAuthenticationDetailsSource().buildDetails(p0)
                SecurityContextHolder.getContext().authentication = user
            }
        }
        p2.doFilter(p0, p1)
    }
}