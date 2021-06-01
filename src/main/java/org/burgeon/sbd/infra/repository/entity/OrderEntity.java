package org.burgeon.sbd.infra.repository.entity;

import org.burgeon.sbd.core.base.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Entity(name = "order")
public class OrderEntity extends Base {

    @Id
    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "place_time")
    private Date placeTime;

    @Column(name = "pay_time")
    private Date payTime;

    @Column(name = "cancel_time")
    private Date cancelTime;

    @Column(name = "delete_time")
    private Date deleteTime;

    @Column(name = "status")
    private Integer status;

}
