package me.rayll.vendas.controller

import me.rayll.vendas.dto.AtualizacaoStatusPedidoDTO
import me.rayll.vendas.entity.Produto
import me.rayll.vendas.repository.Produtos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid


@RestController
@RequestMapping("/api/produtos")
class ProdutoController(@Autowired var produtoRepository: Produtos) {

    @GetMapping("{id}")
    fun getProdutoById(@PathVariable id: Int): Produto? {

        return produtoRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
        }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveProduto(@RequestBody @Valid produto: Produto): Produto {

        return produtoRepository.save(produto)

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduto(@PathVariable id: Int) {
        produtoRepository.findById(id).map { t ->
            produtoRepository.delete(t)
        }.orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
        }

    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun atualizarProduto(@PathVariable id: Int, @RequestBody @Valid produto: Produto) {
        produtoRepository.findById(id).map {
            produto.id = it.id
            produtoRepository.save(produto)
        }.orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")
        }
    }

    @GetMapping
    fun find(filtro: Produto): List<Produto> {
        var matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)

        var example = Example.of(filtro, matcher)
        return produtoRepository.findAll(example)
    }


}