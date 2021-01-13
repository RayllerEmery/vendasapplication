package me.rayll.vendas.entity

import me.rayll.vendas.enum.StatusPedido
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "pedido")
data class Pedido(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "cliente")
        var cliente: Cliente? = null,

        @Column(name = "data_pedido")
        var dataPedido: LocalDate? = null,

        @Column(name = "total", precision = 20, scale = 2)
        var total: BigDecimal? = null,

        @Enumerated(EnumType.STRING)
        @Column(name = "string")
        var status: StatusPedido? = null,

        @OneToMany(mappedBy = "pedido")
        var itens: List<ItemPedido> = arrayListOf()
)