package me.rayll.vendas.dto

import me.rayll.vendas.validation.NotEmptyList
import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class PedidoDTO (

        @field:NotNull(message = "{campo.codigo-cliente.obrigatorio}")
        var cliente: Int? = null,

        @field:NotNull(message = "{campo.total-pedido.obrigatorio}")
        var total: BigDecimal? = null,

        @field:NotEmptyList(message = "{campo.itens-pedido.obrigatorio}")
        var itens: List<ItemPedidoDTO>? = arrayListOf()
){
}