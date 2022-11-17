package assignment.cowaysystem.feature.order.entity

import assignment.cowaysystem.feature.order.controller.dto.VisitServiceReq
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "VISIT")
class Visit {
    @Id
    @GeneratedValue
    @Column(name = "visit_id")
    var id: Long = 0

    @Embedded
    var address: Address? = null

    var visitTime: LocalDateTime? = null

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_item_id")
    var orderItem: OrderItem? = null

    /**
     * 연관관계 편의 메소드
     */
    fun addOrderItem(orderItem: OrderItem){
        this.orderItem = orderItem
        orderItem.visit = this
    }

    fun createVisit(
            visitServiceReq: VisitServiceReq,
            orderItem: OrderItem
    ): Visit{
        val visit = Visit().also {
            it.address?.city = visitServiceReq.city
            it.address?.street = visitServiceReq.street
            it.address?.zipcode = visitServiceReq.zipcode
            it.visitTime = visitServiceReq.visitTime
        }
        visit.addOrderItem(orderItem)
        return visit
    }
}