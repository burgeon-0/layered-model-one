package org.burgeon.sbd.adapter.model.res.product;

import lombok.Data;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class ProductVO {

    private String productNo;
    private String name;
    private Integer price;
    private Integer stock;

}
