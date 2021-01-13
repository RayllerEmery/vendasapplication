package me.rayll.vendas.controller

import antlr.Token
import me.rayll.vendas.dto.CredenciaisDTO
import me.rayll.vendas.dto.TokenDTO
import me.rayll.vendas.entity.Usuario
import me.rayll.vendas.exception.SenhaInvalidaException
import me.rayll.vendas.security.jwt.JWTService
import me.rayll.vendas.service.impl.UsuarioServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(
    @Autowired
    var usuarioServiceImpl: UsuarioServiceImpl,
    @Autowired
    private var passwordEncoder: PasswordEncoder,
    @Autowired
    var jwtService: JWTService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun salvar(@RequestBody @Valid usuario: Usuario): Usuario{
        println()
        usuario.senha = passwordEncoder.encode(usuario.senha)
        return usuarioServiceImpl.salvar(usuario)
    }

    @PostMapping("/auth")
    fun autenticar(@RequestBody credenciaisDTO: CredenciaisDTO): TokenDTO{
        try {
            var usuario = Usuario().apply {
                this.login= credenciaisDTO.login!!
                this.senha = credenciaisDTO.senha!!
            }
            var usuarioAutenticado = usuarioServiceImpl.autenticar(usuario)
            var token = jwtService.gerarToken(usuario)
            return TokenDTO(usuario.login, token)

        }catch (ex: UsernameNotFoundException){
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.message)
        }catch (ex: SenhaInvalidaException){
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.message)
        }
    }
}