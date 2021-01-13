package me.rayll.vendas.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.br.CPF
import org.springframework.beans.factory.annotation.Value
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "cliente")
data class Cliente(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @field:NotEmpty(message = "{campo.nome.obrigatorio}")
    var nome: String? = null,

    @Column(name = "cpf")
    @field:NotEmpty(message = "{campo.cpf.obrigatorio}")
    @get:CPF(message = "{campo.cpf.invalido}")
    var cpf: String? = null
) {


    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    var pedidos: Set<Pedido>? = null


}

