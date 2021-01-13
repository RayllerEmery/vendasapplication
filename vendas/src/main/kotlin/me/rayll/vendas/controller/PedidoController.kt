package me.rayll.vendas.controller

import me.rayll.vendas.dto.AtualizacaoStatusPedidoDTO
import me.rayll.vendas.entity.ItemPedido
import me.rayll.vendas.dto.PedidoDTO
import me.rayll.vendas.entity.Pedido
import me.rayll.vendas.dto.InformacoesItemPedidoDTO
import me.rayll.vendas.dto.InformacoesPedidoDTO
import me.rayll.vendas.enum.StatusPedido
import me.rayll.vendas.service.PedidoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.format.DateTimeFormatter
import javax.validation.Valid

@RestController
@RequestMapping("/api/pedidos")
class PedidoController{

    private var pedidoService: PedidoService

    constructor(pedidoService: PedidoService){
        this.pedidoService = pedidoService
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody @Valid dto: PedidoDTO): Int{
        var pedido: Pedido = pedidoService.salvar(dto)
        pedido?.let{
            return it.id!!
        }
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: Int): InformacoesPedidoDTO{
        var pedido = pedidoService.obterPedidoCompleto(id).orElseThrow{
            ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado.")
        }
        return converterPedido(pedido)
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateStatus(@PathVariable id: Int, @RequestBody @Valid statusDto: AtualizacaoStatusPedidoDTO){
        val status = statusDto.novoStatus
        println(status)
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(status!!))
    }

    private fun converterPedido(pedido: Pedido): InformacoesPedidoDTO{

        return InformacoesPedidoDTO().apply {
            codigo = pedido.id
            dataPedido = pedido.dataPedido?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            cpf = pedido.cliente?.cpf
            nomeCliente = pedido.cliente?.nome
            total = pedido.total
            status = pedido.status.toString()
            itens = converterItemPedido(pedido.itens)
        }
    }

    private fun converterItemPedido(itens: List<ItemPedido>): List<InformacoesItemPedidoDTO>{

        if(itens.isEmpty()) return emptyList()

        return itens.map { iten ->
            InformacoesItemPedidoDTO().apply {
                descricao = iten.produto?.descricao
                precoUnitario = iten.produto?.preco
                quantidade = iten.quantidade
            }
        }
    }
}