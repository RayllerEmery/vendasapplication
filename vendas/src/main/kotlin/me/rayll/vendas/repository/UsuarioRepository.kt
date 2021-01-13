package me.rayll.vendas.repository

import me.rayll.vendas.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UsuarioRepository : JpaRepository<Usuario?, Int> {

    fun findByLogin(login: String): Usuario?

}