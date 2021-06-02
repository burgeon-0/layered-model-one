package org.burgeon.sbd.app.model.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.OrderBaseModel;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDTO extends OrderBaseModel {

    private String orderNo;

}
