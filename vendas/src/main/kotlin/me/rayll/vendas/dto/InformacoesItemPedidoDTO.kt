package me.rayll.vendas.dto

import java.math.BigDecimal

data class InformacoesItemPedidoDTO(
        var descricao: String? = null,
        var precoUnitario: BigDecimal? = null,
        var quantidade: Int? = null
)