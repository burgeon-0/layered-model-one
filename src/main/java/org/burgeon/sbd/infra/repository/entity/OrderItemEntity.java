package org.burgeon.sbd.infra.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
@Entity
@Table(name = "t_order_item")
@EqualsAndHashCode(callSuper = false)
public class OrderItemEntity extends BaseModel {

    @Id
    @Column(name = "id")
    private Long id;

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
