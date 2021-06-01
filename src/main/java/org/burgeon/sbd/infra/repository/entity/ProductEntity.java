package org.burgeon.sbd.infra.repository.entity;

import org.burgeon.sbd.core.base.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Entity(name = "product")
public class ProductEntity extends Base {

    @Id
    @Column(name = "product_no")
    private String productNo;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stock")
    private Integer stock;

}
