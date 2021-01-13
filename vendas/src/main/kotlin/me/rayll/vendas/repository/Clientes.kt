package me.rayll.vendas.repository

import me.rayll.vendas.entity.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface Clientes : JpaRepository<Cliente, Int> {

    @Query(value = "select c from Cliente c where c.nome like :nome")
    fun encontrarPorNome(@Param("nome") nome: String): List<Cliente>
    fun findByNomeOrIdOrderById(nome: String, id: Int): List<Cliente>
    fun existsByNome(nome: String): Boolean

    @Query("select c from Cliente c left join fetch c.pedidos p where c = :id")
    fun findClienteFetchPedidos(@Param("id") id: Int): Cliente

}