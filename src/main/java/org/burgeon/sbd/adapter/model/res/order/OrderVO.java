package org.burgeon.sbd.adapter.model.res.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.OrderBase;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderVO extends OrderBase {

    private String orderNo;

}
