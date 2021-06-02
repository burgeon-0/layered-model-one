package org.burgeon.sbd.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sam Lu
 * @date 2021/6/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductBaseModel extends BaseModel {

    private String productName;
    private Integer price;
    private Integer stock;

}
