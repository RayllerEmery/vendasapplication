package me.rayll.vendas.entity

import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name ="usuario")
data class Usuario (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column
    @field:NotEmpty(message = "{campo.login.obrigatorio}")
    var login: String = "",

    @Column
    @field:NotEmpty(message = "{campo.senha.obrigatorio}")
    var senha: String = "",

    @Column
    var admin: Boolean = false

) {

}