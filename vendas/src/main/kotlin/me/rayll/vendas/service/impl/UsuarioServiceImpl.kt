package me.rayll.vendas.service.impl

import me.rayll.vendas.entity.Usuario
import me.rayll.vendas.exception.SenhaInvalidaException
import me.rayll.vendas.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsuarioServiceImpl : UserDetailsService {

    @Autowired
    lateinit var encoder: BCryptPasswordEncoder

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Transactional
    fun salvar(usuario: Usuario): Usuario{
        return usuarioRepository.save(usuario)
    }

    fun autenticar(usuario: Usuario): UserDetails{
        var userDetails = loadUserByUsername(usuario.login)
        var validarSenha = encoder.matches(usuario.senha, userDetails.password)
        if(validarSenha) {
            return userDetails
        }
        throw  SenhaInvalidaException()
    }

    override fun loadUserByUsername(username: String): UserDetails {

        var usuario =
            usuarioRepository.findByLogin(username)

        var roles = if (usuario!!.admin) arrayOf("USER", "ADMIN") else arrayOf("USER")
        return User.builder()
            .username(usuario!!.login)
            .password(usuario!!.senha)
            .authorities(*roles)
            .build()

    }

}