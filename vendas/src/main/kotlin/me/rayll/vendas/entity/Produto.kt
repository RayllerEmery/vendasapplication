package me.rayll.vendas.entity

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "produto")
data class Produto(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Int? = null,

        @Column(name = "descricao")
        @field:NotEmpty(message = "{campo.descricao.obrigatorio}")
        var descricao: String? = null,

        @Column(name = "preco_unitario")
        @field:NotNull(message = "{campo.preco.obrigatorio}")
        var preco: BigDecimal? = null
)
