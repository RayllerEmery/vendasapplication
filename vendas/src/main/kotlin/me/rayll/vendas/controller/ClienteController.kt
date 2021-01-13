package me.rayll.vendas.controller

import me.rayll.vendas.entity.Cliente
import me.rayll.vendas.repository.Clientes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("/api/clientes")
class ClienteController(@Autowired var clienteRepository: Clientes) {

    @GetMapping("{id}")
    fun getClienteById(@PathVariable id: Int): Cliente {

        return clienteRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")
        }

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveCliente(@RequestBody @Valid cliente: Cliente): Cliente {
        return clienteRepository.save(cliente)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCliente(@PathVariable id: Int) {
        clienteRepository.findById(id).map { t ->
            clienteRepository.delete(t)
        }.orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")
        }

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun atualizarCliente(@PathVariable id: Int, @RequestBody @Valid cliente: Cliente) {
        clienteRepository.findById(id).map {
            cliente.id = it.id
            clienteRepository.save(cliente)
        }.orElseThrow{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")
        }
    }

    @GetMapping
    fun find(filtro: Cliente): List<Cliente> {
        var matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)

        var example = Example.of(filtro, matcher)
        println(filtro)
        return clienteRepository.findAll(example)
    }
}