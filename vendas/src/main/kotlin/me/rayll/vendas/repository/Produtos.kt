package me.rayll.vendas.repository

import me.rayll.vendas.entity.Produto
import org.springframework.data.jpa.repository.JpaRepository

interface Produtos : JpaRepository<Produto, Int> {
}