package me.rayll.vendas.dto

import java.math.BigDecimal

data class InformacoesPedidoDTO (
        var codigo: Int? = null,
        var cpf: String? = null,
        var nomeCliente: String? = null,
        var total: BigDecimal? = null,
        var dataPedido: String? = null,
        var status: String? = null,
        var itens: List<InformacoesItemPedidoDTO>? = arrayListOf()
)