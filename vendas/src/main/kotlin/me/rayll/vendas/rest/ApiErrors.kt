package me.rayll.vendas.rest

class ApiErrors(var errors: List<String?> = emptyList()) {
    constructor(mensagemErro: String) : this(listOf(mensagemErro))
}