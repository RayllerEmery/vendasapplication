package me.rayll.vendas.service

import me.rayll.vendas.dto.PedidoDTO
import me.rayll.vendas.entity.Pedido
import me.rayll.vendas.enum.StatusPedido
import java.util.*

interface PedidoService {

    fun salvar(dto: PedidoDTO): Pedido
    fun obterPedidoCompleto(idPedido: Int): Optional<Pedido>
    fun atualizaStatus(id: Int, statusPedido: StatusPedido)
}