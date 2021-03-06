package org.bg181.sbd.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderItem extends BaseModel {

    private String orderNo;
    private String productNo;
    private String productName;
    private Integer totalCount;
    private Integer totalPrice;

}
