package org.bg181.sbd.infra.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bg181.sbd.core.base.BaseModel;

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
@Table(name = "t_product")
@EqualsAndHashCode(callSuper = false)
public class ProductEntity extends BaseModel {

    @Id
    @Column(name = "product_no")
    private String productNo;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "deleted")
    private Boolean deleted;

}
