package me.rayll.vendas.repository

import me.rayll.vendas.entity.Cliente
import me.rayll.vendas.entity.Pedido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface Pedidos : JpaRepository<Pedido, Int> {

    @Query("select p from Pedido p left join fetch p.cliente where p.cliente = :cliente")
    fun buscarPorClientes(@Param("cliente") cliente: Cliente): List<Pedido>

    @Query("select p from Pedido p left join fetch p.itens where p.id = :id")
    fun findByIdFetchItens (@Param("id") id: Int) : Optional<Pedido>
}