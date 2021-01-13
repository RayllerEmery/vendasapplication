package me.rayll.vendas.repository

import me.rayll.vendas.entity.ItemPedido
import org.springframework.data.jpa.repository.JpaRepository

interface ItemPedidos : JpaRepository<ItemPedido, Int> {
}