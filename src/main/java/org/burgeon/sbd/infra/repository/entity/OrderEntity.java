package org.burgeon.sbd.infra.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
@Entity
@Table(name = "t_order")
@EqualsAndHashCode(callSuper = false)
public class OrderEntity extends BaseModel {

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
