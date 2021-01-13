package me.rayll.vendas.entity

import javax.persistence.*

@Entity
@Table(name = "item_pedido")
data class ItemPedido(
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "pedido_id")
        var pedido: Pedido? = null,

        @ManyToOne
        @JoinColumn(name = "produto_id")
        var produto: Produto? = null,

        @Column
        var quantidade: Int? = null
)