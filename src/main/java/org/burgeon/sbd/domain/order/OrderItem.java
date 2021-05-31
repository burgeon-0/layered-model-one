package org.burgeon.sbd.domain.order;

import lombok.Data;
import org.burgeon.sbd.domain.MagicObject;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
public class OrderItem extends MagicObject {

    private String orderNo;
    private String productNo;
    private String productName;
    private Integer totalCount;
    private Integer totalPrice;

}
