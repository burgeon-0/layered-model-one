package org.burgeon.sbd.infra.repository.entity;

import org.burgeon.sbd.core.base.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Entity(name = "order_item")
public class OrderItemEntity extends Base {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "product_no")
    private String productNo;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "total_count")
    private Integer totalCount;

    @Column(name = "total_price")
    private Integer totalPrice;

}
