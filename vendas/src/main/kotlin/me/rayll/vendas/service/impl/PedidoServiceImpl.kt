package me.rayll.vendas.service.impl

import me.rayll.vendas.dto.AtualizacaoStatusPedidoDTO
import me.rayll.vendas.dto.ItemPedidoDTO
import me.rayll.vendas.dto.PedidoDTO
import me.rayll.vendas.entity.ItemPedido
import me.rayll.vendas.entity.Pedido
import me.rayll.vendas.enum.StatusPedido
import me.rayll.vendas.exception.PedidoNaoEncontradoException
import me.rayll.vendas.exception.RegraNegocioException
import me.rayll.vendas.repository.Clientes
import me.rayll.vendas.repository.ItemPedidos
import me.rayll.vendas.repository.Pedidos
import me.rayll.vendas.repository.Produtos
import me.rayll.vendas.service.PedidoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDate
import java.util.*

@Service
class PedidoServiceImpl(
        @Autowired private val pedidosRepository: Pedidos,
        @Autowired private val clienteRepository: Clientes,
        @Autowired private val produtosRepository: Produtos,
        @Autowired private val itensPedidoRepository: ItemPedidos) : PedidoService{

    @Transactional
    override fun salvar(dto: PedidoDTO): Pedido {
        var cliente = clienteRepository.findById(dto.cliente!!).orElseThrow{
            throw RegraNegocioException("Cliente não existe.")
        }
        var pedido1 = Pedido().apply {
            total = dto.total
            dataPedido = LocalDate.now()
            this.cliente = cliente
            status = StatusPedido.REALIZADO
        }

        var itensPedidos = converterItens(pedido1, dto.itens!!)
        pedidosRepository.save(pedido1)
        itensPedidoRepository.saveAll(itensPedidos)
        pedido1.itens = itensPedidos
        return pedido1
    }

    override fun obterPedidoCompleto(idPedido: Int): Optional<Pedido> {
        var pedido = pedidosRepository.findByIdFetchItens(idPedido)
        return pedido
    }

    @Transactional
    override fun atualizaStatus(id: Int, statusPedido: StatusPedido) {
        var p1 = pedidosRepository.findById(id).orElseThrow {
            throw PedidoNaoEncontradoException("Pedido não existe.")
        }
        p1.status = statusPedido
        pedidosRepository.save(p1)
    }

    private fun converterItens(pedido: Pedido, listDto: List<ItemPedidoDTO>): List<ItemPedido>{
        if(listDto.isEmpty())
            throw RegraNegocioException("Não é possível realizar um pedido sem itens")

        return listDto.map {
            var p1 = produtosRepository.findById(it.produto!!).orElseThrow {
                throw RegraNegocioException("Codigo de produto inválido ${it.produto}")
            }

            ItemPedido().also {iP ->
                iP.quantidade = it.quantidade
                iP.pedido = pedido
                iP.produto = p1
                
            }

        }.toMutableList()
    }

}